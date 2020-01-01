package com.savannah.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.MyPage;
import com.savannah.controller.vo.OrderVO;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.OrderService;
import com.savannah.service.PromoService;
import com.savannah.service.model.OrderDTO;
import com.savannah.service.model.UserDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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

    private final PromoService promoService;
    private final HttpServletRequest httpServletRequest;
    private final OrderService orderService;

    public OrderController(HttpServletRequest httpServletRequest, OrderService orderService, PromoService promoService) {
        this.httpServletRequest = httpServletRequest;
        this.orderService = orderService;
        this.promoService = promoService;
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
        orderService.createOrder(orderDTO);
        
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
