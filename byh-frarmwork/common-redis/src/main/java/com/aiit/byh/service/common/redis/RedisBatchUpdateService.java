package com.aiit.byh.service.common.redis;

import com.aiit.byh.service.common.redis.entity.UpdateInfo;
import com.aiit.byh.service.common.redis.entity.UpdateType;
import com.aiit.byh.service.common.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * redis操作父类
 *
 * @author dsqin
 */
public class RedisBatchUpdateService implements IRedisBatchProcess {
    /**
     * 批量操作ids
     */
    private List<UpdateInfo> list;

    private UpdateType updateType;

    public RedisBatchUpdateService() {
        super();
    }

    public RedisBatchUpdateService(List<UpdateInfo> list, UpdateType updatedType) {
        this.list = list;
        this.updateType = updatedType;
    }


    public void process(Pipeline pipe) {
        if (CommonUtils.isEmpty(list)) {
            return;
        }

        updateBatch(pipe);
    }

    /**
     * 批量更新
     * @param pipe
     */
    private void updateBatch(Pipeline pipe) {
        for (int i = 0; i < list.size(); i++) {
            UpdateInfo updateInfo = list.get(i);
            String key = updateInfo.getKey();
            String field = updateInfo.getField();
            String value = updateInfo.getValue();

            if (StringUtils.isNotBlank(field)) {
                switch (updateType) {
                    case HASH:
                        pipe.hset(key, field, value);
                        break;
                    case SORTEDSET:
                        pipe.zadd(key, NumberUtils.toLong(value), field);
                        break;
                }
            }
        }
    }

    public List<UpdateInfo> getList() {
        return list;
    }

    public void setList(List<UpdateInfo> list) {
        this.list = list;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(UpdateType updateType) {
        this.updateType = updateType;
    }
}
