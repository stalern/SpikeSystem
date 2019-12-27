package com.savannah.dao;

import com.savannah.entity.PromoInfoDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author stalern
 * @date 2019年12月9日17:24:41
 */
@Repository
public interface PromoInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoInfoDO record);

    int insertSelective(PromoInfoDO record);

    /**
     * 通过主键拿到活动信息
     * @param id promoId
     * @return 活动信息
     */
    PromoInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromoInfoDO record);

    int updateByPrimaryKey(PromoInfoDO record);

    /**
     * 列出所有活动信息
     * @return 活动信息List
     */
    List<PromoInfoDO> listPromo();
}