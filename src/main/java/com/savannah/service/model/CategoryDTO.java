package com.savannah.service.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * categoryDTO
 * @author stalern
 * @date 2019/12/24~08:38
 */
public class CategoryDTO implements Serializable {
    private Integer id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private List<Integer> itemIds;

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

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemIds=" + itemIds +
                '}';
    }
}
