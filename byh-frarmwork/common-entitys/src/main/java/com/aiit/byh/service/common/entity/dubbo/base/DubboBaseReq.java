package com.aiit.byh.service.common.entity.dubbo.base;

//import com.aiit.byh.com.iflytek.kuyin.service.common.redis.service.com.iflytek.kuyin.service.common.redis.common.client.log.util.DripLogUtils;
import com.aiit.byh.service.common.entity.constants.CommonConstants;
import com.aiit.byh.service.common.utils.encrypt.ExclusiveUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;

/**
 * dubbo服务基础请求类
 *
 * @author dsqin
 * @datetime 2017/9/1
 */
public class DubboBaseReq implements Serializable {

    private static final long serialVersionUID = 2328368388420619010L;

    /**
     * 业务渠道号
     */
    private String appid;
    /**
     * 渠道号
     */
    private String channelId;

    /**
     * 版本号
     */
    private String version;

    /**
     * 应用编号
     */
    private String appNo;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 设备激活编号
     */
    private String activeId;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 调用者标识(使用调用组件的groupid+artifactid+ip）
     */
    private String invoker;

    /**
     * 调用跟踪标识
     */
    private String traceId;

    /**
     * 操作类型 0:Android 1:ios
     * <p>
     * 推荐使用osType
     */
    @Deprecated
    private int operatingSystemType;

    /**
     * 系统类型，0-未知、1-Android、2-iOS、3-H5，默认为0
     */
    private int osType;
    /**
     * 省份
     */
    private String p;

    /**
     * 运营商
     * 1-移动
     * 2-联通
     * 3-电信
     */
    private String ct;
    /**
     * 产品编号
     */
    private String pi = CommonConstants.PRODUCTNO_KUYIN;

    private String userName;

    /**
     * 渠道分组标识
     * 通常根据渠道号查询得出结果（考虑到渠道的分组会发生变化）
     */
    private String channelGroup;
    /**
     * 手机号归属城市名称
     */
    private String city;
    /**
     * 设备位置所在的国家二字代码
     */
    private String lcc;

    /**
     * 设备位置所在省份
     */
    private String lp;

    /**
     * 设备位置所在城市
     */
    private String lcity;

    /**
     * 0或未配置-默认，自有渠道（不带下载功能），必须要有无线版权或（运营商+互联网版权（5+6+10））
     * 1-指定渠道，要有设置能力（无线版权或运营商彩铃）
     * 2-API的渠道以及自有渠道（带下载功能），必须要有互联网版权（5+6+10）+（无线版权或运营商）
     * 3--运营商彩铃，仅支持个彩用户的DIY铃音（1+2+3）+试听下载剪辑
     * 4--APP||酷音短视频，仅支持试听下载剪辑
     */
    private int rule;

    /**
     * 场景。
     * <p>
     * 场景定义：
     * 铃音-栏目;
     * 100-常规;
     * 101-更换彩铃推荐;
     * 102-更换来电推荐;
     * 103-更换短信推荐;
     * 104-更换闹铃推荐;
     * 105-更换通知推荐;
     * 106-视频铃声配乐推荐;
     * <p>
     * 铃音-搜索;
     * 200-常规;
     * 201-视频铃声配乐;
     * <p>
     * 铃音-我的;
     * 300-常规（预留，暂不使用）;
     * 301-DIY;
     * 302-收藏;
     * 303-设置彩铃历史;
     * 304-设置来电历史;
     * 305-设置短信历史;
     * 306-设置闹铃历史;
     * 307-设置通知历史;
     * 308-购买振铃;
     * 309-DIY-用于视频铃声配乐;
     * 310-收藏-用于视频铃声配乐;
     * <p>
     * 铃音-其他;
     * 400-常规（预留，暂不使用）;
     * 401-分享页面;
     * 402-单曲订购页;
     * 403-设置彩铃;
     * 404-设置振铃;
     * 405-栏目构建;
     * <p>
     * 视频铃声-栏目;
     * 500-常规;
     * 501-动态壁纸推荐;
     * 502-来电秀推荐;
     * 503-去电秀推荐;
     * <p>
     * 视频铃声-搜索;
     * 600-常规;
     * <p>
     * 视频铃声-我的;
     * 700-常规（预留，暂不使用）;
     * 701-DIY;
     * 702-收藏;
     * 703-设置历史;
     * <p>
     * 视频铃声-其他;
     * 800-常规（预留，暂不使用）;
     * 801-分享页面;
     * 802-设置来电秀;
     * 803-设置去电秀;
     * 804-栏目构建;
     */
    private int scene;

    /**
     * 酷音平台的产品编号
     */
    private String kuYinPlatProductNo;

    /**
     * 手机号码
     */
    private String phone;

    public DubboBaseReq() {

    }

//    public DubboBaseReq(ApiBaseRequest apiRequest, String invoker) {
//        this(apiRequest, null, invoker);
//    }
//
//    public DubboBaseReq(ApiBaseRequest apiRequest, ApiBaseRingRequest apiRingRequest, String invoker) {
//        this.invoker = invoker;
//        if (null != apiRequest) {
//            if (StringUtils.isNotBlank(apiRequest.getPi())) {
//                this.pi = apiRequest.getPi();
//            }
//            this.activeId = apiRequest.getUi();
//            this.appNo = apiRequest.getAn();
//            this.channelId = apiRequest.getCn();
//            this.deviceId = apiRequest.getDi();
//            this.operatingSystemType = apiRequest.getOt();
//            this.userId = apiRequest.getUsid();
//            this.version = apiRequest.getV();
////            this.traceId = DripLogUtils.genTraceId(apiRequest.getTc());
//            this.p = apiRequest.getP();
//            this.city = apiRequest.getCity();
//            this.lcity = apiRequest.getLcity();
//            this.lcc = apiRequest.getLcc();
//            this.lp = apiRequest.getLp();
//            this.osType = apiRequest.getOt();
//
//            if (StringUtils.isNotBlank(apiRequest.getScene())) {
//                this.scene = NumberUtils.toInt(apiRequest.getScene());
//            }
//        }
//        if (null != apiRingRequest) {
//            if (StringUtils.isNotBlank(apiRingRequest.getCt())) {
//                this.ct = apiRingRequest.getCt();
//            }
//        }
//    }
//
//    public DubboBaseReq(WebApiBaseRequest apiRequest, String invoker) {
//        this.userId = apiRequest.getUsid();
//        this.version = apiRequest.getV();
////        this.traceId = DripLogUtils.genTraceId(apiRequest.getTc());
//        this.invoker = invoker;
//        this.userName = apiRequest.getUsnm();
//    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getOperatingSystemType() {
        return operatingSystemType;
    }

