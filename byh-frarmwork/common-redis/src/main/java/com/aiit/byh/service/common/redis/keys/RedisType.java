package com.aiit.byh.service.common.redis.keys;

/**
 * redis key类型
 *
 * @author dsqin
 */
public enum RedisType {

    /**
     * 栏目与栏目的关系属性，需要把父栏目子栏目的编号使用冒号拼接好
     */
    COLUMN_RELATION_INFO,

    /**
     * 栏目下的子栏目列表
     */
    COLUMN_SUB_COLUMNS,

    /**
     * 栏目下的铃音列表
     */
    COLUMN_SUB_RINGS,

    /**
     * 栏目下的铃音列表，带运营商类型，1-移动，2-联通，3-电信
     */
    COLUMN_SUB_RINGS_CT,

    /**
     * 6.0栏目包含的铃音列表
     */
    REL_COLUMN_RING,

    /**
     * 6.0栏目包含的铃音列表，带运营商类型，1-移动，2-联通，3-电信
     */
    REL_COLUMN_RING_CT,

    /**
     * 栏目资源参照关系
     */
    COLUMN_REFER,

    /**
     * 栏目信息
     */
    COLUMN_INFO,

    /**
     * 铃音版权转VIP规则列表
     */
    COPYRIGHT_TO_VIP_RULE,

    /**
     * 有风险的VIP铃音列表
     */
    RINGLIST_RISK_VIP,
    /**
     * 用户铃音作品
     */
    RING_INFO,

    /**
     * 用户铃声保存
     */
    RING_SAVE_JOB,

    /**
     * 用户喜欢的铃音作品列表
     */
    USER_ENJOY_WORK_LIST
    /**
     * 推荐铃声
     */
    , RECOMMEND_RINGS

    /**
     * 铃声榜单--飙升榜
     */
    , RANKING_RING_UPFAST

    /**
     * 精选词推荐
     */
    , RECOMMEND_WORD

    /**
     * 人工运营精选词推荐
     */
    , RECOMMEND_WORD_HUMANLY

    /**
     * 搜索热词推荐
     */
    , RECOMMEND_SEARCHWORD

    /**
     * 人工运营用户推荐
     */
    , RECOMMEND_USER_HUMANLY

    /**
     * 用户推荐
     */
    , RECOMMEND_USER

    /**
     * 榜单 -- 达人榜
     */
    , RANKING_TALENT
    /**
     * 分类推荐池
     */
    , RECOMMEND_CATEGORY

    /**
     * 用户信息
     */
    , USER_INFO
    /**
     * 用户关注列表
     */
    , USER_ENJOYEDUSER_LIST

    /**
     * 用户粉丝列表
     */
    , USER_FANS_LIST

    /**
     * 设置来电历史
     */
    , SET_CALL_HISTORY
    /**
     * 设置短信历史
     */
    , SET_SMS_HISTORY
    /**
     * 设置闹铃历史
     */
    , SET_ALARM_HISTORY
    /**
     * 设置通知历史
     */
    , SET_NOTICE_HISTORY
    /**
     * 视频铃声信息
     */
    , MV_INFO

    /**
     * 视频铃声推荐
     */
    , RECOMMED_MV

    /**
     * 视频铃声推荐兜底
     */
    , RECOMMEND_MV_COVER

    /** 视频铃声栏目信息
     */
    , MV_COLUMN_INFO

    /**
     * 栏目参照关系
     */
    , MV_COLUMN_REFER

    /**
     * 栏目与子栏目关联关系
     */
    , MV_COLUMN_SUBCOLUMN

    /**
     * 栏目与子栏目关联信息，详细信息
     */
    , MV_COLUMN_SUBCOLUMN_DETAIL

    /**
     * 栏目下的视频铃声列表
     */
    , COLUMN_SUB_MV

    /**
     * 栏目个推资源列表
     */
    , COLUMN_RECOMMED_MV

    /**
     * 访问token
     */
    , TOKEN_ACCESS_INFO
    /**
     * 刷新token
     */
    , TOKEN_REFRESH_INFO

    /**
     * 栏目下运营配置资源列表（包含普通分类中的兜底数据）
     */
    , MVRING_COL_USERRECM_HUMANLY

    /**
     * 推荐栏目下用户推荐的兜底用户
     */
    , MVRING_COL_USERRECM_COVER

    /**
     * 视频铃声 -- 个性化用户推荐
     */
    , MVRING_COL_USERRECM

    /**
     * 视频铃声 -- 个性化精选词推荐
     */
    , MVRING_COL_WORDRECM

