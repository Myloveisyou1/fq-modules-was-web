package com.fq.modules.was.web.mapper.basic;

import com.fq.modules.was.web.entity.basic.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 系统参数配置表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-18 14:18:40
 */
@Component
@Mapper
public interface SysConfigMapper {

    List<Map<String, Object>> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    SysConfig selectById(Long wasId);

    boolean addSysConfig(SysConfig sysConfig);

    boolean optUpdateSysConfig(SysConfig sysConfig);

    int deleteById(Long wasId);

    SysConfig selectByThree(@Param("wasType" ) String wasType, @Param("wasSource" ) String wasSource, @Param("wasConfigType" ) String wasConfigType);

    Map<String, Object> selectByTypeAndSource(@Param("wasType" ) String wasType, @Param("wasSource" ) String wasSource);
}
