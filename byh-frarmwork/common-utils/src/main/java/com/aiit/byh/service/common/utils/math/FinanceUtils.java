package com.aiit.byh.service.common.utils.math;

import java.math.BigDecimal;

/**
 * 金融相关的工具类
 *
 * @author dsqin
 * @datetime 2017/6/15
 */
public class FinanceUtils {

    /**
     * 分转元
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(long amount) throws Exception {
        if (0 == amount) {
            return "0";
        }

        return BigDecimal.valueOf(amount).divide(new BigDecimal(100)).toString();
    }

}
