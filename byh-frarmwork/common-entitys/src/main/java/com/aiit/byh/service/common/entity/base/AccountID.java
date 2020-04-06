package com.aiit.byh.service.common.entity.base;

import com.aiit.byh.service.common.utils.encrypt.ExclusiveUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 账号编号
 * 
 * @author jianchen3
 * @date 2017年12月19日上午10:22:48
 */
public class AccountID implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户编号
	 */
	/**
	 * 用户编号
	 */
	private String bizid;

	/**
	 * 扩展编号
	 */
	private String extid;

	public String getBizid() {
		return bizid;
	}

	public void setBizid(String bizid) {
		this.bizid = bizid;
	}

	public String getExtid() {
		if (StringUtils.isBlank(extid) && StringUtils.isNotBlank(bizid)) {
			return ExclusiveUtil.encode(this.bizid);
		}
		return extid;
	}

	public void setExtid(String extid) {
		if (StringUtils.isBlank(extid) && StringUtils.isNotBlank(bizid)) {
			this.extid = ExclusiveUtil.encode(this.bizid);
		}
		this.extid = extid;
	}

	public AccountID() {
		super();
	}

	public AccountID(long userid) {
		this.bizid = String.valueOf(userid);
		this.extid = ExclusiveUtil.encode(bizid);
	}

	public AccountID(String userid) {
		this.bizid = userid;
		this.extid = ExclusiveUtil.encode(bizid);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountID-{\"bizid\":\"");
		builder.append(bizid);
		builder.append("\",\"extid\":\"");
		builder.append(extid);
		builder.append("\"}");
		return builder.toString();
	}

}
