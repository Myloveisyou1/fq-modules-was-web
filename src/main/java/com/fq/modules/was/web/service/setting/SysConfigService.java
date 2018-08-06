package com.fq.modules.was.web.service.setting;

import com.fq.modules.was.web.entity.setting.SysConfig;

import java.util.Map;

/**
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 11:14:49
 */
public interface SysConfigService {

    Map<String, Object> queryPage(Map<String, Object> params);

    SysConfig selectById(Long wasId);

    boolean insert(SysConfig sysConfig);

    boolean updateById(SysConfig sysConfig);

    int deleteById(Long wasId);

    Map<String, Object> selectByTypeAndSource(String wasType, String wasSource);
}

