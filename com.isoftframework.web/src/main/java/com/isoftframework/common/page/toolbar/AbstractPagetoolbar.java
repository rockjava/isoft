package com.isoftframework.common.page.toolbar;

/*
 * 创建日期 2005-1-24
 * 
 * 翻页处理
 *  
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;

abstract public class AbstractPagetoolbar {
	///framework/jsp/twater/product/productManage.do?page=1&pageSize=2 
	Logger log=Logger.getLogger(this.getClass());
	
	static Lock lock = new ReentrantLock();// 锁  
	 
	private static AbstractPagetoolbar pagetoolbar=null;
	
	private static CommonPagetoolbar commonPagetoolbar=null;
	
	public static AbstractPagetoolbar getPagetoolbar(){
		if(pagetoolbar==null){
			//只有第一次才彻底执行这里的代码   
			lock.lock();
			try{   
	          //再检查一次   
	          if(pagetoolbar == null)   
	        	  pagetoolbar=new AutoSizePagetoolbar();   
	       }finally{
	    	   lock.unlock(); 
	       }
			
			
		}
		return pagetoolbar;
	}
	
	public static AbstractPagetoolbar getCommonPagetoolbar(){
		if(commonPagetoolbar==null){
			//只有第一次才彻底执行这里的代码   
		  lock.lock();	
	      try {   
	          //再检查一次   
	          if(commonPagetoolbar == null)   
	        	  commonPagetoolbar=new CommonPagetoolbar(); 
	       }finally{
	    	   lock.unlock(); 
	       }
			
		}
		return commonPagetoolbar;
	}
	
	
	
	abstract public   String getPageToolBarHtml(HttpServletRequest request,AbstractPageInfo pageInfo);
	
	
}