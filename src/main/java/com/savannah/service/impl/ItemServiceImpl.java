package com.savannah.service.impl;

import com.savannah.dao.ItemCategoryMapper;
import com.savannah.dao.ItemInfoMapper;
import com.savannah.dao.ItemStockMapper;
import com.savannah.dao.PromoItemMapper;
import com.savannah.dataobject.ItemCategoryDO;
import com.savannah.dataobject.ItemInfoDO;
import com.savannah.dataobject.ItemStockDO;
import com.savannah.dataobject.PromoItemDO;
import com.savannah.service.ItemService;
import com.savannah.service.model.ItemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author stalern
 * @date 2019/12/17~20:59
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemInfoMapper itemInfoMapper;
    private final ItemStockMapper itemStockMapper;
    private final PromoItemMapper promoItemMapper;
    private final ItemCategoryMapper itemCategoryMapper;

    public ItemServiceImpl(ItemInfoMapper itemInfoMapper, ItemStockMapper itemStockMapper, PromoItemMapper promoItemMapper, ItemCategoryMapper itemCategoryMapper) {
        this.itemInfoMapper = itemInfoMapper;
        this.itemStockMapper = itemStockMapper;
        this.promoItemMapper = promoItemMapper;
        this.itemCategoryMapper = itemCategoryMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectRow = itemStockMapper.decreaseStock(itemId,amount);
        return affectRow > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean increaseSales(Integer itemId, Integer amount) {
        return itemInfoMapper.increaseSales(itemId,amount) > 0;
    }

    @Override
    public ItemDTO getItemById(Integer itemId) {
        // 信息
        ItemInfoDO itemInfoDO = itemInfoMapper.selectByPrimaryKey(itemId);
        if (itemInfoDO == null) {
            return null;
        }
        // 分类
        List<ItemCategoryDO> itemCategoryDos = itemCategoryMapper.selectByItemId(itemId);
        // 库存
        ItemStockDO itemStockDO = itemStockMapper.selectByItemId(itemId);
        // 活动,必须是商品参加并且活动在当前时间
        PromoItemDO promoItemDO = promoItemMapper.selectByItemId(itemId);

        return convertDtoFromDo(itemInfoDO,itemCategoryDos,itemStockDO,promoItemDO);
    }

    private ItemDTO convertDtoFromDo(ItemInfoDO itemInfoDO, List<ItemCategoryDO> itemCategoryDos, ItemStockDO itemStockDO, PromoItemDO promoItemDO) {
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(itemInfoDO, itemDTO);
        itemDTO.setStock(itemStockDO.getStock());
        if (promoItemDO != null) {
            itemDTO.setPrice(promoItemDO.getPromoItemPrice());
            itemDTO.setPromoId(promoItemDO.getPromoId());
        }
        itemDTO.setCategoryIds(itemCategoryDos.stream().map(ItemCategoryDO::getCategoryId).collect(Collectors.toList()));
        return itemDTO;
    }
}
