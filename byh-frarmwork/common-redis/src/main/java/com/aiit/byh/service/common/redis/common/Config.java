package com.aiit.byh.service.common.redis.common;


public class Config {
	/**
	 * 哨兵ip和端口配置
	 */
	private String redisSentinelConfig;

	private String redisConfig;
	
	private String shardRedisConfig;
	
	private int timeout;

	private int database;

	public String getRedisSentinelConfig() {
		return redisSentinelConfig;
	}

	public void setRedisSentinelConfig(String redisSentinelConfig) {
		this.redisSentinelConfig = redisSentinelConfig;
	}

	public String getRedisConfig() {
		return redisConfig;
	}

	public void setRedisConfig(String redisConfig) {
		this.redisConfig = redisConfig;
	}
	
	
	public String getShardRedisConfig() {
		return shardRedisConfig;
	}

	public void setShardRedisConfig(String shardRedisConfig) {
		this.shardRedisConfig = shardRedisConfig;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
}
