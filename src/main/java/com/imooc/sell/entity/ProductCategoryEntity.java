package com.imooc.sell.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("product_category")
public class ProductCategoryEntity {

    @TableId(type = IdType.AUTO)
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    //set字段自定义注入
    @TableField(update = "now()")
    private Date createTime;

    @TableField(update = "now()")
    private Date updateTime;

    public ProductCategoryEntity(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategoryEntity() {

    }
}
