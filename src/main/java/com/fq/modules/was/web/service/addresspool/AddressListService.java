package com.fq.modules.was.web.service.addresspool;

import com.fq.modules.was.web.entity.addresspool.AddressList;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 钱包地址列表（运维生成）
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 10:06:21
 */
public interface AddressListService {

    Map<String, Object> queryPage(Map<String, Object> params);

    AddressList selectById(Integer wasId);

    boolean insert(AddressList addressList);

    boolean updateById(AddressList addressList);

    int deleteById(Integer wasId);

    Map<String, Object> queryPageDetails(Map<String, Object> stringObjectMap) throws ParseException;

    String findAll(Map<String, Object> map);

    String findAllDetails(Map<String, Object> map) throws ParseException;

    /**
     * 查询需要预警的币种
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> findWarnAddressList(Map<String, Object> map);
}

