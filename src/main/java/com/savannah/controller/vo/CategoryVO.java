package com.savannah.controller.vo;

/**
 * @author stalern
 * @date 2019/12/30~11:26
 */
public class CategoryVO {
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
