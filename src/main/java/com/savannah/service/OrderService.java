package com.savannah.service;

import com.savannah.error.ReturnException;
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
     * @param orderLogId 流水订单id，第5次优化新增
     * @throws ReturnException 下单异常
     */
    void createOrder(OrderDTO orderDTO, String orderLogId) throws ReturnException;

    /**
     * 列出某个用户历史订单
     * @param id 用户id
     * @return 用户集合
     */
    List<OrderDTO> listOrderByUser(Integer id);

    /**
     * 初始化订单流水
     * @param itemId 商品id
     * @param amount 商品数量
     * @return String 库存流水号
     */
    String initOrderLog(Integer itemId, Integer amount);
}
