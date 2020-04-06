package com.aiit.byh.service.common.entity.redis.user;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jhyuan on 2017/9/4.
 */
public class UserRedis implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6094450749392293421L;
	/**
	 * 用户编号
	 */
	private String UserID;
	/**
	 * 用户名称
	 */
	private String UserName;
	/**
	 * 大图
	 */
	private String BigImageUrl;
	/**
	 * 小图
	 */
	private String SmallImageUrl;
	/**
	 * 用户铃声作品数量
	 */
	private String UserRingWorkCount;
	/**
     *
     */
	private String EnjoyTimes;
	/**
	 * 下载次数
	 */
	private String UserRingWorkDownLoadTimes;
	/**
	 * 铃声收藏次数
	 */
	private String UserRingWorkEnjoyTimes;
	/**
     *
     */
	private String ModifyStatus;
	private String BackgroundImageUrl;
	private String AccountType;
	private String AuthorAccount;
	private String Money;
	private String FreezeMoney;
	private String EnjoyUserCount;
	private String UserType;
	/**
	 * 个彩vip状态 0：非个彩 1：个彩
	 */
	private String DiyRingStatus;
	private String Sex;
	private String Sign;
	private String tel;
	private String fansCount;
	private String pmodel;
	/**
	 * 收到鲜花数
	 */
	private String precount;
	private String usextid;
	/**
	 * 视频铃声公开作品个数
	 */
	private String rspubcnt;
	/**
	 * 视频铃声私密作品个数
	 */
	private String rsprivcnt;
	/**
	 * 用户角标，JsonString 同mongodb中一致
	 */
	private String icons;

	/**
	 * 视频铃声vip状态 0：非会员 1：会员
	 */
	private String mvvip;

	/**
	 * 黑名单 0-不是1-是
	 */
	private String black;

	/**
	 * 购买下载铃音个数
	 */
	private String orcnt;

	public String getRspubcnt() {
		return rspubcnt;
	}

	public void setRspubcnt(String rspubcnt) {
		this.rspubcnt = rspubcnt;
	}

	public String getRsprivcnt() {
		return rsprivcnt;
	}

	public void setRsprivcnt(String rsprivcnt) {
		this.rsprivcnt = rsprivcnt;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getBigImageUrl() {
		return BigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		BigImageUrl = bigImageUrl;
	}

	public String getSmallImageUrl() {
		return SmallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		SmallImageUrl = smallImageUrl;
	}

	public String getUserRingWorkCount() {
		return UserRingWorkCount;
	}

	public void setUserRingWorkCount(String userRingWorkCount) {
		UserRingWorkCount = userRingWorkCount;
	}

	public String getEnjoyTimes() {
		return EnjoyTimes;
	}

	public void setEnjoyTimes(String enjoyTimes) {
		EnjoyTimes = enjoyTimes;
	}

	public String getUserRingWorkDownLoadTimes() {
		return UserRingWorkDownLoadTimes;
	}

	public void setUserRingWorkDownLoadTimes(String userRingWorkDownLoadTimes) {
		UserRingWorkDownLoadTimes = userRingWorkDownLoadTimes;
	}

	public String getUserRingWorkEnjoyTimes() {
		return UserRingWorkEnjoyTimes;
	}

	public void setUserRingWorkEnjoyTimes(String userRingWorkEnjoyTimes) {
		UserRingWorkEnjoyTimes = userRingWorkEnjoyTimes;
	}

	public String getModifyStatus() {
		return ModifyStatus;
	}

	public void setModifyStatus(String modifyStatus) {
		ModifyStatus = modifyStatus;
	}

	public String getBackgroundImageUrl() {
		return BackgroundImageUrl;
	}

	public void setBackgroundImageUrl(String backgroundImageUrl) {
		BackgroundImageUrl = backgroundImageUrl;
	}

	public String getAccountType() {
		return AccountType;
	}

	public void setAccountType(String accountType) {
		AccountType = accountType;
	}

	public String getAuthorAccount() {
		return AuthorAccount;
	}

	public void setAuthorAccount(String authorAccount) {
		AuthorAccount = authorAccount;
	}

	public String getMoney() {
		return Money;
	}

	public void setMoney(String money) {
		Money = money;
	}

	public String getFreezeMoney() {
		return FreezeMoney;
	}

	public void setFreezeMoney(String freezeMoney) {
		FreezeMoney = freezeMoney;
	}

	public String getEnjoyUserCount() {
		return EnjoyUserCount;
	}

	public void setEnjoyUserCount(String enjoyUserCount) {
		EnjoyUserCount = enjoyUserCount;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public String getDiyRingStatus() {
		return DiyRingStatus;
	}

	public void setDiyRingStatus(String diyRingStatus) {
		DiyRingStatus = diyRingStatus;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFansCount() {
		return fansCount;
	}

	public void setFansCount(String fansCount) {
		this.fansCount = fansCount;
	}

	public String getPmodel() {
		return pmodel;
	}

	public void setPmodel(String pmodel) {
		this.pmodel = pmodel;
	}

	public String getPrecount() {
		return precount;
	}

	public void setPrecount(String precount) {
		this.precount = precount;
	}

	public String getUsextid() {
		return usextid;
	}

	public void setUsextid(String usextid) {
		this.usextid = usextid;
	}

	public String getIcons() {
		return icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}

	public String getMvvip() {
		return mvvip;
	}

	public void setMvvip(String mvvip) {
		this.mvvip = mvvip;
	}

	public String getBlack() {
		return black;
	}

	public void setBlack(String black) {
		this.black = black;
	}

	public String getOrcnt() {
		return orcnt;
	}

	public void setOrcnt(String orcnt) {
		this.orcnt = orcnt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRedis-{\"UserID\":\"");
		builder.append(UserID);
		builder.append("\",\"UserName\":\"");
		builder.append(UserName);
		builder.append("\",\"BigImageUrl\":\"");
		builder.append(BigImageUrl);
		builder.append("\",\"SmallImageUrl\":\"");
		builder.append(SmallImageUrl);
		builder.append("\",\"UserRingWorkCount\":\"");
		builder.append(UserRingWorkCount);
		builder.append("\",\"EnjoyTimes\":\"");
		builder.append(EnjoyTimes);
		builder.append("\",\"UserRingWorkDownLoadTimes\":\"");
		builder.append(UserRingWorkDownLoadTimes);
		builder.append("\",\"UserRingWorkEnjoyTimes\":\"");
		builder.append(UserRingWorkEnjoyTimes);
		builder.append("\",\"ModifyStatus\":\"");
		builder.append(ModifyStatus);
		builder.append("\",\"BackgroundImageUrl\":\"");
		builder.append(BackgroundImageUrl);
		builder.append("\",\"AccountType\":\"");
		builder.append(AccountType);
		builder.append("\",\"AuthorAccount\":\"");
		builder.append(AuthorAccount);
		builder.append("\",\"Money\":\"");
		builder.append(Money);
		builder.append("\",\"FreezeMoney\":\"");
		builder.append(FreezeMoney);
		builder.append("\",\"EnjoyUserCount\":\"");
		builder.append(EnjoyUserCount);
		builder.append("\",\"UserType\":\"");
		builder.append(UserType);
		builder.append("\",\"DiyRingStatus\":\"");
		builder.append(DiyRingStatus);
		builder.append("\",\"Sex\":\"");
		builder.append(Sex);
		builder.append("\",\"Sign\":\"");
		builder.append(Sign);
		builder.append("\",\"tel\":\"");
		builder.append(tel);
		builder.append("\",\"fansCount\":\"");
		builder.append(fansCount);
		builder.append("\",\"pmodel\":\"");
		builder.append(pmodel);
		builder.append("\",\"precount\":\"");
		builder.append(precount);
		builder.append("\",\"usextid\":\"");
		builder.append(usextid);
		builder.append("\",\"rspubcnt\":\"");
		builder.append(rspubcnt);
		builder.append("\",\"rsprivcnt\":\"");
		builder.append(rsprivcnt);
		builder.append("\",\"icons\":\"");
		builder.append(icons);
		builder.append("\",\"mvvip\":\"");
		builder.append(mvvip);
		builder.append("\",\"black\":\"");
		builder.append(black);
		builder.append("\",\"orcnt\":\"");
		builder.append(orcnt);
		builder.append("\"}");
		return builder.toString();
	}

}
