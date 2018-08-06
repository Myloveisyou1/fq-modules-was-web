package com.fq.modules.was.web.service.basic;

import com.fq.modules.was.web.entity.basic.SysCurrency;

import java.util.List;
import java.util.Map;

/**
 * 币种类型表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
public interface SysCurrencyService {

    Map<String, Object> queryPage(Map<String, Object> params);

    SysCurrency selectById(Integer wasId);

    boolean insert(SysCurrency sysCurrency);

    boolean updateById(SysCurrency sysCurrency);

    int deleteById(Integer wasId);

    List<SysCurrency> findAll();
}

