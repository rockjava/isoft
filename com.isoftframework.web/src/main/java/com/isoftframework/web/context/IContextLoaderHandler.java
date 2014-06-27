package com.isoftframework.web.context;

import javax.servlet.ServletContextEvent;

public interface IContextLoaderHandler {
	public void contextInitialized(ServletContextEvent sce);
	public void contextDestroyed(ServletContextEvent sce);
}
