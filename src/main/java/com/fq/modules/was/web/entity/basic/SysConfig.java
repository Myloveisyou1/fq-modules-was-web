package com.fq.modules.was.web.entity.basic;

import lombok.Data;

import java.util.Date;

/**
 * 系统参数配置表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-18 14:18:40
 */
@Data
public class SysConfig {

    /**
     *
     */
    private Long wasId;
    /**
     * 币种类型，如BTC、ETH等等
     */
    private String wasType;
    /**
     * 配置类型 比如剩余预警值
     */
    private String wasConfigType;
    /**
     * 数值,例如 3
     */
    private Integer wasNum;
    /**
     * 百分比的整数部分 例如20
     */
    private Integer wasPercent;
    /**
     * 来源系统，比如Pizza（P
     */
    private String wasSource;
    /**
     * 状态 0.禁用 1.启用
     */
    private Integer wasStaus;
    /**
     * 备注
     */
    private String wasRemark;
    /**
     * 创建时间
     */
    private Date wasCreateTime;
}
