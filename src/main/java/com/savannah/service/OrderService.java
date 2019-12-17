package com.savannah.service;

import com.savannah.error.ReturnException;
import com.savannah.service.model.OrderDTO;

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
}
