package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品 包含类目
 */
@Data
public class ProductVo implements Serializable{
    private static final long serialVersionUID = -7754921630271829344L;
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> ProductInfoVOList;
}
