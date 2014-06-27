package com.isoftframework.dao.orm.hibernate4;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.springframework.dao.DataAccessException;

import com.isoftframework.dao.orm.OrmDaoSupport;
public class HibernateDaoSupport  implements OrmDaoSupport  {
	private SessionFactory sessionFactory;

	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	/* (non-Javadoc)
	 * @see com.frm.model.dao.orm.hibernate4.OrmDaoSupport#save(java.lang.Object)
	 */
	@Override
	public Serializable save(Object dto) throws Exception {
		return  this.getCurrentSession().save(dto);
	}
	
	public Object merge(final Object entity) throws Exception{
		return this.getCurrentSession().merge(entity);
	}
	
	public void persist(final Object entity) throws Exception{
		   this.getCurrentSession().persist(entity);
	}

	@Override
	public void update(Object dto) throws Exception {
		this.getCurrentSession().update(dto);
	}

	@Override
	public void saveOrUpdate(Object dto) throws Exception {
		this.getCurrentSession().saveOrUpdate(dto);
	}

	@Override
	public void deleteById(Class  entityClass, Serializable id) {
		  this.getCurrentSession().delete(this.get(entityClass, id));
	}


	@Override
	public void delete(Object entity) {
		this.getCurrentSession().delete(entity);
	}


	@Override
	public int deleteAll(Collection entities) throws Exception {
		Object entity;
        for(Iterator itr = entities.iterator(); itr.hasNext(); getCurrentSession().delete(entity))
            entity = itr.next();

        return entities.size();
	}

	/*------Hibernate  query------*/
	
	@Override
	public <T> T load(Class<T> entityClass, Serializable id) {
		return (T) this.getCurrentSession().load(entityClass, id);
	}

	
	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		
		return  (T) this.getCurrentSession().get(entityClass, id);
	}

	
	@Override
	public <T> List<T> find(String queryString)
	 {
	     return find(queryString, (Object[])null);
	 }
	
	 /* (non-Javadoc)
	 * @see com.frm.model.dao.orm.hibernate4.OrmDaoSupport#find(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T> List<T> find(String queryString, Object value)
	 {
	     return find(queryString, new Object[] {value});
	 }

	
	@Override
	public <T> List<T> find(String hql, Object[] values) {
		Query query = getCurrentSession().createQuery(hql);
        if(values != null)
        {
            for(int i = 0; i < values.length; i++)
            	query.setParameter(i, values[i]);
        }
        return query.list();
	}
	
	@Override
	public <T> List<T> findWithLimit(String queryHql,long start,int limit) throws Exception{
		return this.findWithLimit(queryHql, (Object[])null, start, limit);
	}
	
	public <T> List<T> findWithLimit(String queryHql,Object value, long start,int limit)throws Exception{
		return this.findWithLimit(queryHql, new Object[]{value}, start, limit);
	}
	
	public <T> List<T> findWithLimit(String queryHql,Object[] values, long start,int limit)throws Exception{
		Query query = getCurrentSession().createQuery(queryHql);
		query.setFirstResult((int) start);
		query.setMaxResults(limit);
        if(values != null)
        {
            for(int i = 0; i < values.length; i++)
            	query.setParameter(i, values[i]);
        }
        return query.list();
	}
	
	
	@Override
	public <T> List<T> findByNamedParams(final String hql, final Map params) {
		Query query = getCurrentSession().createQuery(hql);
		if (params != null && params.size() > 0)
			query.setProperties(params);

		return query.list();
	}
	
	public <T> List<T> findByNamedQuery(String queryName)
    throws DataAccessException
	{
	    return findByNamedQuery(queryName, (Object[])null);
	}
	
	public <T> List<T> findByNamedQuery(String queryName, Object value)
	    throws DataAccessException
	{
	    return findByNamedQuery(queryName, new Object[] {value });
	}
	public <T> List<T> findByNamedQuery(String queryName, Object values[]){
		Query queryObject = getCurrentSession().getNamedQuery(queryName);
        if(values != null)
        {
            for(int i = 0; i < values.length; i++)
                queryObject.setParameter(i, values[i]);

        }
        return queryObject.list();
	}


	
	 
	/* (non-Javadoc)
	 * @see com.frm.model.dao.orm.hibernate4.OrmDaoSupport#findUnique(java.lang.String)
	 */
	@Override
	public Object findUnique(final String hql) {
		Query query = getCurrentSession().createQuery(hql);
		return query.uniqueResult();

	}
	
	 
	 
	//------------

	/* (non-Javadoc)
	 * @see com.frm.model.dao.orm.hibernate4.OrmDaoSupport#findBySql(java.lang.String)
	 */
	@Override
	public List findBySql(final String sql)  {

		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		return query.list();

	}
	

	@Override
	public List findWithLimitBySql(final String sql,int start,int limit) {

		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}

	@Override
	public List findWithLimitBySql(  String sql,int start,int limit,String entityName,Class entityClass) {

		SQLQuery query = getCurrentSession().createSQLQuery(sql).addEntity(entityName, entityClass);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();

	}

	@Override
	public Object findUniqueBySql(final String sql) {

		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		return query.uniqueResult();

	}

	/*
	 * 用这里面的警醒代替 StandardBasicTypes.CHARACTER_ARRAY StringType.INSTANCE
	 */
	 
	public long countBySql(final String countSql) {
		return (Long) getCurrentSession().createSQLQuery(countSql)
				.addScalar("count", LongType.INSTANCE).uniqueResult();
	}
	 
 

	// ==========hibernate update

	
	@Override
	public int execUpdate(final String hql, final Map params) throws Exception {
		Query query = getCurrentSession().createQuery(hql);
		query.setProperties(params);
		return query.executeUpdate();
	}

	
	@Override
	public int execUpdate(final String hql) throws Exception {
		Query query = getCurrentSession().createQuery(hql);
		int n = query.executeUpdate();
		// getCurrentSession().beginTransaction().commit();
		return n;
	}

	
	@Override
	public int execUpdate(final String hql, final Object[] values) {
		Query query = getCurrentSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.frm.model.dao.orm.hibernate4.OrmDaoSupport#execSqlUpdate(java.lang.String)
	 */
	@Override
	public int execUpdateBySql(final String sql) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);

		int n = query.executeUpdate();
		// getCurrentSession().beginTransaction().commit();
		return n;
	}

	
	@Override
	public int execUpdateBySql(final String sql, final Object[] values)
			throws Exception {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	
	@Override
	public int execUpdateBySql(final String sql, final Map params)
			throws Exception {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		query.setProperties(params);
		int n = query.executeUpdate();
		return n;
	}


	@Resource(name="sessionFactory")
	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	 
	 
	 
}
