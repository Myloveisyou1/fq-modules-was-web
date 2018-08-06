package com.fq.modules.was.web.service.addresspool;

import com.fq.modules.was.web.entity.addresspool.WarnRule;

import java.util.Map;

/**
 * 预警提醒规则
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 17:15:52
 */
public interface WarnRuleService {

    Map<String, Object> queryPage(Map<String, Object> params);

    WarnRule selectById(Integer wasId);

    boolean insert(WarnRule warnRule);

    boolean updateById(WarnRule warnRule);

    int deleteById(Integer wasId);

    WarnRule selectByTypeAndSource(String wasType, String wasSource);
}

