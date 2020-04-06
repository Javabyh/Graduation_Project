package com.aiit.byh.service.common.redis.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
    //JedisPool getJedisPoolByKey(String flag) throws Exception;

    Jedis getJedisByKey(String flag) throws Exception;

    void returnJedisByKey(String flag, Jedis jedis) throws Exception;

    ShardedJedis getShardedJedisByKey(String flag) throws Exception;

    void returnShardedJedisByKey(String flag, ShardedJedis jedis) throws Exception;

    Long append(String flag, String key, String value) throws Exception;

    List<String> blpop(String flag, int timeout, String... keys) throws Exception;

    List<String> brpop(String flag, int timeout, String... keys) throws Exception;

    List<String> configGet(String flag, String pattern) throws Exception;

    String configSet(String flag, String parameter, String value) throws Exception;

    Long decr(String flag, String key) throws Exception;

    Long decrBy(String flag, String key, long integer) throws Exception;

    Long del(String flag, String... keys) throws Exception;

    Object eval(String flag, String script) throws Exception;

    Boolean exists(String flag, String key) throws Exception;

    Long expire(String flag, String key, int seconds) throws Exception;

    String flushAll(String flag) throws Exception;

    String flushDB(String flag) throws Exception;

    String get(String flag, String key) throws Exception;

    Boolean getbit(String flag, String key, long offset) throws Exception;

    String getrange(String flag, String key, long startOffset, long endOffset) throws Exception;

    String getSet(String flag, String key, String value) throws Exception;

    Long hdel(String flag, String key, String... fields) throws Exception;

    Boolean hexists(String flag, String key, String field) throws Exception;

    String hget(String flag, String key, String field) throws Exception;

    Map<String, String> hgetAll(String flag, String key) throws Exception;

    Long hincrBy(String flag, String key, String field, long value) throws Exception;

    Set<String> hkeys(String flag, String key) throws Exception;

    Long hlen(String flag, String key) throws Exception;

    List<String> hmget(String flag, String key, String... fields) throws Exception;

    String hmset(String flag, String key, Map<String, String> hash) throws Exception;

    Long hset(String flag, String key, String field, String value) throws Exception;

    Long hsetnx(String flag, String key, String field, String value) throws Exception;

    List<String> hvals(String flag, String key) throws Exception;

    Long incr(String flag, String key) throws Exception;

    Long incrBy(String flag, String key, long integer) throws Exception;

    Set<String> keys(String flag, String pattern) throws Exception;

    String lindex(String flag, String key, long index) throws Exception;

    Long llen(String flag, String key) throws Exception;

    String lpop(String flag, String key) throws Exception;

    Long lpush(String flag, String key, String... strings) throws Exception;

    Long lpushx(String flag, String key, String string) throws Exception;

    List<String> lrange(String flag, String key, long start, long end) throws Exception;

    Long lrem(String flag, String key, long count, String value) throws Exception;

    String lset(String flag, String key, long index, String value) throws Exception;

    String ltrim(String flag, String key, long start, long end) throws Exception;

    List<String> mget(String flag, String... keys) throws Exception;

    Long move(String flag, String key, int dbIndex) throws Exception;

    String mset(String flag, String... keysvalues) throws Exception;

    Long msetnx(String flag, String... keysvalues) throws Exception;

    Long publish(String flag, String channel, String message) throws Exception;

    String randomKey(String flag) throws Exception;

    String rename(String flag, String oldkey, String newkey) throws Exception;

    Long renamenx(String flag, String oldkey, String newkey) throws Exception;

    String rpop(String flag, String key) throws Exception;

    String rpoplpush(String flag, String srckey, String dstkey) throws Exception;

    Long rpush(String flag, String key, String... strings) throws Exception;

    Long rpushx(String flag, String key, String string) throws Exception;

    Long sadd(String flag, String key, String... members) throws Exception;

    Long scard(String flag, String key) throws Exception;

    Set<String> sdiff(String flag, String... keys) throws Exception;

    Long sdiffstore(String flag, String dstkey, String... keys) throws Exception;

    String select(String flag, int index) throws Exception;

    String set(String flag, String key, String value) throws Exception;

    Boolean setbit(String flag, String key, long offset, boolean value) throws Exception;

    String setex(String flag, String key, int seconds, String value) throws Exception;

    Long setnx(String flag, String key, String value) throws Exception;

    Long setrange(String flag, String key, long offset, String value) throws Exception;

    Set<String> sinter(String flag, String... keys) throws Exception;

    Set<String> smembers(String flag, String key) throws Exception;

    Long smove(String flag, String srckey, String dstkey, String member) throws Exception;

    List<String> sort(String flag, String key) throws Exception;

    List<String> sort(String flag, String key, SortingParams sortingParameters) throws Exception;

    Long sort(String flag, String key, SortingParams sortingParameters, String dstkey) throws Exception;

    Long sort(String flag, String key, String dstkey) throws Exception;

    String spop(String flag, String key) throws Exception;

    String srandmember(String flag, String key) throws Exception;

    List<String> srandmember(String flag, String key, int count) throws Exception;

    Long strlen(String flag, String key) throws Exception;

    String substr(String flag, String key, int start, int end) throws Exception;

    Long zadd(String flag, String key, double score, String member) throws Exception;

    Long zadd(String flag, String key, Map<String, Double> scoreMembers) throws Exception;

    Long zcount(String flag, final String key, double min, double max) throws Exception;

    Long zcard(String flag, String key) throws Exception;

    Set<String> zrange(String flag, String key, long start, long end) throws Exception;

    Long zrank(String flag, String key, String member) throws Exception;

    Double zscore(String flag, String key, String member) throws Exception;

    Set<String> zrangeByScore(String flag, String key, double min, double max) throws Exception;

    Set<String> zrangeByScore(String flag, String key, double min, double max, int offset, int count) throws Exception;

    Set<String> zrangeByScore(String flag, String key, String min, String max) throws Exception;

    Set<String> zrangeByScore(String flag, String key, String min, String max, int offset, int count) throws Exception;

    Set<Tuple> zrangeByScoreWithScores(String flag, String key, String min, String max, int count) throws Exception;

    Set<Tuple> zrevrangeByScoreWithScores(String flag, String key, String max, String min, int count) throws Exception;

    Set<Tuple> zrevrangeWithScores(String flag, String key, long start, long end) throws Exception;

    Long zrevrank(String flag, String key, String member) throws Exception;

    Set<String> zrevrange(String flag, String key, long start, long end) throws Exception;

    Set<Tuple> zrangeWithScores(String flag, String key, long start, long end) throws Exception;

    Long srem(String flag, String key, String... members) throws Exception;

    public Long deleteByScore(String flag, String key, double start, double end) throws Exception;

    Boolean sismember(String flag, String key, String member) throws Exception;

    Long zrem(String flag, String key, String... members) throws Exception;

    Double zincrby(String flag, String key, double increment, String member) throws Exception;

    List<Object> setAndExpire(String flag, String key, String value, int seconds) throws Exception;

    List<Object> lpushAndExpire(String flag, int seconds, String key, String... strings) throws Exception;

    List<Object> rpushAndExpire(String flag, int seconds, String key, String... strings) throws Exception;

    List<Object> saddAndExpire(String flag, int seconds, String key, String... members) throws Exception;

    List<Object> zaddAndExpire(String flag, String key, Map<String, Double> scoreMembers, int seconds) throws Exception;

    List<Object> hmsetAndExpire(String flag, String key, Map<String, String> hash, int seconds) throws Exception;

    Pool getJedisPoolByKey(String s) throws Exception;
    
    Long zunionstore(String flag, String destKey, String... sourceKeys) throws Exception;
}
