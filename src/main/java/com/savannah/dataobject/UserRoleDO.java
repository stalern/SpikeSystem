package com.savannah.dataobject;

/**
 * @author stalern
 * @date 2019年12月16日14:04:42
 */
public class UserRoleDO {
    private Integer id;

    private Integer userId;

    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoleDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", role=" + role +
                '}';
    }
}