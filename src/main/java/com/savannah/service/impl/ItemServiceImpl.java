package com.savannah.service.impl;

import com.savannah.dao.*;
import com.savannah.entity.*;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.mq.MqProducer;
import com.savannah.service.ItemService;
import com.savannah.service.model.ItemDTO;
import com.savannah.util.collection.EqualCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author stalern
 * @date 2019/12/17~20:59
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private RedisTemplate<String, ItemDTO> redisTemplate;
    @Resource
    private RedisTemplate<String, Integer> redisItemStock;
    @Resource
    private RedisTemplate<String, Boolean> redisItemInvalid;

//    private final MqProducer producer;
    private final ItemInfoMapper itemInfoMapper;
    private final ItemStockMapper itemStockMapper;
    private final PromoItemMapper promoItemMapper;
    private final ItemCategoryMapper itemCategoryMapper;
    private final UserItemMapper userItemMapper;

    public ItemServiceImpl(ItemInfoMapper itemInfoMapper, ItemStockMapper itemStockMapper,
                           PromoItemMapper promoItemMapper, ItemCategoryMapper itemCategoryMapper, UserItemMapper userItemMapper
//            , MqProducer producer
    ) {
        this.itemInfoMapper = itemInfoMapper;
        this.itemStockMapper = itemStockMapper;
        this.promoItemMapper = promoItemMapper;
        this.itemCategoryMapper = itemCategoryMapper;
        this.userItemMapper = userItemMapper;
//        this.producer = producer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectRow = itemStockMapper.decreaseStock(itemId,amount);
        return affectRow > 0;
    }

    @Override
    public boolean decreaseStockInCache(Integer itemId, Integer amount) {
        long result = redisItemStock.opsForValue().decrement("item_stock_" + itemId, amount);
        if (result >= 0
//                && producer.asyncReduceStock(itemId, amount)
//                此处不再需要，因为更新了事物异步下单
        ) {
            // 6次优化，打上库存售罄标识
            if (result == 0) {
                redisItemInvalid.opsForValue().set("item_stock_invalid" + itemId, true);
            }
            return true;
        } else {
            redisItemStock.opsForValue().increment("item_stock_" + itemId, amount);
            return false;
        }
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

    @Override
    public ItemDTO getItemByIdInCache(Integer itemId) {
        ItemDTO itemDTO = redisTemplate.opsForValue().get("item_validate_" + itemId);
        if (itemDTO == null) {
            itemDTO = getItemById(itemId);
            redisTemplate.opsForValue().set("item_validate_" + itemId, itemDTO);
            redisTemplate.expire("item_validate_" + itemId, 10, TimeUnit.MINUTES);
        }
        return itemDTO;
    }

    @Override
    @Transactional(rollbackFor = ReturnException.class)
    public ItemDTO createItem(ItemDTO itemDTO) throws ReturnException {
        if (itemDTO == null) {
            throw new ReturnException(EmReturnError.ITEM_CAN_NOT_CREATE);
        }
        // 商品
        ItemInfoDO itemInfoDO = convertInfoDoFromDto(itemDTO);
        itemInfoMapper.insertSelective(itemInfoDO);
        itemDTO.setId(itemInfoDO.getId());
        // 分类
        List<ItemCategoryDO> itemCategoryDos = convertCategoryDoFromDto(itemDTO);
        itemCategoryDos.forEach(itemCategoryMapper::insertSelective);
        // 库存
        ItemStockDO itemStockDO = convertStockDoFromDto(itemDTO);
        itemStockMapper.insertSelective(itemStockDO);
        // 活动
        if (itemDTO.getPromoId() != null) {
            PromoItemDO promoItemDO = convertPromoDoFromDto(itemDTO);
            promoItemMapper.insertSelective(promoItemDO);
        }
        // 把新增商品库存存入Redis
        redisItemStock.opsForValue().set("item_stock_" + itemDTO.getId(), itemDTO.getStock());
        return getItemById(itemDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = ReturnException.class)
    public ItemDTO updateItem(ItemDTO itemDTO) throws ReturnException {
        ItemDTO oldItemDTO = getItemById(itemDTO.getId());
        // 先校验新的DTO和原来的DTO有什么不同
        if (oldItemDTO == null) {
            throw new ReturnException(EmReturnError.ITEM_NOT_EXIT);
        }
        // 分类
        if (!EqualCollection.equalList(oldItemDTO.getCategoryIds(),itemDTO.getCategoryIds())) {
            List<ItemCategoryDO> itemCategoryDos = convertCategoryDoFromDto(itemDTO);
            itemCategoryMapper.deleteByItemId(itemDTO.getId());
            itemCategoryDos.forEach(itemCategoryMapper::insertSelective);
        }
        // 商品
        ItemInfoDO itemInfoDO = convertInfoDoFromDto(itemDTO);
        if ( !itemInfoDO.equals(convertInfoDoFromDto(oldItemDTO))) {
            itemInfoMapper.updateByPrimaryKeySelective(itemInfoDO);
        }
        // 库存
        ItemStockDO itemStockDO = convertStockDoFromDto(itemDTO);
        if ( !itemStockDO.equals(convertStockDoFromDto(oldItemDTO))) {
            itemStockMapper.updateByItemIdSelective(itemStockDO);
        }
        // 活动
        PromoItemDO promoItemDO = convertPromoDoFromDto(itemDTO);
        if (!Objects.equals(promoItemDO,convertPromoDoFromDto(oldItemDTO))) {
            promoItemMapper.updateByItemIdSelective(promoItemDO);
        }

        return getItemById(itemDTO.getId());
    }

    @Override
    public List<ItemDTO> listItem() {
        List<Integer> integerList = itemInfoMapper.listItemId();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        integerList.forEach(e-> itemDTOList.add(getItemById(e)));
        return itemDTOList;
    }

    @Override
    public List<ItemDTO> listItemByCategory(Integer id) {
        List<ItemInfoDO> itemInfoDOList = itemInfoMapper.listItemByCategoryId(id);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemInfoDOList.forEach(e-> itemDTOList.add(getItemById(e.getId())));
        return itemDTOList;
    }

    @Override
    public List<ItemDTO> listItemByPromoId(Integer id) {
        List<ItemInfoDO> itemInfoDOList = itemInfoMapper.listItemByPromoId(id);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemInfoDOList.forEach(e-> itemDTOList.add(getItemById(e.getId())));
        return itemDTOList;
    }

    @Override
    public List<ItemDTO> listItemByUser(Integer id) {
        List<ItemInfoDO> itemInfoDOList = itemInfoMapper.listItemByUserId(id);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemInfoDOList.forEach(e-> itemDTOList.add(getItemById(e.getId())));
        return itemDTOList;
    }

    @Override
    public void createUserItem(Integer id, Integer itemId) {
        UserItemDO userItemDO = new UserItemDO();
        userItemDO.setItemId(itemId);
        userItemDO.setUserId(id);
        userItemMapper.insertSelective(userItemDO);
    }

    @Override
    public void deleteUserItem(Integer id, Integer itemId) {
        userItemMapper.deleteByUserItem(id,itemId);
    }

    @Override
    public void deleteItem(Integer id) throws ReturnException {
        if (itemInfoMapper.deleteByPrimaryKey(id) < 0) {
            throw new ReturnException(EmReturnError.ITEM_DELETE_ERROR);
        }
        itemCategoryMapper.deleteByItemId(id);
        itemStockMapper.deleteByItemId(id);
        promoItemMapper.deleteByItemId(id);
    }

    private ItemStockDO convertStockDoFromDto(ItemDTO itemDTO) {
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemDTO.getId());
        itemStockDO.setStock(itemDTO.getStock());
        return itemStockDO;
    }
    private PromoItemDO convertPromoDoFromDto(ItemDTO itemDTO) {
        PromoItemDO promoItemDO = new PromoItemDO();
        promoItemDO.setItemId(itemDTO.getId());
        promoItemDO.setPromoId(itemDTO.getPromoId());
        promoItemDO.setPromoItemPrice(itemDTO.getPromoPrice());
        return promoItemDO;
    }

    private ItemInfoDO convertInfoDoFromDto(ItemDTO itemDTO) {
        ItemInfoDO itemInfoDO = new ItemInfoDO();
        BeanUtils.copyProperties(itemDTO,itemInfoDO);
        return itemInfoDO;
    }

    private List<ItemCategoryDO> convertCategoryDoFromDto(ItemDTO itemDTO) {
        List<ItemCategoryDO> itemCategoryDos = new ArrayList<>();
        itemDTO.getCategoryIds().forEach(e->{
            ItemCategoryDO itemCategoryDO = new ItemCategoryDO();
            itemCategoryDO.setItemId(itemDTO.getId());
            itemCategoryDO.setCategoryId(e);
            itemCategoryDos.add(itemCategoryDO);
        });
        return itemCategoryDos;
    }

    private ItemDTO convertDtoFromDo(ItemInfoDO itemInfoDO, List<ItemCategoryDO> itemCategoryDos,
                                     ItemStockDO itemStockDO, PromoItemDO promoItemDO) {
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(itemInfoDO, itemDTO);
        itemDTO.setStock(itemStockDO.getStock());
        if (promoItemDO != null) {
            itemDTO.setPromoPrice(promoItemDO.getPromoItemPrice());
            itemDTO.setPromoId(promoItemDO.getPromoId());
        }
        itemDTO.setCategoryIds(itemCategoryDos.stream().map(ItemCategoryDO::getCategoryId).collect(Collectors.toList()));
        return itemDTO;
    }
}
