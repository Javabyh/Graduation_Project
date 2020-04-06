package com.aiit.byh.service.common.entity.dubbo.base;

import java.io.Serializable;

/**
 * dubbo服务基础返回类
 *
 * @author dsqin
 * @datetime 2017/9/1
 */
public class DubboBaseResp implements Serializable {

    private static final long serialVersionUID = 2414834577913346701L;

    /**
     * 返回码
     */
    private String retCode;

    /**
     * 返回描述
     */
    private String retDesc;

    /**
     * 调用跟踪标识
     */
    private String traceId;

    public DubboBaseResp(String retCode, String retDesc, String traceId) {
        this.retCode = retCode;
        this.retDesc = retDesc;
        this.traceId = traceId;
    }

    public DubboBaseResp(String retCode, String retDesc) {
        this.retCode = retCode;
        this.retDesc = retDesc;
    }

    public DubboBaseResp(String traceId) {
        this.retCode = DubboCommonRetCode.SUCCESS;
        this.traceId = traceId;
    }

    public DubboBaseResp() {
        this.retCode = DubboCommonRetCode.SUCCESS;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public static DubboBaseResp buildInvalidParamResp() {
        return new DubboBaseResp(DubboCommonRetCode.IVALID_PARAM, "参数验证失败");
    }

    public boolean isSuccess() {
        return DubboCommonRetCode.SUCCESS.equals(this.retCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DubboBaseResp{");
        sb.append("retCode='").append(retCode).append('\'');
        sb.append(", retDesc='").append(retDesc).append('\'');
        sb.append(", traceId='").append(traceId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
