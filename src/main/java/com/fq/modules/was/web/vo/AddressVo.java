package com.fq.modules.was.web.vo;

import lombok.Data;

/**
 * 地址池管理请求参数
 */
@Data
public class AddressVo {

    /**币种类型**/
    private String wasType;

    /**充值地址**/
    private String wasAddress;

    /**币种所属业务系统**/
    private String wasSource;

    /**预警状态  0未预警 1已预警**/
    private String wasWarnStatus;

    /**预警剩余值**/
    private String wasConfigNum;
}
