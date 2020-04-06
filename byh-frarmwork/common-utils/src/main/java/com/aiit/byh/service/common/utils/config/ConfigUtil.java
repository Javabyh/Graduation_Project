package com.aiit.byh.service.common.utils.config;

import com.google.common.collect.Lists;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置工具类
 *
 * @author dsqin
 *
 */
public class ConfigUtil {

	private static PropertiesConfiguration cfg = null;

	static {
		try {
			cfg = new PropertiesConfiguration();
			cfg.setEncoding("utf8");

			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			URL url = cl.getResource("application.properties");
			cfg.setURL(url);
			FileChangedReloadingStrategy reloadStrategy = new FileChangedReloadingStrategy();
			reloadStrategy.setRefreshDelay(10000);
			cfg.setReloadingStrategy(reloadStrategy);
			cfg.setListDelimiter('~');
			cfg.load();
		} catch (Exception e) {

		}
	}

	/**
	 * 获取Int配置值
	 *
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
	    return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getInt(key, defaultValue);
		}else {
            return cfg.getInt(key, defaultValue);
        }
    }

    public static int getIntNotDef(String key) {
        if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getIntNotDef(key);
		}else {
            return cfg.getInt(key);
        }
    }

	/**
	 * 获取String配置值
	 *
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
        return getString(key, StringUtils.EMPTY);
	}

    public static String getString(String key, String defaultValue) {
        if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getString(key, defaultValue);
		} else {
            return cfg.getString(key, defaultValue);
        }
    }

	/**
	 * 获取Boolean配置值
	 *
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key) {
        return getBoolean(key, true);
	}

    public static boolean getBoolean(String key, boolean defaultValue) {
        if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getBoolean(key, defaultValue);
		} else {
            return cfg.getBoolean(key, defaultValue);
        }
    }

	/**
	 * 获取Long配置值
	 *
	 * @param key
	 * @return
	 */
	public static long getLong(String key) {
        return getLong(key, 0);
	}

    public static long getLong(String key, long defaultValue) {
        if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getLong(key, defaultValue);
		} else {
            return cfg.getLong(key, defaultValue);
        }
    }

    public static double getDouble(String key, double defaultValue) {
        if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getDouble(key, defaultValue);
		} else {
            return cfg.getDouble(key, defaultValue);
        }
    }

	/**
	 * 获取string数组
	 *
	 * @param key
	 * @return
	 */
	public static String[] getStringArray(String key) {
		if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getStringArray(key);
		} else {
			return cfg.getStringArray(key);
		}
	}

	/**
	 * list列表
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
    public static List<String> getListString(String key,
                                             List<Integer> defaultValue) {
        List<String> cfgs = new ArrayList<>();
        if (ApolloConfigUtil.inited) {
            cfgs = ApolloConfigUtil.getListString(key, defaultValue);
        } else {
            cfgs = cfg.getList(key, defaultValue);
        }

        return cfgs;
    }

	/**
	 * list列表
	 *
	 * @param key
	 * @param separatorChars
	 * @return
	 */
    public static List<String> getListString(String key, String separatorChars) {
        if(ApolloConfigUtil.inited){
			return ApolloConfigUtil.getListString(key, separatorChars);
		}
        String cfgs = cfg.getString(key);
        if (StringUtils.isBlank(cfgs)) {
            return null;
        }
        String[] arr = StringUtils.split(cfgs, separatorChars);
        List<String> list = Lists.newArrayListWithExpectedSize(arr.length);
        for (String item : arr) {
            list.add(item);
        }
        return list;
    }

	/**
	 * 插入配置文件内容(弃用，若集成apollo请使用apollo-openapi)
	 * @param val
	 * @param key
	 * @return
	 */
	@Deprecated
	public static boolean saveProperties(String key, Object val) {
		cfg.setProperty(key, val);
		try {
			cfg.save();
			return true;
		} catch (ConfigurationException e) {
			return false;
		}
	}
}
