package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;
import lombok.Data;

@Data
public class SellException extends RuntimeException{
    private Integer code;


    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
