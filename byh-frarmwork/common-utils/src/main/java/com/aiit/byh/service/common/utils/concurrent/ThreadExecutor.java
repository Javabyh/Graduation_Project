package com.aiit.byh.service.common.utils.concurrent;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步线程池executor
 * </br>
 * @see ThreadPoolExecutor
 * 
 * @author dsqin
 *
 */
public class ThreadExecutor {

	/**
	 * 线程池最小线程数
	 */
	private static final String CORE_POOL_SIZE = "mq.thread.pool.corepoolsize";
	
	/**
	 * 线程池最大线程数
	 */
	private static final String MAX_POOL_SIZE = "mq.thread.pool.maxpoolsize";
	
	/**
	 * 空闲线程最大存活时间
	 */
	private static final String KEEPALIVETIME = "mq.thread.pool.keepalivetime";
	
	/**
	 * 线程池队列长度
	 */
	private static final String QUEUE_SIZE = "mq.thread.pool.blockingqueuesize";
	
	/**
	 * 异步线程池对象
	 */
	public final static ListeningExecutorService executor = MoreExecutors
			.listeningDecorator(new ThreadPoolExecutor(0, 200,
					60, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(1000),
					new ThreadPoolExecutor.DiscardOldestPolicy()));
	
	/**
	 * 向线程池中提交任务
	 * @param task
	 */
	public static void submit(Callable<?> task) {
		executor.submit(task);
	}
}
