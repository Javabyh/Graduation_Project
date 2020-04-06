/**  
 * @Title: NumberValidator.java
 * @Package com.aiit.byhc.basic.validation
 * @author sdwan
 * @date 2014-4-18
 * @version V1.0  
 */
package com.aiit.byh.service.common.entity.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 数字验证 器
 * 
 */
public class NumberValidator implements ConstraintValidator<Number, String> {

	/**
	 * 最小长度
	 */
	private int min;

	/**
	 * 最大长度
	 */
	private int max;

	public void initialize(Number constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		value = value.trim();
		if (value.length() > this.max) {
			return false;
		}

		if (value.length() < this.min) {
			return false;
		}

		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
