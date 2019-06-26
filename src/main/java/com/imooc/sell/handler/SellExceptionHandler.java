package com.imooc.sell.handler;

        import com.imooc.sell.VO.ResultVO;
        import com.imooc.sell.exception.SellException;
        import com.imooc.sell.exception.SellerAuthorizeException;
        import com.imooc.sell.utils.ResultVoUtil;
        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.ResponseBody;
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

    //拦截SellException修改返回异常文本
    @ExceptionHandler
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        return ResultVoUtil.error(e.getMessage(),e.getCode());
    }

}
