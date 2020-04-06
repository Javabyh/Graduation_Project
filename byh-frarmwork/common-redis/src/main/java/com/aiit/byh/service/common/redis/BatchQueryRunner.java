package com.aiit.byh.service.common.redis;

import com.aiit.byh.service.common.utils.redis.UserDefineHandler;
import com.aiit.byh.service.common.utils.redis.Valid;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 批量查询redis工具类
 *
 * @author dsqin
 */
public class BatchQueryRunner {

    /**
     * 批量查询，返回entity
     *
     * @param batchService
     * @param redisType    redis类型
     * @param list         返回的list
     * @param clz          list中对象的类型
     * @param ids          批量查询的编号
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void run(IRedisBatchProcess batchService, List list,
                           Class<?> clz) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        List<Object> objs = RedisProcessor.batchQuery(batchService,
                RedisUtil.getReadRedisFlag());
        if (objs == null || objs.size() == 0) {
            return;
        }
        for (Object obj : objs) {
            if (obj instanceof Map) {
                if (((Map) obj).isEmpty()) {
                    continue;
                }
                Object target = clz.newInstance();
                //暂不确定哪些使用这个方法，如果有同样属性，类型不是string，盲目使用cglib转会出错，需要循序渐进，逐步转换成cglib
                Map<String, String> map = (Map<String, String>) obj;
                BeanUtils.populate(target, map);
                if (target instanceof Valid) {
                    if (!((Valid) target).valid()) {
                        continue;
                    }
                }
                if (target instanceof UserDefineHandler) {
                    ((UserDefineHandler) target).handle();
                }
                list.add(target);
            }
        }
    }

    /**
     * 批量查询，返回map
     *
     * @param batchService redisType    redis类型
     *                     ids          批量查询的编号
     * @param list         返回的list，redis中查出的数据，未转化成entity
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Object> run(IRedisBatchProcess batchService) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        return RedisProcessor.batchQuery(batchService,
                RedisUtil.getReadRedisFlag());
    }

    /**
     * 批量查询(支持对象拷贝)
     *
     * @param batchService
     * @param redisType    redis类型
     * @param list         返回的list
     * @param clz          list中对象的类型
     * @param overrideList
     * @param ids          批量查询的编号
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void run(IRedisBatchProcess batchService, List list,
                           Class<?> clz, boolean overrideList) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        List<Object> objs = RedisProcessor.batchQuery(batchService,
                RedisUtil.readFlag);
        if (objs == null || objs.size() == 0) {
            return;
        }
        int size = objs.size();
        if (overrideList && list.size() != size) {
            return;
        }
        for (int i = 0; i < size; i++) {
            Object obj = objs.get(i);
            if (obj instanceof Map) {
                Object target = null;
                if (overrideList) {
                    target = list.get(i);
                } else {
                    target = clz.newInstance();
                }
                Map<String, String> map = (Map<String, String>) obj;
                BeanUtils.populate(target, map);
                if (target instanceof Valid) {
                    if (!((Valid) target).valid()) {
                        continue;
                    }
                }
                if (overrideList) {
                    list.set(i, target);
                } else {
                    list.add(target);
                }
            }
        }
    }

}
