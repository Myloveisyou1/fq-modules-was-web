package com.fq.modules.was.web.service.setting;

import com.fq.modules.was.web.entity.setting.User;

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

    boolean resetPassword(User user);
}
