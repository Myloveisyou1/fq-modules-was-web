package com.fq.modules.was.web.controller.setting;

import com.fq.modules.was.web.entity.setting.User;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.setting.UserService;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Descript: 用户管理
 * @Author: liuyingjie
 * @Date: create in 2018/5/30 0030 9:17
 */
@RestController
@RequestMapping(value = "/user" )
public class UserController {

    @Autowired
    private UserService service;

    /**
     * 根据账号查询
     *
     * @param userName
     * @return
     */
    @GetMapping(value = "/findUserByName" )
    public Result findUserByName(@RequestParam(value = "userName" ) String userName) {

        return ResultUtil.success(service.findUserByName(userName), null);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/updateUser" )
    public Result updateUser(User user) {

        return ResultUtil.success(service.updateUser(user), null);

    }

    /**
     * 删除用户信息
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/deleteUser" )
    public Result deleteUser(User user) {

        return ResultUtil.success(service.deleteUser(user), null);

    }

    /**
     * 校验密码
     *
     * @param userName
     * @param password
     * @return
     */
    @GetMapping(value = "/checkPassword" )
    public Result checkPassword(@RequestParam(value = "userName" ) String userName, @RequestParam(value = "password" ) String password) {

        return ResultUtil.success(service.checkPassword(userName, password), null);
    }

    /**
     * 修改密码
     *
     * @param userName
     * @param password
     * @return
     */
    @GetMapping(value = "/updatePassword" )
    public Result updatePassword(@RequestParam(value = "userName" ) String userName, @RequestParam(value = "password" ) String password) {

        return ResultUtil.success(service.updatePassword(userName, password), null);
    }

    /**
     * 查询所有用户
     *
     * @param userName
     * @return
     */
    @GetMapping(value = "/findAllUser" )
    public Result findAllUser(@RequestParam(value = "userName", required = false) String userName) {

        List<User> list = service.findAllUser(userName);
        Pages page = new Pages();
        page.setTotalCount(list.size());
        return ResultUtil.success(list, page);
    }

    /**
     * 新增用户,初始密码123456
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/addUser" )
    public Result addUser(User user) {

        return ResultUtil.success(service.addUser(user), null);
    }

    /**
     * 重置用户密码为123456
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/resetPassword" )
    public Result resetPassword(User user) {

        return ResultUtil.success(service.resetPassword(user), null);
    }
}
