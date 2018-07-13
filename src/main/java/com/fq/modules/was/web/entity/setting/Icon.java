package com.fq.modules.was.web.entity.setting;

import lombok.Data;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/6/2 0002 14:48
 */
@Data
public class Icon {

    private Long gid;

    private String name;
    private String url;

    private Long version;

    private String createTime;

    private String updateTime;

    private Integer status;
}
