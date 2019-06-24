package com.imooc.sell.controller;

import com.imooc.sell.entity.ProductCategoryEntity;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.CategoryFrom;
import com.imooc.sell.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author ：cjy
 * @description ：卖家端类目
 * @CreateTime ：Created in 2019/6/24 14:47
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategoryEntity> ProductCategoryEntityList = productCategoryService.list();
        map.put("ProductCategoryEntityList",ProductCategoryEntityList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 修改/添加页面
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false)Integer categoryId,
                              Map<String,Object> map){
        //判断是否传值
        if(categoryId!=null){
            ProductCategoryEntity categoryEntity = productCategoryService.getById(categoryId);
            map.put("categoryEntity",categoryEntity);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 新增和修改
     * @param categoryFrom
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryFrom categoryFrom,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        try {
            ProductCategoryEntity categoryEntity=new ProductCategoryEntity();
            if(categoryFrom.getCategoryId()!=null){
                categoryEntity = productCategoryService.getById(categoryFrom.getCategoryId());
            }
            BeanUtils.copyProperties(categoryFrom,categoryEntity);
            productCategoryService.saveOrUpdate(categoryEntity);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
