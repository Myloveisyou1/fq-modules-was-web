package com.fq.modules.was.web.service;

import com.fq.modules.was.web.entity.WasDataDictionary;

import java.util.List;
import java.util.Map;

/**
 * 数据字典表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-05 10:20:55
 */
public interface WasDataDictionaryService {

    Map<String,Object> queryPage(Map<String, Object> params);

    WasDataDictionary selectById(Integer wasId);

    boolean insert(WasDataDictionary wasDataDictionary);

    boolean updateById(WasDataDictionary wasDataDictionary);

    int deleteBatchIds(List<Integer> integers);

    int disabledAll(String way);

    String findAll(Map<String, Object> params);

    Map<String,Object> getNewHeight(String wasType);

}

