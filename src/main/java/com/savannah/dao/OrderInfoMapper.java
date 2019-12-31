package com.savannah.dao;

import com.savannah.controller.vo.OrderVO;
import com.savannah.entity.OrderInfoDO;
import com.savannah.service.model.OrderDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author stalern
 * @date 2019年12月9日17:21:13
 */
@Repository
public interface OrderInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderInfoDO record);

    /**
     * 选择性row插入订单
     * @param record 订单信息
     * @return 主键
     */
    int insertSelective(OrderInfoDO record);

    OrderInfoDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderInfoDO record);

    int updateByPrimaryKey(OrderInfoDO record);

    /**
     * 列出用户的订单
     * @param id 用户id
     * @return 订单
     */
    List<OrderDTO> selectByUser(Integer id);
}