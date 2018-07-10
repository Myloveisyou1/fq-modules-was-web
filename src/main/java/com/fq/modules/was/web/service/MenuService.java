package com.fq.modules.was.web.service;

import com.fq.modules.was.web.entity.Icon;
import com.fq.modules.was.web.entity.Menu;
import com.fq.modules.was.web.entity.MenuList;

import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/31 0031 10:32
 */
public interface MenuService {

    List<Menu> findAllMenu();

    List<Icon> findIconList();

    boolean addIcon(String name, String url);

    boolean addMenu(Menu menu);

    boolean delMenu(Long gid);

    Menu findById(Long gid);

    boolean editMenu(Menu menu);

    List<Menu> findChildMenu(String parentCode);

    List<MenuList> findMenuByRole(Long roleId);

    Menu findMenuByUrl(String url);
}
