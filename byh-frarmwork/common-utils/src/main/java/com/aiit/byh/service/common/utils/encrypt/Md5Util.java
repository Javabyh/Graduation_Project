package com.aiit.byh.service.common.utils.encrypt;

import com.google.common.base.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 主要用于验证数据的完整性，可以为数据创建“数字指纹”(散列值),结果具有唯一性 并且计算结果不可逆
 */
public class Md5Util {
	public static String MD5(String s) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		// byte[] buffer = new byte[1024];
		byte[] btInput = s.getBytes(Charsets.UTF_8);
		// 使用指定的字节更新摘要
		messageDigest.update(btInput);
		// 获得密文
		byte[] result = messageDigest.digest();
		// 把密文转换成十六进制的字符串形式
		return bytes2HexString(result);
	}
	
	/**
	 * 结果小写
	 * @param s
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String MD5ToLower(String s) throws NoSuchAlgorithmException{
		String digest=MD5(s);
		if (StringUtils.isNotBlank(digest)) {
			return digest.toLowerCase();
		}
		return digest;
	}
	
	public static String MD5CaseSensitive(String s) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		// byte[] buffer = new byte[1024];
		byte[] btInput = s.getBytes();
		// 使用指定的字节更新摘要
		messageDigest.update(btInput);
		// 获得密文
		byte[] result = messageDigest.digest();
		// 把密文转换成十六进制的字符串形式
		return bytes2HexStringCaseSensitive(result);
	}

	/**
	 * 把字节数组转换为16进制的形式
	 * 
	 * @param b
	 * @return
	 */
	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
	
	public static String bytes2HexStringCaseSensitive(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex;
		}
		return ret;
	}

    public static String md5Hex(byte[] plainText, boolean needShort, boolean uppercase) {
        String encrypted = DigestUtils.md5Hex(plainText);
        if(needShort) {
            encrypted = encrypted.substring(8, 24);
        }

        if(uppercase) {
            encrypted = encrypted.toUpperCase();
        }

        return encrypted;
    }

    public static String md5Hex(byte[] plainText) {
        return md5Hex(plainText, false, false);
    }

    public static String md5HexWithSalt(byte[] plainText, byte[] salt) {
        return md5Hex(ArrayUtils.addAll(plainText, salt));
    }

}
