package com.aiit.byh.service.common.redis.service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @author rlyu
 *
 */
public abstract class ShardRedisCallBack<T> {
	
	public T callback(ShardedJedisPool shardJedisPool) throws Exception {
		if (shardJedisPool != null) {
			ShardedJedis shardJedis = null;
			boolean borrowOrOprSuccess = true;
			try {
				shardJedis = shardJedisPool.getResource();
				if (shardJedis != null) {
					T result = doCallback(shardJedis);
					return result;
				}
			} catch (Exception e) {
				borrowOrOprSuccess = false;
				if (shardJedis != null) {
					shardJedisPool.returnBrokenResource(shardJedis);
				}
				e.printStackTrace();
				throw e;
			}finally{
				if(borrowOrOprSuccess && shardJedis != null){
					shardJedisPool.returnResource(shardJedis);
		        }
			}
		}
		
		return null;
	}
	

	
	public abstract T doCallback(ShardedJedis shardJedis ) ;
	
	
}
