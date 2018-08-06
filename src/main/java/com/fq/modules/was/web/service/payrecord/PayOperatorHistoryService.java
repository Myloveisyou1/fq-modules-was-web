package com.fq.modules.was.web.service.payrecord;

import com.fq.modules.was.web.entity.payrecord.PayOperatorHistory;

import java.util.Map;

/**
 * 提币失败的操作历史表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-17 09:44:07
 */
public interface PayOperatorHistoryService {

    Map<String, Object> queryPage(Map<String, Object> params);

    PayOperatorHistory selectById(Integer wasId);

    boolean insert(PayOperatorHistory payOperatorHistory);

    boolean updateById(PayOperatorHistory payOperatorHistory);

    int deleteById(Integer wasId);

}

