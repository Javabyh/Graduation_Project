package com.aiit.byh.service.common.entity.entity;

import java.io.Serializable;

/**
 * @Author: cwma
 * @Description:
 * @Date: 2019/3/26 16:15
 */
public class ChannelGroupSimple implements Serializable {

    /**
     * 分组标识
     */
    private String groupId;
    /**
     * 渠道分组维度 0：版权渠道分组维度 1：渠道所属维度自我渠道、API渠道) 默认0
     */
    private int groupType;

    public ChannelGroupSimple() {
    }

    public ChannelGroupSimple(String groupId, int groupType) {
        this.groupId = groupId;
        this.groupType = groupType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChannelGroupSimple{");
        sb.append("groupId='").append(groupId).append('\'');
        sb.append(", groupType=").append(groupType);
        sb.append('}');
        return sb.toString();
    }
}
