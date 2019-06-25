package com.imooc.sell.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/6/24 16:22
 */
@Data
@TableName("seller_info")
public class SellerInfoEntity {
    @TableId(type = IdType.ID_WORKER_STR)
    private String sellerId;

    private String username;

    private String password;

    private String phone;

//    private Date create_time;
//
//    @TableField(update = "now()")
//    private Date update_time;
}
