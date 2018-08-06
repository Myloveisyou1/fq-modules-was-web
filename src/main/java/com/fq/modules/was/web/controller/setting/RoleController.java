package com.fq.modules.was.web.controller.setting;


import com.fq.modules.was.web.entity.setting.Role;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.setting.RoleService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/29 0029 17:39
 */
@RestController
@RequestMapping(value = "/role" )
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     *
     * @param roleName
     * @return
     */
    @GetMapping(value = "/findAllRole" )
    public Result findAllRole(@RequestParam(value = "roleName", required = false) String roleName) {

        return ResultUtil.success(roleService.findAllRole(roleName), null);
    }

    /**
     * 所有角色
     *
     * @param roleId
     * @return
     */
    @GetMapping(value = "/findRoleById" )
    public Result findRoleById(@RequestParam(value = "roleId" ) Long roleId) {

        return ResultUtil.success(roleService.findMenuByRole(roleId), null);
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @GetMapping(value = "/updateRole" )
    public Result updateRole(Role role) {

        return ResultUtil.success(roleService.updateRole(role), null);

    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @GetMapping(value = "/addRole" )
    public Result addRole(Role role, String menus) {

        return ResultUtil.success(roleService.addRole(role, menus), null);
    }

    /**
     * 修改角色权限
     *
     * @param role
     * @param ymenus 拥有的权限
     * @param nmenus 没有的权限
     * @return
     */
    @GetMapping(value = "/updateRoleMenu" )
    public Result updateRomeMenu(Role role, @RequestParam(value = "ymenus" ) String ymenus, @RequestParam(value = "nmenus" ) String nmenus) {

        return ResultUtil.success(roleService.updateRomeMenu(role, ymenus, nmenus), null);
    }

    /**
     * 删除角色
     *
     * @return
     */
    @GetMapping(value = "/deleteRole" )
    public Result deleteRole(@RequestParam(value = "gid" ) Long gid) {

        return ResultUtil.success(roleService.deleteRole(gid), null);
    }
}
