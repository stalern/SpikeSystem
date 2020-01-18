package com.savannah.service;

import com.savannah.controller.vo.MyPage;
import com.savannah.error.ReturnException;
import com.savannah.service.model.ItemDTO;

import java.util.List;

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
     * 减少Redis中的库存
     * @param itemId 商品id
     * @param amount 数量
     * @return 成功为true，失败为false
     */
    boolean decreaseStockInCache(Integer itemId, Integer amount);

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

    /**
     * 通过缓存拿到ItemDTO
     * @param itemId itemId
     * @return ItemDTO
     */
    ItemDTO getItemByIdInCache(Integer itemId);

    /**
     * 创建商品，商品活动价格和商品价格一样，要想不一样，需要修改
     * @param itemDTO 商品信息
     * @return 商品信息
     * @throws ReturnException 商品创建异常
     */
    ItemDTO createItem(ItemDTO itemDTO) throws ReturnException;

    /**
     * 更新商品信息，包括商品参加的活动和价格
     * @param itemDTO 商品信息
     * @return 商品DTO
     * @throws ReturnException 商品不存在异常
     */
    ItemDTO updateItem(ItemDTO itemDTO) throws ReturnException;

    /**
     * 通过分类列出商品信息
     * @param id 分类id
     * @return 列表DTO
     */
    List<ItemDTO> listItemByCategory(Integer id);

    /**
     * 通过活动列出商品信息
     * @param id 分类id
     * @return 列表DTO
     */
    List<ItemDTO> listItemByPromoId(Integer id);

    /**
     * 通过商品id删除商品
     * @param id itemId
     * @throws ReturnException 商品删除失败
     */
    void deleteItem(Integer id) throws ReturnException;

    /**
     * 列出某用户的商品
     * @param id userId
     * @return itemList
     */
    List<ItemDTO> listItemByUser(Integer id);

    /**
     * 创建商品用户联系
     * @param id userId
     * @param itemId itemId
     */
    void createUserItem(Integer id, Integer itemId);

    /**
     * 删除商品用户联系
     * @param id userId
     * @param itemId itemId
     */
    void deleteUserItem(Integer id, Integer itemId);
    /**
     * 列出所有商品
     * @return itemList
     */
    List<ItemDTO> listItem();
}
