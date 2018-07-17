package com.fq.modules.was.web.entity.payrecord;

import lombok.Data;

import java.util.Date;

/**
 * 提币失败的操作历史表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-17 09:44:07
 */
@Data
public class PayOperatorHistory{

/**
 * 主键
 */
    private Integer wasId;
/**
 * 提现记录表id
 */
    private Integer wasPayRecordId;
/**
 * 执行事件
 */
    private Integer wasAction;
/**
 * 执行时间
 */
    private Date wasCreateTime;
/**
 * 操作人
 */
    private String wasCreateUser;

    public PayOperatorHistory(){

    }

    public PayOperatorHistory(Integer wasId,Integer wasAction,Date wasCreateTime,String wasCreateUser) {
        this.wasPayRecordId = wasId;
        this.wasAction = wasAction;
        this.wasCreateTime = wasCreateTime;
        this.wasCreateUser = wasCreateUser;
    }
}
