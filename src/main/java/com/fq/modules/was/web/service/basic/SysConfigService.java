package com.fq.modules.was.web.service.basic;

import com.fq.modules.was.web.entity.basic.SysConfig;

import java.util.Map;

/**
 * 系统参数配置表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-18 14:18:40
 */
public interface SysConfigService {

    Map<String, Object> queryPage(Map<String, Object> params);

    SysConfig selectById(Long wasId);

    boolean insert(SysConfig sysConfig);

    boolean updateById(SysConfig sysConfig);

    int deleteById(Long wasId);

    Map<String, Object> selectByTypeAndSource(String wasType, String wasSource);
}

