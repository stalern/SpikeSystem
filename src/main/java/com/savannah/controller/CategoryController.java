package com.savannah.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.CategoryVO;
import com.savannah.controller.vo.MyPage;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.CategoryService;
import com.savannah.service.model.CategoryDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stalern
 * @date 2019/12/30~11:16
 */
@RestController
@RequestMapping("/category")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取分类详细信息
     * @param id id
     * @return DTO
     */
    @GetMapping("/get/{id}")
    public ReturnType getCategory(@PathVariable Integer id) {
        return ReturnType.create(categoryService.getCategoryById(id));
    }
    /**
     * 列出分类
     * @param page 页数
     * @return list
     */
    @GetMapping("/list")
    public ReturnType listCategory(MyPage page) {
        List<CategoryVO> categoryVOList = new ArrayList<>(page.getSize());
        PageHelper.startPage(page.getPage(),page.getSize());
        categoryService.listCategory().forEach(e->{
            try {
                categoryVOList.add(convertFromDTO(e));
            } catch (ReturnException ex) {
                ex.printStackTrace();
            }
        });
        return ReturnType.create(new PageInfo<>(categoryVOList));
    }

    /**
     * 创建分类信息
     * @param categoryDTO 分类中包含商品id
     * @return CategoryVO
     */
    @PostMapping("/create")
    @Auth(Group.SELLER)
    public ReturnType postCategory(@RequestBody CategoryDTO categoryDTO) throws ReturnException {
        CategoryVO categoryVO = convertFromDTO(categoryService.createCategory(categoryDTO));
        return ReturnType.create(categoryVO);
    }

    /**
     * 更新分类
     * @param categoryDTO 包括商品
     * @return vo
     * @throws ReturnException 商品不存在
     */
    @PutMapping("/update")
    @Auth(Group.SELLER)
    public ReturnType updateCategory(@RequestBody CategoryDTO categoryDTO) throws ReturnException {
        CategoryVO categoryVO = convertFromDTO(categoryService.updateCategory(categoryDTO));
        return ReturnType.create(categoryVO);
    }

    /**
     * 删除商品分类
     * @param id categoryId
     * @return 成功为ok
     */
    @DeleteMapping("/delete/{id}")
    @Auth(Group.SELLER)
    public ReturnType deleteCategory(@PathVariable Integer id) throws ReturnException {
        categoryService.deleteCategory(id);
        return ReturnType.create();
    }

    private CategoryVO convertFromDTO(CategoryDTO categoryDTO) throws ReturnException {
        if (categoryDTO == null) {
            throw new ReturnException(EmReturnError.CATEGORY_CREATE_ERROR);
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(categoryDTO,categoryVO);
        return categoryVO;
    }
}
