package com.aiit.byh.service.common.entity.constants;

import com.google.common.collect.Maps;
import com.aiit.byh.service.common.utils.CommonUtils;
import com.aiit.byh.service.common.utils.config.ConfigUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 静态变量
 *
 * @author dsqin
 */
public class CommonConstants {
    /**
     * 1
     */
    public final static String ONE_STRING = "1";

    /**
     * -1
     */
    public final static String NEGATIVE_ONE_STRING = "-1";

    /**
     * 0
     */
    public final static String ZERO_STRING = "0";

    /**
     * 短信标签
     */
    public final static String SMS_LABEL = "短信";

    /**
     * 竖线
     */
    public final static String VERTICAL_LINE = "|";

    /**
     * 冒号 :
     */
    public static final String SEPERATOR_SEMI = ":"; // 分隔符 :

    /**
     * 顿号
     */
    public static final String PAUSE = "、";

    /**
     * 斜线
     */
    public static final String SLASH = "/";

    /**
     * 左括号 (
     */
    public static final String LEFT_BRACKET = "(";

    /**
     * 来电宝审核类型
     */
    public final static String SMARTCALL_AUDIT_TYPE = "7001";

    /**
     * 酷音产品编号
     */
    public final static String PRODUCTNO_KUYIN = "001";

    /**
     * 状态--有效
     */
    public final static int STATUS_VALID = 1;

    /**
     * 状态--无效
     */
    public final static int STATUS_UNVALID = 0;

    /**
     * osid--安卓
     */
    public static final String OSID_ANDROID = "Android";

    /**
     * osid--ios
     */
    public static final String OSID_IOS = "iPhone";

    /**
     * 酷音小编系统配置key
     */
    public static final String KUYIN_EDITOR_USERID = "kuyin.editor.userid";

    /**
     * 1
     */
    public final static int ONE_INT = 1;

    /**
     * 0
     */
    public final static int ZERO_INT = 0;

    /**
     * MV会员周期
     */
    public final static int MV_VIP_CYCLE = 31;

    /**
     * 两个&符号，&&
     */
    public final static String VERTICAL_DOUBLEAND = "&&";

    /**
     * 小横杠 -
     */
    public final static String LITTLE_HYPHEN = "-";

    /**
     * 是
     */
    public final static String YES = "是";

    /**
     * 否
     */
    public final static String NO = "否";

    /**
     * fee最大值  单位为元
     */
    public final static int REDIS_CONSTANT_RING_BIG = 15;

    /**
     * fee最小值  单位为元
     */
    public final static int REDIS_CONSTANT_RING_SMALL = 0;

    /**
     * 点 .
     */
    public final static String DOT = ".";

    /**
     * 分号
     */
    public static final String SEPERATOR_SEMICOLON = ";";

    /**
     * 分号，中文，；
     */
    public final static String SEMICOLON_CHS = "；";

    /**
     * 1小时
     */
    public final static String ONEHOUR_STRING = "1小时";

    /**
     * 分钟
     */
    public final static String MINUTE_STRING = "分钟";

    /**
     * 小时
     */
    public final static String HOUR_STRING = "小时";

    /**
     * 移动
     */
    public final static String MOBILE_STRING = "移动";

    /**
     * 联通
     */
    public final static String UNICOM_STRING = "联通";

    /**
     * 电信
     */
    public final static String TELECOM_STRING = "电信";

    /**
     * 铃声域名集合
     */
    public static Map<String, String> MAP_DOMAINREPLACE = Maps.newHashMap();

    static {
        String domainReplace = ConfigUtil.getString("domain.replace");
        if (StringUtils.isNotBlank(domainReplace)) {
            //分割
            String[] kvs = StringUtils.split(domainReplace, SEPERATOR_SEMICOLON);
            if (CommonUtils.isNotEmpty(kvs)) {
                //再次分割
                for (String kv : kvs) {
                    String[] temp = StringUtils.split(kv, VERTICAL_LINE);
                    if (CommonUtils.isEmpty(temp)
                            || 2 != temp.length
                            || StringUtils.isBlank(temp[0])
                            || StringUtils.isBlank(temp[1])) {
                        continue;
                    }
                    MAP_DOMAINREPLACE.put(temp[0], temp[1]);
                }
            }
        }
    }

    /**
     * 酷音H5编号
     */
    public final static String PRODUCTNO_H5 = "002";

    /**
     * 2
     */
    public final static int TWO_INT = 2;

    /**
     * 获取数据单页大小
     */
    public static final int PAGESIZE = 200;

}
