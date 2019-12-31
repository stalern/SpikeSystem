package com.savannah.service.impl;

import com.savannah.dao.CategoryInfoMapper;
import com.savannah.dao.ItemCategoryMapper;
import com.savannah.entity.CategoryInfoDO;
import com.savannah.entity.ItemCategoryDO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.service.CategoryService;
import com.savannah.service.model.CategoryDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stalern
 * @date 2019/12/24~08:45
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryInfoMapper categoryInfoMapper;
    private final ItemCategoryMapper itemCategoryMapper;

    public CategoryServiceImpl(CategoryInfoMapper categoryInfoMapper, ItemCategoryMapper itemCategoryMapper) {
        this.categoryInfoMapper = categoryInfoMapper;
        this.itemCategoryMapper = itemCategoryMapper;
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        CategoryInfoDO categoryInfoDO = categoryInfoMapper.selectByPrimaryKey(id);
        List<ItemCategoryDO> itemCategoryDOList = itemCategoryMapper.selectByCategoryId(id);
        return convertDtoFromDO(categoryInfoDO,itemCategoryDOList);
    }

    @Override
    public List<CategoryDTO> listCategory() {
        List<CategoryInfoDO> categoryInfoDOList = categoryInfoMapper.listCategoryInfo();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryInfoDOList.forEach(e-> categoryDTOList.add(convertDtoFromDO(e)));
        return categoryDTOList;
    }
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws ReturnException {
        CategoryInfoDO categoryInfoDO = convertInfoFromDTO(categoryDTO);
        try {
            categoryInfoMapper.insertSelective(categoryInfoDO);
        } catch (DuplicateKeyException e) {
            throw new ReturnException(EmReturnError.CATEGORY_EXIST_ERROR, "该分类名称已经存在");
        }
        categoryDTO.setId(categoryInfoDO.getId());
        if (CollectionUtils.isNotEmpty(categoryDTO.getItemIds())) {
            List<ItemCategoryDO> itemCategoryDO = convertItemFromDTO(categoryDTO);
            itemCategoryDO.forEach(itemCategoryMapper::insertSelective);
        }
        return getCategoryById(categoryDTO.getId());
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) throws ReturnException {
        CategoryDTO oldCategoryDTO = getCategoryById(categoryDTO.getId());
        if (oldCategoryDTO == null) {
            throw new ReturnException(EmReturnError.CATEGORY_EXIST_ERROR,"不存在该分类");
        }
        CategoryInfoDO categoryInfoDO = convertInfoFromDTO(categoryDTO);
        if (!categoryInfoDO.equals(convertInfoFromDTO(oldCategoryDTO))) {
            categoryInfoMapper.updateByPrimaryKeySelective(categoryInfoDO);
        }
        if (!Objects.equals(categoryDTO.getItemIds(),oldCategoryDTO.getItemIds())) {
            List<ItemCategoryDO> itemCategoryDOList = convertItemFromDTO(categoryDTO);
            itemCategoryMapper.deleteByCategoryId(categoryDTO.getId());
            itemCategoryDOList.forEach(itemCategoryMapper::insertSelective);
            // 对没有分类的商品归类
            List<Integer> integers = oldCategoryDTO.getItemIds();
            integers.removeAll(categoryDTO.getItemIds());
            oldCategoryDTO.setItemIds(integers);
            oldCategoryDTO.setId(categoryInfoMapper.selectByName("未分类"));
            convertItemFromDTO(oldCategoryDTO).forEach(itemCategoryMapper::insertSelective);
        }
        return getCategoryById(categoryDTO.getId());
    }

    @Override
    public void deleteCategory(Integer id) throws ReturnException {
        if (categoryInfoMapper.deleteByPrimaryKey(id) < 0) {
            throw new ReturnException(EmReturnError.CATEGORY_DELETE_FAIL);
        }
        itemCategoryMapper.deleteByCategoryId(id);
    }

    private List<ItemCategoryDO> convertItemFromDTO(CategoryDTO categoryDTO) {
        List<ItemCategoryDO> itemCategoryDOList = new ArrayList<>();
        categoryDTO.getItemIds().forEach(e -> {
            ItemCategoryDO itemCategoryDO = new ItemCategoryDO();
            itemCategoryDO.setCategoryId(categoryDTO.getId());
            itemCategoryDO.setItemId(e);
            itemCategoryDOList.add(itemCategoryDO);
        });
        return itemCategoryDOList;
    }

    private CategoryInfoDO convertInfoFromDTO(CategoryDTO categoryDTO) {
        CategoryInfoDO categoryInfoDO = new CategoryInfoDO();
        BeanUtils.copyProperties(categoryDTO, categoryInfoDO);
        return categoryInfoDO;
    }

    private CategoryDTO convertDtoFromDO(CategoryInfoDO categoryInfoDO) {
        return convertDtoFromDO(categoryInfoDO,null);
    }

    private CategoryDTO convertDtoFromDO(CategoryInfoDO categoryInfoDO, List<ItemCategoryDO> itemCategoryDOList) {
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(categoryInfoDO,categoryDTO);
        if (itemCategoryDOList != null) {
            categoryDTO.setItemIds(itemCategoryDOList.stream()
                    .map(ItemCategoryDO::getItemId).collect(Collectors.toList()));
        }
        return categoryDTO;
    }
}
