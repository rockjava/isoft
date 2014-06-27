/**
 * 
 */
package com.isoftframework.web.session;

import javax.servlet.http.HttpSessionEvent;



public class SessionLiTest implements ISessionEventListener
{

	public void sessionCreated(HttpSessionEvent arg0)
	{
		System.out.println(arg0.getSession().getId() + ".....create....");
	}

	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		System.out.println(arg0.getSession().getId() + ".....destroy....");

	}

}
