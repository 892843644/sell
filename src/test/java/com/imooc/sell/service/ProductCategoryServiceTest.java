package com.imooc.sell.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.sell.entity.ProductCategoryEntity;
import com.imooc.sell.service.impl.ProductCategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryServiceTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void test1(){
//        ProductCategoryEntity byId = productCategoryService.getById(7);
//        log.info(byId+"");

        List<ProductCategoryEntity> list = productCategoryService.list();
        log.info(list+"");

    }
    @Test
    public void test2(){
        ProductCategoryEntity productCategoryEntity = productCategoryService.getById(9);
        productCategoryEntity.setCategoryName("我最爱");
        productCategoryEntity.setCategoryType(1004);
        productCategoryService.saveOrUpdate(productCategoryEntity);
    }
    @Test
    public void test3(){
        ProductCategoryEntity productCategoryEntity=new ProductCategoryEntity("男生最",1004);
        productCategoryService.saveOrUpdate(productCategoryEntity);
    }
    @Test
    //in查询
    public void findByCategoryTypeIn(){
        QueryWrapper<ProductCategoryEntity> queryWrapper=new QueryWrapper();
        queryWrapper.lambda().in(ProductCategoryEntity::getCategoryType, Arrays.asList(1001,1002));
        List<ProductCategoryEntity> list = productCategoryService.list(queryWrapper);
        log.info(list+"");

    }
}