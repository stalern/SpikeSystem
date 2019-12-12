package com.savannah.dao;

import com.savannah.dataobject.PromoInfoDO;

/**
 * @author stalern
 * @date 2019年12月9日17:24:41
 */
public interface PromoInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoInfoDO record);

    int insertSelective(PromoInfoDO record);

    PromoInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromoInfoDO record);

    int updateByPrimaryKey(PromoInfoDO record);
}