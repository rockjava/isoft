package com.isoftframework.common.page.pageInfo.hibernate;

/*
 * Created on 2005-6-20
 */

import java.util.List;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.dao.IDao;

public class HibernatePageInfo extends AbstractPageInfo {
	
	public static final String ORDER_BY_REGEX="(?i)order\\s+by(?-i)";
	
	public static final String ORDER_BY="ORDER BY";
	
	public static final String FROM_REGEX="(?i)from(?-i)";
	
	public static final String FROM="FROM";

	IDao dao=null;
	
	public HibernatePageInfo(IDao dao){
		super();
		this.dao=dao;
	}
	public HibernatePageInfo(IDao dao,int curPage,int pageSize ) {
		super(pageSize);
		this.dao=dao;
		
	}
	
	public String generateCountSql(final String querySql){
		
		return ( "select count(*) as count  "+processQuerySqlForCount(querySql));
		//this.countSql ="select count(T) from("+querySql+")T";
		 
	}
	
	private String processQuerySqlForCount(final String querySql){

		String  sql=querySql.replaceAll(FROM_REGEX, FROM);
		sql = querySql.substring(sql.indexOf(FROM));
		//sql=RegexUtil.regexReplace(sql, ORDER_BY_REGEX, ORDER_BY);
		sql=sql.replaceAll(ORDER_BY_REGEX, ORDER_BY);
		int orderIdx=sql.lastIndexOf(ORDER_BY);
		if(orderIdx>-1){
			sql=sql.substring(0,orderIdx);
		}
		return sql;
	}

	@Override
	public long computeTotalSize(String countSql) throws Exception {
		 
		return (Long) dao.findUnique(countSql );
	}

	@Override
	public List queryWithLimit(String querySql, long start, int limit,Class clas) throws Exception {
		return dao.findWithLimit(querySql,start,limit);
		 
	}
}