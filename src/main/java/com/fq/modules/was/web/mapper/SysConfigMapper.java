package com.fq.modules.was.web.mapper;

import com.fq.modules.was.web.entity.SysConfig;
import com.fq.modules.was.web.entity.common.Pages;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 16:54:47
 */
@Mapper
public interface SysConfigMapper {

    List<SysConfig> pageQueryList(SysConfig sysConfig, Pages pages);
}
