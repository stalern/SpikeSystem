package com.savannah.service.model;

import com.savannah.util.validator.TimeReasonable;
import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * @author stalern
 * @date 2019/12/17~19:44
 */
@TimeReasonable(message = "活动结束时间必须晚于开始时间")
public class PromoDTO {
    private Integer id;

    /**
     * 秒杀活动名称
     */
    @NotBlank(message = "活动名称必须填")
    private String promoName;

    /**
     * 秒杀开始时间
     */
    @NotNull(message = "活动开始时间必须填写")
    private DateTime startDate;

    /**
     * 秒杀活动结束时间
     */
    @NotNull(message = "活动结束时间必须填写")
    private DateTime endDate;

    /**
     * 秒杀商品的id和活动时的价格
     * 一个秒杀活动可以包含多个商品
     * 可能会删除
     */
    private Map<Integer, BigDecimal> promoPrice;

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

    public Map<Integer, BigDecimal> getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Map<Integer, BigDecimal> promoPrice) {
        this.promoPrice = promoPrice;
    }

    @Override
    public String toString() {
        return "PromoDTO{" +
                "id=" + id +
                ", promoName='" + promoName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", promoPrice=" + promoPrice +
                '}';
    }

    public boolean isNow() {
        return startDate.isBeforeNow() && endDate.isAfterNow();
    }
    public boolean timeRight() {
        return endDate.isBefore(startDate);
    }
    public boolean beforeNow() {
        return timeRight() && endDate.isBeforeNow();
    }
}
