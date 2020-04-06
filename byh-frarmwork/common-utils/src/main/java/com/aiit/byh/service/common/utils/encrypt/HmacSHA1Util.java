package com.aiit.byh.service.common.utils.encrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HmacSHA1加密算法
 * Created by jhyuan on 2017/3/22.
 */
public class HmacSHA1Util {
    private static final String HMAC_SHA1 = "HmacSHA1";
    /**
     * 生成签名数据
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     */
    public static byte[] getSignature(String data,String key) throws Exception{
        byte[] keyBytes=key.getBytes("UTF-8");
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        return mac.doFinal(data.getBytes());
    }
}
