package com.savannah.entity;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * @author MybatisGenerator
 * @date 2019年12月9日17:25:31
 */
public class PromoInfoDO {
    private Integer id;

    private String promoName;

    private Date startDate;

    private Date endDate;

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
        this.promoName = promoName == null ? null : promoName.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isNow() {
        DateTime start = new DateTime(startDate);
        DateTime end = new DateTime(endDate);
        return start.isBeforeNow() && end.isAfterNow();
    }

    @Override
    public String toString() {
        return "PromoInfoDO{" +
                "id=" + id +
                ", promoName='" + promoName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}