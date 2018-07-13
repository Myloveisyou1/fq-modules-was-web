package com.fq.modules.was.web.service.logs;

import com.fq.modules.was.web.entity.logs.SysLog;

import java.util.Map;

/**
 * 系统操作日志表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-11 19:01:03
 */
public interface  SysLogService {

    Map<String, Object> queryPage(Map<String, Object> params);

    SysLog selectById(Long wasId);

    boolean insert(SysLog sysLog);

    boolean updateById(SysLog sysLog);

    int deleteById(Long wasId);

}

