package com.imooc.sell.service;

import com.imooc.sell.entity.ProductInfoEntity;
import com.imooc.sell.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoServiceTest {
    @Autowired
    private ProductInfoService productInfoService;
    @Test
    public void onSale() {
        ProductInfoEntity productInfoEntity = productInfoService.onSale("123456");
        Assert.assertEquals(ProductStatusEnum.UP.getCode(),productInfoEntity.getProductStatus());

    }

    @Test
    public void offSale() {
        ProductInfoEntity productInfoEntity = productInfoService.offSale("123456");
        Assert.assertEquals(ProductStatusEnum.DOWN,productInfoEntity.getProductStatusEnum());
    }
}