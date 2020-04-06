package com.aiit.byh.service.common.entity.dubbo.base;

/**
 * dubbo服务公共返回码
 *
 * @author dsqin
 * @datetime 2017/9/1
 */
public class DubboCommonRetCode {

    /**
     *
     * 返回码由4位数字组成，其中第1位为返回码类型，第2-3位为业务识别位，4位为业务自主定义位。
     * 通用返回码定义：
     * 数值	含义
     * 0000	成功
     * 1000	参数验证失败
     * 2000	本页无数据
     * 3000	重复处理
     * 4000	非法操作
     * 9999	系统未定义异常
     * 返回码类型定义：
     * 数值	含义
     * 0	成功类
     * 1	参数验证错误类
     * 2	资源为空或者不存在类
     * 3	重复处理类
     * 4	操作失败类
     * 业务识别位定义：
     * 数值	定义
     * 00	铃声查询/操作
     * 10	视频铃声查询/基础操作
     * 20	设彩铃
     * 30	H5查询/基础操作
     * 40	支付
     * 50	活动
     * 60	账户
     * 70   曲库
     */

    /**
     * 返回码：成功
     */
    public final static String SUCCESS = "0000";

    /**
     * 返回码：非法参数
     */
    public final static String IVALID_PARAM = "1000";
    /**
     * 验证码不正确
     */
    public final static String SMS_RANDOMCODE_ERROR = "1020";

    /**
     * 验证码过期
     */
    public final static String SMS_RANDOMCODE_TIMEOUT = "1022";

    /**
     * 验证码验证超过指定次数
     */
    public final static String SMS_RANDOMCODE_MAXRETRYTIMES = "1023";

    /**
     * 号码归属地未知
     */
    public final static String PHONELOCAL_NOTEXIST = "1024";
    /**
     * 1026 验证码超过最小时间间隔
     */
    public static final String SMS_RANDOMCODE_LESSTHANMINTIME = "1026";
    /**
     * 1027 获取验证码达到上限次数
     */
    public static final String SMS_RANDOMCODE_UPPERLIMITTIMES = "1027";
    /**
     * 1028 超过最大失败次数
     */
    public static final String SMS_RANDOMCODE_MAXRETRYFAILURETIMES = "1028";

    /**
     * 兑换码不正确
     */
    public final static String DUI_CDKEY_ERROR = "1029";

    /**
     * 兑换码过期
     */
    public final static String DUI_CDKEY_TIMEOUT = "1030";

    /**
     * 兑换码已使用
     */
    public final static String DUI_CDKEY_USED = "1031";

    /**
     * 栏目资源无数据
     */
    public final static String RES_NOMORE_DATA = "2000";

    /**
     * 栏目资源未更新
     */
    public final static String RES_NOT_UPDATE = "2001";

    /**
     * 铃声评论不存在
     */
    public final static String COMMENT_NOTEXIST = "2002";

    /**
     * 铃声不存在
     */
    public static final String RING_NOTEXIST = "2003";
    /**
     * TOKEN不存在
     */
    public final static String TOKEN_NOTEXIST = "2004";
    /**
     * 用户不存在
     */
    public final static String USER_NOTEXIST = "2005";
    /**
     * 用户已被禁用
     */
    public final static String USER_DISABLE = "2015";
    /**
     * 用户密码错误
     */
    public final static String USER_PWD_ERROR = "2016";
    /**
     * 用户权限不足
     */
    public final static String USER_LACK_AUTHORITY = "2017";

    /**
     * 礼物不存在
     */
    public final static String PRESENT_NOTEXIST = "2007";

    /**
     * 来电mv - 会员信息不存在
     */
    public final static String MVVIP_USER_NOT_EXIST = "2060";

    /**
     * 视频铃声栏目不存在
     */
    public static final String MVRING_COLUMN_NOTEXIST = "2100";

    /**
     * 视频铃声栏目未更新
     */
    public static final String MVRING_COLUMN_NOTUPDATE = "2101";

    /**
     * 视频铃声不存在
     */
    public static final String MVRING_NOTEXIST = "2102";

    /* * 视频铃声栏目下用户推荐不存在
     */
    public static final String MVRING_COLUMN_USERRECM_NOTEXIST = "2103";

    /**
     * 视频铃声栏目下的精选词推荐不存在
     */
    public static final String MVRING_COLUMN_WORDRECM_NOTEXIST = "2104";

    /**
     * 视频铃声收藏信息
     */
    public static final String MVRING_STOREINFO_NOTEXIST = "2105";

    /**
     * 视频铃声运营点计费信息不存在
     */
    public static final String MVRING_OPERATOR_NODE_NOTEXIST = "2106";

    /**
     * 重复提交任务
     */
    public final static String REPEATED_OPERATION = "3000";

    /**
     * 正在签约，已经提交给平台，但未获得结果
     */
    public static final String MVRING_CONTRACTING = "3001";

    /**
     * 已经是会员
     */
    public static final String MVRING_ALREADY_VIP = "3002";

    /**
     * 正在开通中
     */
    public static final String MVRING_OPENING = "3003";

