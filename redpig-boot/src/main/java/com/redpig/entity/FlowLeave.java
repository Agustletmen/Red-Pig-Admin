package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 请假流程 实体类
 *
 * @author zqd
 *
 * @date 2023-07-18 14:45:11
 */
@Data
@TableName(value = "redpig_flow_leave")
@Schema(description = "请假流程")
public class FlowLeave {

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

    /** 请假申请人ID **/
    @Schema(description = "请假申请人ID")
    @TableField(value = "user_id")
    private Long userId;

    @TableField(exist = false)
    private User user;

    /** 请假理由 **/
    @Schema(description = "请假理由")
    @TableField(value = "leave_reason")
    private String leaveReason;

    /** 请假起始时间 **/
    @Schema(description = "请假起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "startTime")
    private java.util.Date startTime;

    /** 请假结束时间 **/
    @Schema(description = "请假结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "endTime")
    private java.util.Date endTime;

    /** 流程实例ID **/
    @Schema(description = "流程实例ID")
    @TableField(value = "processInstance_id")
    private String processInstanceId;

    /** 流程定义ID **/
    @Schema(description = "流程定义ID")
    @TableField(value = "processDefinition_id")
    private String processDefinitionId;

    /** 请假是否同意 **/
    @Schema(description = "请假是否同意")
    @TableField(value = "agree")
    private boolean agree;

    /** 创建者 **/
    @Schema(description = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 更新者 **/
    @Schema(description = "更新者")
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
