package com.savannah.dataobject;

import java.math.BigDecimal;

/**
 * @author MybatisGenerator
 * @date 2019年12月9日17:25:48
 */
public class PromoItemDO {
    private Integer id;

    private Integer promoId;

    private Integer itemId;

    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }


    @Override
    public String toString() {
        return "PromoItemDO{" +
                "id=" + id +
                ", promoId=" + promoId +
                ", itemId=" + itemId +
                ", promoItemPrice=" + promoItemPrice +
                '}';
    }
}