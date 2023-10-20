package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜单-权限 实体类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:58:01
 */
@Data
@TableName(value = "redpig_sys_menu_perm")
@Schema(description = "菜单-权限")
public class MenuPerm{

    /** 菜单主键ID **/
    @Schema(description = "菜单主键ID")
    @TableField(value = "menu_id")
    private Long menuId;

    /** 权限主键ID **/
    @Schema(description = "权限主键ID")
    @TableField(value = "perm_id")
    private Long permId;

}
