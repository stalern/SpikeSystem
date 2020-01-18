package com.savannah.dao;

import com.savannah.entity.OrderLogDO;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2020年1月18日08:52:22
 */
@Repository
public interface OrderLogMapper {
    int deleteByPrimaryKey(String orderLogId);

    int insert(OrderLogDO record);

    int insertSelective(OrderLogDO record);

    OrderLogDO selectByPrimaryKey(String orderLogId);

    int updateByPrimaryKeySelective(OrderLogDO record);

    int updateByPrimaryKey(OrderLogDO record);
}