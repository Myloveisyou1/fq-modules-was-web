package com.fq.modules.was.web.service.basic.impl;

import com.fq.modules.was.web.entity.basic.SysSource;
import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.mapper.basic.SysSourceMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.service.basic.SysSourceService;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.fq.modules.was.web.utils.DatesUtils;

import com.fq.modules.was.web.entity.common.Pages;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class SysSourceServiceImpl extends BaseServiceImpl implements SysSourceService {

    @Autowired
    private SysSourceMapper sysSourceMapper;

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
        List<SysSource> list = sysSourceMapper.pageQueryList(params);
        Long count = sysSourceMapper.pageQueryCount(params);

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
    public SysSource selectById(Integer wasId) {

        return sysSourceMapper.selectById(wasId);
    }

    /**
     * 保存信息
     * @param sysSource 需要保存的对象
     * @return
     */
    public boolean insert(SysSource sysSource) {

        SysSource bean = sysSourceMapper.selectByWasSource(sysSource.getWasSource());
        if (CommonUtil.isNotEmpty(bean)) {
            throw new WasWebException(ResultEnum.SAME_DATA);
        }
        Date date = new Date();
        sysSource.setWasCreateTime(date);
        sysSource.setWasUpdateTime(date);
        boolean flag = sysSourceMapper.addSysSource(sysSource);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"新增了【平台名称为"+sysSource.getWasSource()+"】的信息";
        String result = "新增成功";
        if (!flag) {
            result = "新增失败";
        }
        SysLog sysLog = new SysLog(1,getUserName(),content,result);
        sysLogMapper.addSysLog(sysLog);

        return flag;
    }

    /**
     * 修改信息
     * @param sysSource 需要修改的对象
     * @return
     */
    public boolean updateById(SysSource sysSource) {

        boolean flag = sysSourceMapper.optUpdateSysSource(sysSource);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"修改了【平台名称为"+sysSource.getWasSource()+"】的信息";
        String result = "修改成功";
        if (!flag) {
            result = "修改失败";
        }
        SysLog sysLog = new SysLog(2,getUserName(),content,result);
        sysLogMapper.addSysLog(sysLog);
        return flag;
    }

    /**
    * 根据id删除信息
    * @return
    */
    public int deleteById(Integer wasId) {

        int ret = sysSourceMapper.deleteById(wasId);

        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"删除了id=【wasId】的信息";
        String result = "删除成功";
        if (ret == 0) {
            result = "删除失败";
        }
        SysLog sysLog = new SysLog(4,getUserName(),content,result);
        sysLogMapper.addSysLog(sysLog);

        return ret;
    }

    /**
     * 查询所有平台
     * @return
     */
    @Override
    public List<SysSource> findAll() {

        return sysSourceMapper.findAll();
    }
}
