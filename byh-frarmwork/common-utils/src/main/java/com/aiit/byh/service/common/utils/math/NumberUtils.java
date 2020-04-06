package com.aiit.byh.service.common.utils.math;

import java.text.DecimalFormat;
import java.util.Collection;

/**
 * Created by jhyuan on 2016/12/7.
 */
public class NumberUtils {
    public static final String DECIAML_2F = "0.00";

    public static String format(double number, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(number);
    }

    public static Long max(Collection<Long> array) {
        if (array == null || array.size() == 0){
            return 0L;
        }

        long max = Long.MIN_VALUE;
        for (long value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;

    }

    public static Long min(Collection<Long> array) {
        if (array == null || array.size() == 0){
            return 0L;
        }

        long min = Long.MAX_VALUE;
        for (long value : array) {
            if (value < min) {
                min = value;
            }
        }
        return min;

    }

    public static double defaultDouble(Double value) {
        if (value == null) {
            return 0;
        }

        return value;
    }
}
