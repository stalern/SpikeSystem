package com.savannah.entity;

import java.math.BigDecimal;

/**
 * @author MybatisGenerator
 * @date 2019年12月9日17:18:55
 */
public class OrderInfoDO {
    private String id;

    private Integer userId;

    private Integer itemId;

    /**
     * 总价格
     */
    private BigDecimal orderPrice;

    private Integer amount;

    private Integer promoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    @Override
    public String toString() {
        return "OrderInfoDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", orderPrice=" + orderPrice +
                ", amount=" + amount +
                ", promoId=" + promoId +
                '}';
    }
}