package com.isoftframework.dao.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface OrmDaoSupport {

	
	public abstract Serializable save(Object entity) throws Exception;
	
	public <T> T merge(final Object entity) throws Exception;
	
	public void persist(final Object entity) throws Exception;

	public abstract void update(Object entity) throws Exception;

	public abstract void saveOrUpdate(Object entity) throws Exception;

	public abstract <T> void deleteById(Class<T> entityClass, Serializable id)throws Exception;

	public abstract void delete(Object entity) throws Exception;

	public abstract int deleteAll(Collection entities) throws Exception;

	/*------Hibernate  query------*/
	public   <T> T load(Class<T> entityClass, Serializable id) throws Exception;

	public abstract <T> T get(Class<T> entityClass, Serializable id) throws Exception;

	public abstract <T> List<T> find(String queryHql)throws Exception;

	public abstract <T> List<T> find(String queryHql, Object value)throws Exception;

	public abstract <T> List<T> find(String hql, Object[] values) throws Exception;
	
	public abstract <T> List<T> findWithLimit(String queryHql, long start,int limit)throws Exception;
	
	public abstract <T> List<T> findWithLimit(String queryHql,Object value, long start,int limit)throws Exception;
	
	public abstract <T> List<T> findWithLimit(String queryHql,Object[] values, long start,int limit)throws Exception;
	
	public <T> List<T> findByNamedParams(String queryHql, Map params)throws Exception;
	
	public <T> List<T> findByNamedQuery(String queryHql)throws Exception;
	 
	public <T> List<T> findByNamedQuery(String queryHql, Object value)throws Exception;
	
	public <T> List<T> findByNamedQuery(String queryName, Object values[])throws Exception;

	
	public abstract Object findUnique(final String hql)throws Exception;;

	public abstract <T> List<T> findBySql(final String sql)throws Exception;;

	public abstract <T> List<T> findWithLimitBySql(final String sql, int start,
			int limit)throws Exception;;

	public abstract <T> List<T> findWithLimitBySql(String sql, int start, int limit,
			String entityName, Class<T> entityClass)throws Exception;;

	public abstract Object findUniqueBySql(final String sql)throws Exception;;
	

	public abstract int execUpdate(final String hql) throws Exception;

	public abstract int execUpdate(final String hql, final Object[] values)throws Exception;;
	
	public abstract int execUpdate(final String hql, final Map params)throws Exception;
	
	public abstract int execUpdateBySql(final String hql) throws Exception;

	public abstract int execUpdateBySql(final String hql, final Object[] values)throws Exception;;
	
	public abstract int execUpdateBySql(final String hql, final Map params)throws Exception;

	
	
	 
 

 

}