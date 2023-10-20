package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 日志异常记录 实体类
 *
 * @author zqd
 *
 * @date 2023-07-24 15:19:40
 */
@Data
@TableName(value = "redpig_exception_log")
@Schema(description = "日志异常记录")
public class ExceptionLog{

    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /** 删除状态：0、已删除 1、未删除 **/
    @Schema(description = "删除状态：0、已删除 1、未删除")
    @TableField(value = "deleteStatus")
    private int deleteStatus;

    /** 创建时间 **/
    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "createTime",fill = FieldFill.INSERT)
    private java.util.Date createTime;

    /** 更新时间 **/
    @Schema(description = "更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "updateTime",fill = FieldFill.INSERT_UPDATE)
    private java.util.Date updateTime;

    /** 请求参数 **/
    @Schema(description = "请求参数")
    @TableField(value = "exc_requ_param")
    private String excRequParam;

    /** 操作方法 **/
    @Schema(description = "操作方法")
    @TableField(value = "oper_method")
    private String operMethod;

    /** 异常名称 **/
    @Schema(description = "异常名称")
    @TableField(value = "exc_name")
    private String excName;

    /** 异常信息 **/
    @Schema(description = "异常信息")
    @TableField(value = "exc_message")
    private String excMessage;

    /** 操作者用户ID **/
    @Schema(description = "操作者用户ID")
    @TableField(value = "oper_user_id")
    private String operUserId;

    /** 操作者用户名 **/
    @Schema(description = "操作者用户名")
    @TableField(value = "oper_user_name")
    private String operUserName;

    /** 操作的地址 **/
    @Schema(description = "操作的地址")
    @TableField(value = "oper_uri")
    private String operUri;

    /** 操作者IP **/
    @Schema(description = "操作者IP")
    @TableField(value = "oper_ip")
    private String operIp;

    /** 操作时间 **/
    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "oper_create_time")
    private java.util.Date operCreateTime;

    /** 创建者 **/
    @Schema(description = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 更新者 **/
    @Schema(description = "更新者")
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
