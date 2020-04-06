package com.aiit.byh.service.common.utils.config;

import com.github.diamond.client.PropertiesConfiguration;
import com.github.diamond.client.PropertiesConfigurationFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置工具类
 * 
 * @author jhyuan
 * 
 */
public class DiamondConfigUtil {

	private static Logger logger = LoggerFactory.getLogger(DiamondConfigUtil.class);

	private static PropertiesConfiguration cfg = null;

	static {
		try {
			cfg = PropertiesConfigurationFactoryBean.getPropertiesConfiguration();
		} catch (Exception e) {
			logger.warn("****配置中心加载失败****");
		}
	}

	public static boolean getBoolean(String key) {
		return getBoolean(key, true);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getBoolean(key, defaultValue);
		}
		if (null == cfg) {
			return defaultValue;
		}
		return cfg.getBoolean(key, defaultValue);
	}

	public static double getDouble(String key) {
		return getDouble(key, 0);
	}

	public static double getDouble(String key, double defaultValue) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getDouble(key, defaultValue);
		}
		if (null == cfg) {
			return defaultValue;
		}
		return cfg.getDouble(key, defaultValue);
	}

	public static float getFloat(String key) {
		return getFloat(key, 0);
	}

	public static float getFloat(String key, float defaultValue) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getFloat(key, defaultValue);
		}
		if (null == cfg) {
			return defaultValue;
		}
		return cfg.getFloat(key, defaultValue);
	}

	public static int getInt(String key) {
		return getInt(key, 0);
	}

	public static int getInt(String key, int defaultValue) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getInt(key, defaultValue);
		}
		if (null == cfg) {
			return defaultValue;
		}
		return cfg.getInt(key, defaultValue);
	}

	public static long getLong(String key) {
		return getLong(key, 0);
	}

	public static long getLong(String key, long defaultValue) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getLong(key, defaultValue);
		}
		if (null == cfg) {
			return defaultValue;
		}
		return cfg.getLong(key, defaultValue);
	}

	public static short getShort(String key, short defaultValue) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getShort(key, defaultValue);
		}
		if (null == cfg) {
			return defaultValue;
		}
		return cfg.getShort(key, defaultValue);
	}

	public static String getString(String key) {
		return getString(key, "");
	}

	public static String getString(String key, String defaultValue) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getString(key, defaultValue);
		}
		if (null == cfg) {
			return defaultValue;
		}
		return cfg.getString(key, defaultValue);
	}
}
