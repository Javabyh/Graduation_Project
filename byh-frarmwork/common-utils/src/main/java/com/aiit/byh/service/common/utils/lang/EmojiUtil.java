package com.aiit.byh.service.common.utils.lang;

import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang.StringUtils;

/**
 * emoji util
 * @author dsqin
 *
 */
public class EmojiUtil {

	/**
	 * 过滤emoji字符
	 * @param input
	 * @return
	 */
	public static String filter(String input) {
		if (StringUtils.isBlank(input)) {
			return StringUtils.EMPTY;
		}
		return EmojiParser.parseToAliases(input);
	}
}