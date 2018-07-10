package com.fq.modules.was.web.controller;

import com.fq.modules.was.web.entity.Menu;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.MenuService;
import com.fq.modules.was.web.utils.ResultUtil;
import com.fq.modules.was.web.entity.Menu;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.MenuService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descript: 权限菜单
 * @Author: liuyingjie
 * @Date: create in 2018/5/31 0031 10:31
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService service;

    /**
     * 查询所有权限菜单
     * @return
     */
    @GetMapping(value = "/findAllMenu")
    public Result findAllMenu() {

        return ResultUtil.success(service.findAllMenu(),null);
    }

    /**
     * 查询所有图标
     * @return
     */
    @GetMapping(value = "/findIconList")
    public Result findIconList() {

        return ResultUtil.success(service.findIconList(),null);
    }
    /**
     * 添加图标
     * @return
     */
    @GetMapping(value = "/addIcon")
    public Result addIcon(@RequestParam(value = "name") String name, @RequestParam(value = "url") String url) {

        return ResultUtil.success(service.addIcon(name,url),null);
    }

    /**
     * 添加菜单
     * @return
     */
    @GetMapping(value = "/addMenu")
    public Result addMenu(Menu menu) {

        return ResultUtil.success(service.addMenu(menu),null);
    }

    /**
     * 删除菜单
     * @return
     */
    @GetMapping(value = "/delMenu")
    public Result delMenu(Long gid) {

        return ResultUtil.success(service.delMenu(gid),null);
    }

    /**
     * 根据id查询菜单信息
     * @param gid
     * @return
     */
    @GetMapping(value = "/findById")
    public Result findById(@RequestParam(value = "id") Long gid) {

        return ResultUtil.success(service.findById(gid),null);
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @GetMapping(value = "/editMenu")
    public Result editMenu(Menu menu) {

        return ResultUtil.success(service.editMenu(menu),null);
    }

    /**
     * 查询子菜单
     * @param parentCode
     * @return
     */
    @GetMapping(value = "/findChildMenu")
    public Result findChildMenu(@RequestParam(value = "parentCode") String parentCode) {

        return ResultUtil.success(service.findChildMenu(parentCode),null);
    }

    /**
     * 查询角色的权限
     * @return
     */
    @GetMapping(value = "/findMenuByRole")
    public Result findMenuByRole(Long roleId) {

        return ResultUtil.success(service.findMenuByRole(roleId),null);
    }

}
