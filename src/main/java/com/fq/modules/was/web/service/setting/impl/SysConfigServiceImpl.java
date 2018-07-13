package com.fq.modules.was.web.service.setting.impl;

import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.entity.common.Constant;
import com.fq.modules.was.web.enums.ResultEnum;
import com.fq.modules.was.web.exception.WasWebException;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.fq.modules.was.web.utils.DatesUtils;

import com.fq.modules.was.web.entity.setting.SysConfig;
import com.fq.modules.was.web.service.setting.SysConfigService;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.mapper.setting.SysConfigMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class SysConfigServiceImpl extends BaseServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

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
        List<SysConfig> list = sysConfigMapper.pageQueryList(params);
        Long count = sysConfigMapper.pageQueryCount(params);

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
    public SysConfig selectById(Long wasId) {

        return sysConfigMapper.selectById(wasId);
    }

    /**
     * 保存信息
     * @param sysConfig 需要保存的对象
     * @return
     */
    public boolean insert(SysConfig sysConfig) {

        //校验数据
        Map<String,Object> bean = sysConfigMapper.selectByTypeAndSource(sysConfig.getWasType(),sysConfig.getWasSource());
        if (CommonUtil.isNotEmpty(bean)) {
            throw new WasWebException(ResultEnum.SAME_DATA);
        }
        sysConfig.setWasStaus(1);
        sysConfig.setWasConfigType(Constant.MIN_EARLY_WARNING);
        sysConfig.setWasCreateTime(new Date());
        boolean flag = sysConfigMapper.addSysConfig(sysConfig);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"新增了【剩余预警值】的配置信息";
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
     * @param sysConfig 需要修改的对象
     * @return
     */
    public boolean updateById(SysConfig sysConfig) {

        boolean flag = sysConfigMapper.optUpdateSysConfig(sysConfig);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"修改了【剩余预警值】的配置信息";
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
    public int deleteById(Long wasId) {

        int ret = sysConfigMapper.deleteById(wasId);

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

    @Override
    public Map<String, Object> selectByTypeAndSource(String wasType, String wasSource) {

        return sysConfigMapper.selectByTypeAndSource(wasType,wasSource);
    }
}
