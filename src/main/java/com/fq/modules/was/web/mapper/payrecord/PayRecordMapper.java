package com.fq.modules.was.web.mapper.payrecord;

import com.fq.modules.was.web.entity.payrecord.PayRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 钱包转币操作记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 15:53:09
 */
@Component
@Mapper
public interface PayRecordMapper {

    List<Map<String, Object>> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    PayRecord selectById(Integer wasId);

    boolean addPayRecord(PayRecord payRecord);

    boolean optUpdatePayRecord(PayRecord payRecord);

    int deleteById(Integer wasId);

}
