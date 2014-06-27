package com.isoftframework.dao.jdbc;

import java.util.List;

public interface IJdbcDaoSupport {

	public int queryForInt(String querySql);

	public abstract List queryForList(String sql);
	
	public <T> T getEntity(String sql, final Class<T> clas);
	
	public <T> T getEntity(String sql,Object[] values, final Class<T> clas);

	public abstract <T> List<T> queryEntityForList(String sql, Class<T> clas);
	
	public abstract <T> List<T> queryEntityForList(String sql, Object[] values,Class<T> clas);

	public abstract <T> List<T> queryEntityForList(String sql, Object values,Class<T> clas);

	public abstract <T> List<T> queryEntityForListWithLimit(String sql, long start,int limit, final Class<T> clas);
	public abstract <T> List<T> queryEntityForListWithLimit(String sql, Object value, long start,int limit, final Class<T> clas);
	public abstract <T> List<T> queryEntityForListWithLimit(String sql, Object[] values, long start,int limit, final Class<T> clas);
	
	public abstract int[] batchUpdate(final String sqls[]);
}
