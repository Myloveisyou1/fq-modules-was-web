package com.fq.modules.was.web.entity.addresspool;

import lombok.Data;

import java.util.Date;

/**
 * 预警提醒历史记录表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 18:18:34
 */
@Data
public class WarnHistory{

    /**
     * 主键
     */
    private Integer wasId;
    /**
     * 币种全称
     */
    private String wasTypeName;
    /**
     * 币种类型，如BTC、ETH等等
     */
    private String wasType;
    /**
     * 来源系统，比如Pizza
     */
    private String wasSource;
    /**
     * 地址总数
     */
    private Integer wasAddressTotalCount;
    /**
     * 未使用数量
     */
    private Integer wasAddressSurplusCount;
    /**
     * 使用比例
     */
    private String wasAddressRate;
    /**
     * 预警值
     */
    private String wasWarnNum;
    /**
     * 预警时间
     */
    private Date wasWarnTime;

    private String startTime;

    private String endTime;
}
