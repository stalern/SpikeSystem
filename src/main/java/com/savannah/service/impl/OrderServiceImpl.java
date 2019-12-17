package com.savannah.service.impl;

import com.savannah.dao.OrderInfoMapper;
import com.savannah.dataobject.OrderInfoDO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.service.ItemService;
import com.savannah.service.OrderService;
import com.savannah.service.UserService;
import com.savannah.service.model.ItemDTO;
import com.savannah.service.model.OrderDTO;
import com.savannah.service.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author stalern
 * @date 2019/12/17~20:12
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ItemService itemService;
    private final OrderInfoMapper orderInfoMapper;

    public OrderServiceImpl(UserService userService, ItemService itemService, OrderInfoMapper orderInfoMapper) {
        this.userService = userService;
        this.itemService = itemService;
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    @Transactional(rollbackFor = ReturnException.class)
    public void createOrder(OrderDTO orderDTO) throws ReturnException {
        // 校验下单商品是否存在
        ItemDTO itemDTO = itemService.getItemById(orderDTO.getItemId());
        if (itemDTO == null) {
            throw new ReturnException(EmReturnError.ITEM_NOT_EXIT);
        }
        // 校验用户是否存在
        UserDTO userDTO = userService.getUserById(orderDTO.getUserId());
        if (userDTO == null) {
            throw new ReturnException(EmReturnError.USER_NOT_EXIST);
        }
        // 如果商品参加活动，则校验活动id是否适合该商品
        if (orderDTO.getPromoId() != null && orderDTO.getPromoId() != -1) {
            if (itemDTO.getPromoId() == null || !orderDTO.getPromoId().equals(itemDTO.getPromoId())) {
                throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            } else {
                orderDTO.setItemPrice(itemDTO.getPrice());
            }
        }
        // 减少库存
        if (!itemService.decreaseStock(orderDTO.getItemId(),orderDTO.getAmount())){
            throw new ReturnException(EmReturnError.STOCK_NOT_ENOUGH);
        }
        // 订单入库
        OrderInfoDO orderInfoDO = convertDoFromDto(orderDTO);
        orderInfoMapper.insertSelective(orderInfoDO);
        // 增加销量，其中订单号通过触发器生成
        if (!itemService.increaseSales(orderDTO.getItemId(),orderDTO.getAmount())){
            throw new ReturnException(EmReturnError.ITEM_NOT_EXIT,"销量增加错误");
        }
    }

    private OrderInfoDO convertDoFromDto(OrderDTO orderDTO) {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        BeanUtils.copyProperties(orderDTO, orderInfoDO);
        orderInfoDO.setOrderPrice(orderDTO.getItemPrice().multiply(BigDecimal.valueOf(orderDTO.getAmount())));
        return orderInfoDO;
    }
}
