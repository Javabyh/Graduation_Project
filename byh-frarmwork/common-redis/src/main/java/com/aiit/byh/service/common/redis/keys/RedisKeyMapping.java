package com.aiit.byh.service.common.redis.keys;

import com.aiit.byh.service.common.entity.constants.CommonConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * redis key定义
 *
 * @author dsqin
 */
public class RedisKeyMapping {

    /**
     * redis type与key映射表
     */
    private final static HashMap<RedisType, String> REDIS_KEY_MAPPING = new HashMap<RedisType, String>() {

        private static final long serialVersionUID = -8367347356194398337L;

        {
            put(RedisType.COLUMN_INFO, "res:col:base:%s");
            put(RedisType.COLUMN_REFER, "rel:res:depend:%s:%s");
            put(RedisType.COLUMN_RELATION_INFO, "rel:col:col:%s");
            put(RedisType.COLUMN_SUB_COLUMNS, "rel:col:sub:cols:%s:%s:%s:%s:%s");
            put(RedisType.COLUMN_SUB_RINGS, "rel:col:subring:%s:%s");
            put(RedisType.COLUMN_SUB_RINGS_CT, "rel:col:subring:%s:%s:%s");
            put(RedisType.REL_COLUMN_RING, "res:col:sub:rings:%s");
            put(RedisType.REL_COLUMN_RING_CT, "res:col:sub:rings:%s:%s");
            put(RedisType.RINGLIST_RISK_VIP, "res:rings:vip:cr:list");
            put(RedisType.COPYRIGHT_TO_VIP_RULE, "res:rule:vip:cr:%s:list");
            put(RedisType.RING_INFO, "UserRingWork_%s");
            put(RedisType.USER_ENJOY_WORK_LIST, "AuthorUserAndEnjoyWork_%s");
            put(RedisType.RECOMMEND_RINGS, "res:recom:fancy:%s");
            put(RedisType.RANKING_RING_UPFAST, "recom:ranking:ring:upfast");
            put(RedisType.RECOMMEND_WORD_HUMANLY,
                    "recom:user:complitionword:humanly");
            put(RedisType.RECOMMEND_WORD, "recom:user:complitionword:%s");
            put(RedisType.RECOMMEND_SEARCHWORD, "recom:user:hotword");
            put(RedisType.RECOMMEND_USER_HUMANLY,
                    "recom:user:maybelike:humanly");
            put(RedisType.RECOMMEND_USER, "recom:user:maybelike:%s");
            put(RedisType.RANKING_TALENT, "res:list:ringshow:talent");
            put(RedisType.RECOMMEND_CATEGORY, "recom:ring:column:%s");
            put(RedisType.USER_INFO, "AuthorUser_%s");
            put(RedisType.USER_ENJOYEDUSER_LIST, "user:enjoyeduser:list:%s");
            put(RedisType.USER_FANS_LIST, "user:fans:list:%s");
            put(RedisType.SET_CALL_HISTORY, "user:set:call:his:%s");
            put(RedisType.SET_SMS_HISTORY, "user:set:sms:his:%s");
            put(RedisType.SET_ALARM_HISTORY, "user:set:alarm:his:%s");
            put(RedisType.SET_NOTICE_HISTORY, "user:set:notice:his:%s");
            put(RedisType.MV_INFO, "mv:ring:%s");
            put(RedisType.RECOMMED_MV, "mv:col:recom:res:%s");
            put(RedisType.RECOMMEND_MV_COVER, "mv:col:cover:res");
            put(RedisType.MV_COLUMN_INFO, "mv:col:%s");
            put(RedisType.MV_COLUMN_REFER, "mv:rel:res:depend:%s");
            put(RedisType.MV_COLUMN_SUBCOLUMN_DETAIL,
                    "mv:col:subcol:detail:%s:%s:%s");
            put(RedisType.MV_COLUMN_SUBCOLUMN, "mv:col:subcols:%s:%s");
            put(RedisType.TOKEN_ACCESS_INFO, "actk:%s");
            put(RedisType.TOKEN_REFRESH_INFO, "rftk:%s");
            put(RedisType.COLUMN_SUB_MV, "mv:col:res:%s");
            put(RedisType.MVRING_COL_USERRECM_HUMANLY,
                    "mv:col:recom:user:humanly:%s");
            put(RedisType.MVRING_COL_USERRECM_COVER, "mv:col:cover:user");
            put(RedisType.MVRING_COL_USERRECM, "mv:col:recom:user:%s");
            put(RedisType.MVRING_COL_WORDRECM, "mv:col:recom:word:%s");
            put(RedisType.MVRING_COL_WORDMRECM_COVER, "mv:col:cover:word");
            put(RedisType.MVRING_COL_WORDRECM_HUMANLY,
                    "mv:col:recom:word:humanly:%s");
            put(RedisType.MVRING_SAVETOKEN, "mv:ring:savetoken:%s");
            put(RedisType.MV_STORE_LIST, "res:list:ringshow:stored:%s");
            put(RedisType.MVRING_COL_WORDRECM, "mv:col:recom:word:%s");
            put(RedisType.MVRING_COL_WORDMRECM_COVER, "mv:col:cover:word");
            put(RedisType.MVRING_COL_WORDRECM_HUMANLY,
                    "mv:col:recom:word:humanly:%s");
            put(RedisType.RING_SAVE_JOB, "job:ring:save:%s:%s");
            put(RedisType.USER_DIY_RING_LIST, "AuthorUserAndRingWork_%s");
            put(RedisType.USER_DIY_RING_PUB_LIST,
                    "res:ring:author:visible:all:%s");
            put(RedisType.MV_BIZ_DATA, "res:smartcall:list:%s");
            put(RedisType.MV_COMMENT_LIST, "res:ringshow:comment:list:%s");
            put(RedisType.SET_RING_HISTORY, "user:set:ring:his:%s");

            put(RedisType.TALENT_LIST, "res:list:ringshow:talent");
            put(RedisType.RING_MV_LIST, "mv:ring:associated:%s");
            put(RedisType.MVRING_SAVETOKEN, "mv:ring:savetoken:%s");
            put(RedisType.RING_ASSOCIATION_MVRING, "mv:ring:associated:%s");
            put(RedisType.MVRING_SAVECONTACT_TOKEN,
                    "mv:ring:savecontacts:token:%s:%s");
            put(RedisType.RING_SHOW_COMMENT, "res:ringshow:comment:%s");
            put(RedisType.RING_SHOW_REPLY_COMMENT_LIST,
                    "res:ringshow:comment:reply:list:%s");
            put(RedisType.RING_COMMENT, "res:ring:comment:list:%s");
            put(RedisType.RING_REPLY_COMMENT_LIST,
                    "res:ring:comment:reply:list:%s");
            put(RedisType.RING_COMMENT_REPLY, "res:ring:comment:reply:%s");
            put(RedisType.SUIT_COMMENT, "Interface:SuitComment:%s");
            put(RedisType.SUIT_COMMENT_REPLY, "Interface:SuitReplyComment:%s");
            put(RedisType.ALBUM_COMMENT, "Interface:AlbumComment:%s");
            put(RedisType.ALBUM_COMMENT_REPLY, "Interface:AlbumReplyComment:%s");
            put(RedisType.SOUND_SKIN, "Interface:SoundSkin:%s");
            put(RedisType.ALBUM_INFO, "Interface:AlbumInfo:%s");
            put(RedisType.REDIS_MVVIP_UNIQUE_CHARGE,
                    "mv:vip:unique:chargetask:%s:%s");
            put(RedisType.ACCOUNT_BIZID, "accountid");
            put(RedisType.RING_ASSOCIATION_MVRING_NEW, "mv:ring:associated:new:%s");
            put(RedisType.MV_AUDIT_PASSED_UNIQUE, "mv:audit:passed:unique:%s");
            put(RedisType.COLUMN_RECOMMED_MV, "mv:col:recom:res:%s:%s");
            put(RedisType.USER_SIGNIN, "user:signin:1:%s");
            put(RedisType.CFG_SYS, "cfg:sys:%s:%s:%s");
            put(RedisType.AUTH_LINK, "auth:link:%s");
            put(RedisType.MV_RANK_NEW, "mv:rank:new");
            put(RedisType.MV_IDOL_DIY, "mv:idol:diy:%s");
            put(RedisType.MV_IDOL_DIY_COMMON, "mv:idol:diy:com.iflytek.kuyin.service.common.redis.common");
            put(RedisType.PHONENO_LOCATION, "CitySubTelNoDetail");
            put(RedisType.USER_COLORRINGSTATUS, "user:cringstatus:%s");
            put(RedisType.VIDEORING, "videoring:%s");
        }
    };

