package com.fq.modules.was.web.mapper.datadictionary;

//import com.fq.modules.was.web.domain.WasDataDictionary;
//import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
//import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;

import com.fq.modules.was.web.entity.datadictionary.WasDataDictionary;
import com.fq.modules.was.web.provider.BaseProvider;
import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 数据字典表
 * 
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-05 10:54:20
 */
@Component
@Mapper
public interface WasDataDictionaryMapper {

    @Select("select was_id wasId,was_base_currency wasBaseCurrency,was_type wasType," +
            "was_begin_block wasBeginBlock,was_block_num wasBlockNum,was_gate_way wasGateWay," +
            "was_token_address wasTokenAddress,was_min_confirm wasMinConfirm," +
            "was_status wasStatus,was_precision wasPrecision," +
            "was_zero_gas_price wasZeroGasPrice,was_zero_gas_limit wasZeroGasLimit," +
            "was_transfer_gas_price wasTransferGasPrice,was_transfer_gas_limit wasTransferGasLimit," +
            "was_remark wasRemark,was_create_time wasCreateTime,was_last_time wasLastTime,was_coin_introduce_url wasCoinIntroduceUrl," +
            "was_block_browsers_url wasBlockBrowsersUrl,was_spare wasSpare from was_data_dictionary where was_id=#{wasId}")
    WasDataDictionary selectById(@Param("wasId" ) Integer wasId);

    @Insert("insert into was_data_dictionary (#{wasDataDictionary})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true,keyProperty = "wasId",keyColumn = "was_id")
    boolean insert(WasDataDictionary wasDataDictionary);

    @Update("update was_data_dictionary (#{wasDataDictionary}) where was_id = #{wasId}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateById(WasDataDictionary wasDataDictionary);

    @SelectProvider(type = BaseProvider.class,method = "pageQuery")
    List<WasDataDictionary> pageQuery(Map<String, Object> params);

    @SelectProvider(type = BaseProvider.class,method = "pageQueryCount")
    Long pageQueryCount(Map<String, Object> params);

    @Update("update was_data_dictionary set was_status = #{way}")
    int disabledAll(@Param("way" ) String way);

    @SelectProvider(type = BaseProvider.class,method = "findAll")
    List<WasDataDictionary> findAll(Map<String, Object> params);

    @Update("update was_data_dictionary set was_begin_block = #{blockNum} where was_type = #{wasType}")
    int updateBlockNumByWasType(@Param(value = "wasType" ) String wasType, @Param(value = "blockNum" ) Integer blockNumber);
}
