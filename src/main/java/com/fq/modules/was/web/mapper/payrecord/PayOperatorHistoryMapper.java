package com.fq.modules.was.web.mapper.payrecord;

import com.fq.modules.was.web.entity.payrecord.PayOperatorHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 提币失败的操作历史表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-17 09:44:07
 */
@Component
@Mapper
public interface PayOperatorHistoryMapper {

    List<Map<String, Object>> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    PayOperatorHistory selectById(Integer wasId);

    boolean addPayOperatorHistory(PayOperatorHistory payOperatorHistory);

    boolean optUpdatePayOperatorHistory(PayOperatorHistory payOperatorHistory);

    int deleteById(Integer wasId);

    List<PayOperatorHistory> findHistoryByWasId(@Param("wasId" ) Integer wasId, @Param("userName" ) String userName);
}
