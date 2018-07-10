package com.fq.modules.was.web.service;

import com.fq.modules.was.web.entity.WasAddressList;

import java.util.List;
import java.util.Map;

/**
 * 钱包地址列表（运维生成）
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 16:05:19
 */
public interface  WasAddressListService {

    Map<String,Object> queryPage(Map<String, Object> params);

    WasAddressList selectById(Integer wasId);

    boolean insert(WasAddressList wasAddressList);

    boolean updateById(WasAddressList wasAddressList);

    boolean deleteBatchIds(List<Integer> integers);

    /** 查询的是总数的详情**/
    Map<String,Object> queryPageTotalCount(Map<String,Object> stringObjectMap, Integer type);
    /** (废弃)查询的是剩余数量的详情**/
    Map<String,Object> queryPageSurplusCount(Map<String,Object> stringObjectMap);
}

