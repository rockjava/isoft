/**
 * 
 */
package com.isoftframework.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.common.sqlbuilder.SQLBuilder;
import com.isoftframework.common.sqlbuilder.SQLBuilderFactory;
import com.isoftframework.dao.jdbc.EntityRowMapper;
import com.isoftframework.dao.jdbc.JdbcSupport;
import com.isoftframework.dao.orm.OrmDaoSupport;

public class OrmAndJdbcDao  implements IDao{
	public   Logger logger = Logger.getLogger(this.getClass());

	public OrmAndJdbcDao() {
	}

	private OrmDaoSupport myOrmDaoSupport;

	private JdbcSupport myJdbcDaoSupport;

	/*------Hibernate------*/
	/*------Hibernate  update------*/
	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#save(java.lang.Object)
	 */
	@Override
	public Serializable save(Object dto) throws Exception {
		return this.myOrmDaoSupport.save(dto);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#update(java.lang.Object)
	 */
	@Override
	public void update(Object dto) throws Exception {
		this.myOrmDaoSupport.update(dto);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Object dto) throws Exception {
		this.myOrmDaoSupport.saveOrUpdate(dto);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#deleteById(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public void deleteById(Class entityClass, Serializable id)throws Exception {
		this.myOrmDaoSupport.deleteById(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object entity) throws Exception {
		this.myOrmDaoSupport.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#deleteAll(java.util.Collection)
	 */
	@Override
	public int deleteAll(Collection entities) throws Exception {
		return this.myOrmDaoSupport.deleteAll(entities);
	}

	/*------Hibernate  query------*/
	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#load(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T>  T load(Class<T>  entityClass, Serializable id) throws Exception {
		return (T) this.myOrmDaoSupport.load(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T get(Class<T>  entityClass, Serializable id) throws Exception {
		return (T) this.myOrmDaoSupport.get(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#find(java.lang.String)
	 */
	@Override
	public <T>  List<T> find(String hql) throws Exception {
		return this.myOrmDaoSupport.find(hql);
	}

	 
	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#find(java.lang.String, java.lang.String[])
	 */
	@Override
	public <T>  List<T> find(String hql, Object[] values) throws Exception{
		return this.myOrmDaoSupport.find(hql, values);
	}
	public   <T> List<T> findWithLimit(String queryHql, long start,int limit)throws Exception{
		return this.myOrmDaoSupport.findWithLimit(queryHql, start, limit);
	}
	
	public   <T> List<T> findWithLimit(String queryHql,Object value, long start,int limit)throws Exception{
		return this.myOrmDaoSupport.findWithLimit(queryHql, value, start, limit);
	}
	
	public   <T> List<T> findWithLimit(String queryHql,Object[] values, long start,int limit)throws Exception{
		return this.myOrmDaoSupport.findWithLimit(queryHql, values, start, limit);
	}
	
	@Override
	public <T> T merge(Object entity) throws Exception {
		return myOrmDaoSupport.merge(entity);
	}

	@Override
	public void persist(Object entity) throws Exception {
		myOrmDaoSupport.persist(entity);
		
	}

	@Override
	public List find(String queryString, Object value) throws Exception {
		
		return myOrmDaoSupport.find(queryString,value);
	}

	@Override
	public List findByNamedQuery(String queryString) throws Exception {
		
		return myOrmDaoSupport.findByNamedQuery(queryString);
	}

	@Override
	public List findByNamedQuery(String queryString, Object value)
			throws Exception {
		return myOrmDaoSupport.findByNamedQuery(queryString,value);
	}

	@Override
	public List findByNamedQuery(String queryName, Object[] values)
			throws Exception {
		return myOrmDaoSupport.findByNamedQuery(queryName,values);
	}

	@Override
	public List findWithLimitBySql(String sql, int start, int limit)throws Exception {
		return myOrmDaoSupport.findWithLimitBySql(sql, start, limit);
	}

	@Override
	public List findWithLimitBySql(String sql, int start, int limit,
			String entityName, Class entityClass)throws Exception {
		return myOrmDaoSupport.findWithLimitBySql(sql, start, limit, entityName, entityClass);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#findByNamePrams(java.lang.String, java.util.Map)
	 */
	@Override
	public List findByNamedParams(final String hql, final Map params) throws Exception {
		return myOrmDaoSupport.findByNamedParams(hql, params);
	}

	/*
	 * 根据hql分页查询
	 */
	
	@Override
	public <T> List<T> findAsPageList(final AbstractPageInfo pageInfo)
			throws Exception {
		return pageInfo.compute(OrmAndJdbcDao.this);

	}
	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#findAsPageList(com.frm.common.page.pageInfo.AbstractPageInfo, java.lang.Class)
	 */
	@Override
	public <T> List<T> findAsPageList(final AbstractPageInfo pageInfo,Class clas) throws Exception {
		return pageInfo.compute(OrmAndJdbcDao.this,clas);
	
	}
	
	@Override
	public List findAllChildIdByParentId(String parentId, String idName,
			String parentIdName, String tableName) throws Exception {
		return SQLBuilderFactory.getSQLBuilder().findAllChildIdByParentId(this, parentId, idName,
				parentIdName, tableName);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#findUnique(java.lang.String)
	 */
	@Override
	public Object findUnique(final String hql) throws Exception{
		return myOrmDaoSupport.findUnique(hql);
	}
 
	//====sql
	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#findBySql(java.lang.String)
	 */
	@Override
	public List findBySql(final String sql) throws Exception {
		return myOrmDaoSupport.findBySql(sql) ;
	}


	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#findUniqueBySql(java.lang.String)
	 */
	@Override
	public Object findUniqueBySql(final String sql)throws Exception {
		return myOrmDaoSupport.findUniqueBySql(sql);
	}

	@Override
	public int execUpdate(final String hql) throws Exception {
		return myOrmDaoSupport.execUpdate(hql);
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.DaoSupport#execUpdate(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int execUpdate(final String hql, final Object[] values) throws Exception {
		return myOrmDaoSupport.execUpdate(hql, values);
	}
	
	@Override
	public int execUpdate(final String hql, final Map params) throws Exception {
		return myOrmDaoSupport.execUpdate(hql, params);
	}

	
	@Override
	public int execUpdateBySql(final String sql) throws Exception {
		return myOrmDaoSupport.execUpdateBySql(sql);
	}

	@Override
	public int execUpdateBySql(final String sql, final Object[] values)
			throws Exception {
		return myOrmDaoSupport.execUpdateBySql(sql, values);
	}

	
	@Override
	public int execUpdateBySql(final String sql, final Map params)
			throws Exception {
		return myOrmDaoSupport.execUpdateBySql(sql, params);
	}

	/*----------------jdbc----------------*/

	@Override
	public List queryForList(String sql) {
		return myJdbcDaoSupport.queryForList(sql);
	}
	@Override
	public int queryForInt(String querySql){
		return getJdbcTemplate().queryForInt(querySql);
	}
	@Override
	public <T> T getEntity(String sql, final Class<T> clas){
		return (T) myJdbcDaoSupport.getEntity(sql, clas);
	}
	@Override
	public <T> T getEntity(String sql,Object[] values,final Class<T> clas){
		return (T) myJdbcDaoSupport.getEntity(sql,values, clas);
	}
	@Override
	public <T> List<T> queryEntityForList(String sql, Class<T> clas) {
		return myJdbcDaoSupport.queryEntityForList(sql, clas);
	}
	
	public <T> List<T> queryEntityForList(String sql, Object[] values,Class<T> clas) {
		return myJdbcDaoSupport.queryEntityForList(sql,values, clas);
	}
	
	public <T> List<T> queryEntityForList(String sql, Object value,Class<T> clas) {
		return myJdbcDaoSupport.queryEntityForList(sql,value, clas);
	}

	@Override
	public <T> List<T> queryEntityForListWithLimit(String sql, long start, int limit,
			Class<T> clas) {
		return myJdbcDaoSupport.queryEntityForListWithLimit(sql, start, limit, clas);
	}
	
	@Override 
	public <T> List<T> queryEntityForListWithLimit(String sql,Object value, long start, int limit,
			final Class<T> clas) {
		return myJdbcDaoSupport.queryEntityForListWithLimit(sql, value,start, limit, clas);
	}
	@Override
	public <T> List<T> queryEntityForListWithLimit(String sql,Object[] values, long start, int limit,
			final Class<T> clas) {
		return myJdbcDaoSupport.queryEntityForListWithLimit(sql, values,start, limit, clas);
	}
	
	@Override
	public Long countBySql(final String countSql) {
		return getJdbcTemplate().queryForObject(
				countSql, Long.class, "count");
	}
	@Override
	public int[] batchUpdate(String[] sqls) {
		return myJdbcDaoSupport.batchUpdate(sqls);
	}
	@Resource(name="myHibernate4DaoSupport")
	public void setMyOrmDaoSupport(OrmDaoSupport myOrmDaoSupport) {
		this.myOrmDaoSupport = myOrmDaoSupport;
	}

	@Resource(name="myJdbcDaoSupport")
	public void setMyJdbcDaoSupport(JdbcSupport myJdbcDaoSupport) {
		this.myJdbcDaoSupport = myJdbcDaoSupport;
	}
	 
	public JdbcTemplate getJdbcTemplate() {
		return this.myJdbcDaoSupport.getJdbcTemplate();
	}
	
	 

	

	

	
}
