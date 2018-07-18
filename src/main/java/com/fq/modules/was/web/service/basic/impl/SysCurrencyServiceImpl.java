package com.fq.modules.was.web.service.basic.impl;

import com.fq.modules.was.web.entity.basic.SysCurrency;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.mapper.basic.SysCurrencyMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.service.basic.SysCurrencyService;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysCurrencyServiceImpl extends BaseServiceImpl implements SysCurrencyService {

    @Autowired
    private SysCurrencyMapper sysCurrencyMapper;

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
        List<Map<String,Object>> list = sysCurrencyMapper.pageQueryList(params);
        Long count = sysCurrencyMapper.pageQueryCount(params);

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
    public SysCurrency selectById(Integer wasId) {

        return sysCurrencyMapper.selectById(wasId);
    }

    /**
     * 保存信息
     * @param sysCurrency 需要保存的对象
     * @return
     */
    public boolean insert(SysCurrency sysCurrency) {

        SysCurrency bean = sysCurrencyMapper.selectByWasShortName(sysCurrency.getWasShortName());
        if (CommonUtil.isNotEmpty(bean)) {
            throw new WasWebException(ResultEnum.SAME_DATA);
        }
        Date date = new Date();
        sysCurrency.setWasCreateTime(date);
        sysCurrency.setWasUpdateTime(date);
        boolean flag = sysCurrencyMapper.addSysCurrency(sysCurrency);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"新增了【币种为"+sysCurrency.getWasName()+"】的信息";
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
     * @param sysCurrency 需要修改的对象
     * @return
     */
    public boolean updateById(SysCurrency sysCurrency) {

        boolean flag = sysCurrencyMapper.optUpdateSysCurrency(sysCurrency);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"修改了【币种"+sysCurrency.getWasName()+"】的信息";
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

        int ret = sysCurrencyMapper.deleteById(wasId);

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
     * 查询全部币种信息
     * @return
     */
    @Override
    public List<SysCurrency> findAll() {

        return sysCurrencyMapper.findAll();
    }
}
