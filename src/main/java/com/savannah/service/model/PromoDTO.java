package com.savannah.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 秒杀活动模型
 * @author stalern
 * @date 2019/12/09~21:59
 */
public class PromoDTO {
    private Integer id;

    /**
     * 秒杀活动名称
     */
    private String promoName;

    /**
     * 秒杀开始时间
     */
    private DateTime startDate;

    /**
     * 秒杀活动结束时间
     */
    private DateTime endDate;

    /**
     * 秒杀商品的id和秒杀时的价格
     * 一个秒杀活动可以包含多个商品
     */
    private Map<Integer,BigDecimal> itemPriceMap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Map<Integer, BigDecimal> getItemPriceMap() {
        return itemPriceMap;
    }

    public void setItemPriceMap(Map<Integer, BigDecimal> itemPriceMap) {
        this.itemPriceMap = itemPriceMap;
    }
}
