package com.savannah.service.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户下单交易模型
 * @author stalern
 * @date 2019/12/09~21:39
 */
public class OrderDTO implements Serializable {
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
     * 参加的秒杀活动id，没有参加则为-1
     */
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

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", itemPrice=" + itemPrice +
                ", amount=" + amount +
                ", promoId=" + promoId +
                '}';
    }
}
