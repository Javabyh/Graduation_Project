package com.aiit.byh.service.common.redis.factory;

import com.aiit.byh.service.common.redis.service.RedisService;

/**
 * redis server工厂类
 * @author dsqin
 * @create 2016年7月27日
 */
public class RedisServiceFactory {
	
	private static RedisService redisService;

	private static RedisService shardRedisService;

	public static void setRedisService(RedisService redis){
		redisService=redis;
	}
	
	public static RedisService getRedisService() {
		return redisService;
	}

	public static void setShardRedisService(RedisService redis){
		shardRedisService=redis;
	}
	
	public static RedisService getShardRedisService() {
		return shardRedisService;
	}
}
