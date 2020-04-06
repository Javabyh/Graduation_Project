package com.aiit.byh.service.common.utils.lang;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * 时长工具类
 * @author dsqin
 * @datetime 2017/4/21
 */
public class DurationUtils extends DurationFormatUtils {

    /**
     * 解析时长至秒数
     * @param duration
     * @return
     */
    public static long parseDuration(String duration) {
        if (StringUtils.isBlank(duration)) {
            return 0;
        }

        String[] durationArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(duration, ":");

        if (ArrayUtils.isEmpty(durationArray)) {
            return 0;
        }

        int durationLength = durationArray.length;

        long durationSum = 0;
        for (int i = 0 ; i <  durationLength ; i++) {
            durationSum += Math.pow(60, durationLength - i - 1) * NumberUtils.toLong(durationArray[i], 0);
        }

        return durationSum;
    }
}