    /**
     * 推荐栏目下精选词推荐的兜底词
     */
    , MVRING_COL_WORDMRECM_COVER

    /**
     * 精选词推荐：固定位置精选词
     */
    , MVRING_COL_WORDRECM_HUMANLY
    /**
     * 用户自制铃音列表，公开+私密
     */
    , USER_DIY_RING_LIST
    /**
     * 用户自制铃音列表，仅公开
     */
    , USER_DIY_RING_PUB_LIST
    /**
     * 用户MV业务数据，主要是制作个数、购买个数等
     */
    , MV_BIZ_DATA
    /**
     * 用户设置彩铃历史
     */
    , SET_RING_HISTORY

    /**
     * 达人榜单
     */
    , TALENT_LIST
    /**
     * 视频铃声 保存token
     */
    , MVRING_SAVETOKEN

    /**
     * 铃声MV--收藏列表
     */
    , MV_STORE_LIST

    /**
     * 铃声MV--评论列表
     */
    , MV_COMMENT_LIST
    /**
     * 铃声关联的视频铃声列表
     */
    , RING_MV_LIST
    /**
     * 铃声关联的视频铃声(最热榜)
     */
    , RING_ASSOCIATION_MVRING

    /**
     * 视频铃声同步联系人的标识
     */
    , MVRING_SAVECONTACT_TOKEN

    /**
     * 视频铃声评论
     */
    , RING_SHOW_COMMENT
    /**
     * 视频铃声跟评列表
     */
    , RING_SHOW_REPLY_COMMENT_LIST
    /**
     * 铃声评论
     */
    , RING_COMMENT
    /**
     * 铃声跟评列表
     */
    , RING_REPLY_COMMENT_LIST
    /**
     * 铃声跟评
     */
    , RING_COMMENT_REPLY
    /**
     * 套装评论
     */
    , SUIT_COMMENT
    /**
     * 套装跟评
     */
    , SUIT_COMMENT_REPLY
    /**
     * 合辑评论
     */
    , ALBUM_COMMENT
    /**
     * 合辑跟评
     */
    , ALBUM_COMMENT_REPLY
    /**
     * SoundSkin
     */
    , SOUND_SKIN
    /**
     * 合辑信息
     */
    , ALBUM_INFO
    /**
     * 包月扣费工具同一任务只扣费一次
     */
    , REDIS_MVVIP_UNIQUE_CHARGE
    /**
     * 生成Account的bizid的key，每次自增1
     */
    , ACCOUNT_BIZID
    /**
     * 铃声关联的视频铃声(最新榜)
     */
    , RING_ASSOCIATION_MVRING_NEW
    /**
     * 视频铃声审核通过消息处理唯一标示
     */
    , MV_AUDIT_PASSED_UNIQUE
    /**
     * 兑吧签到
     */
    , USER_SIGNIN

    /**
     * 系统配置
     */
    , CFG_SYS

    /**
     * 鉴权防盗链
     */
    , AUTH_LINK
    /**
     * 视频铃声最新作品
     key
     rank:new

     type
     Zset

     member
     视频铃声编号

     score
     Unix时间戳（毫秒），高并发存在重复，翻页会跳过一些作品，暂作为已知问题

     ttl
     不过期
     */
    , MV_RANK_NEW
    /**
     *  我关注的人的视频铃声作品列表
     *  key
     *  mv:idol:我的用户编号
     *
     *  type
     *  Zset
     *
     *  member
     *  视频铃声编号
     *
     *  score
     *  Unix时间戳（毫秒），高并发存在重复，翻页会跳过一些作品，暂作为已知问题
     *
     *  ttl
     *  不过期
     */
    , MV_IDOL_DIY
    /**
     *
     *  默认关注的人的视频铃声作品列表
     *  key
     *  mv:idol:com.iflytek.kuyin.service.common.redis.common
     *
     *  type
     *  Zset
     *
     *  member
     *  视频铃声编号
     *
     *  score
     *  Unix时间戳（毫秒），高并发存在重复，翻页会跳过一些作品，暂作为已知问题
     *
     *  ttl
     *  不过期
     */
    , MV_IDOL_DIY_COMMON
    /**
     * 号码归属地
     */
    , PHONENO_LOCATION

    /**
     *
     *  用户彩铃状态
     *  key
     *  user:cringstatus:用户号码
     *
     *  type
     *  String
     *
     *  value
     *  用户彩铃状态，如：1
     *
     *  ttl
     *  5min
     */
    , USER_COLORRINGSTATUS
    /**
     * 视频彩铃
     * key  videoring:视频彩铃编号
     * type  Hash
     */
    ,VIDEORING
}
