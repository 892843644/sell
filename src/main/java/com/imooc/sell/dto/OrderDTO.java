package com.imooc.sell.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;
    /** 买家微信openid */
    private String buyerOpenid;
    /** 订单金额 */
    private BigDecimal orderAmount;

    /** 订单状态，默认0新下单 */
    private Integer orderStatus;
    /** 支付状态，默认0未支付 */
    private Integer payStatus ;

    private Date createTime;

    private Date updateTime;

    /** 一个订单对于多个订单详情 */
    private List<OrderDetailEntity> orderDetailEntityList;

}
