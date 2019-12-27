package com.savannah.dao;

import com.savannah.entity.CategoryInfoDO;
import org.springframework.stereotype.Repository;

/**
 * 分类信息
 * @author stalern
 * @date 2019年12月24日20:23:39
 * MyBatis Generator
 */
@Repository
public interface CategoryInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryInfoDO record);

    int insertSelective(CategoryInfoDO record);

    /**
     * 通过id查找分类信息
     * @param id 主键
     * @return 分类信息
     */
    CategoryInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryInfoDO record);

    int updateByPrimaryKey(CategoryInfoDO record);
}