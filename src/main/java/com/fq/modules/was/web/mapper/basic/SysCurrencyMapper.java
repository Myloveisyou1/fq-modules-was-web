package com.fq.modules.was.web.mapper.basic;

import com.fq.modules.was.web.entity.basic.SysCurrency;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 币种类型表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
@Component
@Mapper
public interface SysCurrencyMapper {

    List<Map<String,Object>> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    SysCurrency selectById(Integer wasId);

    boolean addSysCurrency(SysCurrency sysCurrency);

    boolean optUpdateSysCurrency(SysCurrency sysCurrency);

    int deleteById(Integer wasId);

    SysCurrency selectByWasShortName(String wasShortName);

    List<SysCurrency> findAll();
}
