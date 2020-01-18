package com.savannah.service.impl;

import com.savannah.dao.OrderInfoMapper;
import com.savannah.dao.OrderLogMapper;
import com.savannah.entity.OrderInfoDO;
import com.savannah.entity.OrderLogDO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author stalern
 * @date 2019/12/17~20:12
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderLogMapper orderLogMapper;
    private final UserService userService;
    private final ItemService itemService;
    private final OrderInfoMapper orderInfoMapper;

    public OrderServiceImpl(UserService userService, ItemService itemService, OrderInfoMapper orderInfoMapper, OrderLogMapper orderLogMapper) {
        this.userService = userService;
        this.itemService = itemService;
        this.orderInfoMapper = orderInfoMapper;
        this.orderLogMapper = orderLogMapper;
    }

    @Override
    @Transactional(rollbackFor = ReturnException.class)
    public void createOrder(OrderDTO orderDTO, String orderLogId) throws ReturnException {
        // 校验下单商品是否存在
        ItemDTO itemDTO = itemService.getItemByIdInCache(orderDTO.getItemId()
//        itemService.getItemById(orderDTO.getItemId()
        );
        if (itemDTO == null) {
            throw new ReturnException(EmReturnError.ITEM_NOT_EXIT);
        }
        // 校验用户是否存在，改地方也需要放在缓存中
        UserDTO userDTO = userService.getUserById(orderDTO.getUserId());
        if (userDTO == null) {
            throw new ReturnException(EmReturnError.USER_NOT_EXIST);
        }
        // 如果商品参加活动，则校验活动id是否适合该商品
        if (orderDTO.getPromoId() != null && orderDTO.getPromoId() != -1) {
            if (!Objects.equals(orderDTO.getPromoId(),itemDTO.getPromoId())) {
                throw new ReturnException(EmReturnError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }
        } else {
            orderDTO.setItemPrice(itemDTO.getPrice());
        }
        // 减少库存
        if (!itemService.decreaseStockInCache(orderDTO.getItemId(),orderDTO.getAmount())
                //decreaseStock(orderDTO.getItemId(),orderDTO.getAmount())
        ){
            throw new ReturnException(EmReturnError.STOCK_NOT_ENOUGH);
        }
        // 订单入库
        OrderInfoDO orderInfoDO = convertDoFromDto(orderDTO);
        orderInfoMapper.insertSelective(orderInfoDO);
        // 增加销量，其中订单号通过触发器生成
        if (!itemService.increaseSales(orderDTO.getItemId(),orderDTO.getAmount())){
            throw new ReturnException(EmReturnError.ITEM_NOT_EXIT,"销量增加错误");
        }

        // 第五次优化，设置库存流水状态为成功
        OrderLogDO orderLogDO = orderLogMapper.selectByPrimaryKey(orderLogId);
        if (orderLogDO == null) {
            throw new ReturnException(EmReturnError.UNKNOWN_ERROR, "库存流水不存在");
        }
        orderLogDO.setStatus((byte) 1);
        orderLogMapper.updateByPrimaryKeySelective(orderLogDO);
    }

    @Override
    public List<OrderDTO> listOrderByUser(Integer id) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderInfoMapper.selectByUser(id).forEach(e->orderDTOList.add(convertDtoFromDO(e)));
        return orderDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String initOrderLog(Integer itemId, Integer amount) {
        OrderLogDO orderLogDO = new OrderLogDO();
        orderLogDO.setItemId(itemId);
        orderLogDO.setAmount(amount);
        orderLogMapper.insertSelective(orderLogDO);
        return orderLogDO.getOrderLogId();
    }

    private OrderDTO convertDtoFromDO(OrderInfoDO orderInfoDO) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderInfoDO, orderDTO);
        orderDTO.setItemPrice(orderInfoDO.getOrderPrice().
                divide(BigDecimal.valueOf(orderInfoDO.getAmount()),4,BigDecimal.ROUND_HALF_EVEN));
        return orderDTO;
    }

    private OrderInfoDO convertDoFromDto(OrderDTO orderDTO) {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        BeanUtils.copyProperties(orderDTO, orderInfoDO);
        orderInfoDO.setOrderPrice(orderDTO.getItemPrice().multiply(BigDecimal.valueOf(orderDTO.getAmount())));
        return orderInfoDO;
    }
}
