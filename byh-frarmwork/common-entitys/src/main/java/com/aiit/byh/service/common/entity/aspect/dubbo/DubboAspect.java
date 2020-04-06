package com.aiit.byh.service.common.entity.aspect.dubbo;

//import com.aiit.byh.com.iflytek.kuyin.service.common.redis.service.com.iflytek.kuyin.service.common.redis.common.client.log.com.iflytek.kuyin.service.common.redis.entity.LogParam;
//import com.aiit.byh.com.iflytek.kuyin.service.common.redis.service.com.iflytek.kuyin.service.common.redis.common.client.log.logger.Driplogger;
import com.aiit.byh.service.common.entity.dubbo.base.DubboBaseReq;
import com.aiit.byh.service.common.entity.dubbo.base.DubboBaseResp;
import com.aiit.byh.service.common.entity.dubbo.base.DubboCommonRetCode;
import com.aiit.byh.service.common.utils.validator.jsr303.ValidUtils;
import com.aiit.byh.service.common.utils.validator.jsr303.ValidateMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 *
 * dubbo服务切面
 * @author bbhan3
 * @date 2020/1/6 14:21
 */
public class DubboAspect {

    private static Logger logger = LoggerFactory.getLogger(DubboAspect.class);

    public Object doAround(ProceedingJoinPoint joinPoint) {

        if (null == joinPoint) {
            return null;
        }

        // 返回类型必须为继承自base response
        Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();

        if (!DubboBaseResp.class.isAssignableFrom(returnType)) {
            logger.warn("****dubbo服务参数验证失败,返回参数需继承DubboBaseResp****");
            return _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "返回参数需继承DubboBaseResp");
        }

        // 参数限定为一个bean,且必须继承自base request
        Object[] params = joinPoint.getArgs();

        if (null == params ||
                (null != params && params.length != 1)) {
            logger.warn("****dubbo服务参数验证失败,请求参数为空或者参数个数非1个****");
            return _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "请求参数为空或者参数个数非1个");
        }

        Object param = params[0];
        if (null == param || !(param instanceof DubboBaseReq)) {
            logger.warn("****dubbo服务参数验证失败,请求参数为空或者未继承");
            return _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "请求参数为空或者未继承");
        }

        // 请求参数日志记录
        logger.info("****dubbo请求:{}****", param);

        String traceId = ((DubboBaseReq) param).getTraceId();

        // 进行参数valid
        List<ValidateMessage> validateMessages = ValidUtils.valid(param);

        if (null != validateMessages && validateMessages.size() > 0) {
            logger.warn("****dubbo参数验证失败,traceId:{},失败信息:{}****", traceId, validateMessages.get(0));
            return _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "参数验证失败");
        }

        // 执行方法调用
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("****dubbo内部异常,tracId:{}****", traceId, e);
            return _buildResp(returnType, DubboCommonRetCode.INNER_ERROR, "服务内部异常");
        }

        // 返回参数日志记录
        logger.info("****dubbo服务处理完成,traceId:{},返回信息:{}****", traceId, result);

        return result;
    }

    public Object doAroundDrip(ProceedingJoinPoint joinPoint) {

        Object resp = null;
        Object req = null;
        String methodName = joinPoint.getSignature().getName();
        String traceId = null;
        if (null == joinPoint) {
            return null;
        }
        long beginTime = System.currentTimeMillis();
        // 返回类型必须为继承自base response
        Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
        try {
            if (!DubboBaseResp.class.isAssignableFrom(returnType)) {
                resp = _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "返回参数需继承DubboBaseResp");
                return resp;
            }

            // 参数限定为一个bean,且必须继承自base request
            Object[] params = joinPoint.getArgs();

            if (null == params ||
                    (null != params && params.length != 1)) {
                resp = _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "请求参数为空或者参数个数非1个");
                return resp;
            }

            req = params[0];
            if (null == req || !(req instanceof DubboBaseReq)) {
                resp = _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "请求参数为空或者未继承");
                return resp;
            }
            traceId = ((DubboBaseReq) req).getTraceId();

            // 进行参数valid
            List<ValidateMessage> validateMessages = ValidUtils.valid(req);

            if (null != validateMessages && validateMessages.size() > 0) {

                resp = _buildResp(returnType, DubboCommonRetCode.IVALID_PARAM, "参数验证失败");
                return resp;
            }
            // 执行方法调用
            try {
                resp = joinPoint.proceed();
            } catch (Throwable e) {
                return _buildResp(returnType, DubboCommonRetCode.INNER_ERROR, "服务内部异常");
            }
            return resp;

        }catch (Throwable e) {
            return _buildResp(returnType, DubboCommonRetCode.INNER_ERROR, "服务内部异常");
        }finally {
            long endTime = System.currentTimeMillis();
        }

    }

    /**
     * 获取返回类
     * @param clz
     * @param retCode
     * @param retDesc
     * @return
     */
    private static Object _buildResp(Class clz, String retCode, String retDesc) {
        Constructor constructor = null;
        // 先取有参构造函数,取返回码和返回描述构造函数
        try {
            constructor = clz.getConstructor(String.class, String.class);
            return constructor.newInstance(retCode, retDesc);
        } catch (Exception e) {

            try {
                // 如果无,取默认无参构造函数
                constructor = clz.getConstructor(null);
                return constructor.newInstance(null);
            } catch (Exception e1) {
                return null;
            }
        }
    }

}
