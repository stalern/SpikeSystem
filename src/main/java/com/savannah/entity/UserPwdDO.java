package com.savannah.entity;


/**
 * @author MybatisGenerator
 * @date 2019年12月9日17:19:30
 */
public class UserPwdDO {
    private Integer id;

    private String encryptedPwd;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncryptedPwd() {
        return encryptedPwd;
    }

    public void setEncryptedPwd(String encryptedPwd) {
        this.encryptedPwd = encryptedPwd == null ? null : encryptedPwd.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserPwdDO{" +
                "id=" + id +
                ", encryptedPwd='" + encryptedPwd + '\'' +
                ", userId=" + userId +
                '}';
    }
}