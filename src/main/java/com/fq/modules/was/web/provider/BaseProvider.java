package com.fq.modules.was.web.provider;

import com.fq.modules.was.web.utils.CommonUtil;

/**
 * @Descript:
 * @Author: liuyingjie
 * @Date: create in 2018/5/30 0030 11:46
 */
public class BaseProvider {
    /**
     * 用户相关操作===============================================
     * @param userName
     * @return
     */

    public String findAllUser(String userName) {

        StringBuffer sql = new StringBuffer("select gid,user_name userName,password,tel,email,role_id roleId,role_name roleName,create_time createTime,update_time updateTime,login_time loginTime,status,version from was_sys_user where 1=1");
        if (CommonUtil.isNotEmpty(userName)) {
            sql.append(" AND user_name like '%"+userName+"%'");
        }

        sql.append(" order by gid desc");

        return sql.toString();
    }



    /**
     * 日志相关操作===============================================
     * @param
     * @return
     */

    public String pagingQueryLogs(Integer start,Integer end,String startTime,String endTime) {

        StringBuffer sql = new StringBuffer("select gid,url,method,ip,class_method classMethod,args,start_time startTime,end_time endTime,time,rep_data repData from logs where 1=1");

        if (CommonUtil.isNotEmpty(startTime)) {
            sql.append(" AND start_time >= '"+startTime+" 00:00:00'");
        }
        if (CommonUtil.isNotEmpty(endTime)) {
            sql.append(" AND end_time <= '"+endTime+" 23:59:59'");
        }
        sql.append(" order by gid desc limit "+start+","+end);

        return sql.toString();
    }

    public String pagingQueryLogsCount(String startTime,String endTime) {

        StringBuffer sql = new StringBuffer("select count(gid) count from logs where 1=1");

        if (CommonUtil.isNotEmpty(startTime)) {
            sql.append(" AND start_time >= '"+startTime+" 00:00:00'");
        }
        if (CommonUtil.isNotEmpty(endTime)) {
            sql.append(" AND end_time <= '"+endTime+" 23:59:59'");
        }

        return sql.toString();
    }
}
