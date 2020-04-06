package com.aiit.byh.service.common.utils.encrypt;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

/**
 * 使用异或加密解密
 * 
 * @author jhyuan
 * 
 */
public class ExclusiveUtil {
	private static final String key0 = "01";
	private static final Charset charset = Charset.forName("UTF-8");
	private static byte[] keyBytes = key0.getBytes(charset);

	public static String encode(String enc) {
		if (StringUtils.isBlank(enc)){
			return StringUtils.EMPTY;
		}

		byte[] b = enc.getBytes(charset);
		for (int i = 0, size = b.length; i < size; i++) {
			for (byte keyBytes0 : keyBytes) {
				b[i] = (byte) (b[i] ^ keyBytes0);
			}
		}

		return new String(b);
	}

	public static String decode(String dec) {
		if (StringUtils.isBlank(dec)){
			return null;
		}

		byte[] e = dec.getBytes(charset);
		byte[] dee = e;
		for (int i = 0, size = e.length; i < size; i++) {
			for (byte keyBytes0 : keyBytes) {
				e[i] = (byte) (dee[i] ^ keyBytes0);
			}
		}
		return new String(e);
	}

}