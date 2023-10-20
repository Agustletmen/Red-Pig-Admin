package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户-权限 实体类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:04
 */
@Data
@TableName(value = "redpig_sys_user_perm")
@Schema(description = "用户-权限")
public class UserPerm{

    /** 用户主键ID **/
    @Schema(description = "用户主键ID")
    @TableField(value = "user_id")
    private Long userId;

    /** 权限主键ID **/
    @Schema(description = "权限主键ID")
    @TableField(value = "perm_id")
    private Long permId;

}
