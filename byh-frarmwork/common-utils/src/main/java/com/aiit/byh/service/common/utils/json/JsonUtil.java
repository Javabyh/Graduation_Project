package com.aiit.byh.service.common.utils.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * json帮助类
 * 
 * @author dsqin
 * 
 */
public class JsonUtil {

	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static Gson gson = new Gson();

	private static Gson gsonDateFormatDisableHtml =  new GsonBuilder().disableHtmlEscaping().setDateFormat(DATE_FORMAT).create();

	private static Gson gsonUpperDateFormatDisableHtml =  new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setDateFormat(DATE_FORMAT).create();

	/**
	 * 生成json对象
	 * 
	 * @param maps
	 * @return
	 */
	public static <T> String mapToJson(Map<String, T> map) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(map, new TypeToken<Map<String, T>>() {
		}.getType());
		return jsonStr;
	}
	
	/**
	 * object -> json string
	 * 首字母大写
	 * @return
	 */
	public static String genJsonString(Object obj, boolean firstUpper) {
		try {
			if (firstUpper) {
				Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setDateFormat(DATE_FORMAT).create();
				return gson.toJson(obj);
			}
			return gson.toJson(obj);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * object -> json string
	 * 首字母大写
	 * DisableHtml
	 * DateFormat = "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String genJsonStringDateFormatDisableHtml(Object obj, boolean firstUpper) {
		try {
			if (firstUpper) {
				return gsonUpperDateFormatDisableHtml.toJson(obj);
			}
			return gsonDateFormatDisableHtml.toJson(obj);
		} catch (Exception ex) {
			return "";
		}
	}
	
	/**
	 * object -> json string
	 * 忽略默写字段
	 * @param obj
	 * @param excludeExpose
	 * @return
	 */
	public static String genJsonStringExpose(Object obj, boolean excludeExpose) {
		try {
			if (excludeExpose) {
				Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setDateFormat(DATE_FORMAT).excludeFieldsWithoutExposeAnnotation().create();
				return gson.toJson(obj);
			}
			return gson.toJson(obj);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String genJsonStringExpose(Object obj) {
		try {
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				return gson.toJson(obj);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * object -> json string
	 * @return
	 */
	public static String genJsonStringSetDateFormat(Object obj) {
		try {
			Gson gson =new GsonBuilder()  
			  .setDateFormat(DATE_FORMAT)  
			  .create();  
			return gson.toJson(obj);
		} catch (Exception ex) {
			return "";
		}
	}
	
	public static String genJsonString(Object obj){
		try {
			return gson.toJson(obj);
		} catch (Exception ex) {
			return "";
		}
	}
	
	public static void main(String[] args) {
		
	}
	
	public static <T> T parseBean(String str, Class<T> clazz) {
		try {
			return new Gson().fromJson(str, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T parseBeanExcept(String str, Class<T> clazz) {
		return new Gson().fromJson(str, clazz);
	}
	
	public static <T> T parseBean(String str, Class<T> clazz, boolean firstUpper) {
		try {
			if (firstUpper) {
				Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
				return gson.fromJson(str, clazz);
			} else {
				return new Gson().fromJson(str, clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T parseBean(String str, Type type) {
		try {
			return gson.fromJson(str, type);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 解析成bean
	 * @param str
	 * @param type
	 * @param firstUpper 首字母大写
	 * @param <T>
	 * @return
	 */
	public static <T> T parseBean(String str, Type type, boolean firstUpper, boolean convertDatetime) {
		try {
			if (firstUpper && convertDatetime) {
				Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
				return gson.fromJson(str, type);
			}
			else if (firstUpper) {
				Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
				return gson.fromJson(str, type);
			}
			else if (convertDatetime) {
				Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
				return gson.fromJson(str, type);
			}else {
				return gson.fromJson(str, type);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解析string
	 * 
	 * @param str
	 * @return
	 */
	public static JsonObject parseString(String str) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(str, JsonObject.class);
		} catch (Exception ex) {
			return new JsonObject();
		}
	}

	/**
	 * 合并json
	 * 
	 * @param isCover
	 *            属性相同时,是否覆盖
	 * @param objs
	 * @return
	 */
	public static JsonObject merge(JsonObject... objs) {
		int size = objs.length;
		if (size <= 0) {
			return null;
		}
		JsonObject obj1 = objs[0];

		for (int i = 0; i < size; i++) {
			JsonObject obj = objs[i];
			Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
			for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet
					.iterator(); iter.hasNext();) {
				Map.Entry<String, JsonElement> entry = iter.next();
				String key = entry.getKey();
				if (!obj1.has(key)) {
					JsonElement value = entry.getValue();
					obj1.add(key, value);
				}
			}
		}
		return obj1;
	}

	/**
	 * 获取json对象
	 * 
	 * @param json
	 * @param member
	 * @return
	 */
	public static JsonObject getJsonObject(JsonObject json, String member) {
		try {
			JsonElement em = json.get(member);
			return em.getAsJsonObject();
		} catch (Exception ex) {
			return new JsonObject();
		}
	}

	/**
	 * 获取json数组对象
	 * 
	 * @param json
	 * @param member
	 * @return
	 */
	public static JsonArray getJsonArray(JsonObject json, String member) {
		try {
			JsonElement em = json.get(member);

			return em.getAsJsonArray();
		} catch (Exception ex) {
			return new JsonArray();
		}
	}

	/**
	 * 获取string对象
	 * 
	 * @param json
	 * @param member
	 * @return
	 */
	public static String getJsonString(JsonObject json, String member) {
		try {
			JsonElement em = json.get(member);

			if (em.isJsonArray()) {
				return em.toString();
			}
			return em.getAsString();
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 获取int对象
	 * 
	 * @param json
	 * @param member
	 * @return
	 */
	public static int getJsonInt(JsonObject json, String member) {
		try {
			JsonElement em = json.get(member);

			return em.getAsInt();
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * 获取boolean值
	 * 
	 * @param json
	 * @param member
	 * @return
	 */
	public static boolean getJsonBoolean(JsonObject json, String member) {
		try {
			JsonElement em = json.get(member);

			return em.getAsBoolean();
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * 过滤特殊字符
	 * @param s
	 * @return
	 */
	public static String filter(String s) { 
	    StringBuilder sb = new StringBuilder(s.length()+20); 
	    sb.append('\"'); 
	    for (int i=0; i<s.length(); i++) { 
	        char c = s.charAt(i); 
	        switch (c) { 
	        case '\"': 
	            sb.append("\\\""); 
	            break; 
	        case '\\': 
	            sb.append("\\\\"); 
	            break; 
	        case '/': 
	            sb.append("\\/"); 
	            break; 
	        case '\b': 
	            sb.append("\\b"); 
	            break; 
	        case '\f': 
	            sb.append("\\f"); 
	            break; 
	        case '\n': 
	            sb.append("\\n"); 
	            break; 
	        case '\r': 
	            sb.append("\\r"); 
	            break; 
	        case '\t': 
	            sb.append("\\t"); 
	            break; 
	        default: 
	            sb.append(c); 
	        } 
	    } 
	    sb.append('\"'); 
	    return sb.toString(); 
	 }
	
	/**
	 * 获取数组
	 * （没有key，直接数组的字符串）
	 * @param str
	 * @return
	 */
	public static <T> List<T> getJsonArray(String str) {
		try {
			return gson.fromJson(str, new TypeToken<List<T>>(){}.getType());
		} catch (Exception ex) {
			return null;
		}
	}

}
