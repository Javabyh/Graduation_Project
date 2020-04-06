package com.aiit.byh.service.common.redis;

import redis.clients.jedis.Pipeline;

/**
 * 批量redis处理器
 * @author dsqin
 *
 */
public interface IRedisBatchProcess {
	public void process(Pipeline pipe);
}
