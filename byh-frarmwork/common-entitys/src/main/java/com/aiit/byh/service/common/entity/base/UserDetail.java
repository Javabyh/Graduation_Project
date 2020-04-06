package com.aiit.byh.service.common.entity.base;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.aiit.byh.service.common.entity.annotation.FieldDocs;

import java.io.Serializable;
import java.util.List;

/**
 * 用户详细信息
 * 
 * @author jianchen3
 * @date 2017年10月24日上午10:34:50
 */
public class UserDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户编号
	 */
	@FieldDocs("用户编号")
	@Protobuf(fieldType = FieldType.STRING, order = 1, required = false)
	private String id;
	/**
	 * 头像
	 */
	@FieldDocs("头像")
	@Protobuf(fieldType = FieldType.STRING, order = 2, required = false)
	private String head;
	/**
	 * 昵称
	 */
	@FieldDocs("昵称")
	@Protobuf(fieldType = FieldType.STRING, order = 3, required = false)
	private String nick;
	/**
	 * 性别
	 */
	@FieldDocs("性别")
	@Protobuf(fieldType = FieldType.INT32, order = 4, required = false)
	private int sex;
	/**
	 * 签名
	 */
	@FieldDocs("签名")
	@Protobuf(fieldType = FieldType.STRING, order = 5, required = false)
	private String sign;
	/**
	 * 铃音个数
	 */
	@FieldDocs("铃音个数")
	@Protobuf(fieldType = FieldType.INT32, order = 8, required = false)
	private int ringcnt;
	/**
	 * MV个数
	 */
	@FieldDocs("MV个数")
	@Protobuf(fieldType = FieldType.INT32, order = 9, required = false)
	private int mvcnt;
	/**
	 * 关注用户数
	 */
	@FieldDocs("关注用户数")
	@Protobuf(fieldType = FieldType.INT32, order = 10, required = false)
	private int sucnt;
	/**
	 * 粉丝数
	 */
	@FieldDocs("粉丝数")
	@Protobuf(fieldType = FieldType.INT32, order = 11, required = false)
	private int fanscnt;
	/**
	 * 收藏铃音数
	 */
	@FieldDocs("收藏铃音数")
	@Protobuf(fieldType = FieldType.INT32, order = 12, required = false)
	private int srcnt;
	/**
	 * 收藏MV数
	 */
	@FieldDocs("收藏MV数")
	@Protobuf(fieldType = FieldType.INT32, order = 13, required = false)
	private int smvcnt;
	/**
	 * 是否已被请求者关注 0：未关注 1：已关注
	 */
	@FieldDocs("是否已被请求者关注 0：未关注 1：已关注")
	@Protobuf(fieldType = FieldType.INT32, order = 14, required = false)
	private int like;
	/**
	 * 设置历史MV总个数
	 */
	@FieldDocs("设置历史MV总个数")
	@Protobuf(fieldType = FieldType.INT32, order = 15, required = false)
	private int smvhcnt;
	@FieldDocs("金币数")
	@Protobuf(fieldType = FieldType.INT64, order = 16)
	private long money;
	@FieldDocs("扩展ID，APP上显示的用户的一个扩展编号")
	@Protobuf(fieldType = FieldType.STRING, order = 17)
	private String extid;
	@FieldDocs("留言数")
	@Protobuf(fieldType = FieldType.INT64, order = 18)
	private long lwcnt;
	/**
	 * 个彩vip状态 0：非个彩 1：个彩
	 */
	@FieldDocs("个彩vip状态 0：非个彩 1：个彩")
	@Protobuf(fieldType = FieldType.INT32, order = 19)
	private int diyvip;

	/**
	 * 视频铃声vip状态 0：非会员 1：会员
	 */
	@FieldDocs("视频铃声vip状态 0：非会员 1：会员")
	@Protobuf(fieldType = FieldType.INT32, order = 20)
	private int mvvip;

	/**
	 * 购买下载铃音个数
	 */
	@FieldDocs("购买下载铃音个数")
	@Protobuf(fieldType = FieldType.STRING, order = 21)
	private String orcnt;

	/**
	 * 用户类型， 0-普通用户，1-独家创作人,2-音乐（歌手）,3-马甲用户，4-版权方（dkwang）
	 */
	private int tp;

	/**
	 * 黑名单用户，0-不是，1-是
	 */
	private int black;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getRingcnt() {
		return ringcnt;
	}

	public void setRingcnt(int ringcnt) {
		this.ringcnt = ringcnt;
	}

	public int getMvcnt() {
		return mvcnt;
	}

	public void setMvcnt(int mvcnt) {
		this.mvcnt = mvcnt;
	}

	public int getSucnt() {
		return sucnt;
	}

	public void setSucnt(int sucnt) {
		this.sucnt = sucnt;
	}

	public int getFanscnt() {
		return fanscnt;
	}

	public void setFanscnt(int fanscnt) {
		this.fanscnt = fanscnt;
	}

	public int getSrcnt() {
		return srcnt;
	}

	public void setSrcnt(int srcnt) {
		this.srcnt = srcnt;
	}

	public int getSmvcnt() {
		return smvcnt;
	}

	public void setSmvcnt(int smvcnt) {
		this.smvcnt = smvcnt;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public int getSmvhcnt() {
		return smvhcnt;
	}

	public void setSmvhcnt(int smvhcnt) {
		this.smvhcnt = smvhcnt;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public String getExtid() {
		return extid;
	}

	public void setExtid(String extid) {
		this.extid = extid;
	}

	public long getLwcnt() {
		return lwcnt;
	}

	public void setLwcnt(long lwcnt) {
		this.lwcnt = lwcnt;
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

	public UserDetail() {
		super();
	}

	public UserDetail(UserSimple user) {
		this.id = user.getId();
		this.head = user.getUserPic();
		this.nick = user.getUserName();
		this.sex = user.getSex();
		this.ringcnt = user.getRingCount();
		this.mvcnt = user.getMvCount();
		this.fanscnt = user.getFans();
		this.like = user.getLike();
		this.sign = user.getSign();
		this.diyvip = user.getDiyvip();
		this.mvvip = user.getMvvip();
		this.tp = user.getTp();
		// this.pno = user.getPno();
		this.black = user.getBlack();
		this.orcnt = user.getOrcnt();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDetail-{\"id\":\"");
		builder.append(id);
		builder.append("\",\"head\":\"");
		builder.append(head);
		builder.append("\",\"nick\":\"");
		builder.append(nick);
		builder.append("\",\"sex\":\"");
		builder.append(sex);
		builder.append("\",\"sign\":\"");
		builder.append(sign);
		builder.append("\",\"ringcnt\":\"");
		builder.append(ringcnt);
		builder.append("\",\"mvcnt\":\"");
		builder.append(mvcnt);
		builder.append("\",\"sucnt\":\"");
		builder.append(sucnt);
		builder.append("\",\"fanscnt\":\"");
		builder.append(fanscnt);
		builder.append("\",\"srcnt\":\"");
		builder.append(srcnt);
		builder.append("\",\"smvcnt\":\"");
		builder.append(smvcnt);
		builder.append("\",\"like\":\"");
		builder.append(like);
		builder.append("\",\"smvhcnt\":\"");
		builder.append(smvhcnt);
		builder.append("\",\"money\":\"");
		builder.append(money);
		builder.append("\",\"extid\":\"");
		builder.append(extid);
		builder.append("\",\"lwcnt\":\"");
		builder.append(lwcnt);
		builder.append("\",\"diyvip\":\"");
		builder.append(diyvip);
		builder.append("\",\"mvvip\":\"");
		builder.append(mvvip);
		builder.append("\",\"tp\":\"");
		builder.append(tp);
		builder.append("\",\"black\":\"");
		builder.append(black);
		builder.append("\",\"orcnt\":\"");
		builder.append(orcnt);
		builder.append("\"}");
		return builder.toString();
	}

}
