package com.fq.modules.was.web.entity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Descript: 分页数据
 * @Author: liuyingjie
 * @Date: create in 2018/1/6 0006 14:31
 */
@Data
public class Pages implements Serializable{

    private boolean first;

    private boolean last;

    private Integer pageSize;//每页数据

    private Integer pageNumber;//页码

    private long totalCount;//总数据

    private Integer totalPage;//总页码

}
