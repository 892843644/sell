package com.imooc.sell.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.sell.dao.ProductInfoDAO;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.entity.ProductInfoEntity;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoDAO,ProductInfoEntity> implements ProductInfoService{
    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfoEntity productInfoEntity = this.getById(cartDTO.getProductId());
            //判断表中是否存在此id的商品
            if(productInfoEntity==null){
                //抛出异常
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //添加库存
            Integer newStock = productInfoEntity.getProductStock() + cartDTO.getProductQuantity();
            productInfoEntity.setProductStock(newStock);
            //通过id更新库存
            this.updateById(productInfoEntity);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for(CartDTO cartDTO: cartDTOList){
            //查询商品信息表
            ProductInfoEntity productInfoEntity = this.getById(cartDTO.getProductId());

            //判断表中是否存在此id的商品
            if(productInfoEntity==null){
                //抛出异常
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //获取减去购买数之后的新库存
            Integer newStock = productInfoEntity.getProductStock() - cartDTO.getProductQuantity();

            //判断新库存是否小于0
            if(newStock<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfoEntity.setProductStock(newStock);
            //更新库存
            this.updateById(productInfoEntity);
        }
    }

    @Override
    public ProductInfoEntity onSale(String productId) {
        //查询商品是否存在
        ProductInfoEntity productInfoEntity = this.getById(productId);
        if(productInfoEntity==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //判断商品是否在架，在架则错误
        if(productInfoEntity.getProductStatusEnum()== ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新为上架
        productInfoEntity.setProductStatus(ProductStatusEnum.UP.getCode());
        this.updateById(productInfoEntity);
        return productInfoEntity;
    }

    @Override
    public ProductInfoEntity offSale(String productId) {
        //查询商品是否存在
        ProductInfoEntity productInfoEntity = this.getById(productId);
        if(productInfoEntity==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //判断商品是否下架，下架则错误
        if(productInfoEntity.getProductStatusEnum()== ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新为上架
        productInfoEntity.setProductStatus(ProductStatusEnum.DOWN.getCode());
        this.updateById(productInfoEntity);
        return productInfoEntity;
    }
}
