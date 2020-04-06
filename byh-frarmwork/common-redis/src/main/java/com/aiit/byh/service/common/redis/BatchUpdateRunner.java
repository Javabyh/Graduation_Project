package com.aiit.byh.service.common.redis;

/**
 * Created by jhyuan on 2017/11/15.
 */
public class BatchUpdateRunner {
    public static void run(IRedisBatchProcess process) {
        run(process, RedisUtil.getWriteRedisFlag());
    }

    public static void run(IRedisBatchProcess process, String flag) {
        RedisProcessor.batchUpdate(process, flag);
    }
}
