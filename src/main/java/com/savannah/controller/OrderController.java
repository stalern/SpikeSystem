package com.savannah.controller;

import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.MyPage;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.OrderService;
import com.savannah.service.model.OrderDTO;
import com.savannah.service.model.UserDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 下单即购买，没有取消订单
 * @author stalern
 * @date 2019/12/17~19:56
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class OrderController {

    private final HttpServletRequest httpServletRequest;
    private final OrderService orderService;

    public OrderController(HttpServletRequest httpServletRequest, OrderService orderService) {
        this.httpServletRequest = httpServletRequest;
        this.orderService = orderService;
    }

    /**
     * 下订单=购买
     * @param orderDTO 订单model
     * @return 成功ok
     */
    @PostMapping("/buy")
    @Auth(Group.BUYER)
    public ReturnType postOrder(@RequestBody OrderDTO orderDTO) throws ReturnException {
        if (httpServletRequest.getSession().getAttribute(Constant.IS_LOGIN) == null || !(Boolean)httpServletRequest.getSession().getAttribute(Constant.IS_LOGIN)) {
            throw new ReturnException(EmReturnError.USER_NOT_LOGIN);
        } else {
            UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute(Constant.LOGIN_USER);
            orderDTO.setUserId(userDTO.getId());
            orderService.createOrder(orderDTO);
        }
        return ReturnType.create();
    }

    /**
     * 列出一个用户的订单
     * @return 订单list
     */
    @GetMapping("/listByUser")
    @Auth(Group.BUYER)
    public ReturnType listOrderByUser(MyPage myPage) throws ReturnException {
        if (httpServletRequest.getSession().getAttribute(Constant.IS_LOGIN) == null || !(Boolean)httpServletRequest.getSession().getAttribute(Constant.IS_LOGIN)) {
            throw new ReturnException(EmReturnError.USER_NOT_LOGIN);
        }
        UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute(Constant.LOGIN_USER);
        return ReturnType.create(new PageInfo<>(orderService.listOrderByUser(userDTO.getId(),myPage)));
    }

}
