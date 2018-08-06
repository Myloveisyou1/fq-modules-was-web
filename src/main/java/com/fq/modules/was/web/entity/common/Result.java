package com.fq.modules.was.web.entity.common;

import lombok.Data;

/**
 * @Author: 刘英杰
 * @Description: Http请求返回的最外层对象
 * @Date: Created in 2017/12/26 13:02
 */
@Data
public class Result<T> {

    /**
     * 错误码
     **/
    private Integer code;
    /**
     * 错误提示
     **/
    private String msg;
    /*** 具体的内容 */
    private T data;
    /***分页信息*/
    private Pages page;

    private Long totalCount;
}
