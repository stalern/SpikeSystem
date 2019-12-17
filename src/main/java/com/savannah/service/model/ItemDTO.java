package com.savannah.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author stalern
 * @date 2019/12/17~20:30
 */
public class ItemDTO {
    private Integer id;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String title;

    /**
     * 商品价格
     */
    @NotNull(message = "价格不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    /**
     * 商品分类，可以有多种分类
     */
    @NotNull(message = "商品至少有一个分类")
    private List<Integer> categoryIds;

    /**
     * 商品库存
     */
    @NotNull(message = "库存不能不填")
    @Min(value = 0, message = "库存必须大于0")
    private Integer stock;

    /**
     * 商品描述
     */
    @NotBlank(message = "库存不能为空")
    private String description;

    /**
     * 商品销量
     */
    private Integer sales;

    /**
     * 商品图片URL
     */
    @NotBlank(message = "图片链接不能为null")
    private String imgUrl;

    /**
     * 商品参加的id，如果没有或者活动过期则为null
     */
    private Integer promoId;

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

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", categoryIds=" + categoryIds +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", sales=" + sales +
                ", imgUrl='" + imgUrl + '\'' +
                ", promoId=" + promoId +
                '}';
    }
}
