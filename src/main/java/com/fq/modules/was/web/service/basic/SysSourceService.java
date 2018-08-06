package com.fq.modules.was.web.service.basic;

import com.fq.modules.was.web.entity.basic.SysSource;

import java.util.List;
import java.util.Map;

/**
 * 业务平台表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
public interface SysSourceService {

    Map<String, Object> queryPage(Map<String, Object> params);

    SysSource selectById(Integer wasId);

    boolean insert(SysSource sysSource);

    boolean updateById(SysSource sysSource);

    int deleteById(Integer wasId);

    List<SysSource> findAll();
}

