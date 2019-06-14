package com.imooc.sell.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ProductVo;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.entity.ProductCategoryEntity;
import com.imooc.sell.entity.ProductInfoEntity;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.service.ProductCategoryService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.utils.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController  {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有上架商品
        Map<String,Object> map=new HashMap<>();
        //上架条件
        map.put("product_status", ProductStatusEnum.UP.getCode());
        Collection<ProductInfoEntity> productInfoEntityList = productInfoService.listByMap(map);
        //java8新特性，映射出集合中每一个ProductInfoEntity对象的属性的集合
        List<Integer> categoryTypeList=productInfoEntityList.stream()
                .map(ProductInfoEntity->ProductInfoEntity.getCategoryType()).collect(Collectors.toList());
        //2.查询类目
        QueryWrapper<ProductCategoryEntity> queryWrapper=new QueryWrapper();
        queryWrapper.lambda().in(ProductCategoryEntity::getCategoryType,categoryTypeList);
        List<ProductCategoryEntity> productCategoryEntityList = productCategoryService.list(queryWrapper);
        //3.数据拼接
        List<ProductVo> productVoList=new ArrayList<>();

        for (ProductCategoryEntity productCategoryEntity:productCategoryEntityList){
            ProductVo productVo=new ProductVo();
            productVo.setCategoryName(productCategoryEntity.getCategoryName());
            productVo.setCategoryType(productCategoryEntity.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for(ProductInfoEntity productInfoEntity:productInfoEntityList){
                if(productInfoEntity.getCategoryType().equals(productCategoryEntity.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    //spring工具类，把一个对象属性的值copy到另一个
                    BeanUtils.copyProperties(productInfoEntity,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVo.setProductInfoVOList(productInfoVOList);
            productVoList.add(productVo);
        }

        return ResultVoUtil.success(productVoList);
    }
}
