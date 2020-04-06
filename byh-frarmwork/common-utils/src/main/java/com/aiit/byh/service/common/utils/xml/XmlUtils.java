package com.aiit.byh.service.common.utils.xml;

import com.aiit.byh.service.common.utils.bean.BeanUtils;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jhyuan on 2016/11/29.
 */
public class XmlUtils {

    public static Map<String, String> xml2Map(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        Element root = null;
        try {
            doc = DocumentHelper.parseText(xml);// 将字符串转换为xml格式的文档
            root = doc.getRootElement();// 获取根节点
            List<Element> elementList = root.elements();
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static <T> String bean2Xml(T obj, String nodeName) {
        XStream xs = XStreamUtil.buildXstream();
        xs.alias(nodeName, obj.getClass());
        xs.processAnnotations(obj.getClass());
        String content = xs.toXML(obj);
        return content;
    }

    public static void xml2Bean(String xml, Object bean) throws InvocationTargetException, IllegalAccessException {
        Map<String, String> maps = xml2Map(xml);
        BeanUtils.populate(bean, maps);
    }
}
