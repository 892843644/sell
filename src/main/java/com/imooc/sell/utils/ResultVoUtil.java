package com.imooc.sell.utils;

import com.imooc.sell.VO.ResultVO;

public class ResultVoUtil {
    /**
     *
     * @param obj 返回的data数据
     * @return 返回成功的结果对象
     */
    public static ResultVO success(Object obj){
        ResultVO resultVO= new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(obj);
        return resultVO;
    }
    public static ResultVO success(){
       return success(null);
    }

    /**
     *
     * @param msg 错误信息
     * @param code 提示码
     * @return
     */
    public static ResultVO error(String msg,Integer code){
        ResultVO resultVO= new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
