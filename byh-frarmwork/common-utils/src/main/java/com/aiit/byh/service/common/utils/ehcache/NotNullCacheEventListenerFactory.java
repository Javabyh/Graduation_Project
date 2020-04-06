package com.aiit.byh.service.common.utils.ehcache;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import java.util.Properties;

/**
 * 非空cache listener工厂类
 * @author dsqin
 *
 */
public class NotNullCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		return NotNullCacheListener.getInstance();
	}

}
