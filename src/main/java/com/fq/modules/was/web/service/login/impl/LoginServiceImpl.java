package com.fq.modules.was.web.service.login.impl;

import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.entity.setting.Menu;
import com.fq.modules.was.web.entity.setting.MenuRole;
import com.fq.modules.was.web.entity.setting.Role;
import com.fq.modules.was.web.entity.setting.User;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.mapper.setting.MenuMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.mapper.setting.RoleMapper;
import com.fq.modules.was.web.mapper.setting.UserMapper;
import com.fq.modules.was.web.service.login.LoginService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import com.fq.modules.was.web.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 账户密码加密规则   MD5(账号+MD5(密码))
     * @param userName
     * @param password
     * @return
     */
    public Map<String,Object> findUser(String userName, String password) {

        //加密
        password = MD5Util.getMD5(userName+password);

        User user = mapper.findByUserNameAndPassword(userName,password);
        Map<String,Object> map = new HashMap<>();
        String content = userName+"在"+DatesUtils.time()+"登录系统";
        if(mapper.findByUserName(userName) != null){
            if(user != null){
                if (user.getStatus() == 0) {
                    map.put("user",user);
                    //获取权限信息
                    Long roleId = user.getRoleId();
                    //判断角色是否可用
                    Role role = roleMapper.findById(roleId);
                    if (CommonUtil.isNotEmpty(role)) {
                        if (role.getStatus() == 1) {
                            //记录系统日志
                            String result = "登录失败,用户所属角色被禁用!";
                            sysLogMapper.addSysLog(new SysLog(5,userName,content,result));
                            throw new WasWebException(ResultEnum.ROLE_DISABLED);
                        }
                    }
                    //修改登录时间
                    user.setLoginTime(DatesUtils.time());
                    mapper.optUpdateUser(user);

                    //记录系统日志
                    String result = "登录成功";
                    sysLogMapper.addSysLog(new SysLog(5,userName,content,result));

                    return map;
                } else {
                    //记录系统日志
                    String result = "登录失败,用户被禁用!";
                    sysLogMapper.addSysLog(new SysLog(5,userName,content,result));
                    throw new WasWebException(ResultEnum.USER_DISABLED);
                }

            }else{
                //记录系统日志
                String result = "登录失败,用户密码错误!";
                sysLogMapper.addSysLog(new SysLog(5,userName,content,result));
                throw new WasWebException(ResultEnum.ERROR_PASSWORD);
            }
        }else{
            //记录系统日志
            String result = "登录失败,账户不存在!";
            sysLogMapper.addSysLog(new SysLog(5,userName,content,result));
            throw  new WasWebException(ResultEnum.UNKNOW_ACCOUNT);
        }
    }

    /**
     * 校验登陆
     * @param sessionId
     * @return
     */
//    public boolean check(String sessionId) {
//
//        boolean flag = stringRedisTemplate.hasKey(sessionId);
//        if(flag){
//            //重新设置session有效期
//            String str = stringRedisTemplate.opsForValue().get(sessionId);
//            stringRedisTemplate.opsForValue().set(sessionId, str,1800, TimeUnit.SECONDS);
//        }else{
//            throw new WasWebException(ResultEnum.NOT_LOGIN);
//        }
//        return flag;
//    }

    /**
     * 退出登录
     * @param user
     * @return
     */
    public boolean loginOut(User user) {

        //记录系统日志
        String content = user.getUserName()+"在"+DatesUtils.time()+"登出系统";
        String result = "登出成功";
        SysLog bean = new SysLog(6,user.getUserName(),content,result);
        sysLogMapper.addSysLog(bean);
        return true;
    }

    /**
     * \校验权限,后期权限写入数据库
     * @param user
     * @return
     */
    public boolean checkPermission(User user,String url) {

        log.info(url);//http://localhost:8081/consume/findConsume
        //检验权限
        String[] str = url.split(":")[2].split("/");//   8081/consume/findConsume
        String checkUrl = "";
        for (int i=0;i<str.length;i++) {
            if (i != 0) {
                if (i != str.length -1) {
                    checkUrl += str[i]+"/";
                } else {
                    checkUrl += str[i];
                }
            }
        }
        System.out.println(checkUrl);

        //先判断权限存在不.然后判断是否拥有权限
        Menu menu = menuMapper.findMenuByUrl(checkUrl);
        if (CommonUtil.isNotEmpty(menu)) {
            MenuRole menuRole = menuMapper.findInfoByRoleAndMenu(Long.parseLong(user.getRoleId().toString()),menu.getGid());
            if (CommonUtil.isNotEmpty(menuRole)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
