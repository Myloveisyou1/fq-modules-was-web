package com.fq.modules.was.web.service.impl;

import com.fq.modules.was.web.entity.WasSysLog;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.mapper.WasSysLogMapper;
import com.fq.modules.was.web.service.WasSysLogService;
import com.fq.modules.was.web.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class WasSysLogServiceImpl implements WasSysLogService {

    @Autowired
    private WasSysLogMapper wasSysLogMapper;

    /**
    * 分页查询数据
    * @param params
    * @return
    */
    public Map<String, Object> queryPage(Map<String, Object> params) {

        Map<String, Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        Pages pages = (Pages) params.get("pages");

        List<WasSysLog> list = wasSysLogMapper.pageQuery(params);
        Long count = wasSysLogMapper.pageQueryCount(params);
        pages.setTotalCount(count);
        long totalPage = count % pages.getPageSize() > 0 ? (1 + count / pages.getPageSize()) : (count / pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage + ""));

        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }

    /**
     * 根据id查询信息
     * @param wasId 主键id
     */
    public WasSysLog selectById(Long wasId) {

        WasSysLog wasSysLog =wasSysLogMapper.selectById(wasId);

        return wasSysLog;
    }

    /**
     * 保存信息
     * @param wasSysLog 需要保存的对象
     * @return
     */
    public boolean insert(WasSysLog wasSysLog) {

        return wasSysLogMapper.insert(wasSysLog);
    }

    /**
     * 修改信息
     * @param wasSysLog 需要修改的对象
     * @return
     */
    public boolean updateById(WasSysLog wasSysLog) {

        return wasSysLogMapper.updateById(wasSysLog);
    }

}
