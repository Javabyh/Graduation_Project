package com.aiit.byh.service.common.utils.encrypt;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密算法
 * Created by jhyuan on 2017/2/8.
 */
public class RSAUtil {

    private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    public enum SignAlgorithm {
        SHA("SHA1WithRSA"), SHA2("SHA256withRSA");

        private String value;

        public String getValue() {
            return value;
        }

        SignAlgorithm(String value) {
            this.value = value;
        }

        public static SignAlgorithm diyValueOf(String signType) {
            if ("RSA".equals(signType)) {
                return SHA;
            }

            return SHA;
        }
    }

    /**
     * RSA签名
     *
     * @param content       待签名数据
     * @param privateKey    商户私钥
     * @param input_charset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey,
                              String input_charset, String signType) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            SignAlgorithm signAlgorithm = SignAlgorithm.diyValueOf(signType);
            java.security.Signature signature = java.security.Signature
                    .getInstance(signAlgorithm.getValue());

            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            logger.error("****RSA加密失败,content:{},input_charset:{},signType:{}****", content, input_charset, signType, e);
        }

        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content        待签名数据
     * @param sign           签名值
     * @param ali_public_key 支付宝公钥
     * @param input_charset  编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign,
                                 String ali_public_key, String input_charset, SignAlgorithm signType) {
        try {
            String algorithm= StringUtils.EMPTY;
            switch (signType){
                case SHA:
                    algorithm="RSA";
                    break;
                default:
                    algorithm="RSA";
                    break;

            }
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = Base64.decode(ali_public_key);
            PublicKey pubKey = keyFactory
                    .generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature
                    .getInstance(signType.getValue());

            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));

            boolean bverify = signature.verify(Base64.decode(sign));
            return bverify;

        } catch (Exception e) {
            logger.error("****签名校验失败,content:{},sign:{},input_charset:{},ali_public_key:{},signTyp:{}****", content, sign, input_charset, ali_public_key, signType, e);
        }

        return false;
    }

    /**
     * 解密
     *
     * @param content       密文
     * @param private_key   商户私钥
     * @param input_charset 编码格式
     * @return 解密后的字符串
     */
    public static String decrypt(String content, String private_key,
                                 String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] keyBytes;

        keyBytes = Base64.decode(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

}
