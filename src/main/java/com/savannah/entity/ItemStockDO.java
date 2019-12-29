package com.savannah.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemStockDO that = (ItemStockDO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, itemId);
    }
}