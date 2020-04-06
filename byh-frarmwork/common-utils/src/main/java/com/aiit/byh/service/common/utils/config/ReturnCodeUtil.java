package com.aiit.byh.service.common.utils.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;


/**
 * 返回码工具类
 *
 * @author dsqin
 */
public class ReturnCodeUtil {
    private static PropertiesConfiguration cfg = null;

    static {
        try {
            cfg = new PropertiesConfiguration();
            cfg.setEncoding("utf8");

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL url = cl.getResource("returncode.properties");
            cfg.setURL(url);
            FileChangedReloadingStrategy reloadStrategy = new FileChangedReloadingStrategy();
            reloadStrategy.setRefreshDelay(10000);
            cfg.setReloadingStrategy(reloadStrategy);
            cfg.load();
        } catch (Exception e) {

        }
    }

    /**
     * 获取返回描述
     *
     * @param returnCode
     * @return
     */
    public static String getReturnDescription(String returnCode) {
        return cfg.getString(returnCode, "网络超时，请稍后再试！");
    }

    /**
     * 酷音平台返回码映射
     *
     * @param method
     * @param kuyinPlatReturnCode
     * @return
     */
    public static String getServiceReturnCodeFromKuyinPlat(String method, String kuyinPlatReturnCode) {
        String prefix = "kuyinplat2service.";
        return getServiceReturnCode(prefix, method, kuyinPlatReturnCode);
    }


    /**
     * 酷音平台返回码映射
     *
     * @param method
     * @param kuyinPlatReturnCode
     * @return
     */
    public static String getServiceReturnCodeFromKuyinPlatOverseas(String method, String kuyinPlatReturnCode) {
        String prefix = "kuyinplatoverseas2service.";
        return getServiceReturnCode(prefix, method, kuyinPlatReturnCode);
    }

    /**
     * 返回码映射
     *
     * @param prefix
     * @param method
     * @param platReturnCode
     * @return
     */
    private static String getServiceReturnCode(String prefix, String method, String platReturnCode) {
        String key = null;
        boolean common = StringUtils.isBlank(method);
        if (common) {
            key = StringUtils.join(prefix, platReturnCode);
        } else {
            key = StringUtils.join(prefix, method, ".", platReturnCode);
        }
        String resultCode = cfg.getString(key);
        if (!common
                && StringUtils.isBlank(resultCode)) {
            key = StringUtils.join(prefix, platReturnCode);
            resultCode = cfg.getString(key);
        }

        return resultCode;
    }

}
