package com.savannah.service.model;

import com.savannah.controller.vo.ItemVO;

import javax.validation.constraints.*;

/**
 * @author stalern
 * @date 2019/12/09~21:36
 */
public class UserDTO {
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "性别不能不填写")
    @Min(value = 0,message = "性别可能为女")
    @Max(value = 1,message = "性别可能为男")
    private Byte gender;

    @NotNull(message = "年龄不能不填写")
    @Min(value = 0,message = "年龄必须大于0岁")
    @Max(value = 150,message = "年龄必须小于150岁")
    private Byte age;

    @Email(message = "邮箱格式不符合")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String pwd;

    /**
     * 用户的商品，购物车
     */
    private ItemVO itemDTO;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ItemVO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemVO itemDTO) {
        this.itemDTO = itemDTO;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", itemDTO=" + itemDTO +
                ", role='" + role + '\'' +
                '}';
    }
}
