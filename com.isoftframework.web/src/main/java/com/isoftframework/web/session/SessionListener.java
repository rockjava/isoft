package com.isoftframework.web.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class SessionListener implements HttpSessionListener
{
	public void sessionCreated(HttpSessionEvent arg0)
	{
		
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(arg0.getSession().getServletContext());
		ISessionEventListener s = (ISessionEventListener) wac.getBean("sessionListenerFacade");
		s.sessionCreated(arg0);
	}

	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(arg0.getSession()
						.getServletContext());
		ISessionEventListener s = (ISessionEventListener) wac.getBean("sessionListenerFacade");
		s.sessionDestroyed(arg0);
	}

}
