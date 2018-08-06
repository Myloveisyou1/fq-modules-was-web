package com.fq.modules.was.web.entity.basic;

import lombok.Data;

import java.util.Date;

/**
 * 业务平台表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-16 09:54:44
 */
@Data
public class SysSource {

    /**
     * 主键
     */
    private Integer wasId;
    /**
     * 平台名称
     */
    private String wasSource;
    /**
     * 平台地址
     */
    private String wasUrl;
    /**
     * 创建时间
     */
    private Date wasCreateTime;
    /**
     * 修改时间
     */
    private Date wasUpdateTime;
}
