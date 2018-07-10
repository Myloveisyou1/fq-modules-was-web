package com.fq.modules.was.web.service.impl;

import com.fq.modules.was.web.mapper.SysConfigMapper;
import com.fq.modules.was.web.entity.SysConfig;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.mapper.SysConfigMapper;
import com.fq.modules.was.web.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
    * 分页查询数据
    * @param params
    * @return
    */
    public Map<String,Object> queryPage(Map<String, Object> params) {

        Map<String,Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        SysConfig bean = (SysConfig)params.get("bean");
        Pages pages = (Pages) params.get("pages");

        List<SysConfig> list = sysConfigMapper.pageQueryList(bean,pages);
//        Long count = sysConfigMapper.pageQueryCount(bean,pages);
        Long count = 10l;
        pages.setTotalCount(count);
        long totalPage = count%pages.getPageSize()>0?(1+count/pages.getPageSize()):(count/pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage+""));

        map.put("result",list);//数据信息
        map.put("pages",pages);//分页信息

        return map;
    }

}
