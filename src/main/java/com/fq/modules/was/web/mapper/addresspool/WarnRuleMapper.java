package com.fq.modules.was.web.mapper.addresspool;

import com.fq.modules.was.web.entity.addresspool.WarnRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 预警提醒规则
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 17:15:52
 */
@Component
@Mapper
public interface WarnRuleMapper {

    List<WarnRule> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    WarnRule selectById(Integer wasId);

    boolean addWarnRule(WarnRule warnRule);

    boolean optUpdateWarnRule(WarnRule warnRule);

    int deleteById(Integer wasId);

    WarnRule selectByWasTypeAndSource(@Param("wasType" ) String wasType, @Param("wasSource" ) String wasSource);
}
