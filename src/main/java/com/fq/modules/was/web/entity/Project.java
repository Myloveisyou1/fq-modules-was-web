package com.fq.modules.was.web.entity;

import lombok.Data;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/6/1 0001 10:40
 */
@Data
public class Project {

    private Long gid;

    private String projectName;

    private String nameEn;

    private Long version;

    private String createTime;

    private String updateTime;

    private Integer status;
}
