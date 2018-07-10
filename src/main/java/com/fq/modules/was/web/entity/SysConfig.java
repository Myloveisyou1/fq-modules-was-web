package com.fq.modules.was.web.entity;

import lombok.Data;

import java.util.Date;

/**
 * 
 * 
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 16:54:47
 */
@Data
public class SysConfig{

	/**
	 * 
	 */
		private Long wasId;
	/**
	 * 币种类型，如BTC、ETH等等
	 */
	private String wasType;
	/**
	 * 配置类型 1.货币数量 2.地址数量
	 */
	private Integer wasConfigType;
	/**
	 * 数值
	 */
	private Integer wasNum;
	/**
	 * 来源系统，比如Pizza（P
	 */
	private String wasSource;
	/**
	 * 状态 0.禁用 1.启用
	 */
	private Integer wasStaus;
	/**
	 * 创建时间
	 */
	private Date wasCreateTime;
}
