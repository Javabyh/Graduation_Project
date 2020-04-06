package com.aiit.byh.service.common.entity.aspect.api;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * api服务切面
 * @author bbhan3
 * @date 2020/1/6 14:21
 */
public class ApiServiceAspect {

    private static Logger logger = LoggerFactory.getLogger(ApiServiceAspect.class);

    /**
     * 切片函数
     * @param joinPoint 切点
     * @return
     */
    public Object doAround(ProceedingJoinPoint joinPoint) {

        Object[] params = joinPoint.getArgs();

        String requestString = _parseParamToString(params); // 请求参数

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("****api服务内部异常,请求参数:{}****", requestString, e);
        }

        // 返回参数日志记录
        logger.info("****api服务处理完成,请求:{},返回信息{}****", requestString, _objectToString(result));

        return result;
    }

    /**
     * 解析参数
     * @param params
     * @return
     */
    private String _parseParamToString(Object[] params) {
        if (ArrayUtils.isEmpty(params)) {
            return "";
        }

        return  _objectToString(params[0]);

    }

    private String _objectToString(Object object) {
        if (null == object) {
            return "";
        }

        return object.toString();
    }
}
