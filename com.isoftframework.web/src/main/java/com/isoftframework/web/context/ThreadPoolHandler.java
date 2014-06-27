package com.isoftframework.web.context;

import javax.servlet.ServletContextEvent;

import com.isoftframework.common.thread.ThreadPool;

public class ThreadPoolHandler implements IContextLoaderHandler {

	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ThreadPool.shutdown();
	}

}
