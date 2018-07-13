package com.fq.modules.was.web.service.addresspool.impl;

import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.mapper.setting.SysConfigMapper;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.fq.modules.was.web.utils.DatesUtils;

import com.fq.modules.was.web.entity.addresspool.AddressList;
import com.fq.modules.was.web.service.addresspool.AddressListService;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.mapper.addresspool.AddressListMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class AddressListServiceImpl extends BaseServiceImpl implements AddressListService {

    @Autowired
    private AddressListMapper addressListMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
    * 分页查询数据
    * @param params
    * @return
    */
    public Map<String, Object> queryPage(Map<String, Object> params) {

        Map<String, Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        List<Map<String,Object>> list = addressListMapper.pageQueryList(params);
        Long count = addressListMapper.pageQueryCount(params);

        Pages pages = (Pages) params.get("pages");
        pages.setTotalCount(count);
        long totalPage = count % pages.getPageSize() > 0 ? (1 + count / pages.getPageSize()) : (count / pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage + ""));

        //处理返回值
        if (CommonUtil.isNotEmpty(list)) {
            for (Map temp : list) {
                String wasType = temp.get("wasType").toString();
                String wasSource = temp.get("wasSource").toString();
                //查询对应币种的剩余预警值
                /*Map<String,Object> wasConfig = sysConfigMapper.selectByTypeAndSource(wasType,wasSource);
                if (CommonUtil.isNotEmpty(wasConfig)) {
                    temp.put("wasWarnNum",wasConfig.get("wasNum"));
                    temp.put("wasRemark",wasConfig.get("wasRemark"));
                } else {
                    temp.put("wasWarnNum","0");
                    temp.put("wasRemark","");
                }*/
                //计算未使用数量和对应的使用比例
                Map<String,Object> wasAddress = addressListMapper.queryCountByWasTypeAndSource(wasType,wasSource);
                if (CommonUtil.isNotEmpty(wasAddress)) {
                    temp.put("surplusCount",wasAddress.get("surplusCount"));
                } else {
                    temp.put("surplusCount",0);
                }
                int totalCount = Integer.valueOf(temp.get("totalCount").toString());
                int surplusCount = Integer.valueOf(temp.get("surplusCount").toString());
                double rate = (double)(totalCount-surplusCount)/totalCount;
                if (rate <= 0.00) {
                    temp.put("rate","0%");
                } else {
                    temp.put("rate",String.format("%.2f",rate*100)+"%");
                }
                //计算预警状态
                if ((100 - rate * 100 <= Double.valueOf(temp.get("wasPercent").toString())*100)) {
                    temp.put("warnStatus",true);
                } else {
                    temp.put("warnStatus",false);
                }

                if (surplusCount <= Integer.valueOf(temp.get("wasNum").toString())) {
                    temp.put("warnStatus",true);
                } else {
                    temp.put("warnStatus",false);
                }
                temp.put("wasWarnNum",temp.get("wasNum")+"或者"+String.format("%.0f",Double.valueOf(temp.get("wasPercent").toString())*100)+"%");
            }
        }
        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }

    /**
    * 根据主键查询信息
    */
    public AddressList selectById(Integer wasId) {

        return addressListMapper.selectById(wasId);
    }

    /**
     * 保存信息
     * @param addressList 需要保存的对象
     * @return
     */
    public boolean insert(AddressList addressList) {

        boolean flag = addressListMapper.addAddressList(addressList);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"新增了【】的信息";
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
     * @param addressList 需要修改的对象
     * @return
     */
    public boolean updateById(AddressList addressList) {

        boolean flag = addressListMapper.optUpdateAddressList(addressList);
        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"修改了【】的信息";
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

        int ret = addressListMapper.deleteById(wasId);

        //记录日志信息
        String content = getUserName()+"在"+DatesUtils.time()+"删除了id=【wasId】的信息";
        String result = "删除成功";
        if (ret == 0) {
            result = "删除失败";
        }
        sysLogMapper.addSysLog(new SysLog(4,getUserName(),content,result));

        return ret;
    }

    /**
     * 地址池明细
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> queryPageDetails(Map<String, Object> params) throws ParseException {

        Map<String, Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        List<Map<String,Object>> list = addressListMapper.queryPageDetailsList(params);
        Long count = addressListMapper.queryPageDetailsCount(params);

        Pages pages = (Pages) params.get("pages");
        pages.setTotalCount(count);
        long totalPage = count % pages.getPageSize() > 0 ? (1 + count / pages.getPageSize()) : (count / pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage + ""));

        if (CommonUtil.isNotEmpty(list)) {
            for (Map temp : list) {
                String getTime = temp.get("wasCreateTime").toString();//获取时间
                String expireTime = "-";//到期时间
                String monitorTime = "-";//监控到期时间

                String fromtT = temp.get("fromT").toString();

                if (fromtT.equals("1") || fromtT.equals("3")) {               //已使用
                    long date = DatesUtils.getTimesByString(getTime);
                    int days = Integer.valueOf(temp.get("wasExpireTime").toString());
                    long times = date + days * 24 * 60 *60 *1000;//到期时间
                    long time = times + Integer.valueOf(temp.get("wasMonitorTime").toString()) * 24 * 60 *60 *1000;//监控到期时间
                    expireTime = DatesUtils.getDateByTime(times);
                    monitorTime = DatesUtils.getDateByTime(time);
                } else if (fromtT.equals("2")) {        //未使用

                }

                temp.put("wasCreateTime",getTime);//获取时间
                temp.put("expireTime",expireTime);//到期时间
                temp.put("warnTime",monitorTime);//监控到期时间
            }
        }

        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }
}
