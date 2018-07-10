package com.fq.modules.was.web.service;

import com.fq.modules.was.web.entity.WasSysLog;

import java.util.Map;

/**
 * 系统操作日志表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-10 10:39:15
 */
public interface  WasSysLogService {

    Map<String, Object> queryPage(Map<String, Object> params);

    WasSysLog selectById(Long wasId);

    boolean insert(WasSysLog wasSysLog);

    boolean updateById(WasSysLog wasSysLog);

}

