package com.fq.modules.was.web.utils;

import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.enums.ResultEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 刘英杰
 * @Description: 统一处理返回值
 * @Date: Created in 2017/12/26 13:09
 */
public class ResultUtil {

    public static Result success(Object object, Pages pages){
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());

        result.setData(object);
        result.setPage(pages);
        if(CommonUtil.isNotEmpty(pages)) {

            result.setTotalCount(pages.getTotalCount());
        }

        return result;
    }

    public static Result success(){
        return success(null,null);
    }

    public static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        result.setPage(null);
        return result;
    }

    public static Map<String,Object> initParams(Object o,Pages pages) {

        Map<String,Object> params = new HashMap<>();
        params.put("bean",o);

        //设置分页信息
        pages.setPageNumber((pages.getPageNumber()-1)*pages.getPageSize());
        params.put("pages",pages);

        return params;
    }
}
