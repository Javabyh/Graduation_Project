package com.aiit.byh.service.common.entity.redis.user;


/**
 * 
 * @author ij
 *
 */
public class RefreshTokenRedis extends AccessTokenRedis {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8462715674477698956L;
	/**
	 * 账号类型
	 */
	private String rftk;


	public String getRftk() {
		return rftk;
	}

	public void setRftk(String rftk) {
		this.rftk = rftk;
	}

	public RefreshTokenRedis() {
		super();
	}

	public RefreshTokenRedis(int acctp, String acc, String usid,String rftk) {
		super(acctp, acc, usid);
		this.rftk = rftk;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RefreshTokenRedis-{\"rftk\":\"");
		builder.append(rftk);
		builder.append("\",\"toString()\":\"");
		builder.append(super.toString());
		builder.append("\"}");
		return builder.toString();
	}

}
