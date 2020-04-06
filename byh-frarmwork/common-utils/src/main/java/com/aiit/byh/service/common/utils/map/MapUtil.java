package com.aiit.byh.service.common.utils.map;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.aiit.byh.service.common.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * map相关util
 *
 * @author dsqin
 * @datetime 2016/12/1
 */
public class MapUtil {

    /**
     * map生成签名串
     *
     * @param map
     * @return
     */
    public static String signMap(Map<String, String> map) {
        List<String> keys = Lists.newArrayList(map.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys) {
            String value = map.get(key);
            if (StringUtils.isNotBlank(value)) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(value);
                stringBuilder.append("&");
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    public static String signMapQuotes(Map<String, String> map) {
        List<String> keys = Lists.newArrayList(map.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys) {
            String value = map.get(key);
            if (StringUtils.isNotBlank(value)) {
                stringBuilder.append(key);
                stringBuilder.append("=\"");
                stringBuilder.append(value);
                stringBuilder.append("\"&");
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    /**
     * map按字典序排序，空字符串不参与处理
     * @param map
     * @return
     */
    public static String signMapSorted(Map<String, String> map) {
        return signMapSorted(map, false);
    }

    /**
     * map按字典序排序
     * @param map
     * @param allowBlank
     * @return
     */
    public static String signMapSorted(Map<String, String> map, boolean allowBlank) {
        String result = "";
        if (map == null || map.size() <= 0) {
            return result;
        }

        Map<String, String> sortedMap = new TreeMap<String, String>();
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (!allowBlank && org.apache.commons.lang.StringUtils.isBlank(value)) {
                continue;
            }
            sortedMap.put(key, value);
        }

        for (String key : sortedMap.keySet()) {
            String value = sortedMap.get(key);
            result = result + key + "=" + value + "&";
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }


    /**
     * @param request
     * @MethodName: getAlipayNotifyVerifyMap
     * @Description: 获取支付宝回调参数用于验证
     * @date: 2016年3月30日 上午11:56:36
     * @return: Map<String       ,       String>
     * @author: ppli@iflytek.com
     */
    public static Map<String, String> getAlipayNotifyVerifyMap(
            Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iterator = requestParams.keySet().iterator(); iterator
                .hasNext(); ) {
            String name = iterator.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }
    /**
     * map生成url
     * @param params
     * @return
     */
    public static String assembleUrl(String url, Map<String, String> params) {
        StringBuilder urlStrBuilder = new StringBuilder(url);
        if(StringUtils.isBlank(url)){
            return null;
        }
        if (!url.endsWith("?")) {
            urlStrBuilder.append("?");
        }
        if(CommonUtils.isNotEmpty(params)) {
            for (String key : params.keySet()) {
                String val = params.get(key);
                if (StringUtils.isNotBlank(val)) {
                    try {
                        val = URLEncoder.encode(val, Charsets.UTF_8.name());
                    } catch (UnsupportedEncodingException ex) {
                        return null;
                    }
                }
                urlStrBuilder.append(key).append("=").append(val).append("&");
            }
        }
        urlStrBuilder.deleteCharAt(urlStrBuilder.length()-1);
        return urlStrBuilder.toString();
    }
}
