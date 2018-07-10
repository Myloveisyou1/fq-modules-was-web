package com.fq.modules.was.web.mapper;

import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.provider.BaseProvider;
import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 钱包地址到账情况表
 * 
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 16:05:19
 */
@Component
@Mapper
public interface WasAddressMapper {

    @Select("select "
            +"was_id wasId,"
            +"was_type wasType,"
            +"was_address wasAddress,"
            +"was_expire_time wasExpireTime,"
            +"was_block_number wasBlockNumber,"
            +"was_remark wasRemark,"
            +"was_create_time wasCreateTime,"
            +"was_update_time wasUpdateTime,"
            +"was_source wasSource,"
            +"was_init_time wasInitTime,"
            +" from was_address where was_id=#{wasId}")
    WasAddress selectById(Long wasId);

    @Insert("insert into was_address (#{wasAddress})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true,keyProperty = "wasId",keyColumn = "was_id")
    boolean insert(WasAddress wasAddress);

    @Update("update was_address (#{wasAddress}) where was_id = #{wasId}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateById(WasAddress wasAddress);

//    @SelectProvider(type = BaseProvider.class,method = "pageQueryWasAddressList")
    List<WasAddress> pageQuery(WasAddress wasAddress, Pages pages);

//    @SelectProvider(type = BaseProvider.class,method = "pageQueryWasAddressCount")
    Long pageQueryCount(WasAddress wasAddress, Pages pages);
}
