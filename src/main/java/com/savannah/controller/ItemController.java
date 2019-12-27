package com.savannah.controller;

import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.ItemVO;
import com.savannah.controller.vo.MyPage;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.CategoryService;
import com.savannah.service.ItemService;
import com.savannah.service.PromoService;
import com.savannah.service.model.ItemDTO;
import com.savannah.service.model.PromoDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品，购物车等
 * @author stalern
 * @date 2019/12/17~19:27
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final PromoService promoService;

    public ItemController(ItemService itemService, CategoryService categoryService, PromoService promoService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.promoService = promoService;
    }

    /**
     * 得到商品
     * @param id itemId
     * @return itemVO
     * @throws ReturnException 商品不存在
     */
    @GetMapping("/getItem/{id}")
    public ReturnType getItem(@PathVariable Integer id) throws ReturnException {
        return ReturnType.create(convertFromDTO(itemService.getItemById(id)));
    }
    /**
     * 通过分类id列出商品
     * @param id category_id
     * @param myPage 分页时的页数
     * @return itemVO
     */
    @GetMapping("listItemByPromo/{id}")
    public ReturnType listItemByPromo(@PathVariable Integer id, MyPage myPage){
        List<ItemVO> itemVO = new ArrayList<>();
        itemService.listItemByPromoId(id,myPage).forEach(e->{
            try {
                itemVO.add(convertFromDTO(e));
            } catch (ReturnException ex) {
                ex.printStackTrace();
            }
        });
        return ReturnType.create(new PageInfo<>(itemVO));
    }
    /**
     * 通过分类id列出商品
     * @param id category_id
     * @param myPage 分页时的页数
     * @return itemVO
     */
    @GetMapping("listItemByCategory/{id}")
    public ReturnType listItemByCategory(@PathVariable Integer id, MyPage myPage){
        List<ItemVO> itemVO = new ArrayList<>();
        itemService.listItemByCategory(id,myPage).forEach(e->{
            try {
                itemVO.add(convertFromDTO(e));
            } catch (ReturnException ex) {
                ex.printStackTrace();
            }
        });
        return ReturnType.create(new PageInfo<>(itemVO));
    }
    /**
     * 创建商品
     * 商品创建时如果参加活动，那么也要填下该活动下的商品价格
     * @param itemDTO 商品信息，包括活动和活动时的价格
     * @return 商品VO
     * @throws ReturnException 创建商品异常
     */
    @PostMapping("/postItem")
    @Auth(Group.SELLER)
    public ReturnType postItem(@RequestBody ItemDTO itemDTO) throws ReturnException {
        ItemVO itemVO = convertFromDTO(itemService.createItem(itemDTO));
        return ReturnType.create(itemVO);
    }

    /**
     * 更新商品
     * @param itemDTO 商品DTO
     * @return itemVO
     * @throws ReturnException 商品不存在异常
     */
    @PostMapping("/updateItem")
    @Auth(Group.SELLER)
    public ReturnType updateItem(@RequestBody ItemDTO itemDTO) throws ReturnException {
        ItemVO itemVO = convertFromDTO(itemService.updateItem(itemDTO));
        return ReturnType.create(itemVO);
    }

    /**
     * 删除商品
     * @param id itemId
     * @return 成功为ok，失败抛出异常
     */
    @DeleteMapping("/deleteItem/{id}")
    @Auth(Group.SELLER)
    public ReturnType deleteItem(@PathVariable Integer id) throws ReturnException {
        itemService.deleteItem(id);
        return ReturnType.create();
    }

    private ItemVO convertFromDTO(ItemDTO item) throws ReturnException {
        if (item == null) {
            throw new ReturnException(EmReturnError.ITEM_CAN_NOT_CREATE);
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item, itemVO);
        // 注意把描述id和活动id换成名称，正常情况下活动肯定存在并且没有过期
        if (item.getPromoId() != null) {
            PromoDTO promoDTO = promoService.getPromoById(item.getPromoId());
            // 没有该活动或者该活动已经过期,校验时间工具类
            if (promoDTO == null) {
                throw new ReturnException(EmReturnError.ITEM_NOT_EXIT,"该商品不存在活动");
            }
            itemVO.setPromoName(promoDTO.getPromoName());
        }
        List<String> categoriesName = new ArrayList<>();
        item.getCategoryIds().forEach(e-> categoriesName.add(categoryService.getCategoryById(e).getName()));
        itemVO.setCategoriesName(categoriesName);
        return itemVO;
    }


}
