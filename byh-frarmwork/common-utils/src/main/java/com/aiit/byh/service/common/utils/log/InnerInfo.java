package com.aiit.byh.service.common.utils.log;

import java.io.Serializable;

/**
 * Created by jhyuan on 2017/9/8.
 */
public class InnerInfo implements Serializable {
    private String reqpkg;
    private String respkg;
    private String retcode;
    private String errinfo;
    private String rpcid;
    private String rpctype;
    private String stm;
    private String utm;
    private String traceid;

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

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getErrinfo() {
        return errinfo;
    }

    public void setErrinfo(String errinfo) {
        this.errinfo = errinfo;
    }

    public String getRpcid() {
        return rpcid;
    }

    public void setRpcid(String rpcid) {
        this.rpcid = rpcid;
    }

    public String getRpctype() {
        return rpctype;
    }

    public void setRpctype(String rpctype) {
        this.rpctype = rpctype;
    }

    public String getStm() {
        return stm;
    }

    public void setStm(String stm) {
        this.stm = stm;
    }

    public String getUtm() {
        return utm;
    }

    public void setUtm(String utm) {
        this.utm = utm;
    }

    public String getTraceid() {
        return traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InnerInfo{");
        sb.append("reqpkg='").append(reqpkg).append('\'');
        sb.append(", respkg='").append(respkg).append('\'');
        sb.append(", retcode='").append(retcode).append('\'');
        sb.append(", errinfo='").append(errinfo).append('\'');
        sb.append(", rpcid='").append(rpcid).append('\'');
        sb.append(", rpctype='").append(rpctype).append('\'');
        sb.append(", stm='").append(stm).append('\'');
        sb.append(", utm='").append(utm).append('\'');
        sb.append(", traceid='").append(traceid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
