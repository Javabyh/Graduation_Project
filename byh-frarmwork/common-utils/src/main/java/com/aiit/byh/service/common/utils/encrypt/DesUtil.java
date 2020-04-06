package com.aiit.byh.service.common.utils.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

public class DesUtil {
	private final static String DES = "DES";

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException,
			Exception {
		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf, key.getBytes());
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * 调用酷音平台、运营平台数据加密
	 * 
	 * @param text
	 *            明文
	 * @param rawKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String kyEncrypt(String text, String rawKey) throws Exception {
		StringBuilder ret = new StringBuilder();
		// des && base64 解密
		String oristr = DesUtil.encrypt(text, rawKey);
		// 包括\r \n 需去掉
		String base64str = oristr.replaceAll("\r|\n", "");
		int strlen = base64str.getBytes().length;
		// 每个字符转换为2个16进制 字符 ,不足8位前向补0
		for (int i = 0; i < strlen; i++) {
			char a = base64str.charAt(i);
			int deci = (int) a;
			// 字符 转换为二进制
			String binstr = Integer.toBinaryString(deci);
			int mod4 = binstr.length() % 4;
			String head = "";
			// 可能不足8位 前向补0
			if (mod4 != 0) {
				int rest = 4 - mod4;
				for (int m = 0; m < rest; m++) {
					head += "0";
				}
				binstr = head + binstr;// .
			}
			int binstrlen = binstr.length() / 4;
			// 每4个二进制字符 转化为一个16进制字符
			for (int j = 0; j < binstrlen; j++) {
				ret.append(Integer.toHexString(Integer.parseInt(
						binstr.substring(j * 4, j * 4 + 4), 2)));
			}
		}
		return ret.toString();
	}
}
