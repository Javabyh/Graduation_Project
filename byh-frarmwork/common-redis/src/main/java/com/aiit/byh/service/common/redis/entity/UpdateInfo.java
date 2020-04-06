package com.aiit.byh.service.common.redis.entity;

import java.io.Serializable;

/**
 * Created by jhyuan on 2017/11/14.
 */
public class UpdateInfo implements Serializable {
    /**
     * 键key
     */
    private String key;
    /**
     * field 或 member
     */
    private String field;
    /**
     * value 或 score
     */
    private String value;

    public UpdateInfo(){

    }

    public UpdateInfo(String key,String field,String value){
        this.key = key;
        this.field = field;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateInfo{");
        sb.append("key='").append(key).append('\'');
        sb.append(", field='").append(field).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
