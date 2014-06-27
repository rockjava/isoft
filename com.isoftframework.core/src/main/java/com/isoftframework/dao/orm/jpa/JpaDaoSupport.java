package com.isoftframework.dao.orm.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.isoftframework.dao.orm.OrmDaoSupport;



public class JpaDaoSupport  implements OrmDaoSupport {

	@PersistenceContext
	private EntityManager em;

	public Object getReference(Class entityClass, Object id)
			throws DataAccessException {
		return em.getReference(entityClass, id);
	}

	public boolean contains(Object entity) throws DataAccessException {
		return Boolean.valueOf(em.contains(entity));
	}

	public void refresh(Object entity) throws DataAccessException {
		em.refresh(entity);
	}

	public void persist(Object entity) throws DataAccessException {
		em.persist(entity);
	}

	public Object merge(Object entity) throws DataAccessException {
		return em.merge(entity);
	}
	
	@Override
	public Serializable save(Object entity) throws Exception {
		this.persist(entity);
		return null;
	}

	@Override
	public void update(Object entity) throws Exception {
		this.persist(entity);
		
	}

	@Override
	public void saveOrUpdate(Object entity) throws Exception {
		this.persist(entity);
		
	}

	public void remove(Object entity) throws DataAccessException {
		
		em.remove(em.merge(entity));
	}
	@Override
	public void deleteById(Class entityClass, Serializable id) {
		delete(this.get(entityClass, id));
	}

	@Override
	public void delete(Object entity) {
		this.remove(entity);
	}

	@Override
	public int deleteAll(Collection entities) throws Exception {
		 
		Object entity;
        for(Iterator itr = entities.iterator(); itr.hasNext(); this.delete(entity))
            entity = itr.next();

        return entities.size();
	}


	public void flush() throws DataAccessException {
		em.flush();
	}

	public <T> List<T> find(String queryHql) throws DataAccessException {
		return find(queryHql, (Object[]) null);
	}
	@Override
	public <T> List<T> find(final String queryHql, final Object values[])
			throws DataAccessException {
		Query queryObject = em.createQuery(queryHql);

		if (values != null) {
			for (int i = 0; i < values.length; i++)
				queryObject.setParameter(i + 1, values[i]);

		}
		return queryObject.getResultList();
	}
	@Override
	public <T> List<T> findWithLimit(String queryHql, long start, int limit) throws Exception {
		return this.findWithLimit(queryHql, new Object[]{null}, start, limit);
	}
	
	public   <T> List<T> findWithLimit(String queryHql,Object value, long start,int limit)throws Exception{
		return this.findWithLimit(queryHql, new Object[]{value}, start, limit);
	}
	
	public   <T> List<T> findWithLimit(String queryHql,Object[] values, long start,int limit)throws Exception{
		Query queryObject = em.createQuery(queryHql);
		queryObject.setFirstResult((int) start);
		queryObject.setMaxResults(limit);
		if (values != null) {
			for (int i = 0; i < values.length; i++)
				queryObject.setParameter(i + 1, values[i]);

		}
		return queryObject.getResultList();
	}
	
	@Override
	public <T> List<T> findByNamedParams(String queryHql, Map params)
			throws DataAccessException {
		Query queryObject = em.createQuery(queryHql);
		if (params != null) {
			java.util.Map.Entry entry;
			for (Iterator i$ = params.entrySet().iterator(); i$.hasNext(); queryObject
					.setParameter((String) entry.getKey(), entry.getValue()))
				entry = (java.util.Map.Entry) i$.next();

		}
		return queryObject.getResultList();
	}
	
	@Override
	public <T> List<T> findByNamedQuery(String queryName) throws DataAccessException {
		return findByNamedQuery(queryName, (Object[]) null);
	}
	@Override
	public <T> List<T> findByNamedQuery(String queryHql, Object value) {
		return findByNamedQuery(queryHql, new Object[]{value});
	}
	public <T> List<T> findByNamedQuery(String queryName, Object values[])
			throws DataAccessException {
		Query queryObject = em.createNamedQuery(queryName);
		if (values != null) {
			for (int i = 0; i < values.length; i++)
				queryObject.setParameter(i + 1, values[i]);

		}
		return queryObject.getResultList();
	}

	public <T> List<T> findByNamedQueryAndNamedParams(String queryName, Map params)
			throws DataAccessException {
		Query queryObject = em.createNamedQuery(queryName);
		if (params != null) {
			java.util.Map.Entry entry;
			for (Iterator i$ = params.entrySet().iterator(); i$.hasNext(); queryObject
					.setParameter((String) entry.getKey(), entry.getValue()))
				entry = (java.util.Map.Entry) i$.next();

		}
		return queryObject.getResultList();
	}
	@Override
	public Object load(Class entityClass, Serializable id) {
		return   em.find(entityClass, id);
	}

	@Override
	public Object get(Class entityClass, Serializable id) {
		return   em.find(entityClass, id);
	}

	@Override
	public <T> List<T> find(String queryHql, Object value) {
		
		return this.find(queryHql, new Object[]{value});
	}

	@Override
	public Object findUnique(String hql) {
		Query query = em.createQuery(hql);
		return query.getSingleResult();
	}

	@Override
	public <T> List<T> findBySql(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findWithLimitBySql(String sql, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findWithLimitBySql(String sql, int start, int limit,
			String entityName, Class entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findUniqueBySql(String sql) {
		Query query = em.createQuery(sql);
		return query.getSingleResult();
	}

	@Override
	public int execUpdate(String hql, Map params) throws Exception {
		// TODO Auto-generated method stub
		 
		return 0;
	}

	@Override
	public int execUpdate(String hql) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int execUpdate(String hql, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int execUpdateBySql(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int execUpdateBySql(String sql, Object[] values) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int execUpdateBySql(String sql, Map params) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	 

 



}
