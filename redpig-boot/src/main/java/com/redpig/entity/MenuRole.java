package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜单-角色 实体类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:58:45
 */
@Data
@TableName(value = "redpig_sys_menu_role")
@Schema(description = "菜单-角色")
public class MenuRole{

    /** 菜单主键ID **/
    @Schema(description = "菜单主键ID")
    @TableField(value = "menu_id")
    private Long menuId;

    /** 角色主键ID **/
    @Schema(description = "角色主键ID")
    @TableField(value = "role_id")
    private Long roleId;

}
