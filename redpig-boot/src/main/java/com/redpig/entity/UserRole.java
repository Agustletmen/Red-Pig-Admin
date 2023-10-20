package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户-角色 实体类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:21
 */
@Data
@TableName(value = "redpig_sys_user_role")
@Schema(description = "用户-角色")
public class UserRole{

    /** 用户主键ID **/
    @Schema(description = "用户主键ID")
    @TableField(value = "user_id")
    private Long userId;

    /** 角色主键ID **/
    @Schema(description = "角色主键ID")
    @TableField(value = "role_id")
    private Long roleId;
}
