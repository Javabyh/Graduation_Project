package com.aiit.byh.service.common.utils.log;

import java.io.Serializable;

/**
 * Drip平台日志信息
 * Created by jhyuan on 2017/9/8.
 */
public class DripLogInfo implements Serializable {
    /**
     * *说明* 是否必填 *字段示例*
     * <p>
     * 应用标识 是 100IMEI
     */
    private String appid;
    /**
     * 平台标识 是 android
     */
    private String osid;
    /**
     * 用户标识 否
     */
    private String uid;
    /**
     * 组件名 是
     */
    private String cpname;
    /**
     * 机房 是
     */
    private String room;
    /**
     * 本地ip 是
     */
    private String localip;
    /**
     * 返回状态 是
     */
    private String status;
    /**
     * 开始时间 是
     */
    private String stm;
    /**
     * 结束时间 是
     */
    private String etm;
    /**
     * 日志创建时间 是
     */
    private String ctm;
    /**
     * 内部信息 是
     */
    private InnerInfo inner;
    /**
     * 远程ip 否
     */
    private String remoteip;
    /**
     * 请求地址 否
     */
    private String reqadd;
    /**
     * 使用时间 否
     */
    private String utm;
    /**
     * 客户端版本 否
     */
    private String cver;
    /**
     * 组件协议版本 否
     */
    private String pver;
    /**
     * 客户端版本渠道 否
     */
    private String df;
    /**
     * 手机imsi卡号 否
     */
    private String imsi;
    /**
     * 客户端设备号 否
     */
    private String deviceid;
    /**
     * 手机号码 否
     */
    private String caller;
    /**
     * 调用链跟踪标识 否
     */
    private String traceid;
    /**
     * 鉴权id 否
     */
    private String tokenid;
    /**
     * 返回码 否
     */
    private String retcode;
    /**
     * 返回描述 否
     */
    private String retdesc;
    /**
     * 请求包 否
     */
    private String reqpkg;
    /**
     * 返回包 否
     */
    private String respkg;
    /**
     * 接入点 否
     */
    private String ap;
    /**
     * 终端信息 否
     */
    private String ua;

    /**
     * 接口名称
     */
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public DripLogInfo() {

    }

    public DripLogInfo appid(String appid) {
        this.appid = appid;
        return this;
    }

    public DripLogInfo room(String room) {
        this.room = room;
        return this;
    }

    public DripLogInfo cpname(String cpname) {
        this.cpname = cpname;
        return this;
    }

    public DripLogInfo traceid(String traceid) {
        this.traceid = traceid;
        return this;
    }

    public DripLogInfo osid(String osid) {
        this.osid = osid;
        return this;
    }

    public DripLogInfo usid(String usid) {
        this.uid = usid;
        return this;
    }

    public DripLogInfo phone(String phone) {
        this.caller = phone;
        return this;
    }

    public DripLogInfo ip(String ip) {
        this.localip = ip;
        return this;
    }

    public DripLogInfo status(String status) {
        this.status = status;
        return this;
    }

    public DripLogInfo stm(String stm) {
        this.stm = stm;
        return this;
    }

    public DripLogInfo etm(String etm) {
        this.etm = etm;
        return this;
    }

    public DripLogInfo ctm(String ctm) {
        this.ctm = ctm;
        return this;
    }

    public DripLogInfo utm(String utm) {
        this.utm = utm;
        return this;
    }

    public DripLogInfo inner(InnerInfo inner) {
        this.inner = inner;
        return this;
    }

    public DripLogInfo clientversion(String clientversion) {
        this.cver = clientversion;
        return this;
    }

    public DripLogInfo channelno(String channelno) {
        this.df = channelno;
        return this;
    }

    public DripLogInfo deviceid(String deviceid) {
        this.deviceid = deviceid;
        return this;
    }

    public DripLogInfo retcode(String retcode) {
        this.retcode = retcode;
        return this;
    }

    public DripLogInfo retdesc(String retdesc) {
        this.retdesc = retdesc;
        return this;
    }

    public DripLogInfo reqpkg(String reqpkg) {
        this.reqpkg = reqpkg;
        return this;
    }

    public DripLogInfo respkg(String respkg) {
        this.respkg = respkg;
        return this;
    }

    public DripLogInfo cmd(String cmd) {
        this.cmd = cmd;
        return this;
    }

    public DripLogInfo build() {
        return new DripLogInfo();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOsid() {
        return osid;
    }

    public void setOsid(String osid) {
        this.osid = osid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLocalip() {
        return localip;
    }

    public void setLocalip(String localip) {
        this.localip = localip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStm() {
        return stm;
    }

    public void setStm(String stm) {
        this.stm = stm;
    }

    public String getEtm() {
        return etm;
    }

    public void setEtm(String etm) {
        this.etm = etm;
    }

    public String getCtm() {
        return ctm;
    }

    public void setCtm(String ctm) {
        this.ctm = ctm;
    }

    public InnerInfo getInner() {
        return inner;
    }

    public void setInner(InnerInfo inner) {
        this.inner = inner;
    }

    public String getRemoteip() {
        return remoteip;
    }

    public void setRemoteip(String remoteip) {
        this.remoteip = remoteip;
    }

    public String getReqadd() {
        return reqadd;
    }

    public void setReqadd(String reqadd) {
        this.reqadd = reqadd;
    }

    public String getUtm() {
        return utm;
    }

    public void setUtm(String utm) {
        this.utm = utm;
    }

    public String getCver() {
        return cver;
    }

    public void setCver(String cver) {
        this.cver = cver;
    }

    public String getPver() {
        return pver;
    }

    public void setPver(String pver) {
        this.pver = pver;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getTraceid() {
        return traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetdesc() {
        return retdesc;
    }

    public void setRetdesc(String retdesc) {
        this.retdesc = retdesc;
    }

    public String getReqpkg() {
        return reqpkg;
    }

    public void setReqpkg(String reqpkg) {
        this.reqpkg = reqpkg;
    }

    public String getRespkg() {
        return respkg;
    }

    public void setRespkg(String respkg) {
        this.respkg = respkg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DripLogger{");
        sb.append("appid='").append(appid).append('\'');
        sb.append(", osid='").append(osid).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", cpname='").append(cpname).append('\'');
        sb.append(", room='").append(room).append('\'');
        sb.append(", localip='").append(localip).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", stm='").append(stm).append('\'');
        sb.append(", etm='").append(etm).append('\'');
        sb.append(", ctm='").append(ctm).append('\'');
        sb.append(", inner='").append(inner).append('\'');
        sb.append(", remoteip='").append(remoteip).append('\'');
        sb.append(", reqadd='").append(reqadd).append('\'');
        sb.append(", utm='").append(utm).append('\'');
        sb.append(", cver='").append(cver).append('\'');
        sb.append(", pver='").append(pver).append('\'');
        sb.append(", df='").append(df).append('\'');
        sb.append(", imsi='").append(imsi).append('\'');
        sb.append(", deviceid='").append(deviceid).append('\'');
        sb.append(", caller='").append(caller).append('\'');
        sb.append(", traceid='").append(traceid).append('\'');
        sb.append(", tokenid='").append(tokenid).append('\'');
        sb.append(", retcode='").append(retcode).append('\'');
        sb.append(", retdesc='").append(retdesc).append('\'');
        sb.append(", reqpkg='").append(reqpkg).append('\'');
        sb.append(", respkg='").append(respkg).append('\'');
        sb.append(", df='").append(df).append('\'');
        sb.append(", ap='").append(ap).append('\'');
        sb.append(", ua='").append(ua).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
