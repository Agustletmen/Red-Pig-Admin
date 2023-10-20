package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 流程任务 实体类
 *
 * @author zqd
 *
 * @date 2023-07-19 09:27:57
 */
@Data
@TableName(value = "redpig_flow_task")
@Schema(description = "流程任务")
public class FlowTask {

    /** 主键id **/
    @Schema(description = "主键id")
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

    /** 任务ID **/
    @Schema(description = "任务ID")
    @TableField(value = "taskId")
    private String taskId;

    /** 任务名称 **/
    @Schema(description = "任务名称")
    @TableField(value = "taskName")
    private String taskName;

    /** 执行ID **/
    @Schema(description = "执行ID")
    @TableField(value = "taskExecutionId")
    private String taskExecutionId;

    /** 任务描述 **/
    @Schema(description = "任务描述")
    @TableField(value = "taskDescription")
    private String taskDescription;

    /** 任务处理人 **/
    @Schema(description = "任务处理人")
    @TableField(value = "taskAssignee")
    private String taskAssignee;

    /** 任务申请人:根据请假流程中创建人获取到 **/
    @TableField(exist = false)
    private User taskApplyer;

    /** 流程实例ID **/
    @Schema(description = "流程实例ID")
    @TableField(value = "taskProcessInstanceId")
    private String taskProcessInstanceId;

    /** 创建者 **/
    @Schema(description = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 更新者 **/
    @Schema(description = "更新者")
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