    /**
     * 平台处已经签约成功，我方状态未签约
     */
    public static final String MVRING_PLATFORM_CONTRACTED = "3004";

    /**
     * 开通任务处理中
     */
    public static final String MVRING_TASK_PROCESSING = "3005";

    /**
     * 非法操作
     */
    public final static String ILLEGAL_OPERATION = "4000";

    /**
     * 非评论本人，不能删除
     */
    public final static String NOT_COMMENT_AUTHOR = "4001";
    /**
     * 4101 黑名单用户
     */
    public final static String BLACK_USER = "4101";

    /**
     * 4102 用户运营商状态异常
     * （通常需要联系运营商解决，否则相关业务无法成功继续下去）
     */
    public static final String USERSTATUSERROR_CARRIER = "4102";

    /**
     * 金币不足
     */
    public final static String MONEY_NOTENOUGH = "4400";

    /**
     * 铃音重复购买
     */
    public final static String RING_REPEAT_PURCHASE = "4401";
    /**
     * 铃音查询不存在
     */
    public final static String RING_QUERY_NOTEXISTS = "4402";
    /**
     * 铃音不可购买
     */
    public final static String RING_NOT_DOWNLOAD = "4403";
    /**
     * 非收费铃音
     */
    public final static String RING_CHARGE_FREE = "4404";
    /**
     * 铃音实际价格高于所购买价格
     */
    public final static String RING_PRICE_HIGHER = "4405";
    /**
     * 提交支付订单失败
     */
    public final static String DRIPPAY_ODER_SUBMIT_FAILED = "4406";
    /**
     * 订单不存在
     */
    public final static String BIZ_ODER_NOT_EXISTS = "4407";
    /**
     * 充值失败
     */
    public final static String RECHARE_FAILED = "4408";
    /**
     * 聚合支付失败
     */
    public final static String DRIPPAY_ODER_FAILED = "4409";
    /**
     * 扣钱失败
     */
    public final static String PAY_FAILED = "4410";
    /**
     * 订单成功生成，铃音无需支付（价格不大于0元）
     */
    public final static String RING_NONEED_PAY = "4411";
    /**
     * 聚合支付仍待确认
     */
    public final static String WATI_PAY_CONFIRM = "4412";
    /**
     * 铃音已购买，存储购买关系失败
     */
    public final static String SAVE_RINGORDER_FAILED = "4413";
    /**
     * 订单处理失败
     */
    public final static String ORDER_PROCESS_FAILED = "4414";
    /**
     * 订单购买失败，需要退款
     */
    public final static String ORDER_BUY_FAILED = "4415";
    /**
     * 充值成功，更新订单失败
     */
    public final static String CHARGE_SUCC_UPDATE_FAILED = "4416";

    /**
     * 账号已被绑定
     */
    public final static String ACCOUNT_ALREADY_BIND = "4600";


    /**
     * 来电mv - 正在签约,无法解约
     */
    public final static String MVVIP_SIGNING = "4061";

    /**
     * 内部错误
     */
    public final static String INNER_ERROR = "9999";

    /**
     * 版权不存在
     */
    public static final String COPYRINGSONG_NOTEXIST = "2700";

    /**
     * 铃声已有版权信息
     */
    public static final String RING_HASCOPYRIGHT = "4700";

    /**
     * 用户未开通彩铃业务
     */
    public final static String NOT_RINGUSER = "5002";
    /**
     * 5003	用户已经是彩铃用户
     */
    public static final String ALREADY_RINGUSER = "5003";
    /**
     * 5004	用户已经是个彩用户
     */
    public static final String ALREADY_DIY = "5004";

    /**
     * 5006	成功受理
     */
    public static final String SUCCESS_SUBMIT = "5006";
    /**
     * 5013	支付账号已被绑定（通常是他人，非本用户；用户在支付时，号码等用户标识会和一个第三方支付账户绑定，可能影响该第三方账户无法给另一个号码支付时，返回该错误。比如苹果订阅）
     */
    public final static String PAYACCOUNT_ALREADY_BIND = "5013";
    /**
     * 5014	支付账号错误（通常是iOS，在AppleStore购买应用时使用appleid1，然后在该应用中购买应用内商品使用appleid2，在校验支付结果时会提示该错误。）
     */
    public final static String PAYACCOUNT_NOTMATCH = "5014";
    /**
     * 铃音已存在于个人铃音库中或重复订购
     */
    public final static String ALREADY_RINGINLIB = "5016";
    /**
     * 5101	用户重复领取振铃会员
     */
    public static final String ALREADY_EXCHANGEPAYRINGVIP = "5101";
    /**
     * 5102	用户已经是振铃会员
     */
    public static final String ALREADY_PAYRINGVIP = "5102";

    /**
     * 个人铃音库满（11001602	用户个人铃音库已满）
     */
    public final static String RINGLIB_FULL = "6001";

    /**
     * 8001	渠道添加失败
     */
    public static final String CHANNEL_ADDFAIL = "8001";

    /**
     * 8002	重复添加渠道
     */
    public static final String CHANNEL_ALREADYEXISTS = "8002";

}
