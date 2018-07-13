package com.fq.modules.was.web.entity.setting;

import lombok.Data;

/**
 * @Descript:   角色权限关联
 * @Author: liuyingjie
 * @Date: create in 2018/5/31 0031 14:29
 */
@Data
public class MenuRole {

    private Long gid;

    private Long roleId;

    private Long menuId;

    private Long version;

    private String createTime;

    private String updateTime;

    private Integer status;
}
