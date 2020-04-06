package com.aiit.byh.service.common.entity.message;

import com.aiit.byh.service.common.utils.CommonUtils;
import com.aiit.byh.service.common.utils.json.JsonUtil;

import java.util.List;

/**
 * 视频铃声社区最新
 * Created by bbhan2 on 2019/7/26
 **/
public class MVNewRankMQ {
    /**
     * 视频铃声编号
     */
    List<String> id;
    /**
     * 操作类型：“1”为增加，“0”为删除
     */
    private String type;

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MVNewRankMQ{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public String genJSONMsg(MVNewRankMQ mvNewRankMQ) {
        if (CommonUtils.isEmpty(mvNewRankMQ)) {
            return null;
        }
        return JsonUtil.genJsonString(mvNewRankMQ);
    }
}
