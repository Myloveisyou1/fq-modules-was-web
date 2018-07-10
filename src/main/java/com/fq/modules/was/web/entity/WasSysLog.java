package com.fq.modules.was.web.entity;

import com.fq.modules.was.web.utils.Invisible;
import lombok.Data;

import java.util.Date;

/**
 * 系统操作日志表
 *
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-10 10:39:15
 */
@Data
public class WasSysLog {

/**
 * 
 */
   private Long wasId;
/**
 * 日志类型 1.新增 2.修改 3.查询 4.删除 5.登录 6.登出
 */
    private Integer wasLogType;
/**
 * 操作人
 */
    private String wasLogOperator;
/**
 * 操作内容
 */
    private String wasLogContent;
/**
 * 操作结果
 */
    private String wasLogResult;
/**
 * 操作时间
 */
    private Date wasLogTime = new Date();

    /**
     * 查询条件  开始时间
     */
    @Invisible
    private String startTime;
    /**
     * 查询条件  结束时间
     */
    @Invisible
    private String endTime;

    public WasSysLog() {

    }

    public WasSysLog(Integer wasLogType, String wasLogOperator, String wasLogContent, String wasLogResult){
        this.wasLogType = wasLogType;
        this.wasLogOperator = wasLogOperator;
        this.wasLogContent = wasLogContent;
        this.wasLogResult = wasLogResult;
    }
}
