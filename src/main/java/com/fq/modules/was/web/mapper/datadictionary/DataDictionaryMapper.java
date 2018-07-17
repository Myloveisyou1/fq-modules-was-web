package com.fq.modules.was.web.mapper.datadictionary;

import com.fq.modules.was.web.entity.datadictionary.DataDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 数据字典表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-17 14:16:38
 */
@Component
@Mapper
public interface DataDictionaryMapper {

    List<DataDictionary> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    DataDictionary selectById(Integer wasId);

    boolean addDataDictionary(DataDictionary dataDictionary);

    boolean optUpdateDataDictionary(DataDictionary dataDictionary);

    int deleteById(Integer wasId);

    int updateBlockNumByWasType(@Param(value = "wasType" ) String wasType, @Param(value = "blockNum" ) Integer blockNumber);

    int disabledAll(@Param("way" ) String way);

    List<DataDictionary> findAll(Map<String,Object> params);
}
