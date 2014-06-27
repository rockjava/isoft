package com.isoftframework.dao;

import java.util.List;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.common.sqlbuilder.SQLBuilder;
import com.isoftframework.dao.jdbc.IJdbcDaoSupport;
import com.isoftframework.dao.orm.OrmDaoSupport;

public interface IDao extends OrmDaoSupport,IJdbcDaoSupport{
	
	public <T>  List<T> findAsPageList(final AbstractPageInfo pageInfo)throws Exception ;
 
	public <T>  List<T> findAsPageList(final AbstractPageInfo pageInfo,Class clas) throws Exception;

	public List findAllChildIdByParentId(String parentId, String idName,String parentIdName, String tableName) throws Exception;

	public Long countBySql(final String countSql) throws Exception;
	

}