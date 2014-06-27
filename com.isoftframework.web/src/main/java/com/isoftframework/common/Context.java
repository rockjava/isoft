/*
 * Created on 2005-6-20
 */
package com.isoftframework.common;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;

/**
 * 说明： 存储用户session变量的对象
 */
public class Context  {
	
	private AbstractPageInfo pageInfo;
	
	public AbstractPageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(AbstractPageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	private String backURL="";

	 

	public String getBackURL() {
		backURL=pageInfo.getCurURL();
		return backURL;
	}

	/*public void setBackURL(String backURL) {
		this.backURL = backURL;
	}*/
}