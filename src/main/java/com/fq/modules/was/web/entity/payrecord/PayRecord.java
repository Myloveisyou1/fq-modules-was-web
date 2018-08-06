package com.fq.modules.was.web.entity.payrecord;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包转币操作记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 15:53:09
 */
@Data
public class PayRecord {

    /**
     * auto id
     */
    private Integer wasId;
    /**
     * source system
     */
    private String wasSource;
    /**
     * transfer txid
     */
    private String wasTxid;
    /**
     * currency type
     */
    private String wasType;
    /**
     * transfer address
     */
    private String wasAddress;
    /**
     * transfer account(BTS/XRM/XRP/BUC)
     */
    private String wasPaymentId;
    /**
     * transfer serial number
     */
    private String wasSerialNumber;
    /**
     * transfer amount
     */
    private BigDecimal wasAmount;
    /**
     * remark
     */
    private String wasRemark;
    /**
     * create time
     */
    private Date wasCreateTime;

    private String startTime;

    private String endTime;
}
