package com.aiit.byh.service.common.utils.validator.jsr303;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * 验证工具类
 *
 * @author dsqin
 * @datetime 2017/8/30
 */
public class ValidUtils {

    /**
     * 验证
     * @param param
     * @return
     */
    public static List<ValidateMessage> valid(Object param) {
        List<ValidateMessage> validateMessages = new ArrayList<ValidateMessage>();
        Set<ConstraintViolation<Object>> validResult = Validation.buildDefaultValidatorFactory().getValidator().validate(param);
        if (null != validResult && validResult.size() > 0) {
            for (Iterator<ConstraintViolation<Object>> iterator = validResult.iterator(); iterator.hasNext(); ) {
                ConstraintViolation<Object> constraintViolation = iterator.next();
                validateMessages.add(new ValidateMessage(constraintViolation.getPropertyPath().toString(), StringUtils.isBlank(constraintViolation.getMessage()) ? "不合法" : constraintViolation.getMessage(), 1));
            }
        }
        return validateMessages;
    }
}
