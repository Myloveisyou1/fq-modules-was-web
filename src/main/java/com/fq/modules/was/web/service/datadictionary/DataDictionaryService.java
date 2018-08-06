package com.fq.modules.was.web.service.datadictionary;

import com.fq.modules.was.web.entity.datadictionary.DataDictionary;

import java.util.Map;

/**
 * 数据字典表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-17 14:16:38
 */
public interface DataDictionaryService {

    Map<String, Object> queryPage(Map<String, Object> params);

    DataDictionary selectById(Integer wasId);

    boolean insert(DataDictionary dataDictionary);

    boolean updateById(DataDictionary dataDictionary);

    int deleteById(Integer wasId);

    int disabledAll(String way);

    Map<String, Object> getNewHeight(String wasType);

    String findAll(Map<String, Object> map);
}

