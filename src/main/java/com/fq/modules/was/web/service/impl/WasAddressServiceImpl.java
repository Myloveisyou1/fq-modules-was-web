package com.fq.modules.was.web.service.impl;

import com.fq.modules.was.web.mapper.WasAddressMapper;
import com.fq.modules.was.web.service.WasAddressService;
import com.fq.modules.was.web.entity.WasAddress;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.mapper.WasAddressMapper;
import com.fq.modules.was.web.service.WasAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("wasAddressService")
public class WasAddressServiceImpl implements WasAddressService {

    @Autowired
    private WasAddressMapper wasAddressMapper;

    /**
    * 分页查询数据
    * @param params
    * @return
    */
    public Map<String,Object> queryPage(Map<String, Object> params) {

        Map<String,Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        WasAddress bean = (WasAddress)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        List<WasAddress> list = wasAddressMapper.pageQuery(bean,pages);
        Long count = wasAddressMapper.pageQueryCount(bean,pages);
        pages.setTotalCount(count);
        long totalPage = count%pages.getPageSize()>0?(1+count/pages.getPageSize()):(count/pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage+""));

        map.put("result",list);//数据信息
        map.put("pages",pages);//分页信息

        return map;
    }

    /**
     * 根据id查询信息
     * @param wasId 主键id
     */
    public WasAddress selectById(Long wasId) {

        WasAddress wasAddress = wasAddressMapper.selectById(wasId);

        return wasAddress;
    }

    /**
     * 保存信息
     * @param wasAddress 需要保存的对象
     * @return
     */
    public boolean insert(WasAddress wasAddress) {

        return wasAddressMapper.insert(wasAddress);
    }

    /**
     * 修改信息
     * @param wasAddress 需要修改的对象
     * @return
     */
    public boolean updateById(WasAddress wasAddress) {

        return wasAddressMapper.updateById(wasAddress);
    }

    @Override
    public boolean deleteBatchIds(List<Long> longs) {
        return false;
    }

}
