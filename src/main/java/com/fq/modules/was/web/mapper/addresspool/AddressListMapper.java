package com.fq.modules.was.web.mapper.addresspool;

import com.fq.modules.was.web.entity.addresspool.AddressList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

/**
 * 钱包地址列表（运维生成）
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 10:06:21
 */
@Component
@Mapper
public interface AddressListMapper {

    /**地址池列表**/
    List<Map<String,Object>> pageQueryList(Map<String, Object> params);

    Long pageQueryCount(Map<String, Object> params);

    AddressList selectById(Integer wasId);

    boolean addAddressList(AddressList addressList);

    boolean optUpdateAddressList(AddressList addressList);

    int deleteById(Integer wasId);

    Map<String,Object> queryCountByWasTypeAndSource(@Param("wasType") String wasType, @Param("wasSource") String wasSource);

    /**地址池明细**/
    List<Map<String,Object>> queryPageDetailsList(Map<String,Object> params);

    Long queryPageDetailsCount(Map<String,Object> params);

    /***导出地址池管理的Excel*/
    List<Map<String,Object>> findAll(Map<String,Object> params);

    /***导出地址池明细的Excel*/
    List<Map<String,Object>> findAllDetails(Map<String,Object> params);
}
