//package com.imooc.sell.repository;
//
//
//import com.imooc.sell.dataObject.ProductCategory;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Example;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class ProductCategoryRepositoryTest {
//    @Autowired
//    private ProductCategoryRepository productCategoryRepository;
//    @Test
//    public void test1(){
//        ProductCategory productCategory=new ProductCategory();
//        productCategory.setCategoryId(1);
//        Example<ProductCategory> example = Example.of(productCategory);
//        Optional<ProductCategory> all = productCategoryRepository.findOne(example);
//        log.info(all.get()+"");
//    }
//    @Test
//    public void test2(){
//        ProductCategory productCategory=new ProductCategory();
//        productCategory.setCategoryId(7);
//        Example<ProductCategory> example = Example.of(productCategory);
//        Optional<ProductCategory> all = productCategoryRepository.findOne(example);
//        productCategory = all.get();
//        productCategory.setCategoryName("男最爱");
//        productCategory.setCategoryType(1001);
//        productCategoryRepository.save(productCategory);
//    }
//}