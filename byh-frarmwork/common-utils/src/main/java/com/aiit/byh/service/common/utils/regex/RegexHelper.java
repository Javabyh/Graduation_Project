package com.aiit.byh.service.common.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式辅助类
 * 
 * @author Administrator
 * 
 */
public class RegexHelper {
	public static boolean isMatch(String regex, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
}
