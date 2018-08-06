package com.fq.modules.was.web.service.payrecord.impl;

import com.alibaba.fastjson.JSONObject;
import com.fq.modules.was.web.client.WasApiClient;
import com.fq.modules.was.web.entity.common.Pages;
import com.fq.modules.was.web.entity.logs.SysLog;
import com.fq.modules.was.web.entity.payrecord.PayOperatorHistory;
import com.fq.modules.was.web.entity.payrecord.PayRecord;
import com.fq.modules.was.web.mapper.logs.SysLogMapper;
import com.fq.modules.was.web.mapper.payrecord.PayOperatorHistoryMapper;
import com.fq.modules.was.web.mapper.payrecord.PayRecordMapper;
import com.fq.modules.was.web.service.common.impl.BaseServiceImpl;
import com.fq.modules.was.web.service.payrecord.PayRecordService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class PayRecordServiceImpl extends BaseServiceImpl implements PayRecordService {

    @Autowired
    private PayRecordMapper payRecordMapper;

    @Autowired
    private PayOperatorHistoryMapper payOperatorHistoryMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private WasApiClient wasApiClient;

    /**
     * 分页查询数据
     *
     * @param params
     * @return
     */
    public Map<String, Object> queryPage(Map<String, Object> params) {

        Map<String, Object> map = new HashMap<>();

        //这里开始写具体的业务逻辑
        List<Map<String, Object>> list = payRecordMapper.pageQueryList(params);
        Long count = payRecordMapper.pageQueryCount(params);

        Pages pages = (Pages) params.get("pages" );
        pages.setTotalCount(count);
        long totalPage = count % pages.getPageSize() > 0 ? (1 + count / pages.getPageSize()) : (count / pages.getPageSize());
        pages.setTotalPage(Integer.valueOf(totalPage + "" ));

        map.put("result", list);//数据信息
        map.put("pages", pages);//分页信息

        return map;
    }

    /**
     * 根据主键查询信息
     */
    public PayRecord selectById(Integer wasId) {

        return payRecordMapper.selectById(wasId);
    }

    /**
     * 保存信息
     *
     * @param payRecord 需要保存的对象
     * @return
     */
    public boolean insert(PayRecord payRecord) {

        boolean flag = payRecordMapper.addPayRecord(payRecord);
        //记录日志信息
        String content = getUserName() + "在" + DatesUtils.time() + "新增了【】的信息";
        String result = "新增成功";
        if (!flag) {
            result = "新增失败";
        }
        SysLog sysLog = new SysLog(1, getUserName(), content, result);
        sysLogMapper.addSysLog(sysLog);

        return flag;
    }

    /**
     * 修改信息
     *
     * @param payRecord 需要修改的对象
     * @return
     */
    public boolean updateById(PayRecord payRecord) {

        boolean flag = payRecordMapper.optUpdatePayRecord(payRecord);
        //记录日志信息
        String content = getUserName() + "在" + DatesUtils.time() + "修改了【】的信息";
        String result = "修改成功";
        if (!flag) {
            result = "修改失败";
        }
        SysLog sysLog = new SysLog(2, getUserName(), content, result);
        sysLogMapper.addSysLog(sysLog);
        return flag;
    }

    /**
     * 根据id删除信息
     *
     * @return
     */
    public int deleteById(Integer wasId) {

        int ret = payRecordMapper.deleteById(wasId);

        //记录日志信息
        String content = getUserName() + "在" + DatesUtils.time() + "删除了id=【wasId】的信息";
        String result = "删除成功";
        if (ret == 0) {
            result = "删除失败";
        }
        SysLog sysLog = new SysLog(4, getUserName(), content, result);
        sysLogMapper.addSysLog(sysLog);

        return ret;
    }

    /**
     * 再次执行    从而获得交易hash或者重新请求到对应的钱包客户端
     *
     * @param wasId
     * @param oType 1.未收到hash的情况;2.请求发送失败的情况
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean doAgain(Integer wasId, String oType) {

        boolean flag = false;
        if (CommonUtil.isNotEmpty(wasId) && CommonUtil.isNotEmpty(oType)) {
            //根据id获取交易信息
            PayRecord bean = payRecordMapper.selectById(wasId);
            if (CommonUtil.isNotEmpty(bean)) {
                //查询看是否需要重新发送请求

                Map<String, Object> map = new HashMap<>();
                if ("1".equals(oType)) {
                    //未收到hash的情况 相比于 请求发送失败的情况多了两个参数,其他都一样
                    map.put("was_txid", bean.getWasTxid());
                    map.put("was_number", 1);
                    map.put("was_method", "callback" );
                } else if ("2".equals(oType)) {    //转账获取交易hash值
                    map.put("was_method", "transfer" );//标识转账
                }
                //请求发送失败的情况,
                map.put("was_type", bean.getWasType());
                map.put("was_address", bean.getWasAddress());
                map.put("was_acount", bean.getWasPaymentId());
                map.put("was_serial_number", bean.getWasSerialNumber());
                map.put("was_source", bean.getWasSource());
                map.put("was_txfee", 0);
                map.put("was_amount", bean.getWasAmount());
                map.put("was_remark", bean.getWasRemark());

                String str = JSONObject.toJSONString(map);
                System.out.println(str);

                Result back = null;
                //请求要请求的服务,数据中的wasSource属于哪个就发送到那个服务||||||根据不同的平台调用不同的请求
                if (CommonUtil.isNotEmpty(bean.getWasSource())) {
                    if (bean.getWasSource().equals("bita" )) {
                        back = wasApiClient.originalTransferPizza(str);
                    } else if (bean.getWasSource().equals("pie" )) {
                        back = wasApiClient.originalTransferPie(str);
                    } else if (bean.getWasSource().equals("xbrick" )) {
                        back = wasApiClient.originalTransferXbrick(str);
                    } else if (bean.getWasSource().equals("x/net" )) {
                        back = wasApiClient.originalTransferXnet(str);
                    }
                }
                log.info("=========操作返回值:" + JSONObject.toJSONString(back));
                if (back.getCode().equals("200" )) {
                    flag = true;
                }
                //记录操作历史
                PayOperatorHistory history = new PayOperatorHistory(wasId, Integer.valueOf(oType), new Date(), getUserName());
                flag = payOperatorHistoryMapper.addPayOperatorHistory(history);
                //记录系统日志
                String content = getUserName() + "在" + DatesUtils.time() + "重新执行" + oType + "的请求";
                String result = "请求成功";
                if (!flag) {
                    result = "请求失败";
                }
                SysLog sysLog = new SysLog(2, getUserName(), content, result);
                sysLogMapper.addSysLog(sysLog);
            }
        }
        return flag;
    }

    /**
     * 根据wasId查询操作历史记录
     *
     * @param wasId
     * @return
     */
    @Override
    public List<PayOperatorHistory> findHistoryByWasId(Integer wasId) {

        return payOperatorHistoryMapper.findHistoryByWasId(wasId, getUserName());
    }
}