    /**
     * 生成redis key
     *
     * @param type   redis key类型
     * @param params 参数
     * @return redis key
     */
    public static String genRedisKey(RedisType type, Object... params) {
        String keyFormat = REDIS_KEY_MAPPING.get(type);
        if (StringUtils.isBlank(keyFormat)) {
            return StringUtils.EMPTY;
        }

        // 某些redis key的定义不包含可变部分(如res:rings:vip:cr:list),直接返回
        if (ArrayUtils.isEmpty(params)) {
            return keyFormat;
        }

        try {
            return String.format(keyFormat, params);
        } catch (Exception ex) {
            // nothing to do
            return StringUtils.EMPTY;
        }
    }
    /**
     * 获取栏目下铃音的redis的key
     *
     * @param columnId 栏目编号
     * @param ct       运营商类型
     * @return
     */
    public static String genRedisKeyOfColRing(String productNo, String columnId, String ct) {
        return genRedisKeyOfColRing(productNo, columnId, ct, true);
    }

    public static String genRedisKeyOfColRing(String productNo, String columnId, String ct, boolean isSeven) {
        boolean haveCt = StringUtils.isNotBlank(ct)
                && !CommonConstants.ZERO_STRING.equals(ct);
        return genRedisKeyOfColRing(productNo, columnId, haveCt, ct, isSeven);
    }

    /**
     * 获取栏目下铃音的redis的key
     *
     * @param columnId 栏目编号
     * @param ct       运营商类型
     * @return
     */
    public static String genRedisKeyOfColRing(String productNo, String columnId, boolean haveCt,
                                              String ct) {
        return genRedisKeyOfColRing(productNo, columnId, haveCt, ct, true);
    }

    /**
     * 获取栏目下铃音的redis的key
     *
     * @param columnId 栏目编号
     * @param haveCt   是否有运营商
     * @param ct       运营商类型
     * @param isSeven  是否为7.0栏目
     * @return
     */
    public static String genRedisKeyOfColRing(String productNo, String columnId, boolean haveCt,
                                              String ct, boolean isSeven) {
        String redisKey = null;
        if (haveCt) {
            redisKey = RedisKeyMapping.genRedisKey(
                    isSeven ? RedisType.COLUMN_SUB_RINGS_CT : RedisType.REL_COLUMN_RING_CT
                    , productNo, columnId, ct);
        } else {
            redisKey = RedisKeyMapping.genRedisKey(
                    isSeven ? RedisType.COLUMN_SUB_RINGS : RedisType.REL_COLUMN_RING
                    , productNo, columnId);
        }
        return redisKey;
    }


    public static void main(String[] args) {
        System.out.println(Double.MAX_VALUE - Long.MAX_VALUE);
    }
}
