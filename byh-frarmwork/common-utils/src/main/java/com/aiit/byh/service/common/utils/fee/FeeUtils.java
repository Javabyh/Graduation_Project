package com.aiit.byh.service.common.utils.fee;


import com.aiit.byh.service.common.utils.config.ConfigUtil;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 计费相关
 *
 * @author jianchen3
 * @date 2018年1月16日下午2:41:20
 */
public class FeeUtils {
    /**
     * 计费类型转换，from计费编号
     *
     * @param chargeID
     * @return
     */
    public static String convertCgtp(String chargeID) {
        return ConfigUtil.getString(
                String.format("cgtp.%s", chargeID), "1");
    }

    public static String fen2Yuan(String fen) {
        return String.valueOf((int) Math.ceil(NumberUtils.toInt(fen) / 100d));
    }


}
