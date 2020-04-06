package com.aiit.byh.service.common.redis;

import com.google.common.collect.Maps;
import com.aiit.byh.service.common.redis.factory.RedisServiceFactory;
import com.aiit.byh.service.common.redis.service.RedisService;
import com.aiit.byh.service.common.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis工具类
 *
 * @author dsqin
 */
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 写入redis
     */
    static RedisService redisService = RedisServiceFactory.getRedisService();

    /**
     * 读取flag
     */
    public static final String readFlag = "Read";

    /**
     * 写入flag
     */
    public static final String writeFlag = "Write";

    /**
     * 推荐flag
     */
    public static final String recmFlag = "Recm";

    /**
     * 读取flag
     */
    public static final String readFlag2 = "Read2";

    /**
     * 写flag
     */
    public static final String writeFlag2 = "Write2";

    /**
     * 哨兵读取flag
     */
    public static final String readFlagSentinel = "ReadS";

    /**
     * 哨兵写flag
     */
    public static final String writeFlagSentinel = "WriteS";

    /**
     * 曲库铃音读取flag
     */
    public static final String readRingFlag = "ReadRing";

    /**
     * 曲库铃音写入flag
     */
    public static final String writeRingFlag = "WriteRing";

    /**
     * min和max可以是-inf和+inf
     */
    public static final String min = "-inf";

    /**
     * min和max可以是-inf和+inf
     */
    public static final String max = "+inf";

    /**
     * 判断redis key是否存在
     *
     * @param type    key类型
     * @param keySuff key后缀
     * @return
     */
    public static boolean isKeyExists(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            return isKeyExists(key, getReadRedisFlag());
        } catch (Exception e) {
            logger.error("****redis判断key是否存在失败,key:{}****", key, e);
            return false;
        }
    }

    /**
     * 判断redis key是否存在
     *
     * @param key
     * @param flag
     * @return
     */
    public static boolean isKeyExists(String key, String flag) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            return redisService.exists(flag, key);
        } catch (Exception e) {
            logger.error("****redis判断key是否存在失败,key:{}****", key, e);
            return false;
        }
    }

    /**
     * redis取值
     *
     * @param key
     * @return
     */
    public static Map<String, String> getMapValue(String key) {
        return getMapValue(key, getReadRedisFlag());
    }

    /**
     * redis取值
     *
     * @param key
     * @param redisFlag
     * @return
     */
    public static Map<String, String> getMapValue(String key, String redisFlag) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 获取值
        try {
            return redisService.hgetAll(redisFlag, key);
        } catch (Exception e) {
            logger.error("****redis取值失败,key:{}****", key, e);
            return null;
        }
    }

    /**
     * 获取hash中field值
     *
     * @param type
     * @param keySuff
     * @param field
     */
    public static String getHashFieldValue(String key, String field) {
        return getHashFieldValue(key, field, getReadRedisFlag());
    }

    public static String getHashFieldValue(String key, String field, String redisFlag) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return null;
        }
        // 获取值
        try {
            return redisService.hget(redisFlag, key, field);
        } catch (Exception e) {
            logger.error("****redis取值失败,key:{},field:{}****", key, field, e);
            return null;
        }
    }

    /**
     * 获取hash中field值
     *
     * @param key
     * @param fields
     * @return
     */
    public static Map<String, String> getHashFieldsValue(String key, String... fields) {
        if (StringUtils.isBlank(key) || CommonUtils.isEmpty(fields)) {
            return null;
        }
        // 获取值
        try {
            List<String> values = redisService.hmget(getReadRedisFlag(), key, fields);
            if (CommonUtils.isEmpty(values)
                    || fields.length != values.size()) {
                return null;
            }
            Map<String, String> map = Maps.newHashMap();
            for (int i = 0, count = values.size(); i < count; i++) {
                if (!map.containsKey(fields[i])) {
                    map.put(fields[i], values.get(i));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error("****redis取值失败,key:{},fields:{}****", key, fields, e);
            return null;
        }
    }

    public static boolean setHashFieldValue(String key, String field,
                                            String value) {
        return setHashFieldValue(key, field, value, getWriteRedisFlag());
    }

    public static boolean setHashFieldValue(String key, String field, String value, String redisFlag) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return true;
        }
        try {
            redisService.hset(redisFlag, key, field, StringUtils.trimToEmpty(value));
            return true;
        } catch (Exception e) {
            logger.error("***** setHashFieldValue:redis更新hash field失败："
                    + "key={},field={},value={} *****", key, field, value, e);
        }
        return false;
    }

    /**
     * redis保存值
     *
     * @param type
     * @param map
     */
    public static boolean saveMapValue(String key, Map<String, String> map) {
        if (map != null && !map.isEmpty() && StringUtils.isNotBlank(key)) {
            try {
                saveMapValue(getWriteRedisFlag(), key, map);
            } catch (Exception e) {
                logger.error("****redis保存异常,参数:{}****", map.toString(), e);
                return false;
            }
        }
        return true;
    }

    /**
     * redis保存值
     *
     * @param flag
     * @param key
     * @param map
     * @return
     */
    public static boolean saveMapValue(String flag, String key, Map<String, String> map) {
        if (map != null && !map.isEmpty() && StringUtils.isNotBlank(key)) {
            for (String mapKey : map.keySet()) {
                try {
                    redisService.hset(flag, key, mapKey,
                            StringUtils.trimToEmpty(map.get(mapKey)));
                } catch (Exception e) {
                    logger.error("****redis保存异常,参数:{}****", map.toString(), e);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean saveMapValueCheck(String key, Map<String, String> map) {
        if (map != null && !map.isEmpty() && StringUtils.isNotBlank(key)) {
            Map<String, String> copy = new HashMap<String, String>();
            Set<String> keySet = map.keySet();
            // 检查参数
            for (String mapKey : keySet) {
                String value = map.get(mapKey);
                if (null != value) {
                    copy.put(mapKey, value);
                }
            }
            try {
                redisService.hmset(getWriteRedisFlag(), key, copy);
            } catch (Exception e) {
                logger.error("****redis保存异常,参数:{}****", map.toString(), e);
                return false;
            }
        }
        return true;
    }

    /**
     * 保存map值(保证map中value无null值)
     *
     * @param type       redis类型
     * @param keySuff    redis key后缀
     * @param map
     * @param expireTime 过期时间
     * @return
     */
    public static boolean saveMapValueNotNull(String key,
                                              Map<String, String> map, int expireTime) {
        if (map != null && !map.isEmpty() && StringUtils.isNotBlank(key)) {
            try {
                if (0 < expireTime) {
                    redisService.hmsetAndExpire(getWriteRedisFlag(), key, map,
                            expireTime);
                } else {
                    redisService.hmset(getWriteRedisFlag(), key, map);
                }
            } catch (Exception e) {
                logger.error("****redis保存异常,参数:{}****", map.toString(), e);
                return false;
            }
        }
        return true;
    }

    public static boolean saveMapValueNotNull(String key,
                                              Map<String, String> map) {
        if (map != null && !map.isEmpty() && StringUtils.isNotBlank(key)) {
            try {
                redisService.hmset(getWriteRedisFlag(), key, map);
            } catch (Exception e) {
                logger.error("****redis保存异常,参数:{}****", map.toString(), e);
                return false;
            }
        }
        return true;
    }

    /**
     * @param flag
     * @param key
     * @param fields
     * @return
     */
    public static boolean delHashFieldsWithFlag(String flag, String key, String... fields) {
        if (fields != null && 0 < fields.length && StringUtils.isNotBlank(key)) {
            try {
                redisService.hdel(flag, key, fields);
            } catch (Exception e) {
                logger.error("****redis删除异常,参数:{}{}****", key,
                        fields.toString(), e);
                return false;
            }
        }
        return true;
    }

    public static boolean delHashFields(String key, String... fields) {
        if (fields != null && 0 < fields.length && StringUtils.isNotBlank(key)) {
            try {
                delHashFieldsWithFlag(getWriteRedisFlag(), key, fields);
            } catch (Exception e) {
                logger.error("****redis删除异常,参数:{}{}****", key,
                        fields.toString(), e);
                return false;
            }
        }
        return true;
    }

    /**
     * redis取值(支持分页)正序排序
     *
     * @param type    数据类型
     * @param keySuff 数据key后缀值
     * @param start
     * @param limit
     * @return
     */
    public static Set<String> getSetValue(String key, long start, long limit) {
        return getSetValue(key, start, limit, false);
    }

    /**
     * 获取set分页数据(返回string数组)
     *
     * @param type
     * @param keySuff
     * @param start
     * @param limit
     * @return
     */
    public static String[] getSetValueArray(String key, long start, long limit) {
        return getSetValueArray(key, start, limit, false);
    }

    /**
     * 获取set分页数据(返回string数组)
     *
     * @param type
     * @param keySuff
     * @param start
     * @param limit
     * @return
     */
    public static String[] getSetValueArray(String key, long start, long limit,
                                            boolean isReverse) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Set<String> sets = getSetValue(key, start, limit, isReverse);
        if (null == sets || sets.isEmpty()) {
            return null;
        }
        String[] ids = CommonUtils.setToArray(sets);
        return ids;
    }

    /**
     * redis取值支持分页
     *
     * @param type
     * @param keySuff
     * @param start
     * @param limit
     * @param isReverse
     * @return
     */
    public static Set<String> getSetValue(String key, long start, long limit,
                                          boolean isReverse) {
        return getSetValue(key, start, limit, isReverse, getReadRedisFlag());
    }

    /**
     * redis取值支持分页，支持不同Flag
     *
     * @param key
     * @param start
     * @param limit
     * @param isReverse
     * @param redisFlag
     * @return
     */
    public static Set<String> getSetValue(String key, long start, long limit,
                                          boolean isReverse, String redisFlag) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 获取值
        try {
            if (isReverse) {
                return redisService.zrevrange(redisFlag, key, start, limit);
            }
            return redisService.zrange(redisFlag, key, start, limit);
        } catch (Exception e) {
            logger.error("****redis取set数据失败,key:{},start:{},limit:{}****", key,
                    start, limit, e);
            return null;
        }
    }

    /**
     * 获取set所有成员
     *
     * @param type
     * @param keySuff
     * @return
     */
    public static Set<String> getSetMembers(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return redisService.smembers(getReadRedisFlag(), key);
        } catch (Exception ex) {
            logger.error("****获取set值失败,key:{}****", key, ex);
            return null;
        }
    }

    /**
     * 保存zset field数据
     *
     * @param type
     * @param keySuff
     * @param field
     * @param value
     */
    public static boolean saveZSetFieldValue(String key, String field,
                                             long score) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return true;
        }
        // 获取值
        try {
            redisService.zadd(getWriteRedisFlag(), key, score, field);
            return true;
        } catch (Exception e) {
            logger.error("****redis存储set数据失败,key:{},field:{},value:{}****",
                    key, field, score, e);
            return false;
        }
    }

    /**
     * 查询zset(返回value和score)
     *
     * @param key
     * @param start
     * @param limit
     * @param isReverse
     * @param redisFlag
     * @return
     */
    public static Set<Tuple> getZsetWithScores(String key, long start,
                                               long end, boolean isReverse, String redisFlag) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 获取值
        try {
            if (isReverse) {
                return redisService.zrevrangeWithScores(redisFlag,
                        key, start, end);
            }
            return redisService.zrangeWithScores(redisFlag, key,
                    start, end);
        } catch (Exception e) {
            logger.error("****redis取set数据失败,key:{},start:{},end:{}****", key,
                    start, end, e);
            return null;
        }
    }

    /**
     * 查询zset(返回value和score)
     *
     * @param type
     * @param keySuff
     * @param start
     * @param limit
     * @return
     */
    public static Set<Tuple> getZsetWithScores(String key, long start,
                                               long end, boolean isReverse) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 获取值
        try {
            if (isReverse) {
                return redisService.zrevrangeWithScores(getReadRedisFlag(),
                        key, start, end);
            }
            return redisService.zrangeWithScores(getReadRedisFlag(), key,
                    start, end);
        } catch (Exception e) {
            logger.error("****redis取set数据失败,key:{},start:{},end:{}****", key,
                    start, end, e);
            return null;
        }
    }

    /**
     * 根据score查zset，并返回score
     * @param key
     * @param startScore
     * @param endScore
     * @param count
     * @param isReverse
     * @param redisFlag
     * @return
     */
    public static Set<Tuple> zrangeByScoreWithScores(String key, String startScore,
                                                     String endScore, int count,
                                                     boolean isReverse, String redisFlag) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 获取值
        try {
            if (isReverse) {
                if (StringUtils.isBlank(startScore)) {
                    startScore = max;
                }
                if (StringUtils.isBlank(endScore)) {
                    endScore = min;
                }

                return redisService.zrevrangeByScoreWithScores(redisFlag,
                        key, startScore, endScore, count);
            }

            if (StringUtils.isBlank(startScore)) {
                startScore = min;
            }
            if (StringUtils.isBlank(endScore)) {
                endScore = max;
            }
            return redisService.zrangeByScoreWithScores(redisFlag, key,
                    startScore, endScore, count);
        } catch (Exception e) {
            logger.error("****redis取set数据失败,key:{},start:{},end:{},count:{}****", key,
                    startScore, endScore, count, e);
            return null;
        }
    }

    /**
     * 获取redis中set长度
     *
     * @param type
     *            类型
     * @param suff
     *            后缀
     * @return
     */
    /**
     * 获取redis中set长度
     *
     * @param key
     * @param redisFlag redis的类型 Read、Write、Recm
     * @return
     */
    public static long getSetLength(String key, String redisFlag) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        // 获取长度
        try {
            return redisService.zcard(redisFlag, key);
        } catch (Exception e) {
            logger.error("****redis获取长度,key:{},****", key, e);
            return 0;
        }
    }

    public static long getSetLength(String key) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        // 获取长度
        try {
            return redisService.zcard(getReadRedisFlag(), key);
        } catch (Exception e) {
            logger.error("****redis获取长度,key:{},****", key, e);
            return 0;
        }
    }

    /**
     * 保存SET数值
     *
     * @param type
     * @param keySuff
     * @param score    分数
     * @param setValue set数值
     */
    public static boolean saveZSetValue(String key, long score, String setValue) {
        return saveZSetValue(getWriteRedisFlag(), key, score, setValue);
    }

    public static boolean saveZSetValue(String redisFlag, String key, long score, String setValue) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.zadd(redisFlag, key, score,
                    StringUtils.trimToEmpty(setValue));
        } catch (Exception e) {
            logger.error("****redis保存set数据异常,data:{}****", setValue.toString(),
                    e);
            return false;
        }
        return true;
    }

    /**
     * 保存set数据
     *
     * @param key
     * @param member
     * @return
     */
    public static boolean sadd(String key, String member) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.sadd(getWriteRedisFlag(), key,
                    StringUtils.trimToEmpty(member));
        } catch (Exception e) {
            logger.error("****redis保存set数据异常,data:{}****", member.toString(),
                    e);
            return false;
        }
        return true;
    }

    /**
     * 保存set数据
     *
     * @param redisFlag
     * @param key
     * @param member
     * @return
     */
    public static boolean sadd(String redisFlag, String key, String member) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.sadd(redisFlag, key,
                    StringUtils.trimToEmpty(member));
        } catch (Exception e) {
            logger.error("****redis保存set数据异常,data:{}****", member.toString(),
                    e);
            return false;
        }
        return true;
    }

    /**
     * 删除set数据
     *
     * @param
     * @param key
     * @param member
     * @return
     */
    public static boolean srem(String key, String member) {
        return srem(getWriteRedisFlag(), key, member);
    }

    /**
     * 删除set数据
     *
     * @param redisFlag
     * @param key
     * @param member
     * @return
     */
    public static boolean srem(String redisFlag, String key, String member) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.srem(redisFlag, key, member);
        } catch (Exception e) {
            logger.error("****redis删除set成员异常,member:{}****", member, e);
            return false;
        }
        return true;
    }

    /**
     * 删除ZSET指定值
     *
     * @param type
     * @param keySuff
     * @param scoreMembers member score
     */
    public static boolean delZsetMember(String key, String member) {
        return delZsetMember(getWriteRedisFlag(), key, member);
    }

    public static boolean delZsetMember(String redisFlag, String key, String member) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.zrem(redisFlag, key, member);
        } catch (Exception e) {
            logger.error("****redis删除zset成员异常,member:{}****", member, e);
            return false;
        }
        return true;
    }

    /**
     * 批量删除zset
     *
     * @param key
     * @param member
     * @return
     */
    public static boolean delZset(String key, String[] member) {
        return delZset(getWriteRedisFlag(), key, member);
    }
    public static boolean delZset(String redisFlag, String key, String[] member) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.zrem(redisFlag, key, member);
        } catch (Exception e) {
            logger.error("****redis删除zset成员异常,member:{}****", member, e);
            return false;
        }
        return true;
    }
    /**
     * 保存ZSET数值
     *
     * @param type
     * @param keySuff
     * @param scoreMembers member score
     */
    public static boolean incrZsetScore(String key, String member, double score) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.zincrby(getWriteRedisFlag(), key, score, member);
        } catch (Exception e) {
            logger.error("****redis增加zset成员得分异常,member:{},score:{}****",
                    member, score, e);
            return false;
        }
        return true;
    }

    public static double zscore(String key, String member) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        try {
            return redisService.zscore(getReadRedisFlag(), key, member);
        } catch (Exception e) {
            logger.error("****zscore失败,key:{},member:{}****", key, member, e);
            return 0;
        }
    }

    /**
     * 保存ZSET数值
     *
     * @param type
     * @param keySuff
     * @param scoreMembers member score
     */
    public static boolean saveZsetMapValue(String key,
                                           Map<String, Double> scoreMembers, int seconds) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            if (seconds <= 0) {
                redisService.zadd(getWriteRedisFlag(), key, scoreMembers);
            } else {
                redisService.zaddAndExpire(getWriteRedisFlag(), key,
                        scoreMembers, seconds);
            }
        } catch (Exception e) {
            logger.error("****redis保存set数据异常,data:{}****",
                    scoreMembers.toString(), e);
            return false;
        }
        return true;
    }

    /**
     * 增加hash的field的数值
     *
     * @param type
     * @param field
     * @param num
     * @return
     */
    public static boolean incrHashValue(String key, String field, long num) {
        return incrHashValue(key, field, num, getWriteRedisFlag());
    }

    public static boolean incrHashValue(String key, String field, long num, String redisFlag) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return true;
        }
        try {
            redisService.hincrBy(redisFlag, key, field, num);
        } catch (Exception e) {
            logger.error(
                    "****redis增加hash的field的数值异常,key:{},field:{},num:{},data:{}****",
                    key, field, num, e);
            return false;
        }
        return true;
    }

    /**
     * String操作:setnx
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setNxString(String key, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return false;
        }
        try {
            return 1 == redisService.setnx(getWriteRedisFlag(), key, value);
        } catch (Exception ex) {
            logger.error("****setnx异常,key:{},value:{}****", key, value, ex);
            return false;
        }
    }

    /**
     * String操作:setnx and expire
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setNxStringAndExpire(String key, String value,
                                               int expireSeconds) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return false;
        }
        try {

            if (redisService == null) {
                logger.error("****setnx失败,redis加载失败 key:{},value:{}****", key, value);
                return false;
            }

            boolean res = 1 == redisService.setnx(getWriteRedisFlag(), key,
                    value);
            if (res && expireSeconds > 0) {
                redisService.expire(getWriteRedisFlag(), key, expireSeconds);
            }
            return res;
        } catch (Exception ex) {
            logger.error("****setnx异常,key:{},value:{}****", key, value, ex);
            return true;
        }
    }

    /**
     * String操作:incr and expire
     *
     * @param key
     * @param expireSeconds <=0则没有有效期
     * @return
     */
    public static long incrStringAndExpire(String key, int expireSeconds) {
        if (StringUtils.isBlank(key)) {
            return -1;
        }
        try {
            long res = redisService.incr(getWriteRedisFlag(), key);
            if (1 == res && expireSeconds > 0) {
                redisService.expire(getWriteRedisFlag(), key, expireSeconds);
            }
            return res;
        } catch (Exception ex) {
            logger.error("****String incr异常,key:{}****", key, ex);
            return -1;
        }
    }

    /**
     * String操作:decr and expire
     *
     * @param key
     * @return
     */
    public static long decrStringAndExpire(String key, int expireSeconds) {
        if (StringUtils.isBlank(key)) {
            return -1;
        }
        try {
            long res = redisService.decr(getWriteRedisFlag(), key);
            if (1 == res && expireSeconds > 0) {
                redisService.expire(getWriteRedisFlag(), key, expireSeconds);
            }
            return res;
        } catch (Exception ex) {
            logger.error("****String incr异常,key:{}****", key, ex);
            return -1;
        }
    }

    /**
     * redis key expire命令
     *
     * @param key     redis key
     * @param seconds 过期时间(秒)
     */
    public static void expireKey(String key, int seconds) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        try {
            redisService.expire(getWriteRedisFlag(), key, seconds);
        } catch (Exception ex) {
            logger.error("****redis设置过期时间失败,key:{},过期时间:{}ms****", key,
                    seconds, ex);
        }
    }

    /**
     * redis key expire命令
     *
     * @param redisFlag
     * @param key
     * @param seconds
     */
    public static void expireKey(String redisFlag, String key, int seconds) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        try {
            redisService.expire(redisFlag, key, seconds);
        } catch (Exception ex) {
            logger.error("****redis设置过期时间失败,key:{},过期时间:{}ms****", key,
                    seconds, ex);
        }
    }

    /**
     * 获取redis read flag
     *
     * @return
     */
    public static String getReadRedisFlag() {
        return readFlag;
    }

    /**
     * 获取redis write flag
     *
     * @return
     */
    public static String getWriteRedisFlag() {
        return writeFlag;
    }

    public static String getReadRedisFlag2() {
        return readFlag2;
    }

    public static String getWriteRedisFlag2() {
        return writeFlag2;
    }

    public static boolean delKey(String key) {
        return delKey(key, getWriteRedisFlag());
    }

    public static boolean delKey(String key, String redisFlag) {
        try {
            redisService.del(redisFlag, key);
        } catch (Exception e) {
            logger.error("****redis删除key数据异常,key:{}****", key, e);
            return false;
        }
        return true;
    }

    public static final String getString(String key) {
        try {
            return redisService.get(getReadRedisFlag(), key);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("***** 在Redis中请求内容失败,key={},异常：{} *****", key, e);
            return null;
        }
    }

    public static boolean setString(String key, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return false;
        }
        try {
            if (StringUtils.isBlank(redisService.set(getWriteRedisFlag(), key,
                    value))) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            logger.error("****setnx异常,key:{},value:{}****", key, value, ex);
            return false;
        }
    }

    /**
     * set string and expire
     *
     * @param key           redis key
     * @param value         redis value
     * @param expireSeconds expire seconds
     * @return
     */
    public static boolean setStringAndExpire(String key, String value, int expireSeconds) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return false;
        }
        try {
            redisService.setAndExpire(getReadRedisFlag(), key, value, expireSeconds);
        } catch (Exception ex) {
            logger.error("****setAndExpire异常,key:{},value:{}****", key, value, ex);
            return false;
        }
        return true;
    }

    public static List<String> getStringList(String key, int start, int end) {
        try {
            return redisService.lrange(getReadRedisFlag(), key, start, end);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是否为zset的member
     *
     * @param key    redis key
     * @param member
     * @return
     */
    public static boolean isZsetMember(String key, String member) {
        return isZsetMember(getReadRedisFlag(), key, member);
    }

    public static boolean isZsetMember(String redisFlag, String key, String member) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(member)) {
            return false;
        }
        try {
            return null != redisService.zrank(redisFlag, key, member);
        } catch (Exception ex) {
            logger.error("****redis zrank失败****", ex);
        }
        return false;
    }

    /**
     * hincrby命令
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static boolean decrHashMember(String key, String field, long value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return false;
        }
        try {
            redisService.hincrBy(getWriteRedisFlag(), key, field, value);
        } catch (Exception ex) {
            logger.error("****hincrby失败****", ex);
            return false;
        }
        return true;
    }

    /**
     * hincrby命令
     * 对哈希表中的字段值加上指定增量值并获取
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static long incrHashMember(String key, String field, long value) {
        return incrHashMember(key, field, value, getWriteRedisFlag());
    }

    /**
     * hincrby命令
     * 对哈希表中的字段值加上指定增量值并获取
     *
     * @param key
     * @param field
     * @param value
     * @param redisFlag
     * @return
     */
    public static long incrHashMember(String key, String field, long value, String redisFlag) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            return -1;
        }
        try {
            long res = redisService.hincrBy(redisFlag, key, field, value);
            return res;
        } catch (Exception ex) {
            logger.error("****对哈希表中的字段值加上指定增量值并获取,失败****", ex);
            return -1;
        }
    }


    /**
     * 保存SET数值
     *
     * @param type
     * @param keySuff
     * @param score    分数
     * @param setValue set数值
     */
    public static boolean saveSetValue(String key, long score, String setValue) {
        return saveSetValue(key, score, setValue, getWriteRedisFlag());
    }

    public static boolean saveSetValue(String key, long score, String setValue, String redisFlag) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.zadd(redisFlag, key, score,
                    StringUtils.trimToEmpty(setValue));
        } catch (Exception e) {
            logger.error("****redis保存set数据异常,data:{}****", setValue.toString(),
                    e);
            return false;
        }
        return true;
    }

    /**
     * set
     *
     * @param key
     * @param expire
     * @param setValue
     * @return
     */
    public static boolean saddAndExpire(String key, int expire, String setValue) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.saddAndExpire(getWriteRedisFlag(), expire, key,
                    StringUtils.trimToEmpty(setValue));
        } catch (Exception e) {
            logger.error("****redis保存set数据异常,data:{}****", setValue.toString(),
                    e);
            return false;
        }
        return true;
    }

    /**
     * zremrangebyscore
     * 删除 scores
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static boolean zremrangebyscore(String key, long min, long max) {
        return zremrangebyscore(getWriteRedisFlag(), key, min, max);
    }

    public static boolean zremrangebyscore(String redisFlag, String key, long min, long max) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            redisService.deleteByScore(redisFlag, key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * zremrangebyscore
     *    删除 scores double类型
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static boolean zremrangebyscore(String key, long min, double max) {
        return zremrangebyscore(getWriteRedisFlag(), key, min, max);
    }

    public static boolean zremrangebyscore(String redisFlag, String key, long min, double max) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            redisService.deleteByScore(redisFlag, key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    /**
     * list lpop
     *
     * @param key
     * @return
     */
    public static final String lpop(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return redisService.lpop(getReadRedisFlag(), key);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("***** 在Redis中请求内容失败,key={},异常：{} *****", key, e);
            return null;
        }
    }

    /**
     * list rpush
     *
     * @param key
     * @param seconds
     * @param strings
     * @return
     */
    public static final boolean rpushAndExpire(String key, int seconds, String... strings) {
        if (StringUtils.isBlank(key)) {
            return true;
        }
        try {
            redisService.rpushAndExpire(getReadRedisFlag(), seconds, key, strings);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("***** 在Redis中请求内容失败,key={},异常：{} *****", key, e);
            return false;
        }
        return true;
    }

    /**
     * 拷贝SET数值，destKey存在会被覆盖
     *
     * @param type
     * @param keySuff
     * @param score    分数
     * @param setValue set数值
     */
    public static boolean zunionstore(String destKey, String sourceKey) {
        if (StringUtils.isBlank(sourceKey) || StringUtils.isBlank(destKey)) {
            return true;
        }
        try {
            redisService.zunionstore(getWriteRedisFlag(), destKey, sourceKey);
        } catch (Exception e) {
            logger.error("****redis保存set数据异常,destKey:{},sourceKey:{}****",
                    destKey, sourceKey, e);
            return false;
        }
        return true;
    }
}
