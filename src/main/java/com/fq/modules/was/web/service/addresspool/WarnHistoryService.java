package com.fq.modules.was.web.service.addresspool;

import com.fq.modules.was.web.entity.addresspool.WarnHistory;

import java.util.Map;

/**
 * 预警提醒历史记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 18:18:34
 */
public interface WarnHistoryService {

    Map<String, Object> queryPage(Map<String, Object> params);

    WarnHistory selectById(Integer wasId);

    boolean insert(WarnHistory warnHistory);

    boolean updateById(WarnHistory warnHistory);

    int deleteById(Integer wasId);

}

