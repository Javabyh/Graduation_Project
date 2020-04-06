package com.aiit.byh.service.common.entity.annotation;

//import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * json序列化时属性为null或者""时不返回码
 * @author bbhan3
 * @date 2020/1/6 14:21
 */
@JsonSerialize(include=Inclusion.NON_EMPTY)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public interface JsonFieldNotEmpty {

}
