package com.redpig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 类描述： 订单表
 */
@Data
@TableName(value = "t_order")
@Schema(description = "订单表")
public class Order {

    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "order_id")
    private Long orderId;

    /** 入库时间 **/
    @Schema(description = "入库时间")
    @TableField(value = "add_time")
    private Date addTime;

    /** 用户ID **/
    @Schema(description = "用户ID")
    @TableField(value = "user_Id")
    private Long userId;

    /** 订单编号 **/
    @Schema(description = "订单编号")
    @TableField(value = "order_no")
    private String orderNo;

}
