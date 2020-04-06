package com.aiit.byh.service.common.redis;

import com.aiit.byh.service.common.redis.factory.RedisServiceFactory;
import com.aiit.byh.service.common.redis.service.RedisCallBack;
import com.aiit.byh.service.common.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.util.Pool;

import java.util.List;

/**
 * redis处理类
 * 
 * @author dsqin
 * 
 */
public class RedisProcessor {

	private static Logger logger = LoggerFactory
			.getLogger(RedisProcessor.class);
	
	/**
	 * 写入redis
	 */
	static RedisService redisService = RedisServiceFactory.getRedisService();

	/**
	 * 批量更新操作(包括增加、删除、修改)
	 * 
	 * @param processor
	 * @return
	 */
	public static boolean batchUpdate(IRedisBatchProcess processor, String flag) {
		if (StringUtils.isBlank(flag)) {
			return false;
		}
		
		try {
			Pool pool =  redisService.getJedisPoolByKey(flag);
			_batch(processor, pool);
			return true;
		} catch (Exception ex) {
			logger.error("****redis batch失败****", ex);
			return false;
		}
	}

	/**
	 * 批量查询
	 * 
	 * @param processor
	 * @param flag
	 * @return
	 */
	public static List<Object> batchQuery(IRedisBatchProcess processor,
			String flag) {
		if (StringUtils.isBlank(flag)) {
			return null;
		}
		try {
			Pool pool =  redisService.getJedisPoolByKey(flag);
			return _batch(processor, pool);
		} catch (Exception ex) {
			logger.error("****redis批量查询失败****", ex);
			return null;
		}
	}
	
	private static List<Object> _batch(final IRedisBatchProcess processor, Pool pool) throws Exception {
	try {
			return new RedisCallBack<List<Object>>() {

				@Override
				public List<Object> doCallback(Jedis jedis) {
					Pipeline pipe = jedis.pipelined();
					processor.process(pipe);
					return pipe.syncAndReturnAll();
				}
			}.callback(pool);
		} catch (Exception ex) {
			logger.error("****redis批量查询失败****", ex);
			return null;
		}
	}
}
