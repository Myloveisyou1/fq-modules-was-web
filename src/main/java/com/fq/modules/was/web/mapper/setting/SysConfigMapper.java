package com.fq.modules.was.web.mapper.setting;

import com.fq.modules.was.web.entity.setting.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 *
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 11:14:49
 */
@Component
@Mapper
public interface SysConfigMapper {

    List<SysConfig> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    SysConfig selectById(Long wasId);

    boolean addSysConfig(SysConfig sysConfig);

    boolean optUpdateSysConfig(SysConfig sysConfig);

    int deleteById(Long wasId);

    Map<String,Object> selectByTypeAndSource(@Param("wasType") String wasType, @Param("wasSource") String wasSource);
}
