package com.isoftframework.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	static ExecutorService threadPool=null;
	
	public static  ExecutorService getThreadPool(){
		if(threadPool==null){
			//threadPool = Executors.newFixedThreadPool(10);
			threadPool=Executors.newCachedThreadPool();  
		}
		return threadPool;
	}
	
	public static void shutdown(){
		if(threadPool!=null)
			threadPool.shutdown();
	}
	 
}
