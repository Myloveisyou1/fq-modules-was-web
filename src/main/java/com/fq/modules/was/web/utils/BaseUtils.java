package com.fq.modules.was.web.utils;


import com.fq.modules.was.web.entity.setting.Menu;
import com.fq.modules.was.web.entity.setting.MenuList;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/31 0031 10:38
 */
public class BaseUtils {

    /**
     * 统一处理登陆的时候返回的权限列表
     *
     * @param menuList
     * @return
     */
    public static List<MenuList> getMenuListForLogin(List<Menu> menuList) {

        List<MenuList> backList = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            MenuList bean = new MenuList();
            Menu mi = menuList.get(i);
            if (mi.getParentCode() == 0) {
                List<MenuList> list = new ArrayList<>();
                for (int j = 0; j < menuList.size(); j++) {
                    Menu mj = menuList.get(j);
                    MenuList beanj = new MenuList();
                    if (mj.getParentCode() != 0) {
                        if (mi.getCode() == mj.getParentCode()) {
                            beanj.setGid(mj.getGid());
                            beanj.setMenuName(mj.getMenuName() + ";" + mj.getMenuType());
                            beanj.setIcon(mj.getIcon());
                            beanj.setUrl(mj.getUrl());
                            list.add(beanj);
                        }
                    }

                }
                bean.setGid(mi.getGid());
                bean.setMenuName(mi.getMenuName());
                bean.setIcon(mi.getIcon());
                bean.setUrl(mi.getUrl());
                bean.setMenuList(list);
                backList.add(bean);
            }

        }

        return backList;
    }

    /**
     * 菜单列表
     *
     * @param menuList
     * @return
     */
    public static List<Menu> getMenuList(List<Menu> menuList) {

        List<Menu> backList = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            Menu mi = menuList.get(i);
            if (mi.getParentCode() == 0) {
                List<Menu> list = new ArrayList<>();
                for (int j = 0; j < menuList.size(); j++) {
                    Menu mj = menuList.get(j);
                    if (mj.getParentCode() != 0) {
                        if (mi.getCode() == mj.getParentCode()) {

                            list.add(mj);
                        }
                    }

                }

                mi.setMenuList(list);
                backList.add(mi);
            }

        }

        return backList;

    }


    /**
     * 分页参数处理
     * @param pageNumber
     * @param pageSize
     * @return
     */
//    public static Pages pages(Integer pageNumber,Integer pageSize) {
//
//        Pages pages = new Pages();
//        pages.setPageNumber(pageNumber);
//        pages.setPageSize(pageSize);
//
//        return pages;
//    }

    /**
     * 处理分页结果
     * @return
     */
//    public static Pages pagesResult(Pages pages,int count) {
//
//        pages.setTotalCount(count);
//        if (pages.getPageNumber() == 1) {
//            pages.setFirst(true);
//            pages.setLast(false);
//        }
//        pages.setTotalPage((count%pages.getPageSize())>0?(count/pages.getPageSize()+1):(count/pages.getPageSize()));
//
//        return pages;
//    }
}
