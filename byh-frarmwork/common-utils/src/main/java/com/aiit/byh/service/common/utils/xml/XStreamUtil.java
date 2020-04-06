package com.aiit.byh.service.common.utils.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import java.io.Writer;

/**
 * Created by jhyuan on 2016/11/29.
 */
public class XStreamUtil {
    private static XStream xstream;

    private static XStream commonXstream;

    public static XStream buildNormalXstream(){
        if (commonXstream == null) {
            commonXstream = new XStream(new DomDriver("UTF-8", new NoNameCoder()));
        }
        return commonXstream;
    }

    /**
     * @MethodName: buildXstream
     * @Description: 构造一个XStream解析器
     * @date: 2016年1月7日 下午3:25:52
     * @return: XStream
     * @author: ppli@iflytek.com
     */
    public static XStream buildXstream() {
        if (xstream == null) {
            xstream = new XStream(new DomDriver("UTF-8", new NoNameCoder()) {

                public HierarchicalStreamWriter createWriter(Writer writer) {
                    return new PrettyPrintWriter(writer, getNameCoder()) {
                        boolean cdata = false;

                        @Override
                        public void startNode(String name,
                                              @SuppressWarnings("rawtypes") Class clazz) {

                            if (Number.class.isAssignableFrom(clazz)) {//数字类型
                                cdata = false;
                            } else {
                                cdata = true;
                            }
                            super.startNode(name);
                        }

                        @Override
                        protected void writeText(QuickWriter writer, String text) {
                            if (cdata) {
                                writer.write("<![CDATA[");
                                writer.write(text);
                                writer.write("]]>");
                            } else {
                                writer.write(text);
                            }
                        }
                    };
                }
            });
        }
        return xstream;
    }
}
