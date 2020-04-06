package com.aiit.byh.service.common.utils.bean;

import com.google.common.collect.Lists;
import com.aiit.byh.service.common.utils.date.DateUtils;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * bean 相关utils
 *
 * @author dsqin
 * @create 2016年7月21日
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    /**
     * bean拷贝
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    public static void copyAttributes(Object source, Object target) {
        try {
            BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            copier.copy(source, target, null);

        } catch (Exception e) {
        }
    }

    /**
     * map to bean
     *
     * @param bean       target bean
     * @param properties 属性的map
     */
    public static void populate(Object bean, Map<String, ? extends Object> properties) {

        if (null == bean && null == properties && properties.isEmpty()) {
            return;
        }

        try {
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.putAll(properties);
        } catch (Exception ex) {

        }

    }

    /**
     * 获取bean按照属性按首字母排序的计算值
     * 示例: bean
     * ----------------------------
     * fieldname | value
     * ----------------------------
     * field1  | value1
     * ----------------------------
     * a      | value2
     * ----------------------------
     * 返回值: a=value2&field2=value1
     *
     * @param bean bean
     * @return String
     */
    public static String getAttributeString(Object bean) {

        if (null == bean) {
            return "";
        }

        BeanMap beanMap = BeanMap.create(bean);

        Set<String> keys = beanMap.keySet();

        List<String> fieldAnnotations = Lists.newArrayList();

        // 循环所有的field
        for (String key : keys) {
            Object fieldValue = beanMap.get(key);

            if (StringUtils.isNotBlank(key) && null != fieldValue) {
                if (fieldValue instanceof String) {
                    if (StringUtils.isNotBlank((String) fieldValue)) {
                        fieldAnnotations.add(key);
                    }
                } else {
                    fieldAnnotations.add(key);
                }
            }
        }

        // 排序
        Collections.sort(fieldAnnotations);

        String attributeString = "";
        int attributeSize = fieldAnnotations.size();

        for (int i = 0; i < attributeSize; i++) {
            String field = fieldAnnotations.get(i);
            StringBuilder stringBuilder = new StringBuilder(attributeString);
            stringBuilder.append(field);
            stringBuilder.append("=");
            stringBuilder.append(beanMap.get(field));
            if (i != attributeSize - 1) {
                stringBuilder.append("&");
            }
            attributeString = stringBuilder.toString();
        }

        return attributeString;
    }


    /**
     * 自动匹配参数赋值到实体bean中
     *
     * @param bean
     * @param request
     */
    public static void populate(Object bean, HttpServletRequest request) {
        Class<? extends Object> clazz = bean.getClass();
        Method ms[] = clazz.getDeclaredMethods();
        String mname;
        String field;
        String fieldType;
        String value;
        for (Method m : ms) {
            mname = m.getName();
            if (!mname.startsWith("set")
                    || ArrayUtils.isEmpty(m.getParameterTypes())) {
                continue;
            }
            try {
                field = mname.toLowerCase().charAt(3) + mname.substring(4, mname.length());
                value = request.getParameter(field);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                fieldType = m.getParameterTypes()[0].getName();
                //以下可以确认value为String类型
                if (String.class.getName().equals(fieldType)) {
                    m.invoke(bean, (String) value);
                } else if (Integer.class.getName().equals(fieldType) && NumberUtils.isDigits((String) value)) {
                    m.invoke(bean, Integer.valueOf((String) value));
                } else if (Short.class.getName().equals(fieldType) && NumberUtils.isDigits((String) value)) {
                    m.invoke(bean, Short.valueOf((String) value));
                } else if (Float.class.getName().equals(fieldType) && NumberUtils.isNumber((String) value)) {
                    m.invoke(bean, Float.valueOf((String) value));
                } else if (Double.class.getName().equals(fieldType) && NumberUtils.isNumber((String) value)) {
                    m.invoke(bean, Double.valueOf((String) value));
                } else if (Date.class.getName().equals(fieldType)) {
                    m.invoke(bean, DateUtils.parseDate((String) value));
                } else {
                    m.invoke(bean, value);
                }
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 获取HttpServletRequest中按照属性按首字母排序的计算值
     * 示例: HttpServletRequest中属性
     * ----------------------------
     * fieldname | value
     * ----------------------------
     * field1  | value1
     * ----------------------------
     * a      | value2
     * ----------------------------
     * 返回值: value2value1
     *
     * @param HttpServletRequest
     * @return String
     */
    public static String getValueStringByKeySort(HttpServletRequest request) {

        if (null == request) {
            return "";
        }

        List<String> names = getParameterNames(request);
        // 排序
        Collections.sort(names);

        StringBuilder valueString = new StringBuilder();
        for (String name : names) {
            valueString.append(StringUtils.join(request
                    .getParameterValues(name)));
        }

        return valueString.toString();
    }

    /**
     * 获取Map中按照属性按首字母排序的计算值
     * 示例: Map中属性
     * ----------------------------
     * fieldname | value
     * ----------------------------
     * field1  | value1
     * ----------------------------
     * a      | value2
     * ----------------------------
     * 返回值: value2value1
     *
     * @param map
     * @return String
     */
    public static String getValueStringByKeySort(Map<String, String> map) {

        if (null == map || 0 >= map.size()) {
            return "";
        }

        List<String> names = new ArrayList<String>();
        names.addAll(map.keySet());
        // 排序
        Collections.sort(names);

        StringBuilder valueString = new StringBuilder();
        for (String name : names) {
            valueString.append(map.get(name));
        }
        return valueString.toString();
    }

    /**
     * 取入参的map
     *
     * @param request
     * @return
     */
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    /**
     * 取入参的names
     *
     * @param request
     * @return
     */
    public static List<String> getParameterNames(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        List<String> names = new ArrayList<String>();
        Enumeration<String> paraNames = request.getParameterNames();
        for (Enumeration e = paraNames; e.hasMoreElements(); ) {
            names.add(e.nextElement().toString());
        }
        return names;
    }

    /**
     * javaBean -> map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }

}
