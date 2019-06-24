package com.imooc.sell.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.entity.ProductInfoEntity;

import java.util.List;

public interface ProductInfoService extends IService<ProductInfoEntity> {
    /**
     *      增加库存
     * @param cartDTOList 购物车集合
     */
    public void increaseStock(List<CartDTO> cartDTOList);

    /**
     *      减少库存
     * @param cartDTOList 购物车集合
     */
    public void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 商品上架
     * @param productId
     * @return
     */
    public ProductInfoEntity onSale(String productId);

    /**
     * 商品下架
     * @param productId
     * @return
     */
    public ProductInfoEntity offSale(String productId);
}
