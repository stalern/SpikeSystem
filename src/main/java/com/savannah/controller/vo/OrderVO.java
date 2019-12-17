package com.savannah.controller.vo;

import java.math.BigDecimal;

/**
 * @author stalern
 * @date 2019/12/17~23:59
 */
public class OrderVO {
    /**
     * 交易单号，具有明确意义
     */
    private String id;

    /**
     * 购买的用户ID
     */
    private Integer userId;

    /**
     * 购买的商品ID
     */
    private Integer itemId;

    /**
     * 购买商品时的单价
     */
    private BigDecimal itemPrice;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 参加的秒杀活动名称，没有参加则为null
     */
    private String promoName;

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

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", itemPrice=" + itemPrice +
                ", amount=" + amount +
                ", promoName='" + promoName + '\'' +
                '}';
    }
}
