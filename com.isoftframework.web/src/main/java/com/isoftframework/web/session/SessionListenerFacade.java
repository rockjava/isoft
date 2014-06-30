/**
 * 
 */
package com.isoftframework.web.session;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <ul>
 * <li> <b>目的:</b> <br />
 * <p>
 * SESSION创建与删除的监听器
 * </p>
 * </li>
 * <li><b>采用的不变量：</b></li>
 * <li><b>并行策略：</b></li>
 * <li> <b>修改历史：</b><br />
 * <p>
 * 创建:Dec 19, 2007 10:47:03 AM<br />
 * 作者:liulibin@sinovatech.com
 * </p>
 * </li>
 * <li><b>已知问题：</b></li>
 * </ul>
 */

public class SessionListenerFacade implements ISessionEventListener
{
	private static Log log = LogFactory.getLog(SessionListenerFacade.class);
	private List listeners;

	public void sessionCreated(HttpSessionEvent arg0)
	{
		log.debug("Session creatting.....................");
		log
				.debug("Init session with sessionid :\t"
						+ arg0.getSession().getId());
		if (listeners != null)
		{
			Iterator it = listeners.iterator();
			while (it.hasNext())
			{
				ISessionEventListener l = (ISessionEventListener) it.next();
				l.sessionCreated(arg0);
			}
		}
		log.debug("Create Success!...............................");
	}

	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		log.debug("Session destroying.....................");
		log.debug("Destroy session with sessionid :\t"
				+ arg0.getSession().getId());

		if (listeners != null)
		{
			Iterator it = listeners.iterator();
			while (it.hasNext())
			{
				ISessionEventListener l = (ISessionEventListener) it.next();
				l.sessionDestroyed(arg0);
			}
		}
		log.debug("Destroy Success!...............................");
	}

	public List getListeners()
	{
		return listeners;
	}

	public void setListeners(List listeners)
	{
		this.listeners = listeners;
	}

}
