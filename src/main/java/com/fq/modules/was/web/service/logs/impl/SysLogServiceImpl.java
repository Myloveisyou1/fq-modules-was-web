package com.fq.modules.was.web.service.logs.impl;

import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.service.logs.SysLogService;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public Map<String, Object> queryPage(Map<String, Object> params) {

        Map<String, Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        List<SysLog> list = sysLogMapper.pageQueryList(params);
        Long count = sysLogMapper.pageQueryCount(params);

        Pages pages = (Pages) params.get("pages");
        pages.setTotalCount(count);
        long totalPage = count % pages.getPageSize() > 0 ? (1 + count / pages.getPageSize()) : (count / pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage + ""));

        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }

    /**
     * 根据主键查询信息
     */
    public SysLog selectById(Long wasId) {

        return sysLogMapper.selectById(wasId);
    }

    /**
     * 保存信息
     * @param sysLog 需要保存的对象
     * @return
     */
    public boolean insert(SysLog sysLog) {

        boolean flag = sysLogMapper.addSysLog(sysLog);

        return flag;
    }

    /**
     * 修改信息
     * @param sysLog 需要修改的对象
     * @return
     */
    public boolean updateById(SysLog sysLog) {

        boolean flag = sysLogMapper.optUpdateSysLog(sysLog);

        return flag;
    }

    /**
     * 根据id删除信息
     * @return
     */
    public int deleteById(Long wasId) {

        int ret = sysLogMapper.deleteById(wasId);

        return ret;
    }
}
