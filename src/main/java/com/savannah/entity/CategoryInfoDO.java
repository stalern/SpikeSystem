package com.savannah.entity;

/**
 * 商品分类
 * @author stalern
 * @date 2019年12月16日15:37:50
 */
public class CategoryInfoDO {
    private Integer id;

    private String name;

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
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "CategoryInfoDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}