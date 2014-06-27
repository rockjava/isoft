/*
 * Created on 2005-6-20
 */
package com.isoftframework.controller;

import javax.servlet.http.HttpServletRequest;

import com.isoftframework.common.Context;
import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.common.page.pageInfo.hibernate.HibernatePageInfo;
import com.isoftframework.common.page.pageInfo.jdbc.JdbcPageInfo;
import com.isoftframework.common.util.HttpUtil;
/**
 * 
 */
public class PageController extends   BaseController {

	
	private  final AbstractPageInfo getJdbcPageInfo(HttpServletRequest request){
		Context context =getContext(request);
		AbstractPageInfo pageInfo;
		if(context.getPageInfo()!=null&&context.getPageInfo() instanceof JdbcPageInfo){
			pageInfo=context.getPageInfo();
			
		}else{
			pageInfo=new JdbcPageInfo(this.myBaseService.getMyDao());
			context.setPageInfo(pageInfo);
		}
		return pageInfo;
	}
	
	private final AbstractPageInfo getHibernatePageInfo(HttpServletRequest request){
		Context context =getContext(request);
		AbstractPageInfo pageInfo;
		if(context.getPageInfo()!=null&&context.getPageInfo() instanceof HibernatePageInfo){
			pageInfo=context.getPageInfo();
			
		}else{
			pageInfo=new HibernatePageInfo(this.myBaseService.getMyDao());
			context.setPageInfo(pageInfo);
		}
		return pageInfo;
	}
	
	public final AbstractPageInfo computeJdbcPageInfoBefore(HttpServletRequest request,String querySql) {
		return computeJdbcPageInfoBefore(  request,  querySql,null);
	}
	public final AbstractPageInfo computeJdbcPageInfoBefore(HttpServletRequest request,String querySql, String countSql) {
		
		return computePageInfoBefore( request, querySql,   countSql,getJdbcPageInfo(request));
	}
	public final AbstractPageInfo computeHibernatePageInfoBefore(HttpServletRequest request,String querySql ){
		return this.computeHibernatePageInfoBefore(request, querySql, null);
	}
	 
	public final AbstractPageInfo computeHibernatePageInfoBefore(HttpServletRequest request,String querySql, String countSql) {
		
		return computePageInfoBefore( request, querySql,   countSql,getHibernatePageInfo(request));
	}
	/**
	 * 分页计算
	 * @param request
	 * @param querySql
	 * @param countSql
	 */
	public final AbstractPageInfo computePageInfoBefore(HttpServletRequest request,
		 	String querySql, String countSql,AbstractPageInfo pageInfo) {
		
		
		 
		Integer pageSize=this.getPageSize(request);
		if(pageSize!=null && pageSize!=pageInfo.getLimit()){
			pageInfo.setLimit(pageSize);
		}
		/*if(pageSize!=null ){
			if(Constants.PAGE_SIZE!=pageSize){
				Constants.PAGE_SIZE=pageSize;
			}
			
			
		}
		if(pageInfo.getPageSize()!=Constants.PAGE_SIZE){
			pageInfo.setPageSize(Constants.PAGE_SIZE);
			
		}*/
		//翻页时做查询条件用
		 
		Integer curPage=getCurPage(request);
		if(curPage!=null&&curPage!=pageInfo.getPageNum()){
			pageInfo.setPageNum(curPage);
		}
		
		if(querySql!=null){
			//order by 替换为大写的ORDER BY,方便生成CountSql时做字符串处理
			if( pageInfo.getQuerySql()==null || !querySql.equals(pageInfo.getQuerySql()) ){
				// 条件发生了变化
				// 记录新的查询条件
				pageInfo.setQuerySql(querySql);
				pageInfo.setCountSql(countSql);
				 
				
				// 设置当前页为1
				//pageInfo.setCurPage(1);
			}
		}

		String curURL=getURL(request);
		// 记录当前的URL
		pageInfo.setCurURL(curURL);
		Context context=getContext(request);
		context.setPageInfo(pageInfo);
		//context.setBackURL(curURL);
		return pageInfo;
	
		/*System.out.println("request.getRequestURI()="+request.getRequestURI());
		System.out.println("request.getContextPath()="+request.getContextPath());
		System.out.println("request.getServletPath()="+request.getServletPath());
		System.out.println("request.getQueryString()="+ getQueryString(request));*/
		 
	}
	/**
	 * 获取每页显示数目
	 * @param request
	 * @return
	 */
	private final Integer getPageSize(HttpServletRequest request){
		Integer pageSize=null;
		if(request.getParameter("pageSize")!=null&&!request.getParameter("pageSize").trim().equals("")){
			pageSize=Integer.parseInt(request.getParameter("pageSize"));
			 
		}else if(request.getParameter("limit")!=null&&!request.getParameter("limit").trim().equals("")){
			pageSize=Integer.parseInt(request.getParameter("limit"));
			 
		}
		return pageSize;
	}
	private final Integer getCurPage(HttpServletRequest request){
		 Integer curPage=null;
		 if(request.getParameter("pNum")!=null && !request.getParameter("pNum").trim().equals("")){
			 curPage=Integer.parseInt(request.getParameter("pNum"));
		 }else if(request.getParameter("page")!=null && !request.getParameter("page").trim().equals("")){
			 curPage=Integer.parseInt(request.getParameter("page").trim());
		 }
		 return curPage;
	}
	
	private final String getURL(HttpServletRequest request){
		
		return HttpUtil.getWrapURL(request);
		
	}

	 
	
	


}
