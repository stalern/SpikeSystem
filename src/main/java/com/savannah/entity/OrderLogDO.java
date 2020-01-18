package com.savannah.entity;

/**
 * @author stalern
 * @date 2020年1月18日08:41:20
 */
public class OrderLogDO {
    private String orderLogId;

    private Integer itemId;

    private Integer amount;

    private Byte status;

    public String getOrderLogId() {
        return orderLogId;
    }

    public void setOrderLogId(String orderLogId) {
        this.orderLogId = orderLogId == null ? null : orderLogId.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderLogDO{" +
                "orderLogId='" + orderLogId + '\'' +
                ", itemId=" + itemId +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}