package com.fq.modules.was.web.mapper.addresspool;

import com.fq.modules.was.web.entity.addresspool.WarnHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 预警提醒历史记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 18:18:34
 */
@Component
@Mapper
public interface WarnHistoryMapper {

    List<WarnHistory> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    WarnHistory selectById(Integer wasId);

    boolean addWarnHistory(WarnHistory warnHistory);

    boolean optUpdateWarnHistory(WarnHistory warnHistory);

    int deleteById(Integer wasId);

}
