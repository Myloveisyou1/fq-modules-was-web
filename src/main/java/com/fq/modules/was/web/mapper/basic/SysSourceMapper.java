package com.fq.modules.was.web.mapper.basic;

import com.fq.modules.was.web.entity.basic.SysSource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 业务平台表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
@Component
@Mapper
public interface SysSourceMapper {

    List<SysSource> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    SysSource selectById(Integer wasId);

    boolean addSysSource(SysSource sysSource);

    boolean optUpdateSysSource(SysSource sysSource);

    int deleteById(Integer wasId);

    SysSource selectByWasSource(String wasSource);

    List<SysSource> findAll();
}
