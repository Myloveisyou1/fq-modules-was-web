package com.fq.modules.was.web.service.login;

import com.fq.modules.was.web.entity.setting.User;

import java.util.Map;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/24 0024 16:13
 */
public interface LoginService {

    Map<String,Object> findUser(String userName, String password);

    boolean loginOut(User user);

    boolean checkPermission(User user, String url);
}
