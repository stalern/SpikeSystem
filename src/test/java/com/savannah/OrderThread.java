package com.savannah;

import com.savannah.error.ReturnException;
import com.savannah.service.OrderService;
import com.savannah.service.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author stalern
 * @date 2020/01/02~08:22
 */
@Component
public class OrderThread extends Thread{

    @Autowired
    private OrderService orderService;

    @Override
    public void run() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(20);
        orderDTO.setAmount(1);
        orderDTO.setPromoId(6);
        orderDTO.setItemPrice(new BigDecimal("6.6"));
        try {
            orderService.createOrder(orderDTO);
        } catch (ReturnException e) {
            e.printStackTrace();
        }
    }
}
