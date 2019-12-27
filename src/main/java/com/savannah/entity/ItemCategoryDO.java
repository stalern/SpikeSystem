package com.savannah.entity;

/**
 * @author stalern
 * @date 2019年12月16日15:38:42
 */
public class ItemCategoryDO {
    private Integer id;

    private Integer itemId;

    private Integer categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", categoryId=" + categoryId +
                '}';
    }
}