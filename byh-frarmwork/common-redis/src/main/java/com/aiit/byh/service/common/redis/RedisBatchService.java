package com.aiit.byh.service.common.redis;

import com.aiit.byh.service.common.redis.keys.RedisKeyMapping;
import com.aiit.byh.service.common.redis.keys.RedisType;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * redis操作父类
 * 
 * @author dsqin
 * 
 */
public class RedisBatchService implements IRedisBatchProcess {
	/**
	 * 批量操作ids
	 */
	private List<String> ids;

	/**
	 * 操作的redis key
	 */
	private RedisType redisType;

	public RedisBatchService(List<String> ids) {
		super();
		this.ids = ids;
	}

	public RedisBatchService(List<String> ids, RedisType redisType) {
		super();
		this.ids = ids;
		this.redisType = redisType;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public RedisType getRedisType() {
		return redisType;
	}

	public void setRedisType(RedisType redisType) {
		this.redisType = redisType;
	}

	@Override
	public void process(Pipeline pipe) {
		this.batchHgetAll(pipe);
	}

	/**
	 * 批量hgetall
	 * 
	 * @param pipe
	 */
	public void batchHgetAll(Pipeline pipe) {
		if (null == this.getIds() || this.getIds().size() <= 0) {
			return;
		}
		int size = this.getIds().size();
		for (int i = 0; i < size; i++) {
			String key = RedisKeyMapping.genRedisKey(redisType, this.getIds().get(i));
			pipe.hgetAll(key);
		}
	}
}
