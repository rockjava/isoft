package com.isoftframework.web.context;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ResourceBundleHandler implements IContextLoaderHandler {

	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context=sce.getServletContext(); 
	    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	    
	    //InputStream in= context.getResourceAsStream("/constants/request_url.properties");
	    ResourceBundle requestUrlResourceBundle= ResourceBundle.getBundle("conf.constants.request_url");
	    
	   // System.out.println("in=="+in+"\n ru="+requestUrlResourceBundle.getString("a"));
	    context.setAttribute("requrl", requestUrlResourceBundle);
	   // Resource res=wac.getResource("classpath:/constants/request_url.properties");
	   // System.out.println("rec=="+res.getDescription());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
