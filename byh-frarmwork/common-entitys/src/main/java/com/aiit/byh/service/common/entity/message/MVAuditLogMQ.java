package com.aiit.byh.service.common.entity.message;

import com.aiit.byh.service.common.utils.CommonUtils;
import com.aiit.byh.service.common.utils.json.JsonUtil;

/**
 * 视频铃声审核日志MQ
 * Created by bbhan2 on 2019/7/26
 **/
public class MVAuditLogMQ {

    /**
     * 我方任务编号
     */
    private String bizid;
    /**
     * 审核平台审核任务编号
     */
    private String platformtaskid;
    /**
     * 审核类型
     * 1001、用户昵称；1002、用户大头像；1003、用户小头像；1004、背景图片
     * 2001、用户创作铃音
     * 3001、用户设置铃音
     * 4001、用户求铃；4002、用户答复求铃；4003、用户跟评求铃
     * 5001、用户评论铃音；5002、用户跟评论铃音；5003、用户评论套装；5004、用户跟评论套装；5005、用户评论合辑；5006、用户跟评论合辑；5007、大话酷音评论
     * 5001、用户评论铃音
     * 6001、铃声秀；6002、铃声秀评论
     * 7001、来电宝
     * 8001、用户创作视频铃声
     */
    private String type;
    /**
     * "resourceid": "对应资源编号，如铃声编号",
     */
    private String resourceid;
    /**
     * "extensioninfo": "扩展信息，按需记录",
     */
    private String extensioninfo;
    /**
     * "auditresult": "审核结果:0-审核忽略；1-初审通过；2-初审驳回；3-复审通过；4-复审驳回",
     */
    private int auditresult;
    /**
     * "auditresulttime": "审核结果通知时间，Long类型",
     */
    private Long auditresulttime;
    /**
     * "auditresultdesc": "审核结果描述",
     */
    private String auditresultdesc;
    /**
     * "returncode": "审核结果返回码 0000、成功；2000、参数异常；4000、其他系统异常；",
     */
    private String returncode;
    /**
     * "returndesc": "审核结果描述 0000、成功；2000、参数异常；4000、其他系统异常；",
     */
    private String returndesc;
    /**
     * "createdtime": "创建时间，Long类型",
     */
    private Long ceratetime;
    /**
     * "updatedtime": "更新时间，Long类型",
     */
    private Long updatetime;
    /**
     * "version": "审核的任务的版本"
     */
    private String version;

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getPlatformtaskid() {
        return platformtaskid;
    }

    public void setPlatformtaskid(String platformtaskid) {
        this.platformtaskid = platformtaskid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getExtensioninfo() {
        return extensioninfo;
    }

    public void setExtensioninfo(String extensioninfo) {
        this.extensioninfo = extensioninfo;
    }

    public void setAuditresult(int auditresult) {
        this.auditresult = auditresult;
    }


    public Long getAuditresulttime() {
        return auditresulttime;
    }

    public void setAuditresulttime(Long auditresulttime) {
        this.auditresulttime = auditresulttime;
    }

    public String getAuditresultdesc() {
        return auditresultdesc;
    }

    public void setAuditresultdesc(String auditresultdesc) {
        this.auditresultdesc = auditresultdesc;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getReturndesc() {
        return returndesc;
    }

    public void setReturndesc(String returndesc) {
        this.returndesc = returndesc;
    }

    public Long getCeratetime() {
        return ceratetime;
    }

    public void setCeratetime(Long ceratetime) {
        this.ceratetime = ceratetime;
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "MVAuditLogMQ{" +
                "bizid='" + bizid + '\'' +
                ", platformtaskid='" + platformtaskid + '\'' +
                ", type='" + type + '\'' +
                ", resourceid='" + resourceid + '\'' +
                ", extensioninfo='" + extensioninfo + '\'' +
                ", auditresult='" + auditresult + '\'' +
                ", auditresulttime=" + auditresulttime +
                ", auditresultdesc='" + auditresultdesc + '\'' +
                ", returncode='" + returncode + '\'' +
                ", returndesc='" + returndesc + '\'' +
                ", ceratetime=" + ceratetime +
                ", updatetime=" + updatetime +
                ", version='" + version + '\'' +
                '}';
    }
    public String genJSONMsg(MVAuditLogMQ mvAuditLogMQ) {
        if (CommonUtils.isEmpty(mvAuditLogMQ)) {
            return null;
        }
        return JsonUtil.genJsonString(mvAuditLogMQ);
    }
}
