package com.savannah.dao;

import com.savannah.dataobject.PromoItemDO;

/**
 * @author stalern
 * @date 2019年12月9日17:24:52
 */
public interface PromoItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoItemDO record);

    int insertSelective(PromoItemDO record);

    PromoItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromoItemDO record);

    int updateByPrimaryKey(PromoItemDO record);
}