package com.imooc.sell.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.sell.converter.OrderMaster2OrderDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.entity.OrderMasterEntity;
import com.imooc.sell.entity.ProductCategoryEntity;
import com.imooc.sell.entity.ProductInfoEntity;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.ProductFrom;
import com.imooc.sell.service.ProductCategoryService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.security.Key;
import java.util.List;
import java.util.Map;

/**
 * @Author ：cjy
 * @description ：卖家端商品
 * @CreateTime ：Created in 2019/6/24 9:29
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "3") Integer size,
                             Map<String,Object> map){
        IPage<ProductInfoEntity> productInfoEntityPage=new Page<>(page,size);
        productInfoEntityPage = productInfoService.page(productInfoEntityPage);

        map.put("productInfoEntityPage",productInfoEntityPage);
        map.put("pages",productInfoEntityPage.getPages());
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productInfoService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productInfoService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 修改/添加页面
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                              Map<String,Object> map){
        //判断是否为空
        if(!StringUtils.isEmpty(productId)){
            ProductInfoEntity productInfoEntity = productInfoService.getById(productId);
            map.put("productInfoEntity",productInfoEntity);
        }
        //查询类目
        List<ProductCategoryEntity> productCategoryEntityList = productCategoryService.list();
        map.put("productCategoryEntityList",productCategoryEntityList);
        return new ModelAndView("product/index",map);
    }

    /**
     * 商品保存和更新
     * @param productFrom
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductFrom productFrom,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        ProductInfoEntity productInfoEntity=new ProductInfoEntity();
        try{
            //ProductId不为空，说明是新增
            if(!StringUtils.isEmpty(productFrom.getProductId())){
                productInfoEntity = productInfoService.getById(productFrom.getProductId());
            }else {
                productFrom.setProductId(KeyUtil.getUniqueKey());
            }

            BeanUtils.copyProperties(productFrom,productInfoEntity);
            productInfoService.saveOrUpdate(productInfoEntity);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
