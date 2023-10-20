package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 表属性 实体类
 *
 * @author zqd
 *
 * @date 2023-07-13 13:05:02
 */
@Data
@TableName(value = "redpig_sys_table_property")
@Schema(description = "表属性")
public class TableProperty{

    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

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

    /** 字段名 **/
    @Schema(description = "字段名")
    @TableField(value = "columnName")
    private String columnName;

    /** 字段类型 **/
    @Schema(description = "字段类型")
    @TableField(value = "columnType")
    private String columnType;

    /** 列长度 **/
    @Schema(description = "列长度")
    @TableField(value = "columnLength")
    private Integer columnLength;

    /** 字段说明 **/
    @Schema(description = "字段说明")
    @TableField(value = "columnComment")
    private String columnComment;

    /** 不为空 **/
    @Schema(description = "不为空")
    @TableField(value = "not_null")
    private Boolean notNull;

    /** 主键 **/
    @Schema(description = "主键")
    @TableField(value = "primarykey")
    private Boolean primarykey;

    /** 属性名 **/
    @Schema(description = "属性名")
    @TableField(value = "fieldName")
    private String fieldName;

    /** 属性类型 **/
    @Schema(description = "属性类型")
    @TableField(value = "fieldType")
    private String fieldType;

    /** 查询 **/
    @Schema(description = "查询")
    @TableField(value = "select_is")
    private Boolean selectIs;

    /** 查询方式 **/
    @Schema(description = "查询方式")
    @TableField(value = "select_type")
    private String selectType;

    /** 表单 **/
    @Schema(description = "表单")
    @TableField(value = "form_is")
    private Boolean formIs;

    /** 必填 **/
    @Schema(description = "必填")
    @TableField(value = "input_must")
    private Boolean inputMust;

    /** 表单类型 **/
    @Schema(description = "表单类型")
    @TableField(value = "form_type")
    private String formType;

    /** 排序 **/
    @Schema(description = "排序")
    @TableField(value = "seq")
    private Integer seq;

    /** 所属表 **/
    @Schema(description = "所属表")
    @TableField(value = "tableInfo_id")
    private Long tableInfoId;

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

}
