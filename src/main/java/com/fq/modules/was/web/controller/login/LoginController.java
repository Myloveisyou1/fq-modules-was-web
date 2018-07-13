package com.fq.modules.was.web.controller.login;

import com.fq.modules.was.web.entity.setting.User;
import com.fq.modules.was.web.entity.common.Result;
import com.fq.modules.was.web.service.login.LoginService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.MD5Util;
import com.fq.modules.was.web.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = "/signIn")
    public Result login(HttpServletRequest request, @RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password){

        //校验登陆
        Map<String,Object> map = service.findUser(userName, MD5Util.getMD5(password));
        HttpSession session = request.getSession();
        User user = (User)map.get("user");
        if(CommonUtil.isNotEmpty(user)){
            session.setAttribute("user",user);
            return ResultUtil.success(map,null);
        }else{
            return null;
        }
    }

    /**
     * 退出登陆
     * @param request
     * @return
     */
    @GetMapping(value = "/signOut")
    public Result loginOut(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        boolean flag = service.loginOut(user);
        session.removeAttribute("user");
        return ResultUtil.success(flag,null);

    }
}
