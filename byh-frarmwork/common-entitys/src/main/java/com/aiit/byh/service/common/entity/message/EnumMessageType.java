package com.aiit.byh.service.common.entity.message;

/**
 * @author jhyuan
 * @datetime: 2018/04/02
 * Description: 推送消息类型
 */
public enum EnumMessageType {

    // 视频铃声会员开通成功 mst
    FEED_TYPE_MVVIP_OPEN_SUCCESS(11)
    // 视频铃声会员开通失败 mst
    , FEED_TYPE_MVVIP_OPEN_FAIL(12)
    // 视频铃声会员续费成功 mst
    , FEED_TYPE_MVVIP_AUTORENEWAL_SUCCESS(15)
    // 视频铃声会员续费失败 mst
    , FEED_TYPE_MVVIP_AUTORENEWAL_FAIL(16);

    private int value;

    public int getValue(){
        return value;
    }

    EnumMessageType(int value) {
        this.value = value;
    }
}
