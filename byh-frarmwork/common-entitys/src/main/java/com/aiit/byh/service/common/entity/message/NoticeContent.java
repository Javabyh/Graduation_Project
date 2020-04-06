package com.aiit.byh.service.common.entity.message;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by jhyuan on 2017/11/18.
 */
public class NoticeContent implements Serializable {
    /**
     * 版本
     */
    private String v;
    /**
     * 消息类型 1:系统通知 2：系统消息 3：用户消息
     */
    private String mt;
    /**
     * 消息编号，当mt消息类型为2、3时有效，默认为空
     */
    private String id;
    /**
     * 用户编号 接收者，当mt消息类型为2、3时有效，默认为空
     */
    private String uid;
    /**
     * 消息子类型
     */
    private int mst;
    /**
     * 消息体
     */
    private NoticeBody b;
    /**
     * 消息体对应数据的json格式串
     */
    private List<Map> burl;
    /**
     * 创建时间
     */
    private String ct;

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getMst() {
        return mst;
    }

    public void setMst(int mst) {
        this.mst = mst;
    }

    public NoticeBody getB() {
        return b;
    }

    public void setB(NoticeBody b) {
        this.b = b;
    }


    public List<Map> getBurl() {
        return burl;
    }

    public void setBurl(List<Map> burl) {
        this.burl = burl;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NoticeContent{");
        sb.append("v='").append(v).append('\'');
        sb.append(", mt='").append(mt).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", mst='").append(mst).append('\'');
        sb.append(", b=").append(b);
        sb.append(", burl='").append(burl).append('\'');
        sb.append(", ct='").append(ct).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
