package com.imooc.sell.handler;

import com.imooc.sell.exception.SellerAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author ：cjy
 * @description ：
 * @CreateTime ：Created in 2019/6/25 0:03
 */
@ControllerAdvice
public class SellExceptionHandler {
    //拦截登陆异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("login/login");
    }
}
