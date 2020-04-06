package com.aiit.byh.service.common.utils.encrypt;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description: Hmac算法
 * @author: jhyuan
 * @create: 2018/05/24
 **/
public class HmacUtil {
    private static final String HMAC_SHA256 = "HmacSHA256";


    public static String getHmacSha256(String data, String key) throws Exception {
        return sign(data, key, HMAC_SHA256);
    }

    public static String sign(String data, String key, String algorithm) throws Exception {
        byte[] encryByte = encode(data, key, algorithm);
        byte[] hexB = new Hex().encode(encryByte);
        if (hexB == null) {
            return null;
        }

        return new String(hexB, "UTF-8");
    }

    public static byte[] encode(String data, String key) throws Exception {
        return encode(data, key, HMAC_SHA256);
    }

    public static byte[] encode(String data, String key, String algorithm) throws Exception {
        byte[] keyBytes = key.getBytes("UTF-8");
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(signingKey);
        return mac.doFinal(data.getBytes("UTF-8"));
    }

    public static void main(String[] args) {
        try {
            System.out.println(getHmacSha256("12", "12"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
