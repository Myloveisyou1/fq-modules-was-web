package com.fq.modules.was.web.service;

import com.fq.modules.was.web.entity.User;

import java.util.List;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/30 0030 9:19
 */
public interface UserService {

    List<User> findAllUser(String userName);

    boolean updateUser(User bean);

    User findUserByName(String userName);

    boolean checkPassword(String userName, String password);

    boolean updatePassword(String userName, String password);

    int deleteUser(User user);

    int addUser(User user);

}
