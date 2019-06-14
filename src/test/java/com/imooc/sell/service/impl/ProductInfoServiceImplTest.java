package com.imooc.sell.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.entity.ProductInfoEntity;
import com.imooc.sell.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void saveTest(){
        ProductInfoEntity productInfo=new ProductInfoEntity();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.5));
        productInfo.setProductStock(100);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductDescription("很好喝");
        productInfo.setCategoryType(1001);
        productInfoService.save(productInfo);
    }
    @Test
    public void findByProductStatusTest(){
        Map<String,Object> map=new HashMap<>();
        map.put("product_status", ProductStatusEnum.UP.getCode());
        Collection<ProductInfoEntity> productInfoEntities = productInfoService.listByMap(map);
        log.info(productInfoEntities+"");
    }

    @Test
    public void increaseStock() {
    }

    @Test
    public void decreaseStock() {
        //测试事务是否正确
        CartDTO cartDTO=new CartDTO("123459",1);
        CartDTO cartDTO2=new CartDTO("123456",1);
        productInfoService.decreaseStock(Arrays.asList(cartDTO,cartDTO2));
    }
}