package com.aiit.byh.service.common.utils.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * base加解密工具类
 *
 * @author dsqin
 * @datetime 2017/3/22
 */
public class Base64Util {

    /**
     * base64编码
     * @param bytes 原始字符数组
     * @return 编码后的字符串
     */
    public static String encode(byte[] bytes) {

       if (ArrayUtils.isEmpty(bytes)) {
            return StringUtils.EMPTY;
        }

        org.apache.commons.codec.binary.Base64 base64 = new Base64();

        return base64.encodeToString(bytes);
    }

    public static String encode(byte[] bytes, boolean safe) {
        if (ArrayUtils.isEmpty(bytes)) {
            return StringUtils.EMPTY;
        }

        org.apache.commons.codec.binary.Base64 base64 = new Base64(safe);

        return base64.encodeToString(bytes);
    }

    /**
     * base64编码
     * @param oStr 原始字符串
     * @return 编码后的字符串
     */
    public static String encode(String oStr) {
        if (StringUtils.isBlank(oStr)) {
            return oStr;
        }

        return encode(oStr.getBytes(Charsets.UTF_8));
    }


    /**
     * base64编码
     * @param oStr 原始字符串
     * @param safe 安全base64
     * @return 编码后的字符串
     */
    public static String encode(String oStr, boolean safe) {
        if (StringUtils.isBlank(oStr)) {
            return oStr;
        }

        return encode(oStr.getBytes(Charsets.UTF_8), safe);
    }


    /**
     * base64解码
     * @param bytes 原始字符数组
     * @return 解码后的字符数组
     */
    public static String decode(byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return StringUtils.EMPTY;
        }

        String oStr = org.apache.commons.codec.binary.StringUtils.newStringUtf8(bytes);
        return decode(oStr);
    }

    /**
     * base64解码
     * @param bytes 原始字符数组
     * @return 解码后的字符数组
     */
    public static String decode(byte[] bytes, boolean safe) {
        if (ArrayUtils.isEmpty(bytes)) {
            return StringUtils.EMPTY;
        }

        String oStr = org.apache.commons.codec.binary.StringUtils.newStringUtf8(bytes);
        return decode(oStr, safe);
    }



    /**
     * base64解码
     * @param oStr 原始字符串
     * @return 解码后的字符串
     */
    public static String decode(String oStr) {
        if (StringUtils.isBlank(oStr)) {
            return oStr;
        }

        org.apache.commons.codec.binary.Base64 base64 = new Base64();
        byte[] bytes = base64.decode(oStr);
        return org.apache.commons.codec.binary.StringUtils.newStringUtf8(bytes);
    }

    /**
     * base64解码
     * @param oStr 原始字符串
     * @return 解码后的字符串
     */
    public static String decode(String oStr, boolean safe) {
        if (StringUtils.isBlank(oStr)) {
            return oStr;
        }

        org.apache.commons.codec.binary.Base64 base64 = new Base64(safe);
        byte[] bytes = base64.decode(oStr);
        return org.apache.commons.codec.binary.StringUtils.newStringUtf8(bytes);
    }
}
