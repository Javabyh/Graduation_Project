package com.aiit.byh.service.common.redis.service;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * 
 * @author rlyu
 *
 */
public abstract class RedisCallBack<T> {
	
	public T callback(Pool jedisPool) throws Exception {
		if (jedisPool != null) {
			Jedis jedis = null;
			boolean borrowOrOprSuccess = true;
			try {
				jedis = (Jedis) jedisPool.getResource();
				if (jedis != null) {
					T result = doCallback(jedis);

					//jedisPool.returnResource(jedis);

					return result;
				}
			} catch (Exception e) {
				borrowOrOprSuccess = false;
				if (jedis != null) {
					jedisPool.returnBrokenResource(jedis);
				}
				e.printStackTrace();
				throw e;
			}finally{
				if(borrowOrOprSuccess && jedis != null){
					jedisPool.returnResource(jedis);
		        }
			}
		}

		return null;
	}
	
	

	public abstract T doCallback(Jedis jedis ) ;
	

}
