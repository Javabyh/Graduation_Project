package com.aiit.byh.service.common.entity.redis.user;

import java.io.Serializable;

/**
 * 
 * @author ij
 *
 */
public class AccessTokenRedis implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8462715674477698956L;
	/**
	 * 账号类型
	 */
	private int acctp;
	/**
	 * 账号（如号码）
	 */
	private String acc;
	/**
	 * 用户编号
	 */
	private String usid;

	public int getAcctp() {
		return acctp;
	}

	public void setAcctp(int acctp) {
		this.acctp = acctp;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getUsid() {
		return usid;
	}

	public void setUsid(String usid) {
		this.usid = usid;
	}

	public AccessTokenRedis() {
		super();
	}

	public AccessTokenRedis(int acctp, String acc, String usid) {
		super();
		this.acctp = acctp;
		this.acc = acc;
		this.usid = usid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TokenRedis-{\"acctp\":\"");
		builder.append(acctp);
		builder.append("\",\"acc\":\"");
		builder.append(acc);
		builder.append("\",\"usid\":\"");
		builder.append(usid);
		builder.append("\"}");
		return builder.toString();
	}

}
