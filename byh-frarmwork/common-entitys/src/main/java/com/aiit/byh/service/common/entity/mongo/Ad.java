package com.aiit.byh.service.common.entity.mongo;

import org.bson.types.ObjectId;
import org.senseframework.support.orm.mongodb.MongoCollection;
import org.senseframework.support.orm.mongodb.MongoColumn;

import java.util.List;

/**
 * 广告配置信息
 * Created by lysun3 on 2018/3/23.
 */
@MongoCollection("ad")
public class Ad {
    private static final long serialVersionUID = 1L;
    @MongoColumn(desc = "编号")
    private ObjectId _id;

    @MongoColumn(desc = "广告编号，业务编号")
    private Long bizid;

    @MongoColumn(desc = "配置名称")
    private String name;

    @MongoColumn(desc = "配置图片地址")
    private String imgurl;

    @MongoColumn(desc = "打开方式,5链接时指定打开方式1-打开外部浏览器 2-打开内部浏览器")
    private int action;

    @MongoColumn(desc = "资源类型：0-无 1-H5链接 2-视频铃声会员开通页 3-用户")
    private String value;

    @MongoColumn(desc = "资源类型：0-无 1-H5链接 2-视频铃声会员开通页 3-用户")
    private int resourcetype;

    @MongoColumn(desc = "开始时间")
    private Long starttime;

    @MongoColumn(desc = "结束时间")
    private Long endtime;

    @MongoColumn(desc = "状态")
    private int status;

    @MongoColumn(desc = "类型:1-闪屏 ...")
    private int type;

    @MongoColumn(desc = "应用编号：所有0表示")
    private List<String> appno;

    @MongoColumn(desc = "渠道编号：所有0表示")
    private List<String> channel;

    @MongoColumn(desc = "开始时间")
    private Long ctm;

    @MongoColumn(desc = "结束时间")
    private Long utm;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Long getBizid() {
        return bizid;
    }

    public void setBizid(Long bizid) {
        this.bizid = bizid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getUtm() {
        return utm;
    }

    public void setUtm(Long utm) {
        this.utm = utm;
    }

    public Long getCtm() {
        return ctm;
    }

    public void setCtm(Long ctm) {
        this.ctm = ctm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(int resourcetype) {
        this.resourcetype = resourcetype;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getAppno() {
        return appno;
    }

    public void setAppno(List<String> appno) {
        this.appno = appno;
    }

    public List<String> getChannel() {
        return channel;
    }

    public void setChannel(List<String> channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ads{");
        sb.append("_id=").append(_id);
        sb.append(", bizid=").append(bizid);
        sb.append(", name='").append(name).append('\'');
        sb.append(", imgurl='").append(imgurl).append('\'');
        sb.append(", action=").append(action);
        sb.append(", value='").append(value).append('\'');
        sb.append(", resourcetype=").append(resourcetype);
        sb.append(", starttime=").append(starttime);
        sb.append(", endtime=").append(endtime);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", appno=").append(appno);
        sb.append(", channel=").append(channel);
        sb.append(", ctm=").append(ctm);
        sb.append(", utm=").append(utm);
        sb.append('}');
        return sb.toString();
    }
}
