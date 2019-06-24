package com.imooc.sell.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("order_master")
public class OrderMasterEntity {
    @TableId(type=IdType.ID_WORKER_STR)
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;
    /** 买家微信openid */
    private String buyerOpenid;
    /** 订单金额 */
    private BigDecimal orderAmount;

    /** 订单状态，默认0新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态，默认0未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //set字段自定义注入时间
//    @TableField(update = "now()")
    private Date createTime;

    @TableField(update = "now()")
    private Date updateTime;
}
