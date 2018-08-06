package com.fq.modules.was.web.entity.addresspool;

import lombok.Data;

import java.util.Date;

/**
 * 钱包地址列表（运维生成）
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-12 10:06:21
 */
@Data
public class AddressList {

    /**
     * 自动增长ID
     */
    private Integer wasId;
    /**
     * 币种类型，例如BTC、ETH
     */
    private String wasType;
    /**
     * to-up address
     */
    private String wasAddress;
    /**
     * to-up account(BTS/XRM/XRP/BUC)
     */
    private String wasAccount;
    /**
     * 地址有效期时间，单位 天
     */
    private Integer wasExpireTime;
    /**
     * 地址对应生成的账号，比如：bita
     */
    private String wasSource;
    /**
     * 备注
     */
    private String wasRemark;
    /**
     * 创建时间
     */
    private Date wasCreateTime;
}
