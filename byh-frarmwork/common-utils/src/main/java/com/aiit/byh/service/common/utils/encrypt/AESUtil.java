package com.aiit.byh.service.common.utils.encrypt;

import com.aiit.byh.service.common.utils.CommonUtils;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

/**
 * aes工具类
 * 
 * @author dsqin
 *
 */
public class AESUtil {
	
	/**
	 * AES
	 */
	private final static String _AES = "AES/ECB/PKCS5Padding";
	
	/**
	 * http header标识：f
	 */
	private final static String HTTP_HEADER_F = "f";
	
	/**
	 * http header标识：t
	 */
	private final static String HTTP_HEADER_T = "t";

	/**
	 * 加密
	 * @param content 原始内容
	 * @param password 密码
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] encrypt(String content, String password) throws Exception {
		if (StringUtils.isBlank(content) || StringUtils.isBlank(password) || password.length() != 16) {
            return null;
        }
		byte[] raw = password.getBytes(Charsets.UTF_8);
		SecretKeySpec key = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(_AES);
		byte[] byteContent = content.getBytes(Charsets.UTF_8);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(byteContent);
		String retStr =  new BASE64Encoder().encode(result);
		return retStr.getBytes(Charsets.UTF_8);
	}

	/**
	 * 解密
	 * @param content 加密后的内容
	 * @param password 密码
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static String decrypt(byte[] content, String password) throws Exception {
		if (null == content || StringUtils.isBlank(password) || password.length() != 16) {
            return null;
        }
        String contentStr = new String(content, Charsets.UTF_8);        
        byte[] bt = new BASE64Decoder().decodeBuffer(contentStr);
		byte[] raw = password.getBytes(Charsets.UTF_8);
		SecretKeySpec key = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(_AES);// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(bt); // 加密
		return new String(result);
	}

	public static String decrypt(String content, String key) throws Exception {
		if (null == content || StringUtils.isBlank(key) || key.length() != 16) {
			return null;
		}

		byte[] bt = new BASE64Decoder().decodeBuffer(content);
		byte[] raw = key.getBytes(Charsets.UTF_8);
		SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(_AES);// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
		byte[] result = cipher.doFinal(bt); // 加密
		return new String(result);
	}
	
	
	/**
	 * http header中获取aes key值
	 * @param headers http header
	 * @return aes key
	 */
	public static String genAESKeyFromHttpHeader(Map<String, String> headers) {
		if (CommonUtils.isEmpty(headers) || !headers.containsKey(HTTP_HEADER_F) || !headers.containsKey(HTTP_HEADER_T)) {
			return StringUtils.EMPTY;
		}
		
		String time = headers.get(HTTP_HEADER_T);
		String flag = headers.get(HTTP_HEADER_F);
		
		StringBuilder returnStrBuilder = new StringBuilder();
		returnStrBuilder.append(StringUtils.substring(time, 2, 5));
		returnStrBuilder.append(StringUtils.substring(flag, 7, 20));
		
		return returnStrBuilder.toString();
	}

	/**
	 * 获取AES key值
	 * @param time
	 * @param flag
	 * @return
	 */
	public static String genAESKeyFromHttpHeader(String time, String flag) {
		if (StringUtils.isBlank(time) || StringUtils.isBlank(flag)) {
			return StringUtils.EMPTY;
		}

		StringBuilder returnStrBuilder = new StringBuilder();
		returnStrBuilder.append(StringUtils.substring(time, 2, 5));
		returnStrBuilder.append(StringUtils.substring(flag, 7, 20));

		return returnStrBuilder.toString();
	}

	public static void main(String[] args){
		try {
			System.out.println(new String(encrypt("18019546387", "2102b08e4a0f944e"),Charsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 *
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static String encryptToStr(String content, String password)
	{
		byte[] encryptResult = encryptToBytes(content, password);
		return parseByte2HexStr(encryptResult);
	}

	/**
	 * 加密
	 *
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	private static byte[] encryptToBytes(String content, String password)
	{
		try
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 *
	 * @param encryptResultStr
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static String decryptToStr(String encryptResultStr, String password)
	{
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
		byte[] decryptResult = decryptToBytes(decryptFrom, password);
		try
		{
			return new String(decryptResult, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 *
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	private static byte[] decryptToBytes(byte[] content, String password)
	{
		try
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
	 *
	 * @param strSrc
	 *            要加密的字符串
	 * @param encName
	 *            加密类型
	 * @return
	 */
	public static String encryptForStr(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;

		try {
			byte[] bt = strSrc.getBytes("UTF-8");
			if (encName == null || encName.equals("")) {
				encName = "SHA-256";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = Byte2HexStr(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return strDes;
	}


	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	private static String Byte2HexStr(byte buf[])
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex.toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[])
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr)
	{
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++)
		{
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}