    public void setOperatingSystemType(int operatingSystemType) {
        this.operatingSystemType = operatingSystemType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getInvoker() {
        return invoker;
    }

    public void setInvoker(String invoker) {
        this.invoker = invoker;
    }

    public String getTraceId() {
        if (StringUtils.isBlank(this.traceId)) {
//            this.traceId = DripLogUtils.genTraceId(this.traceId);
        }
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public int getOsType() {
        return osType;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getUserBizId() {
        return ExclusiveUtil.encode(this.userId);
    }

    public String getChannelGroup() {
        return channelGroup;
    }

    public void setChannelGroup(String channelGroup) {
        this.channelGroup = channelGroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLcc() {
        return lcc;
    }

    public void setLcc(String lcc) {
        this.lcc = lcc;
    }

    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public String getLcity() {
        return lcity;
    }

    public void setLcity(String lcity) {
        this.lcity = lcity;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public String getKuYinPlatProductNo() {
        return kuYinPlatProductNo;
    }

    public void setKuYinPlatProductNo(String kuYinPlatProductNo) {
        this.kuYinPlatProductNo = kuYinPlatProductNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DubboBaseReq-{");
        sb.append("\"appid\":\"")
                .append(appid).append('\"');
        sb.append(",\"channelId\":\"")
                .append(channelId).append('\"');
        sb.append(",\"version\":\"")
                .append(version).append('\"');
        sb.append(",\"appNo\":\"")
                .append(appNo).append('\"');
        sb.append(",\"userId\":\"")
                .append(userId).append('\"');
        sb.append(",\"activeId\":\"")
                .append(activeId).append('\"');
        sb.append(",\"deviceId\":\"")
                .append(deviceId).append('\"');
        sb.append(",\"invoker\":\"")
                .append(invoker).append('\"');
        sb.append(",\"traceId\":\"")
                .append(traceId).append('\"');
        sb.append(",\"operatingSystemType\":")
                .append(operatingSystemType);
        sb.append(",\"osType\":")
                .append(osType);
        sb.append(",\"p\":\"")
                .append(p).append('\"');
        sb.append(",\"ct\":\"")
                .append(ct).append('\"');
        sb.append(",\"pi\":\"")
                .append(pi).append('\"');
        sb.append(",\"userName\":\"")
                .append(userName).append('\"');
        sb.append(",\"channelGroup\":\"")
                .append(channelGroup).append('\"');
        sb.append(",\"city\":\"")
                .append(city).append('\"');
        sb.append(",\"lcc\":\"")
                .append(lcc).append('\"');
        sb.append(",\"lp\":\"")
                .append(lp).append('\"');
        sb.append(",\"lcity\":\"")
                .append(lcity).append('\"');
        sb.append(",\"rule\":")
                .append(rule);
        sb.append(",\"scene\":")
                .append(scene);
        sb.append(",\"kuYinPlatProductNo\":\"")
                .append(kuYinPlatProductNo).append('\"');
        sb.append(",\"phone\":\"")
                .append(phone).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
