package com.aiit.byh.service.common.utils.version;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 版本工具类
 *
 * @author dsqin
 * @datetime 2017/5/12
 */
public class VersionUtils {

    /**
     * 等于
     */
    public static final int EQUAL = 0;

    /**
     * 大于
     */
    public static final int GREATER_THAN = 1;

    /**
     * 小于
     */
    public static final int LESS_THAN = -1;

    /**
     * 未知
     */
    public static final int UNKNOWN = 2;

    /**
     * 版本号比较
     * @param v1 版本号1
     * @param v2 版本号2
     * @return 0:相等;  1:v1>v2;  -1:v1<v2; 2:未知
     */
    public static int compareVersion(String v1, String v2) {
        if (StringUtils.isBlank(v1) || StringUtils.isBlank(v2)) {
            return UNKNOWN;
        }

        String[] v1Array = StringUtils.splitByWholeSeparatorPreserveAllTokens(v1, ".");

        String[] v2Array = StringUtils.splitByWholeSeparatorPreserveAllTokens(v2, ".");

        if (ArrayUtils.isEmpty(v1Array) || ArrayUtils.isEmpty(v2Array) || (v1Array.length != v2Array.length)) {
            return UNKNOWN;
        }

        int size = v1Array.length;

        for (int i = 0; i < size; i++) {
            long n1 = NumberUtils.toInt(v1Array[i]);
            long n2 = NumberUtils.toInt(v2Array[i]);

            if (n1 > n2) {
                return GREATER_THAN;
            }
            if (n1 < n2) {
                return LESS_THAN;
            }
        }

        return EQUAL;
    }
}
