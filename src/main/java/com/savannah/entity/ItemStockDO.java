package com.savannah.entity;

/**
 * @author MybatisGenerator
 * @date 2019年12月9日17:18:29
 */
public class ItemStockDO {
    private Integer id;

    private Integer stock;

    private Integer itemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "ItemStockDO{" +
                "id=" + id +
                ", stock=" + stock +
                ", itemId=" + itemId +
                '}';
    }
}