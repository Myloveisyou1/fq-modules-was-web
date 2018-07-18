package com.fq.modules.was.web.task;

import com.alibaba.fastjson.JSONObject;
import com.fq.core.result.Result;
import com.fq.core.vo.EmailBean;
import com.fq.core.vo.SmsBean;
import com.fq.modules.client.mail.MailClient;
import com.fq.modules.client.sms.SMSClient;
import com.fq.modules.was.web.entity.addresspool.WarnHistory;
import com.fq.modules.was.web.entity.addresspool.WarnRule;
import com.fq.modules.was.web.service.addresspool.AddressListService;
import com.fq.modules.was.web.service.addresspool.WarnHistoryService;
import com.fq.modules.was.web.service.addresspool.WarnRuleService;
import com.fq.modules.was.web.utils.CommonUtil;
import com.fq.modules.was.web.utils.DatesUtils;
import com.fq.modules.was.web.vo.AddressVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统定时任务
 */
@Slf4j
@Component
public class SystemTask {

    @Autowired
    private AddressListService addressListService;
    @Autowired
    private WarnRuleService warnRuleService;
    @Autowired
    private WarnHistoryService warnHistoryService;
    @Autowired
    private SMSClient smsClient;
    @Autowired
    private MailClient mailClient;

    /**
     * 定时任务,每一小时去检测是否存在需要预警的币种
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void warnForEmailAndPhone() {

        log.info("===========定时任务开始,开始自动预警,开始执行时间:"+DatesUtils.time()+"================");

        //检测是否有需要预警的币种
        Map<String,Object> map = new HashMap<>();
        AddressVo vo = new AddressVo();
        map.put("bean",vo);
        List<Map<String,Object>> list = addressListService.findWarnAddressList(map);
        if (CommonUtil.isNotEmpty(list)) {

            //判断哪些需要预警,目前每个要预警的币种都发送一条短信和一封邮件
            //需要获取币种类型\业务平台\剩余地址数量信息
            //bita-btc未使用地址剩余不足请尽快处理，当前剩余50.
            for (Map temp : list) {
                if ("true".equals(temp.get("warnStatus").toString())) {
                    //1.发送邮件和短信
                    String wasType = temp.get("wasType").toString();
                    String wasSource = temp.get("wasSource").toString();
                    String surplusCount = temp.get("surplusCount").toString();
                    WarnRule warnRule = warnRuleService.selectByTypeAndSource(wasType,wasSource);
                    //获取要发的电话号码和邮箱
                    String[] phone = warnRule.getWasWarnTel().split(",");
                    String[] email = warnRule.getWasWarnEmail().split(",");
                    //短信内容
                    String message = wasSource+"-"+wasType+"未使用地址剩余不足请尽快处理，当前剩余"+surplusCount;
                    for (int i = 0; i < phone.length; i++) {
                        log.info("=======开始发送第"+(i+1)+"条短信=========");
                        log.info("=======短信内容:"+message+",短信接收人:"+phone[i]+"=========");
                    }
                    SmsBean bean = new SmsBean();
                    bean.setSource(2);
                    bean.setMobile(warnRule.getWasWarnTel());
                    bean.setContent(message);
                    Result result = smsClient.send(bean);
                    System.out.println("发送短信返回值:"+JSONObject.toJSON(result));
                    //邮件内容
                    String theme = "【预警|was运营支持后台】"+wasSource+"-"+wasType+"地址池中未使用地址剩余不足请尽快处理";
                    String content = wasSource+"-"+wasType+"未使用地址剩余不足请尽快处理，当前剩余"+surplusCount;
                    for (int i = 0; i < email.length; i++) {
                        log.info("=======开始发送第"+(i+1)+"封邮件=========");
                        log.info("=======邮件主题:"+theme+",邮件内容:"+content+",邮件接收人:"+email[i]+"=========");
                    }
                    EmailBean emailBean = new EmailBean();
                    emailBean.setSource(2);
                    emailBean.setEmail(warnRule.getWasWarnEmail());
                    emailBean.setTitle(theme);
                    emailBean.setContent(content);
                    Result emailResult = mailClient.sendMail(emailBean);
                    System.out.println("发送邮件返回值:"+JSONObject.toJSON(emailResult));
                    //2.记录邮件和短信记录
                    WarnHistory warnHistory = new WarnHistory();
                    warnHistory.setWasTypeName(wasType);
                    warnHistory.setWasType(wasType);
                    warnHistory.setWasSource(wasSource);
                    warnHistory.setWasAddressTotalCount(Integer.valueOf(temp.get("totalCount").toString()));
                    warnHistory.setWasAddressSurplusCount(Integer.valueOf(temp.get("surplusCount").toString()));
                    warnHistory.setWasAddressRate(temp.get("rate").toString());
                    warnHistory.setWasWarnNum(temp.get("wasWarnNum").toString());
                    warnHistory.setWasWarnEmail(warnRule.getWasWarnEmail());
                    warnHistory.setWasWarnTel(warnRule.getWasWarnTel());
                    warnHistory.setWasWarnTime(new Date());

                    warnHistoryService.insert(warnHistory);
                }
            }

        } else {
            log.info("==============没有需要预警的币种地址==============");
        }


        log.info("===========定时任务结束,结束自动预警,结束执行时间:"+DatesUtils.time()+"================");

    }
}
