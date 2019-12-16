package com.savannah.dataobject;

/**
 * @author stalern
 * @date 2019年12月16日15:39:49
 */
public class UserItemDO {
    private Integer id;

    private Integer userId;

    private Integer itemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return "UserItemDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}