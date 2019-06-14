package com.imooc.sell.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.sell.dao.ProductCategoryDAO;
import com.imooc.sell.entity.ProductCategoryEntity;
import com.imooc.sell.service.ProductCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDAO,ProductCategoryEntity> implements ProductCategoryService{

}
