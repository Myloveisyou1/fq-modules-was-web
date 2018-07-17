package com.fq.modules.was.web.service.payrecord;

import com.fq.modules.was.web.entity.payrecord.PayOperatorHistory;
import com.fq.modules.was.web.entity.payrecord.PayRecord;

import java.util.List;
import java.util.Map;

/**
 * 钱包转币操作记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 15:53:09
 */
public interface  PayRecordService {

    Map<String, Object> queryPage(Map<String, Object> params);

    PayRecord selectById(Integer wasId);

    boolean insert(PayRecord payRecord);

    boolean updateById(PayRecord payRecord);

    int deleteById(Integer wasId);

    boolean doAgain(Integer wasId, String oType);

    List<PayOperatorHistory> findHistoryByWasId(Integer wasId);
}

