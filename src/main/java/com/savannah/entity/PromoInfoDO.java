package com.savannah.entity;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PromoInfoDO that = (PromoInfoDO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(promoName, that.promoName) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, promoName, startDate, endDate);
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