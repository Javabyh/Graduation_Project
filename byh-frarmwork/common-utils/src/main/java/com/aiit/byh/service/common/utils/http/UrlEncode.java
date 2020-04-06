package com.aiit.byh.service.common.utils.http;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncode {
    public static String decode(final String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String url, final String code) {
        if (StringUtils.isBlank(url)) {
            return url;
        }

        try {
            url = URLEncoder.encode(url, code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String encodeGBK(final String url) {
        return encode(url, "gbk");
    }

    public static String encodeUTF8(final String url) {
        return encode(url, "utf-8");
    }

    public static String encodeReplaceSpace(final String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        
        return encode(url, "utf-8").replace("+", "%20");
    }
}
