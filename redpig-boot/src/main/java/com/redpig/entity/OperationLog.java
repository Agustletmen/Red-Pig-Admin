package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 日志操作记录 实体类
 *
 * @author zqd
 *
 * @date 2023-07-24 15:19:43
 */
@Data
@TableName(value = "redpig_operation_log")
@Schema(description = "日志操作记录")
public class OperationLog{

    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /** 删除状态：0、已删除 1、未删除 **/
    @Schema(description = "删除状态：0、已删除 1、未删除")
    @TableField(value = "deleteStatus")
    private int deleteStatus;

    /** 更新时间 **/
    @Schema(description = "更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private java.util.Date updateTime;

    /** 创建时间 **/
    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private java.util.Date createTime;

    /** 操作模块 **/
    @Schema(description = "操作模块")
    @TableField(value = "oper_modul")
    private String operModul;

    /** 操作类型 **/
    @Schema(description = "操作类型")
    @TableField(value = "oper_type")
    private String operType;

    /** 操作描述 **/
    @Schema(description = "操作描述")
    @TableField(value = "oper_desc")
    private String operDesc;

    /** 请求方法 **/
    @Schema(description = "请求方法")
    @TableField(value = "oper_method")
    private String operMethod;

    /** 请求参数 **/
    @Schema(description = "请求参数")
    @TableField(value = "oper_requ_param")
    private String operRequParam;

    /** 返回参数 **/
    @Schema(description = "返回参数")
    @TableField(value = "oper_resp_param")
    private String operRespParam;

    /** 用户id **/
    @Schema(description = "用户id")
    @TableField(value = "oper_user_id")
    private String operUserId;

    /** 用户名称 **/
    @Schema(description = "用户名称")
    @TableField(value = "oper_user_name")
    private String operUserName;

    /** 操作IP **/
    @Schema(description = "操作IP")
    @TableField(value = "oper_ip")
    private String operIp;

    /** 操作路径 **/
    @Schema(description = "操作路径")
    @TableField(value = "oper_uri")
    private String operUri;

    /** 创建者 **/
    @Schema(description = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 更新者 **/
    @Schema(description = "更新者")
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
