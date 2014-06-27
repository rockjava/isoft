package com.isoftframework.common.page.pageInfo.jdbc;

/*
 * Created on 2005-6-20
 */

import java.util.List;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.dao.IDao;
 
public class JdbcPageInfo extends AbstractPageInfo {

	IDao dao=null;
	public JdbcPageInfo(IDao dao){
		super();
		this.dao=dao;
	}
	public JdbcPageInfo(IDao dao,int pageSize ) {
		super(pageSize);
		this.dao=dao;
		
	}
	
	public String generateCountSql(String querySql){
		 
		return "select count(1) from("+querySql+")T";
		 
	}

	@Override
	public long computeTotalSize(String countSql) {
		 
		return dao.queryForInt(countSql);
	}

	@Override
	public List queryWithLimit(String querySql, long start, int limit,Class clas) {
		return dao.queryEntityForListWithLimit(querySql,start,  limit, clas);
		
	}
	 
}