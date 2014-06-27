/**
 * 
 */
package com.isoftframework.web.session;

import javax.servlet.http.HttpSessionEvent;

/**
 * <ul>
 * <li> <b>目的:</b> <br />
 * <p>
 * session时间监听器
 * </p>
 * </li>
 * <li><b>采用的不变量：</b></li>
 * <li><b>并行策略：</b></li>
 * <li> <b>修改历史：</b><br />
 * <p>
 * 创建:Dec 12, 2007 12:42:51 PM<br />
 * 作者:liulibin@sinovatech.com
 * </p>
 * </li>
 * <li><b>已知问题：</b></li>
 * </ul>
 */

public interface ISessionEventListener
{
	public void sessionCreated(HttpSessionEvent arg0);

	public void sessionDestroyed(HttpSessionEvent arg0);
}
