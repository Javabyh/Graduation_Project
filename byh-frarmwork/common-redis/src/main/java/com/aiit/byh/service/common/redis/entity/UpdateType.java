package com.aiit.byh.service.common.redis.entity;

/**
 * Created by jhyuan on 2017/11/14.
 */
public enum UpdateType {
    SORTEDSET(1)
    , HASH(2);

    private int value;

    UpdateType(int value){
        this.value = value;
    }
}
