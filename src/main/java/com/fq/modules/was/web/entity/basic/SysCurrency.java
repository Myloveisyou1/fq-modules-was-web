package com.fq.modules.was.web.entity.basic;

import lombok.Data;

import java.util.Date;

/**
 * 币种类型表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
@Data
public class SysCurrency {

    /**
     * 主键
     */
    private Integer wasId;
    /**
     * 币种名称
     */
    private String wasName;
    /**
     * 币种简称
     */
    private String wasShortName;
    /**
     * 创建时间
     */
    private Date wasCreateTime;
    /**
     *
     */
    private Date wasUpdateTime;
}
