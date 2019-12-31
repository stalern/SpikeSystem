package com.savannah.service;

import com.savannah.controller.vo.MyPage;
import com.savannah.error.ReturnException;
import com.savannah.controller.vo.OrderVO;
import com.savannah.service.model.OrderDTO;

import java.util.List;

/**
 * @author stalern
 * @date 2019/12/17~20:06
 */
public interface OrderService {

    /**
     * 下单
     * @param orderDTO 下单信息
     * @throws ReturnException 下单异常
     */
    void createOrder(OrderDTO orderDTO) throws ReturnException;

    /**
     * 列出某个用户历史订单
     * @param id 用户id
     * @param myPage 分页
     * @return 用户集合
     */
    List<OrderDTO> listOrderByUser(Integer id, MyPage myPage);
}
