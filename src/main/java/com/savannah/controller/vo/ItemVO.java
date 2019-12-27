package com.savannah.controller.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品模型
 * @author stalern
 * @date 2019/12/09~20:33
 */
public class ItemVO {
    private Integer id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品分类名称
     */
    private List<String> categoriesName;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品销量
     */
    private Integer sales;

    /**
     * 商品图片URL
     */
    private String imgUrl;

    /**
     * 商品参加的活动名称，如果没有或者活动过期则为null
     */
    private String promoName;

    /**
     * 商品参加活动时的价格，没有活动该价格则为null
     */
    private BigDecimal promoPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(List<String> categoriesName) {
        this.categoriesName = categoriesName;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    @Override
    public String toString() {
        return "ItemVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", categoriesName=" + categoriesName +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", sales=" + sales +
                ", imgUrl='" + imgUrl + '\'' +
                ", promoName='" + promoName + '\'' +
                ", promoPrice=" + promoPrice +
                '}';
    }
}
