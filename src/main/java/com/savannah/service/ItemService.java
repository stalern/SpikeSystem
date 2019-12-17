package com.savannah.service;

import com.savannah.service.model.ItemDTO;

/**
 * @author stalern
 * @date 2019/12/17~20:34
 */
public interface ItemService {

    /**
     * 减少库存
     * @param itemId 商品id
     * @param amount 数量
     * @return 成功为true，失败为false
     */
    boolean decreaseStock(Integer itemId, Integer amount);

    /**
     * 增加销量
     * @param itemId 商品id
     * @param amount 数量
     * @return 成功为true，失败为false
     */
    boolean increaseSales(Integer itemId, Integer amount);

    /**
     * 得到商品
     * @param itemId 商品id
     * @return itemDTO
     */
    ItemDTO getItemById(Integer itemId);
}
