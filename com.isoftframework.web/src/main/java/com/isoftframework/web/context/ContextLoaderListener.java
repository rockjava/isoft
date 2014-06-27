package com.isoftframework.web.context;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class ContextLoaderListener implements ServletContextListener{

	List<IContextLoaderHandler> handlers=new ArrayList<IContextLoaderHandler>();
	/**
	 * ：当Servlet 容器启动Web 应用时调用该方法。
	 * 在调用完该方法之后，容器再对Filter 初始化，并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		registHandlers(sce);
		
		if(handlers!=null && handlers.size()>0){
			for(IContextLoaderHandler handler:handlers){
				handler.contextInitialized(sce);
			}
		}
		
		
	}
	//注册监听
	private final void registHandlers(ServletContextEvent sce){
		handlers.add(new ResourceBundleHandler());
		handlers.add(new ServerUrlHandler());
		handlers.add(new ThreadPoolHandler());
		
		
		
	}

	/**
	 * 当Servlet 容器终止Web 应用时调用该方法。在调用该方法之前，容器会先销毁所有的Servlet 和Filter 过滤器。
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(handlers!=null && handlers.size()>0){
			for(IContextLoaderHandler handler:handlers){
				handler.contextDestroyed(sce);
			}
		}
		
	}

}
