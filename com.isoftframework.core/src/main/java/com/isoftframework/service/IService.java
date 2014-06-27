package com.isoftframework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.dao.IDao;

public interface IService {

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
	public abstract void saveTX(Object dto) throws Exception;

	public abstract void updateTX(Object dto) throws Exception;

	public abstract void saveOrUpdateTX(Object dto) throws Exception;

	public  abstract <T> void deleteByIdTX(Class<T> entityClass, Serializable id)
			throws Exception;

	public abstract void deleteTX(Object dto) throws Exception;

	public abstract void deleteAllTX(Collection dtos) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
	public abstract int[] cascadDelete(String id, String table, String idName,
			String parentIdName) throws Exception;

	public abstract List<String> getMeAndAllChild(String id, String table,
			String idName, String parentIdName, List<String> list)
			throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T>List<T> find(String hql) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T>List<T> find(String hql, String param) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T>List<T> find(String hql, String[] param) throws Exception;
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T> List<T> findWithLimit(String queryHql, int start,int limit)throws Exception;
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T> List<T> findWithLimit(String queryHql,Object value, int start,int limit)throws Exception;
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T> List<T> findWithLimit(String queryHql,Object[] values, int start,int limit)throws Exception;
	

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T> List<T> findAsPageList(AbstractPageInfo pageInfo)
			throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class, readOnly = true)
	public abstract <T> List<T> findAsPageList(final AbstractPageInfo pageInfo,
			Class<T> clas) throws Exception;

	public abstract <T> List<T> findByNamedParams(final String hql, final Map params)
			throws Exception;

	/*@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class, readOnly = true)
	public abstract <T> T get(String id) throws Exception;*/

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class, readOnly = true)
	public abstract <T> T get(Class<T> classOfT, String id) throws Exception;

	public abstract <T>T load(Class<T> cls, String id) throws Exception;

	public abstract int execUpdateTX(final String hql) throws Exception;

	public abstract int execUpdateTX(final String hql, final Object[] values)
			throws Exception;

	public abstract int execUpdateBySqlTX(final String sql) throws Exception;

	public abstract int execUpdateBySql(final String sql, final Object[] values)
			throws Exception;

	public abstract Object findUnique(final String hql) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract List findBySql(String sql) throws Exception;

	public abstract Object findUniqueBySql(String sql) throws Exception;

	public abstract String generateIdForTree(String parentId, String idName,
			String parentIdName, String tableName) throws Exception;

	public abstract int[] cascadDeleteTX(String id, String tableName,
			String parentidName, String idName) throws Exception;

	/**
	 * 针对树结构，获取待删除节点和其子节点的删除语句
	 * @param id
	 * @param list
	 * @param tableName
	 * @param parentidName
	 * @param idName
	 * @return
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract List<String> getDelSqlOfAllChild(String id,
			List<String> list, String tableName, String parentidName,
			String idName) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract List<String> getDelSqlOfMeAndAllChild(String id,
			String tableName, String parentidName, String idName)
			throws Exception;
	
	//============jdbc======
	public abstract int[] batchUpdateTX(final String sql[]);
	//------jdbc query----------
	public abstract <T> T getEntity(String sql, Class<T> cls) throws Exception;

	public abstract <T> T getEntity(String sql, Object[] values, Class<T> cls)
			throws Exception;

	public abstract List queryForList(String sql) ;
	
	/*@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T>List<T> queryEntityForList(String sql) throws Exception;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract <T>List<T> queryEntityForList(String sql, Object value)
			throws Exception;*/

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class, readOnly = true)
	public abstract <T> List<T> queryEntityForList(String sql, Class<T> clas);

	public abstract <T> List<T> queryEntityForList(String sql, Object value, Class<T> clas);

	public abstract <T> List<T> queryEntityForList(String sql, Object[] values,
			Class<T> clas);
	public abstract <T> List<T> queryEntityForListWithLimit(String sql, int start,int limit, final Class<T> clas);
	public abstract <T> List<T> queryEntityForListWithLimit(String sql, Object value, int start,int limit, final Class<T> clas);
	public abstract <T> List<T> queryEntityForListWithLimit(String sql, Object[] values, int start,int limit, final Class<T> clas);
	


	public abstract IDao getMyDao();

	@Resource(name = "myOrmAndJdbcDao")
	public abstract void setMyDao(IDao myDao);

	//public abstract Class getEntityClass();

}