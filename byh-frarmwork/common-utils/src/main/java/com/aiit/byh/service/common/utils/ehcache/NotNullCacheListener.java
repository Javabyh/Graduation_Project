package com.aiit.byh.service.common.utils.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * 非空cache监听器
 * @author dsqin
 *
 */
public class NotNullCacheListener implements CacheEventListener {

	private NotNullCacheListener() {

	}

	private static class LazyHolder {
		static final NotNullCacheListener INSTANCE = new NotNullCacheListener();
	}

	/**
	 * 单例
	 * 
	 * @return
	 */
	public static NotNullCacheListener getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	
	@Override
	public void dispose() {
	}

	@Override
	public void notifyElementEvicted(Ehcache ehcache, Element element) {

	}

	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
	}

	@Override
	public void notifyElementPut(Ehcache ehcache, Element element)
			throws CacheException {
		if (null == element.getObjectValue()) {
			ehcache.remove(element.getObjectKey());
		}
	}

	@Override
	public void notifyElementRemoved(Ehcache ehcache, Element element)
			throws CacheException {

	}

	@Override
	public void notifyElementUpdated(Ehcache ehcache, Element element)
			throws CacheException {
	}

	@Override
	public void notifyRemoveAll(Ehcache ehcache) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
