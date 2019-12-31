package com.savannah.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.ItemVO;
import com.savannah.controller.vo.MyPage;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.ItemService;
import com.savannah.service.model.UserDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * @author stalern
 * @date 2019/12/27~21:27
 */
@RestController
@RequestMapping("/shopCart")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class ShopCartController {

    private final ItemController itemController;
    private final HttpServletRequest request;
    private final ItemService itemService;

    public ShopCartController(ItemController itemController, HttpServletRequest request, ItemService itemService) {
        this.itemController = itemController;
        this.request = request;
        this.itemService = itemService;
    }

    /**
     * 列出用户的购物车
     * 为什么不直接传用户id呢？是因为本地session中存的有，没必要存外面
     * @param myPage 页面
     * @return itemList
     */
    @GetMapping("/getItem")
    @Auth(Group.BUYER)
    public ReturnType getShopCart(MyPage myPage) {
        UserDTO userDTO = (UserDTO)request.getSession().getAttribute(Constant.LOGIN_USER);
        List<ItemVO> itemVO = new ArrayList<>();
        PageHelper.startPage(myPage.getPage(), myPage.getSize());
        itemService.listItemByUser(userDTO.getId()).forEach(e->{
            try {
                itemVO.add(itemController.convertFromDTO(e));
            } catch (ReturnException ex) {
                ex.printStackTrace();
            }
        });
        return ReturnType.create(new PageInfo<>(itemVO));
    }

    /**
     * 为用户增加一个购物车中的商品
     * @param itemId itemId
     * @return 成功为ok
     */
    @PostMapping("/postItemUser/{itemId}")
    @Auth(Group.BUYER)
    public ReturnType postShopCart(@PathVariable Integer itemId) {
        UserDTO userDTO = (UserDTO)request.getSession().getAttribute(Constant.LOGIN_USER);
        itemService.createUserItem(userDTO.getId(),itemId);
        return ReturnType.create();
    }

    /**
     * 删除用户购物车中的一个商品
     * @param itemId 商品id
     * @return 成功为ok
     */
    @DeleteMapping("/deleteItemUser/{itemId}")
    public ReturnType deleteShopCart(@PathVariable Integer itemId) {
        UserDTO userDTO = (UserDTO)request.getSession().getAttribute(Constant.LOGIN_USER);
        itemService.deleteUserItem(userDTO.getId(),itemId);
        return ReturnType.create();
    }
}
