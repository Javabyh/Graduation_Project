package com.aiit.byh.service.common.utils.bean;

import java.lang.annotation.*;

/**
 * @ClassName: FieldRealName
 * @Description: 真实属性名注解
 * @date: 2016年5月21日 下午6:17:09
 * @since JDK 1.6
 * @author: ppli@iflytek.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AliasName {

	String name();
}
