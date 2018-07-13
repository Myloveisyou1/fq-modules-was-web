package com.fq.modules.was.web.entity.datadictionary;

import lombok.Data;

import java.util.Date;

/**
 * 数据字典表
 * 
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-05 09:38:46
 */
@Data
public class WasDataDictionary{

	/**
	 * 自动增长ID
	 */
	private Integer wasId;
	/**
	 * 基于某个币种，用来判断执行哪个钱包下的方法
	 */
	private String wasBaseCurrency;
	/**
	 * 币种类型
	 */
	private String wasType;
	/**
	 * 开始区块
	 */
	private String wasBeginBlock;
	/**
	 * 查询块数
	 */
	private String wasBlockNum;
	/**
	 * was-api访问钱包网关名称
	 */
	private String wasGateWay;
	/**
	 * 代币合约地址或者资产id
	 */
	private String wasTokenAddress;
	/**
	 * 钱包监控到账设置的最小确认数
	 */
	private Integer wasMinConfirm;
	/**
	 * 状态，0：禁用；1：启用
	 */
	private Integer wasStatus;
	/**
	 * 精度
	 */
	private Integer wasPrecision;
	/**
	 * 归零price
	 */
	private String wasZeroGasPrice;
	/**
	 * 归零limit
	 */
	private String wasZeroGasLimit;
	/**
	 * 转账price
	 */
	private String wasTransferGasPrice;
	/**
	 * 归零limit
	 */
	private String wasTransferGasLimit;
	/**
	 * 备注
	 */
	private String wasRemark;
	/**
	 * 创建时间
	 */
	private Date wasCreateTime;
	/**
	 * 最后修改时间
	 */
	private Date wasLastTime;
	/**
	 * 货币介绍URL
	 */
	private String wasCoinIntroduceUrl;
	/**
	 * 区块浏览器URL
	 */
	private String wasBlockBrowsersUrl;
	/**
	 * 备用标签
	 */
	private String wasSpare;
}
