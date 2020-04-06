package com.aiit.byh.service.common.utils.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.google.common.collect.Lists;
import com.aiit.byh.service.common.utils.json.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * apollo配置工具类
 *
 * @author ynxu3
 *
 */
@Component
public class ApolloConfigUtil {

	private static Logger logger = LoggerFactory.getLogger(ApolloConfigUtil.class);

	/**
	 * 初始化状态
	 */
	public static boolean inited;

	static {
		init();
	}

	@PostConstruct
	private void postConstruct(){
		init();
	}

	/**
	 * 初始化方法, 加载优先级 按命名空间顺序
	 */
	private static void init(){
		if(CollectionUtils.isNotEmpty(ConfigService.getConfigs()) && !inited){
			logger.info("****初始化apollo命名空间****");
			inited = true;
			for(Config config:ConfigService.getConfigs()){
				config.addChangeListener(new ConfigChangeListener() {
					@Override
					public void onChange(ConfigChangeEvent configChangeEvent) {
						logger.info("****apollo配置发生变化:【{}】****", JsonUtil.genJsonString(configChangeEvent));
					}
				});
			}
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
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getIntProperty(key, defaultValue);
		}
		return defaultValue;
    }

    public static int getIntNotDef(String key) {
		return getInt(key, 0);
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
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getProperty(key, defaultValue);
		}
		return defaultValue;
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
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getBooleanProperty(key, defaultValue);
		}
		return defaultValue;
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
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getLongProperty(key, defaultValue);
		}
		return defaultValue;
    }

    public static double getDouble(String key, double defaultValue) {
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getDoubleProperty(key, defaultValue);
		}
		return defaultValue;
    }

	public static float getFloat(String key, float defaultValue) {
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getFloatProperty(key, defaultValue);
		}
		return defaultValue;
	}

	public static short getShort(String key, short defaultValue) {
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getShortProperty(key, defaultValue);
		}
		return defaultValue;
	}

	/**
	 * 获取string数组
	 *
	 * @param key
	 * @return
	 */
	public static String[] getStringArray(String key) {
		Config config = ConfigService.getConfigByKey(key);
		if(config != null){
			return config.getArrayProperty(key, ",", new String[]{});
		}
		return null;
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
		String[] array = getStringArray(key);
		if(ArrayUtils.isNotEmpty(array)){
			return Arrays.asList(array);
		}
		List<String> defaultList = Lists.newArrayListWithExpectedSize(defaultValue.size());
		for (Integer i : defaultValue) {
			defaultList.add(String.valueOf(i));
		}
		return defaultList;
	}

	/**
	 * list列表
	 *
	 * @param key
	 * @param separatorChars
	 * @return
	 */

    public static List<String> getListString(String key, String separatorChars) {

        String cfgs = getString(key);
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

}
