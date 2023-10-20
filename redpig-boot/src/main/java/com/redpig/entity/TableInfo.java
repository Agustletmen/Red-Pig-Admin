package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * 表信息 实体类
 *
 * @author zqd
 *
 * @date 2023-07-13 08:59:10
 */
@Data
@TableName(value = "redpig_sys_tableinfo")
@Schema(description = "表信息")
public class TableInfo{

    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

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

    /** 表名 **/
    @Schema(description = "表名")
    @TableField(value = "tableName")
    private String tableName;

    /** 表备注 **/
    @Schema(description = "表备注")
    @TableField(value = "tableComment")
    private String tableComment;

    /** 类名 **/
    @Schema(description = "类名")
    @TableField(value = "className")
    private String className;

    /** 创建者 **/
    @Schema(description = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 更新者 **/
    @Schema(description = "更新者")
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 备注 **/
    @Schema(description = "备注")
    @TableField(value = "remark")
    private String remark;

    /** 表字段信息 **/
    @TableField(exist = false)
    private List<TableProperty> tableProperties;

}
