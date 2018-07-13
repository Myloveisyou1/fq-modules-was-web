package com.fq.modules.was.web.entity.setting;

import lombok.Data;

import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/25 0025 17:36
 */
@Data
public class MenuList {

    private Long gid;
    private String icon;
    private String menuName;
    private String url;
    private List<MenuList> menuList;
}
