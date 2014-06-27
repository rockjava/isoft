package com.isoftframework.web.context;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ServerUrlHandler implements IContextLoaderHandler {

	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		 ServletContext context=sce.getServletContext(); 
		 String path= context.getContextPath();
		 System.out.println("contentPath==="+path);
		 //RequestContextUtils.getWebApplicationContext(request);
		 WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		    
		  //获得服务器的地址，不能直接获取本机tomcat的绝对路径，不然游览器读取不了指定的图片文件  
	        // basePath的值是http://localhost:8080/flexTest  
	       // String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"; 

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
