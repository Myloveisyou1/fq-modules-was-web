package com.fq.modules.was.web.service.addresspool.impl;

import com.fq.modules.was.web.entity.addresspool.AddressList;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.mapper.addresspool.AddressListMapper;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.service.addresspool.AddressListService;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import com.fq.modules.was.web.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AddressListServiceImpl extends BaseServiceImpl implements AddressListService {

    @Autowired
    private AddressListMapper addressListMapper;

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
        List<Map<String,Object>> list = addressListMapper.pageQueryList(params);
        Long count = addressListMapper.pageQueryCount(params);

        Pages pages = (Pages) params.get("pages");
        pages.setTotalCount(count);
        long totalPage = count % pages.getPageSize() > 0 ? (1 + count / pages.getPageSize()) : (count / pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage + ""));

        list = getResultOfList(list);
        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }

    public List<Map<String,Object>> getResultOfList(List<Map<String,Object>> list) {

        //处理返回值
        if (CommonUtil.isNotEmpty(list)) {
            for (Map temp : list) {
                String wasType = temp.get("wasType").toString();
                String wasSource = temp.get("wasSource").toString();
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
                temp.put("wasWarnNum",temp.get("wasNum")+"或者"+temp.get("wasPercent").toString()+"%");
            }
        }

        return list;
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

        list = getResultOfDetail(list);

        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }

    /**
     * 处理明细数据
     * @param list
     * @return
     * @throws ParseException
     */
    private List<Map<String,Object>> getResultOfDetail(List<Map<String,Object>> list) throws ParseException {

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

        return list;
    }

    /**
     * 导出地址池管理 Excel汇总的信息
     * @param params
     * @return
     */
    @Override
    public String findAll(Map<String, Object> params) {

        String path = "";
        String[] title = {"序号","币种类型","对应业务系统","地址总数","剩余未使用数量","使用比例","剩余预警值","预警状态","备注"};
        String excelName = "地址池管理";
        List<Map<String, Object>> list = addressListMapper.findAll(params);
        list = getResultOfList(list);
        if (CommonUtil.isNotEmpty(list)) {
            String[][] content = new String[list.size()][title.length];
            Map<String,Object> map = new HashMap<>();
            map.put("time", DatesUtils.time());
            //处理返回值
            for(int i=0;i<list.size();i++){
                Map<String,Object> temp = list.get(i);
                content[i][0] = i+1+"";
                content[i][1] = temp.get("wasType").toString();
                content[i][2] = temp.get("wasSource").toString();
                content[i][3] = temp.get("totalCount").toString();
                content[i][4] = temp.get("surplusCount").toString();
                content[i][5] = temp.get("rate").toString();
                content[i][6] = temp.get("wasWarnNum").toString();
                String warnStatus = temp.get("warnStatus").toString();
                if ("true".equals(warnStatus)) {
                    content[i][7] = "已预警";
                } else if ("false".equals(warnStatus)) {
                    content[i][7] = "未预警";
                }
                content[i][8] = temp.get("wasRemark").toString();
            }
            path = ExcelUtils.excel(map,title,excelName,content);


            //记录日志
            sysLogMapper.addSysLog(new SysLog(3,getUserName(),getUserName()+"在"+DatesUtils.time()+"导出地址池管理列表数据到Excel","导出Excel成功!"));

        }
        return path;
    }

    /**
     * 导出 地址池详情界面  Excel
     * @param params
     * @return
     */
    @Override
    public String findAllDetails(Map<String, Object> params) throws ParseException {

        String path = "";
        String[] title = {"序号","币种类型","对应业务系统","充值地址","地址有效期(天)","地址监控期(天)","获取时间","到期时间","监控到期时间","地址到账数量","状态"};
        String excelName = "地址池明细列表";
        List<Map<String, Object>> list = addressListMapper.findAllDetails(params);
        list = getResultOfDetail(list);
        if (CommonUtil.isNotEmpty(list)) {
            String[][] content = new String[list.size()][title.length];
            Map<String,Object> map = new HashMap<>();
            map.put("time", DatesUtils.time());
            //处理返回值
            for(int i=0;i<list.size();i++){
                Map<String,Object> temp = list.get(i);
                content[i][0] = i+1+"";
                content[i][1] = temp.get("wasType").toString();
                content[i][2] = temp.get("wasSource").toString();
                content[i][3] = temp.get("wasAddress").toString();
                content[i][4] = temp.get("wasExpireTime").toString();
                content[i][5] = temp.get("wasMonitorTime").toString();
                content[i][6] = temp.get("wasCreateTime").toString();
                content[i][7] = temp.get("expireTime").toString();
                content[i][8] = temp.get("warnTime").toString();
                content[i][9] = temp.get("acceptAmount").toString();
                content[i][10] = temp.get("status").toString();
            }
            path = ExcelUtils.excel(map,title,excelName,content);

            //记录日志
            sysLogMapper.addSysLog(new SysLog(3,getUserName(),getUserName()+"在"+DatesUtils.time()+"导出地址池明细列表数据到Excel","导出Excel成功!"));

        }
        return path;
    }

    /**
     * 查询需要预警的币种
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> findWarnAddressList(Map<String, Object> map) {

        List<Map<String, Object>> list = addressListMapper.findAll(map);

        return getResultOfList(list);
    }
}
