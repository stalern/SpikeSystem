package com.savannah.service.model;

import javax.validation.constraints.NotBlank;

/**
 * categoryDTO
 * @author stalern
 * @date 2019/12/24~08:38
 */
public class CategoryDTO {
    private Integer id;

    @NotBlank(message = "分类名称不能为空")
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
