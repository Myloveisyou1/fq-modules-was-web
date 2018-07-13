package com.fq.modules.was.web.mapper.logs;

import com.fq.modules.was.web.entity.logs.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 系统操作日志表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-11 19:01:03
 */
@Component
@Mapper
public interface SysLogMapper {

    List<SysLog> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    SysLog selectById(Long wasId);

    boolean addSysLog(SysLog sysLog);

    boolean optUpdateSysLog(SysLog sysLog);

    int deleteById(Long wasId);

}
