package com.aiit.byh.service.common.utils.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * 验证类
 * 
 * @author dsqin
 *
 */
public class ValidatorUtils {

	/**
	 * 是否为URL
	 * URL前缀：("http", "https", "ftp")
	 * @param value
	 * @return
	 */
	public static boolean isURL(String value) {
		if (StringUtils.isBlank(value)) {
			return false;
		}
		UrlValidator urlValid = new UrlValidator();
		return urlValid.isValid(value);
	}
}
