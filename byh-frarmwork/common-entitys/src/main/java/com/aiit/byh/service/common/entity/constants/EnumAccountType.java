package com.aiit.byh.service.common.entity.constants;

/**
 * 账号类型：1表示手机号码、2表示腾讯微博账号、3表示新浪微博账号、4QQ互联帐号、5微信帐号、6小米帐号
 * 
 * @author jianchen3
 * @date 2017年10月24日下午4:05:23
 */
public enum EnumAccountType {
	/**
	 * 手机号码
	 */
	PHONENO(1)
	/**
	 * 腾讯微博账号
	 */
	, TENCENTWEIBO(2)
	/**
	 * 新浪微博账号
	 */
	, SINAWEIBO(3)
	/**
	 * QQ互联帐号
	 */
	, QQ(4)
	/**
	 * 微信帐号
	 */
	, PROPRIETOR(5)
	/**
	 * 小米帐号
	 */
	, MI(6);

	private int value;

	private EnumAccountType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
	
	public String getStringValue() {
		return String.valueOf(this.value);
	}
}
