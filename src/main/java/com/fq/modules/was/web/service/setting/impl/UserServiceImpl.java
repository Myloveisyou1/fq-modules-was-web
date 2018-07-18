package com.fq.modules.was.web.service.setting.impl;

import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.entity.setting.Role;
import com.fq.modules.was.web.entity.setting.User;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.mapper.setting.RoleMapper;
import com.fq.modules.was.web.mapper.setting.UserMapper;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.service.setting.UserService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import com.fq.modules.was.web.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 查询所有用户
     * @param userName
     * @return
     */
    public List<User> findAllUser(String userName) {

        //记录系统日志
        String content = getUserName()+"在"+DatesUtils.time()+"查询了用户"+userName+"的信息";
        String result = "查询成功";
        sysLogMapper.addSysLog(new SysLog(3,getUserName(),content,result));
        return userMapper.findAllUser(userName);
    }

    /**
     * 修改用户信息
     * @param bean
     * @return
     */
    public boolean updateUser(User bean) {

        User user = userMapper.findByUserName(bean.getUserName());
        String content = "";
        if (CommonUtil.isNotEmpty(user)) {
            content = getUserName()+"在"+DatesUtils.time()+"修改用户"+user.getUserName()+"的信息,如下:";
            if (CommonUtil.isNotEmpty(bean.getEmail())) {
                content += "email="+bean.getEmail();
                user.setEmail(bean.getEmail());
            }
            if (CommonUtil.isNotEmpty(bean.getTel())) {
                content += "tel="+bean.getTel();
                user.setTel(bean.getTel());
            }
            if (CommonUtil.isNotEmpty(bean.getRoleId())) {
                Role role = roleMapper.findById(bean.getRoleId());
                if (CommonUtil.isNotEmpty(role)) {
                    content += "roleName="+role.getRoleName();
                    user.setRoleId(bean.getRoleId());
                    user.setRoleName(role.getRoleName());
                }

            }
            if (CommonUtil.isNotEmpty(bean.getStatus())) {
                if (bean.getStatus() == 0) {
                    content += "启用用户";
                } else {
                    content += "禁用用户";
                }
                user.setStatus(bean.getStatus());
            }

            user.setUpdateTime(DatesUtils.time());
            user.setVersion(user.getVersion()+1l);

            //记录系统日志
            String result = "修改成功";
            sysLogMapper.addSysLog(new SysLog(2,getUserName(),content,result));
        }

        return userMapper.optUpdateUser(user);
    }

    /**
     * 根据登录名查询数据
     * @param userName
     * @return
     */
    public User findUserByName(String userName) {

        return userMapper.findByUserName(userName);
    }

    /**
     * 校验密码
     * @param userName
     * @param password
     * @return
     */
    public boolean checkPassword(String userName, String password) {

        User user = userMapper.findByUserName(userName);
        if (CommonUtil.isNotEmpty(user)) {
            String str = user.getPassword();
            if (str.equals(MD5Util.getMD5(userName+MD5Util.getMD5(password)))) {
                return true;
            } else {
                //记录日志信息
                String content = getUserName()+"在"+DatesUtils.time()+"尝试修改密码";
                String result = "修改失败,失败原因:原始密码错误!";
                SysLog wasSysLog = new SysLog(2,getUserName(),content,result);
                sysLogMapper.addSysLog(wasSysLog);
                throw new WasWebException(ResultEnum.ERROR_OLD_PASSWORD);
            }
        }
        return false;
    }

    /**
     * 修改密码
     * @param userName
     * @param password
     * @return
     */
    public boolean updatePassword(String userName, String password) {

        User user = userMapper.findByUserName(userName);
        if (CommonUtil.isNotEmpty(user)) {
            String newPassword = MD5Util.getMD5(userName+MD5Util.getMD5(password));
            user.setPassword(newPassword);
            user.setVersion(user.getVersion()+1l);
            user.setUpdateTime(DatesUtils.time());
        }
        boolean flag = userMapper.optUpdateUser(user);

        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"修改了密码";
        String result = "修改成功";
        if (!flag) {
            result = "修改失败";
        }
        SysLog wasSysLog = new SysLog(2,getUserName(),content,result);
        sysLogMapper.addSysLog(wasSysLog);

        return flag;
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    public int deleteUser(User user) {

        String[] str = {};
        int ret = 0;
        if (user.getUserName().indexOf(",") != -1) {
            str = user.getUserName().split(",");

            for(int i=0;i<str.length;i++) {
                User bean = userMapper.findByUserName(str[i]);
                if (CommonUtil.isNotEmpty(bean)) {
                    ret += userMapper.deleteUser(bean.getGid());
                }
            }
        } else {
            User bean = userMapper.findByUserName(user.getUserName());
            if (CommonUtil.isNotEmpty(bean)) {
                ret += userMapper.deleteUser(bean.getGid());
            }
        }
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"删除用户:"+user.getUserName();
        String result = "删除成功";
        if (ret == 0) {
            result = "删除失败";
        }
        SysLog wasSysLog = new SysLog(4,getUserName(),content,result);
        sysLogMapper.addSysLog(wasSysLog);

        return ret;
    }

    /**
     * 新增用户
     * @return
     */
    public int addUser(User user) {
        //查询角色
        Role role = roleMapper.findById(user.getRoleId());
        if (CommonUtil.isNotEmpty(role)) {
            user.setRoleName(role.getRoleName());
        }
        //判断是否存在
        User bean = userMapper.findByUserName(user.getUserName());
        if(CommonUtil.isNotEmpty(bean)) {
            throw new WasWebException(ResultEnum.SAME_USER);
        }
        //这里设置初始密码
        String password = MD5Util.getMD5(user.getUserName()+MD5Util.getMD5("123456"));
        user.setPassword(password);
        user.setCreateTime(DatesUtils.time());
        user.setUpdateTime(DatesUtils.time());
        user.setLoginTime(DatesUtils.time());
        user.setStatus(0);
        user.setVersion(1l);

        int ret = userMapper.addUser(user);

        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"添加用户:"+user.getUserName();
        String result = "添加成功";
        if (ret == 0) {
            result = "添加失败";
        }
        SysLog wasSysLog = new SysLog(1,getUserName(),content,result);
        sysLogMapper.addSysLog(wasSysLog);

        return ret;
    }

    /**
     * 重置密码为123456
     * @param user
     * @return
     */
    @Override
    public boolean resetPassword(User user) {

        user.setPassword(MD5Util.getMD5(user.getUserName()+MD5Util.getMD5("123456")));

        boolean flag = userMapper.optUpdateUser(user);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"重置了用户:"+user.getUserName()+"的登录密码";
        String result = "操作成功";
        if (!flag) {
            result = "操作失败";
        }
        SysLog wasSysLog = new SysLog(1,getUserName(),content,result);
        sysLogMapper.addSysLog(wasSysLog);

        return flag;
    }
}
