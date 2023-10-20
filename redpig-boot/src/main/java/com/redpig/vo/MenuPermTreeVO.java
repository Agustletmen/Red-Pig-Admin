package com.redpig.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 菜单-权限树结构
 * 权限本身不属于菜单 但是为了方便展示 挂着到菜单下面
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuPermTreeVO {
    private Long id;

    private String name;

    private Long parentId;

    private Boolean disabled;

}
