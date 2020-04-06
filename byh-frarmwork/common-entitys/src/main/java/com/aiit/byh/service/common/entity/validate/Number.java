/**  
* @Title: Number.java
* @Package com.aiit.byhc.basic.validation
* @author sdwan
* @date 2014-4-18
* @version V1.0  
*/
package com.aiit.byh.service.common.entity.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Description: 纯数字格式验证
 *
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberValidator.class)
@Documented
public @interface Number{
	int min() default 0;

	int max() default Integer.MAX_VALUE;
	
	String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
