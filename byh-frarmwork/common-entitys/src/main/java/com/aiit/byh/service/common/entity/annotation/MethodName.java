package com.aiit.byh.service.common.entity.annotation;

import java.lang.annotation.*;

/**
 * 方法名称
 * @author bbhan3
 * @date 2020/1/6 14:21
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodName {
	public abstract String value() default "";
}