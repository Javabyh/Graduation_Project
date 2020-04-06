package com.aiit.byh.service.common.entity.base;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.aiit.byh.service.common.entity.annotation.FieldDocs;
import com.aiit.byh.service.common.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户基础信息
 * 
 * @author jhyuan
 * @date 2017年9月
 */
public class UserSimple implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	@FieldDocs("用户编号")
	@Protobuf(fieldType = FieldType.STRING, order = 1, required = true)
	private String id;

	/**
	 * 用户头像
	 */
	@FieldDocs("用户头像")
	@Protobuf(fieldType = FieldType.STRING, order = 2, required = false)
	private String userPic;

	/**
	 * 用户昵称
	 */
	@FieldDocs("用户昵称")
	@Protobuf(fieldType = FieldType.STRING, order = 3, required = false)
	private String userName;

	/**
	 * 用户性别 0：未知 1：男 2：女
	 */
	@FieldDocs("用户性别 0：未知 1：男 2：女")
	@Protobuf(fieldType = FieldType.INT32, order = 4, required = false)
	private int sex;

	/**
	 * 铃声个数
	 */
	@FieldDocs("铃声个数")
	@Protobuf(fieldType = FieldType.INT32, order = 5, required = false)
	private int ringCount;

	/**
	 * 视频铃声个数
	 */
	@FieldDocs("视频铃声个数")
	@Protobuf(fieldType = FieldType.INT32, order = 6, required = false)
	private int mvCount;

	/**
	 * 粉丝数
	 */
	@FieldDocs("粉丝数")
	@Protobuf(fieldType = FieldType.INT32, order = 7, required = false)
	private int fans;


	/**
	 * 是否已被请求者关注 0：未关注 1：已关注
	 */
	@FieldDocs("是否已被请求者关注 0：未关注 1：已关注")
	@Protobuf(fieldType = FieldType.INT32, order = 9, required = false)
	private int like;

	/**
	 * 用户签名
	 */
	@FieldDocs("用户签名")
	@Protobuf(fieldType = FieldType.STRING, order = 10, required = false)
	private String sign;

	/**
	 * 个彩vip状态 0：非个彩 1：个彩
	 */
	@FieldDocs("个彩vip状态 0：非个彩 1：个彩")
	@Protobuf(fieldType = FieldType.INT32, order = 11)
	private int diyvip;

	/**
	 * 视频铃声vip状态 0：非会员 1：会员
	 */
	@FieldDocs("视频铃声vip状态 0：非会员 1：会员")
	@Protobuf(fieldType = FieldType.INT32, order = 12)
	private int mvvip;

	/**
	 * 购买下载铃音个数
	 */
	@FieldDocs("购买下载铃音个数")
	@Protobuf(fieldType = FieldType.STRING, order = 13)
	private String orcnt;

	/**
	 * 用户类型， 0-普通用户，1-独家创作人,2-音乐（歌手）,3-马甲用户，4-版权方（dkwang）
	 */
	private int tp;

	/**
	 * 手机号码
	 */
	private String pno;

	/**
	 * 黑名单 0-不是1-是
	 */
	private int black;

	public UserSimple() {
		super();
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getRingCount() {
		return ringCount;
	}

	public void setRingCount(int ringCount) {
		this.ringCount = ringCount;
	}

	public int getMvCount() {
		return mvCount;
	}

	public void setMvCount(int mvCount) {
		this.mvCount = mvCount;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	public int getDiyvip() {
		return diyvip;
	}

	public void setDiyvip(int diyvip) {
		this.diyvip = diyvip;
	}

	public int getMvvip() {
		return mvvip;
	}

	public void setMvvip(int mvvip) {
		this.mvvip = mvvip;
	}

	public int getTp() {
		return tp;
	}

	public void setTp(int tp) {
		this.tp = tp;
	}

	public String getOrcnt() {
		return orcnt;
	}

	public void setOrcnt(String orcnt) {
		this.orcnt = orcnt;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public UserSimple(Map<String, String> map) {
		if (CommonUtils.isEmpty(map)) {
			return;
		}
		this.id = map.get("UserID");
		this.userName = map.get("UserName");
		this.userPic = map.get("SmallImageUrl");
		this.mvvip = NumberUtils.toInt(map.get("mvvip"));
		this.diyvip = NumberUtils.toInt(map.get("DiyRingStatus"));
		this.orcnt = map.get("orcnt");
		// TODO
	}

	public boolean valid() {
		return StringUtils.isNotBlank(this.id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSimple-{\"id\":\"");
		builder.append(id);
		builder.append("\",\"userPic\":\"");
		builder.append(userPic);
		builder.append("\",\"userName\":\"");
		builder.append(userName);
		builder.append("\",\"sex\":\"");
		builder.append(sex);
		builder.append("\",\"ringCount\":\"");
		builder.append(ringCount);
		builder.append("\",\"mvCount\":\"");
		builder.append(mvCount);
		builder.append("\",\"fans\":\"");
		builder.append(fans);
		builder.append("\",\"like\":\"");
		builder.append(like);
		builder.append("\",\"sign\":\"");
		builder.append(sign);
		builder.append("\",\"diyvip\":\"");
		builder.append(diyvip);
		builder.append("\",\"mvvip\":\"");
		builder.append(mvvip);
		builder.append("\",\"orcnt\":\"");
		builder.append(orcnt);
		builder.append("\",\"tp\":\"");
		builder.append(tp);
		builder.append("\",\"pno\":\"");
		builder.append(pno);
		builder.append("\",\"black\":\"");
		builder.append(black);
		builder.append("\"}");
		return builder.toString();
	}

}
