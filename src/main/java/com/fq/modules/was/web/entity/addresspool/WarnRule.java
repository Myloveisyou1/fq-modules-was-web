package com.fq.modules.was.web.entity.addresspool;

import lombok.Data;

import java.util.Date;

/**
 * 预警提醒规则
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 17:22:52
 */
@Data
public class WarnRule {

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
     * 提醒邮箱
     */
    private String wasWarnEmail;
    /**
     * 提醒电话
     */
    private String wasWarnTel;
}
