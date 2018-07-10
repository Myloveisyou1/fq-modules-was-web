package com.fq.modules.was.web.entity;

import lombok.Data;

/**
 * 钱包地址列表（运维生成）
 * 
 * @author liuyingjie
 * @email liuyingjie@fengqun.ltd
 * @date 2018-07-06 15:58:33
 */
@Data
public class WasAddressList{

	/**
	 * 自动增长ID
	 */
		private Integer wasId;
	/**
	 * 币种类型，例如BTC、ETH
	 */
	private String wasType;
	/**
	 * 充值地址
	 */
	private String wasAddress;
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
	 * 额外的字段,需要计算得到
	 */
	/**地址总数**/
	private Long totalCount;

	/**剩余总数**/
	private Long surplusCount;

	/**使用频率**/
	private String rate;

	/**最小剩余数**/
	private Long minSurplusCount;

	/**是否触发预警**/
	private boolean trigger;
}
