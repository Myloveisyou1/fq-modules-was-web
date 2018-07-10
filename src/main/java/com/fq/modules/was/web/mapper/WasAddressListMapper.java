package com.fq.modules.was.web.mapper;

import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.entity.WasAddressList;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.provider.BaseProvider;
import com.fq.modules.was.web.utils.SimpleInsertLangDriver;
import com.fq.modules.was.web.utils.SimpleUpdateLangDriver;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 钱包地址列表（运维生成）
 * 
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 16:05:19
 */
@Component
@Mapper
public interface WasAddressListMapper {

    @Select("select "
                +"was_id wasId,"
                +"was_type wasType,"
                +"was_address wasAddress,"
                +"was_expire_time wasExpireTime,"
                +"was_source wasSource,"
                +"was_remark wasRemark,"
                +" from was_address_list where was_id=#{wasId}")
    WasAddressList selectById(Integer wasId);

    @Insert("insert into was_address_list (#{wasAddressList})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true,keyProperty = "wasId",keyColumn = "was_id")
    boolean insert(WasAddressList wasAddressList);

    @Update("update was_address_list (#{wasAddressList}) where was_id = #{wasId}")
    @Lang(SimpleUpdateLangDriver.class)
    boolean updateById(WasAddressList wasAddressList);

    @SelectProvider(type = BaseProvider.class,method = "pageQueryWasAddressLists")
    List<WasAddressList> pageQuery(Map<String,Object> params);

    @SelectProvider(type = BaseProvider.class,method = "pageQueryWasAddressListCount")
    Long pageQueryCount(Map<String,Object> params);

    @Select("select count(1) from was_address_list where was_type = #{wasType} and was_source = #{wasSource} and was_expire_time = #{wasExpireTime}")
    Long queryCountByWasTypeAndSource(@Param(value = "wasType") String wasType, @Param(value = "wasSource") String wasSource, @Param(value = "wasExpireTime") Integer wasExpireTime);

    @SelectProvider(type = BaseProvider.class,method = "queryPageTotalCount")
    List<WasAddress> queryPageTotalCount(Map<String,Object> stringObjectMap);

    @SelectProvider(type = BaseProvider.class,method = "queryPageTotalCounts")
    Long queryPageTotalCounts(Map<String,Object> stringObjectMap);

    @SelectProvider(type = BaseProvider.class,method = "queryPageSurplusCount")
    List<WasAddress> queryPageSurplusCount(Map<String,Object> stringObjectMap);

    @SelectProvider(type = BaseProvider.class,method = "queryPageSurplusCounts")
    Long queryPageSurplusCounts(Map<String,Object> stringObjectMap);
}
