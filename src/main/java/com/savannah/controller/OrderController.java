package com.savannah.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.MyPage;
import com.savannah.controller.vo.OrderVO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.mq.MqProducer;
import com.savannah.response.ReturnType;
import com.savannah.service.OrderService;
import com.savannah.service.PromoService;
import com.savannah.service.model.OrderDTO;
import com.savannah.service.model.UserDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 下单即购买，没有取消订单
 * @author stalern
 * @date 2019/12/17~19:56
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class OrderController {

    @Resource
    RedisTemplate<String,Boolean> redisItemInvalid;
    private final PromoService promoService;
    private final HttpServletRequest httpServletRequest;
    private final OrderService orderService;
    private final MqProducer mqProducer;

    public OrderController(HttpServletRequest httpServletRequest, OrderService orderService,
                           PromoService promoService, MqProducer mqProducer) {
        this.httpServletRequest = httpServletRequest;
        this.orderService = orderService;
        this.promoService = promoService;
        this.mqProducer = mqProducer;
    }

    /**
     * 下订单=购买
     * @param orderDTO 订单model
     * @return 成功ok
     */
    @PostMapping("/buy")
    @Auth(Group.BUYER)
    public ReturnType postOrder(@RequestBody OrderDTO orderDTO) throws ReturnException {
        
        UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute(httpServletRequest.getHeader(Constant.X_REAL_IP));
        orderDTO.setUserId(userDTO.getId());
        // 第六次优化，判断库存是否售罄，如果售罄，则直接返回下单失败
        if (redisItemInvalid.hasKey("item_stock_" + orderDTO.getItemId())) {
            throw new ReturnException(EmReturnError.STOCK_NOT_ENOUGH);
        }
//        第五次优化,先初始化库存流水。用于追踪异步构件库存的消息
        String orderLogId = orderService.initOrderLog(orderDTO.getItemId(),orderDTO.getAmount());
//        orderService.createOrder(orderDTO);
        if (!mqProducer.transactionAsyncReduceStock(orderDTO,orderLogId)) {
            throw new ReturnException(EmReturnError.UNKNOWN_ERROR, "下单失败");
        }
        return ReturnType.create();
    }

    /**
     * 列出一个用户的订单
     * @param myPage 页数
     * @return 订单list
     */
    @GetMapping("/listByUser")
    @Auth(Group.BUYER)
    public ReturnType listOrderByUser(MyPage myPage) {
        UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute(httpServletRequest.getHeader(Constant.X_REAL_IP));
        List<OrderVO> userVOList = new ArrayList<>();
        PageHelper.startPage(myPage.getPage(),myPage.getSize());
        orderService.listOrderByUser(userDTO.getId()).forEach(e-> userVOList.add(convertVoFromDTO(e)));
        return ReturnType.create(new PageInfo<>(userVOList));
    }

    private OrderVO convertVoFromDTO(OrderDTO orderDTO) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderDTO,orderVO);
        if (orderDTO.getPromoId() != null && orderDTO.getPromoId() != -1) {
            orderVO.setPromoName(promoService.getPromoById(orderDTO.getPromoId()).getPromoName());
        }
        return orderVO;
    }

}
