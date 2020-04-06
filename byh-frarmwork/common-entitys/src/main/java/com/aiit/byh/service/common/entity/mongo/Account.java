package com.aiit.byh.service.common.entity.mongo;

import com.aiit.byh.service.common.entity.redis.user.UserRedis;
import com.aiit.byh.service.common.utils.CommonUtils;
import com.aiit.byh.service.common.utils.encrypt.ExclusiveUtil;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.senseframework.support.orm.mongodb.MongoCollection;
import org.senseframework.support.orm.mongodb.MongoColumn;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 账号信息
 * 
 * @author jianchen3
 * @date 2017年10月24日上午10:04:26
 */
@MongoCollection("account")
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@MongoColumn(desc = "编号")
	private ObjectId _id;

	@MongoColumn(desc = "用户编号，业务编号")
	private String bizid;

	@MongoColumn(desc = "扩展ID，用于拷贝搜索，是否直接使用bizid")
	private String extid;

	@MongoColumn(desc = "老用户标识")
	private Long userid;

	@MongoColumn(desc = "用户昵称")
	private String name;

	@MongoColumn(desc = "头像图片Url")
	private String headimg;

	@MongoColumn(desc = "作者的类型， 0-普通用户，1-独家创作人,2-音乐（歌手）,3-马甲用户")
	private Integer usertype;

	@MongoColumn(desc = "用户简介")
	private String introduction;

	@MongoColumn(desc = "性别：0-未知，1-男，2-女")
	private Integer sex;
	@MongoColumn(desc = "签名")
	private String sign;
	@MongoColumn(desc = "用户机型名称（默认显示系统配置的，用户可修改）")
	private String phonemodel;
	@MongoColumn(desc = "黑名单用户，0-不是，1-是")
	private Integer black;
    /**
     * 粉丝数
     */
    private long fanscount;
    /**
     *留言数
     */
private  long leavewordcount;
	/**
	 * 状态 1:有效 0:无效
	 */
	private int status;

	/**
	 * 创建时间
	 */
	private long createdtime;
	/**
	 * 更新时间
	 */
	private long updatetime;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

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

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPhonemodel() {
		return phonemodel;
	}

	public void setPhonemodel(String phonemodel) {
		this.phonemodel = phonemodel;
	}

	public Integer getBlack() {
		return black;
	}

	public void setBlack(Integer black) {
		this.black = black;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(long createdtime) {
		this.createdtime = createdtime;
	}

	public long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
	}

    public long getFanscount() {
        return fanscount;
    }

    public void setFanscount(long fanscount) {
        this.fanscount = fanscount;
    }

    public long getLeavewordcount() {
        return leavewordcount;
    }

    public void setLeavewordcount(long leavewordcount) {
        this.leavewordcount = leavewordcount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("_id=").append(_id);
        sb.append(", bizid='").append(bizid).append('\'');
        sb.append(", extid='").append(extid).append('\'');
        sb.append(", userid=").append(userid);
        sb.append(", name='").append(name).append('\'');
        sb.append(", headimg='").append(headimg).append('\'');
        sb.append(", usertype=").append(usertype);
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", sex=").append(sex);
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", phonemodel='").append(phonemodel).append('\'');
        sb.append(", black=").append(black);
        sb.append(", fanscount=").append(fanscount);
        sb.append(", status=").append(status);
        sb.append(", createdtime=").append(createdtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append('}');
        return sb.toString();
    }


	public UserRedis genUserRedis() {
		UserRedis user = new UserRedis();
		user.setUserID(this.bizid);
		user.setUserName(this.name);
		user.setBigImageUrl(this.headimg);
		user.setSmallImageUrl(this.headimg);
		if(null != this.sex){
			user.setSex(String.valueOf(this.sex));
		}
		user.setSign(this.sign);
		if(null != this.usertype){
			user.setUserType(String.valueOf(this.usertype));
		}
		return user;
	}
	
	/**
	 * 生成redis的map
	 * @return
	 */
	public Map<String, String>  genUserRedisMap() {
		UserRedis user = genUserRedis();
		Map<String, String> kvs = CommonUtils.describe(user, true);
		return kvs;
	}

}
