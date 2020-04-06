package com.aiit.byh.service.common.redis.service.impl;

import com.aiit.byh.service.common.redis.common.Config;
import com.aiit.byh.service.common.redis.service.RedisCallBack;
import com.aiit.byh.service.common.redis.service.RedisService;
import com.aiit.byh.service.common.utils.CommonUtils;
import org.amino.ds.lockfree.LockFreeDictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import javax.annotation.PostConstruct;
import java.util.*;

@Component("redisService")
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private Config config;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    private LockFreeDictionary<String, JedisPool> jedisPoolMap;

    private LockFreeDictionary<String, JedisSentinelPool> jedisSentinelPoolMap;

    @PostConstruct
    public void init() {
        Map<String, String> temp = new HashMap<String, String>();

        // 哨兵模式
        initJedisSentinelPool(temp);

        // 主从模式
        initJedisPool(temp);
    }

    /**
     * 初始化哨兵模式的资源池
     *
     * @param temp
     */
    private void initJedisSentinelPool(Map<String, String> temp) {
        jedisSentinelPoolMap = new LockFreeDictionary<String, JedisSentinelPool>();
        String redisConfig = config.getRedisSentinelConfig();
        // 主从模式
        // Read#ip#port#password;
        if (StringUtils.isNotBlank(redisConfig)) {
            // 不同读写数据源
            String[] redises = redisConfig.split(";");

            int length = redises.length;
            for (int i = 0; i < length; i++) {
                String[] redisInfo = redises[i].split("#");
                if (CommonUtils.isEmpty(redisInfo) || redisInfo.length < 3) {
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                for (int j = 1; j < redisInfo.length; j++) {
                    sb.append(redisInfo[j]).append("|");
                }
                String name = temp.get(sb.toString());
                if (StringUtils.isBlank(name)) {
                    JedisSentinelPool jedisSentinelPool = null;

                    Set<String> sentinels = CommonUtils.Array2Set(StringUtils.splitByWholeSeparatorPreserveAllTokens(redisInfo[1], ","));
                    if (CommonUtils.isEmpty(sentinels)) {
                        return;
                    }

                    // Read#ip:port,ip:port#mastername;Write#ip:port,ip:port#mastername
                    if (redisInfo.length == 3) {
                        jedisSentinelPool = new JedisSentinelPool(redisInfo[2], sentinels, jedisPoolConfig, config.getTimeout());
                    } else if (redisInfo.length == 4) {
                        // Read#ip:port,ip:port#mastername#password;Write#ip:port,ip:port#mastername#password
                        jedisSentinelPool = new JedisSentinelPool(redisInfo[2], sentinels, jedisPoolConfig, config.getTimeout(), redisInfo[3]);
                    }

                    if (jedisSentinelPool != null) {
                        Jedis jedis = jedisSentinelPool.getResource();
                        jedisSentinelPool.returnResource(jedis);
                        jedisSentinelPoolMap.put(redisInfo[0], jedisSentinelPool);
                    }

                    temp.put(sb.toString(), redisInfo[0]);
                } else {
                    jedisSentinelPoolMap.put(redisInfo[0], jedisSentinelPoolMap.get(name));
                }
            }
        }
    }

    /**
     * 初始化主动模式的资源池
     *
     * @param temp
     */
    private void initJedisPool(Map<String, String> temp) {
        jedisPoolMap = new LockFreeDictionary<String, JedisPool>();
        String redisConfig = config.getRedisConfig();
        // 主从模式
        // Read#ip#port#password;
        if (StringUtils.isNotBlank(redisConfig)) {
            String[] redises = redisConfig.split(";");

            int length = redises.length;
            for (int i = 0; i < length; i++) {
                String[] redisInfo = redises[i].split("#");
                if (CommonUtils.isEmpty(redisInfo) || redisInfo.length < 3) {
                    continue;
                }

                StringBuilder sb = new StringBuilder();
                for (int j = 1; j < redisInfo.length; j++) {
                    sb.append(redisInfo[j]).append("|");
                }
                String name = temp.get(sb.toString());
                if (StringUtils.isBlank(name)) {
                    JedisPool jedisPool = null;

                    if (redisInfo.length == 3) {
                        jedisPool = new JedisPool(jedisPoolConfig,
                                redisInfo[1], Integer.parseInt(redisInfo[2]),
                                config.getTimeout());
                    } else if (redisInfo.length == 4) {
                        jedisPool = new JedisPool(jedisPoolConfig,
                                redisInfo[1], Integer.parseInt(redisInfo[2]),
                                config.getTimeout(), redisInfo[3]);
                    }

                    if (jedisPool != null) {
                        Jedis jedis = jedisPool.getResource();
                        jedisPool.returnResource(jedis);
                        jedisPoolMap.put(redisInfo[0], jedisPool);
                    }

                    temp.put(sb.toString(), redisInfo[0]);
                } else {
                    jedisPoolMap.put(redisInfo[0], jedisPoolMap.get(name));
                }

            }
        }
    }

    @Override
    public Pool getJedisPoolByKey(String flag) throws Exception {
        if (flag == null) {
            throw new NullPointerException("flag不能为null");
        }

        // 优先处理哨兵模式
        JedisSentinelPool jedisSentinelPool = jedisSentinelPoolMap.get(flag);
        if (jedisSentinelPool != null) {
            return jedisSentinelPool;
        }

        // 不是哨兵，则按照主从模式获取Pool
        JedisPool jedisPool = jedisPoolMap.get(flag);
        if (jedisPool == null) {
            throw new RuntimeException(flag + " redis实例未找到");
        }
        return jedisPool;
    }

    @Override
    public Jedis getJedisByKey(String flag) throws Exception {
        Pool jedisPool = getJedisPoolByKey(flag);

        if (jedisPool != null) {
            Jedis jedis = (Jedis) jedisPool.getResource();

            if (jedis == null) {
                throw new RuntimeException(flag + " redis实例未找到");
            }
            return jedis;
        }

        return null;
    }

    @Override
    public void returnJedisByKey(String flag, Jedis jedis) throws Exception {
        Pool jedisPool = getJedisPoolByKey(flag);

        if (jedisPool != null && jedis != null) {
            jedisPool.returnResource(jedis);
        }

    }

    @Override
    public Long append(String flag, final String key, final String value)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.append(key, value);
            }

        }.callback(getJedisPoolByKey(flag));

    }

    @Override
    public List<String> blpop(String flag, final int timeout,
                              final String... keys) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.blpop(timeout, keys);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> brpop(String flag, final int timeout,
                              final String... keys) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.brpop(timeout, keys);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> configGet(String flag, final String pattern)
            throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.configGet(pattern);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String configSet(String flag, final String parameter,
                            final String value) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.configSet(parameter, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long decr(String flag, final String key) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.decr(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long decrBy(String flag, final String key, final long integer)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.decrBy(key, integer);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long del(String flag, final String... keys) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.del(keys);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Object eval(String flag, final String script) throws Exception {
        return new RedisCallBack<Object>() {
            @Override
            public Object doCallback(Jedis jedis) {
                return jedis.eval(script);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Boolean exists(String flag, final String key) throws Exception {
        return new RedisCallBack<Boolean>() {
            @Override
            public Boolean doCallback(Jedis jedis) {
                return jedis.exists(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long expire(String flag, final String key, final int seconds)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.expire(key, seconds);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String flushAll(String flag) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.flushAll();
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String flushDB(String flag) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.flushDB();
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String get(String flag, final String key) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.get(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Boolean getbit(String flag, final String key, final long offset)
            throws Exception {
        return new RedisCallBack<Boolean>() {
            @Override
            public Boolean doCallback(Jedis jedis) {
                return jedis.getbit(key, offset);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String getrange(String flag, final String key,
                           final long startOffset, final long endOffset) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.getrange(key, startOffset, endOffset);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String getSet(String flag, final String key, final String value)
            throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.getSet(key, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long hdel(String flag, final String key, final String... fields)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.hdel(key, fields);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Boolean hexists(String flag, final String key, final String field)
            throws Exception {
        return new RedisCallBack<Boolean>() {
            @Override
            public Boolean doCallback(Jedis jedis) {
                return jedis.hexists(key, field);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String hget(String flag, final String key, final String field)
            throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.hget(key, field);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Map<String, String> hgetAll(String flag, final String key)
            throws Exception {
        return new RedisCallBack<Map<String, String>>() {
            @Override
            public Map<String, String> doCallback(Jedis jedis) {
                return jedis.hgetAll(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long hincrBy(String flag, final String key, final String field,
                        final long value) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.hincrBy(key, field, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> hkeys(String flag, final String key) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.hkeys(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long hlen(String flag, final String key) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.hlen(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> hmget(String flag, final String key,
                              final String... fields) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.hmget(key, fields);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String hmset(String flag, final String key,
                        final Map<String, String> hash) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.hmset(key, hash);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long hset(String flag, final String key, final String field,
                     final String value) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.hset(key, field, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long hsetnx(String flag, final String key, final String field,
                       final String value) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.hsetnx(key, field, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> hvals(String flag, final String key) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.hvals(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long incr(String flag, final String key) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.incr(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long incrBy(String flag, final String key, final long integer)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.incrBy(key, integer);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> keys(String flag, final String pattern) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.keys(pattern);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String lindex(String flag, final String key, final long index)
            throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.lindex(key, index);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long llen(String flag, final String key) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.llen(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String lpop(String flag, final String key) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.lpop(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long lpush(String flag, final String key, final String... strings)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.lpush(key, strings);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long lpushx(String flag, final String key, final String string)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.lpushx(key, string);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> lrange(String flag, final String key, final long start,
                               final long end) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long lrem(String flag, final String key, final long count,
                     final String value) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.lrem(key, count, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String lset(String flag, final String key, final long index,
                       final String value) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.lset(key, index, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String ltrim(String flag, final String key, final long start,
                        final long end) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.ltrim(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> mget(String flag, final String... keys)
            throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.mget(keys);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long move(String flag, final String key, final int dbIndex)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.move(key, dbIndex);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String mset(String flag, final String... keysvalues)
            throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.mset(keysvalues);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long msetnx(String flag, final String... keysvalues)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.msetnx(keysvalues);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long publish(String flag, final String channel, final String message)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.publish(channel, message);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String randomKey(String flag) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.randomKey();
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String rename(String flag, final String oldkey, final String newkey)
            throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.rename(oldkey, newkey);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long renamenx(String flag, final String oldkey, final String newkey)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.renamenx(oldkey, newkey);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String rpop(String flag, final String key) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.rpop(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String rpoplpush(String flag, final String srckey,
                            final String dstkey) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.rpoplpush(srckey, dstkey);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long rpush(String flag, final String key, final String... strings)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.rpush(key, strings);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long rpushx(String flag, final String key, final String string)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.rpushx(key, string);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long sadd(String flag, final String key, final String... members)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.sadd(key, members);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long scard(String flag, final String key) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.scard(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> sdiff(String flag, final String... keys)
            throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.sdiff(keys);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long sdiffstore(String flag, final String dstkey,
                           final String... keys) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.sdiffstore(dstkey, keys);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String select(String flag, final int index) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.select(index);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String set(String flag, final String key, final String value)
            throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.set(key, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Boolean setbit(String flag, final String key, final long offset,
                          final boolean value) throws Exception {
        return new RedisCallBack<Boolean>() {
            @Override
            public Boolean doCallback(Jedis jedis) {
                return jedis.setbit(key, offset, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String setex(String flag, final String key, final int seconds,
                        final String value) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.setex(key, seconds, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long setnx(String flag, final String key, final String value)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.setnx(key, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long setrange(String flag, final String key, final long offset,
                         final String value) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.setrange(key, offset, value);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> sinter(String flag, final String... keys)
            throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.sinter(keys);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> smembers(String flag, final String key) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.smembers(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long smove(String flag, final String srckey, final String dstkey,
                      final String member) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.smove(srckey, dstkey, member);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> sort(String flag, final String key) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.sort(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> sort(String flag, final String key,
                             final SortingParams sortingParameters) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.sort(key, sortingParameters);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long sort(String flag, final String key,
                     final SortingParams sortingParameters, final String dstkey)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.sort(key, sortingParameters, dstkey);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long sort(String flag, final String key, final String dstkey)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.sort(key, dstkey);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String spop(String flag, final String key) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.spop(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String srandmember(String flag, final String key) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.srandmember(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<String> srandmember(String flag, final String key,
                                    final int count) throws Exception {
        return new RedisCallBack<List<String>>() {
            @Override
            public List<String> doCallback(Jedis jedis) {
                return jedis.srandmember(key, count);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long strlen(String flag, final String key) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.strlen(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public String substr(String flag, final String key, final int start,
                         final int end) throws Exception {
        return new RedisCallBack<String>() {
            @Override
            public String doCallback(Jedis jedis) {
                return jedis.substr(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long zadd(String flag, final String key, final double score,
                     final String member) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zadd(key, score, member);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long zadd(String flag, final String key,
                     final Map<String, Double> scoreMembers) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zadd(key, scoreMembers);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long zcount(String flag, final String key, final double min,
                       final double max) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zcount(key, min, max);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long zcard(String flag, final String key) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zcard(key);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> zrange(String flag, final String key, final long start,
                              final long end) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.zrange(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long zrank(String flag, final String key, final String member)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zrank(key, member);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Double zscore(String flag, final String key, final String member)
            throws Exception {
        return new RedisCallBack<Double>() {
            @Override
            public Double doCallback(Jedis jedis) {
                return jedis.zscore(key, member);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> zrangeByScore(String flag, final String key,
                                     final double min, final double max) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.zrangeByScore(key, min, max);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> zrangeByScore(String flag, final String key,
                                     final double min, final double max, final int offset,
                                     final int count) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.zrangeByScore(key, min, max, offset, count);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> zrangeByScore(String flag, final String key,
                                     final String min, final String max) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.zrangeByScore(key, min, max);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> zrangeByScore(String flag, final String key,
                                     final String min, final String max, final int offset,
                                     final int count) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.zrangeByScore(key, min, max, offset, count);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String flag, final String key,
                                              final String min, final String max,
                                              final int count) throws Exception {
        return new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(Jedis jedis) {
                return jedis.zrangeByScoreWithScores(key, min, max, 0, count);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String flag, final String key,
                                                 final String max, final String min,
                                                 final int count) throws Exception {
        return new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(Jedis jedis) {
                return jedis.zrevrangeByScoreWithScores(key, max, min, 0, count);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String flag, final String key,
                                          final long start, final long end) throws Exception {
        return new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(Jedis jedis) {
                return jedis.zrevrangeWithScores(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long zrevrank(String flag, final String key, final String member)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zrevrank(key, member);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<String> zrevrange(String flag, final String key,
                                 final long start, final long end) throws Exception {
        return new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> doCallback(Jedis jedis) {
                return jedis.zrevrange(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Set<Tuple> zrangeWithScores(String flag, final String key,
                                       final long start, final long end) throws Exception {
        return new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> doCallback(Jedis jedis) {
                return jedis.zrangeWithScores(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long srem(String flag, final String key, final String... members)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.srem(key, members);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long deleteByScore(String flag, final String key, final double start, final double end) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zremrangeByScore(key, start, end);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Boolean sismember(String flag, final String key, final String member)
            throws Exception {
        return new RedisCallBack<Boolean>() {
            @Override
            public Boolean doCallback(Jedis jedis) {
                return jedis.sismember(key, member);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public Long zrem(String flag, final String key, final String... members)
            throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                return jedis.zrem(key, members);
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<Object> setAndExpire(String flag, final String key,
                                     final String value, final int seconds) throws Exception {
        return new RedisCallBack<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                List<Object> result = new ArrayList<Object>(2);
                result.add(jedis.set(key, value));
                result.add(jedis.expire(key, seconds));
                return result;
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<Object> lpushAndExpire(String flag, final int seconds,
                                       final String key, final String... strings) throws Exception {
        return new RedisCallBack<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                List<Object> result = new ArrayList<Object>(2);
                result.add(jedis.lpush(key, strings));
                result.add(jedis.expire(key, seconds));
                return result;
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<Object> rpushAndExpire(String flag, final int seconds,
                                       final String key, final String... strings) throws Exception {
        return new RedisCallBack<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                List<Object> result = new ArrayList<Object>(2);
                result.add(jedis.rpush(key, strings));
                result.add(jedis.expire(key, seconds));
                return result;
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<Object> saddAndExpire(String flag, final int seconds,
                                      final String key, final String... members) throws Exception {
        return new RedisCallBack<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                List<Object> result = new ArrayList<Object>(2);
                result.add(jedis.sadd(key, members));
                result.add(jedis.expire(key, seconds));
                return result;
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<Object> zaddAndExpire(String flag, final String key,
                                      final Map<String, Double> scoreMembers, final int seconds)
            throws Exception {
        return new RedisCallBack<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                List<Object> result = new ArrayList<Object>(2);
                result.add(jedis.zadd(key, scoreMembers));
                result.add(jedis.expire(key, seconds));
                return result;
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public List<Object> hmsetAndExpire(String flag, final String key,
                                       final Map<String, String> hash, final int seconds) throws Exception {
        return new RedisCallBack<List<Object>>() {
            @Override
            public List<Object> doCallback(Jedis jedis) {
                List<Object> result = new ArrayList<Object>(2);
                result.add(jedis.hmset(key, hash));
                result.add(jedis.expire(key, seconds));
                return result;
            }

        }.callback(getJedisPoolByKey(flag));
    }

    @Override
    public ShardedJedis getShardedJedisByKey(String flag) throws Exception {
        // TODO Auto-generated method stub
        throw new Exception("unimplementable");
    }

    @Override
    public void returnShardedJedisByKey(String flag, ShardedJedis jedis)
            throws Exception {
        // TODO Auto-generated method stub
        throw new Exception("unimplementable");
    }

    @Override
    public Double zincrby(final String flag, final String key,
                          final double increment, final String member) throws Exception {
        return new RedisCallBack<Double>() {
            @Override
            public Double doCallback(Jedis jedis) {
                return jedis.zincrby(key, increment, member);
            }

        }.callback(getJedisPoolByKey(flag));

    }

    @Override
    public Long zunionstore(final String flag, final String destKey,
                            final String... sourceKeys) throws Exception {
        return new RedisCallBack<Long>() {
            @Override
            public Long doCallback(Jedis jedis) {
                // 使用redis ZUNIONSTORE(如果key destination存在，就被覆盖)
                // RedisUtil.zunionstore(destKey, sourceKey);
//				return jedis.zunionstore(destKey, sourceKeys);

                long result = 0;
                try {
                    // codis不支持zunionstore命令，需要把一个zset的数据拷贝到一个新的zset中
                    Map<String, Double> scoreMembers = new HashMap<String, Double>();
                    for (String srckey : sourceKeys) {
                        Set<Tuple> source = zrangeWithScores(flag, srckey, 0, -1);
                        if (CommonUtils.isEmpty(source)) {
                            continue;
                        }
                        for (Tuple tuple : source) {
                            scoreMembers.put(tuple.getElement(), tuple.getScore());
                        }
                    }

                    // 先删除后存储
                    if (CommonUtils.isNotEmpty(scoreMembers)) {
                        result = scoreMembers.size();
                        del(flag, destKey);
                        zaddAndExpire(flag, destKey, scoreMembers, -1);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(flag + ex);
                }
                return result;
            }

        }.callback(getJedisPoolByKey(flag));
    }

}
