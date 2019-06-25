package com.imooc.sell.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.sell.entity.SellerInfoEntity;
import com.imooc.sell.utils.KeyUtil;
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
public class SellerInfoServiceTest {
    @Autowired
    private SellerInfoService sellerInfoService;
    @Test
    public void save(){
        SellerInfoEntity sellerInfoEntity=new SellerInfoEntity();
        sellerInfoEntity.setSellerId(KeyUtil.getUniqueKey());
        sellerInfoEntity.setUsername("admin");
        sellerInfoEntity.setPassword("admin");
        sellerInfoEntity.setPhone("18666666666");
        boolean save = sellerInfoService.save(sellerInfoEntity);
        Assert.assertTrue("是否成功",save);
    }
    @Test
    public void findByPhone(){
        QueryWrapper<SellerInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone","18666666666");
        SellerInfoEntity one = sellerInfoService.getOne(queryWrapper);
        log.info(one+"");
       Assert.assertEquals("18666666666",one.getPhone());
    }
}