package com.aiit.byh.service.common.redis;

import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * 针对复杂的key批量处理
 */
public class RedisKeyBatchService implements IRedisBatchProcess {
    /**
     * 批量操作ids
     */
    private List<String> keys;

    public RedisKeyBatchService(List<String> keys) {
        super();
        this.keys = keys;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
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
        if (null == this.getKeys() || this.getKeys().size() <= 0) {
            return;
        }
        int size = this.getKeys().size();
        for (int i = 0; i < size; i++) {
            pipe.hgetAll(this.getKeys().get(i));
        }
    }
}
