/**
 * 
 */
package com.isoftframework.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.internal.Primitives;
import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.common.pkgenerator.TreeId;
import com.isoftframework.common.sqlbuilder.SQLBuilder;
import com.isoftframework.dao.IDao;

@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public abstract class AbstractService implements IService 
{
	public IDao myDao;
	
	 
 
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
	public void saveTX(Object dto)throws  Exception
	{
		this.myDao.save(dto);
	}
 
	public void updateTX(Object dto) throws Exception
	{
		this.myDao.update(dto);
	}
	 
	public void saveOrUpdateTX(Object dto) throws Exception
	{
		this.myDao.saveOrUpdate(dto) ;
	}
	 
	 
	public <T> void deleteByIdTX (Class<T> entityClass,Serializable id) throws Exception{
		this.myDao.deleteById(entityClass,id);
	}
	 
	public void deleteTX (Object dto) throws Exception
	{
		this.myDao.delete(dto);
	}
	
	public void deleteAllTX (Collection dtos) throws Exception
	{
		this.myDao.deleteAll(dtos);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
	public int[]  cascadDelete(String id,String table,String idName,String parentIdName) throws Exception{
		List<String> list=getMeAndAllChild(  id,  table,  idName,  parentIdName,new ArrayList<String>());
		if(list!=null && list.size()>0)
			return this.batchUpdateTX(list.toArray(new String[list.size()]));
		return null;
	}

	
	public List<String>   getMeAndAllChild(String id,String table,String idName,String parentIdName,List<String> list) throws Exception{
		
		List<String> child=this.findBySql("select "+idName+" from "+table+" t where t."+parentIdName+"='"+id+"'");
		if(child!=null && child.size()>0){
			for(String cid:child ){
				getMeAndAllChild(cid,  table,  idName,  parentIdName,list);
			}
		}
		list.add("delete from "+table+" t where t."+idName+"='"+id+"'");
		return list;
	}
	 
	
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly = true)
	public <T>List<T> find(String hql) throws Exception{
		return myDao.find(hql);
	}
 
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly = true)
	public <T>List<T> find(String hql,String param) throws Exception{
		return myDao.find(hql, param);
	}
	 
	
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly = true)
	public <T>List<T> find(String hql,String[]param) throws Exception{
		return myDao.find(hql, param);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public   <T> List<T> findWithLimit(String queryHql, int start,int limit)throws Exception{
		return this.myDao.findWithLimit(queryHql, start, limit);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public   <T> List<T> findWithLimit(String queryHql,Object value, int start,int limit)throws Exception{
		return this.myDao.findWithLimit(queryHql, value, start, limit);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public   <T> List<T> findWithLimit(String queryHql,Object[] values, int start,int limit)throws Exception{
		return this.myDao.findWithLimit(queryHql, values, start, limit);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly = true)
	public <T>List<T> findAsPageList(AbstractPageInfo pageInfo) throws Exception {
		return (List<T>)myDao.findAsPageList(pageInfo);
	}
	
	 
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly = true)
	public <T>List<T>  findAsPageList(final AbstractPageInfo pageInfo,Class<T> clas) throws Exception {
		return myDao.findAsPageList(pageInfo,clas);
	}
	 
	
	public <T>List<T> findByNamedParams(final String hql, final Map params) throws Exception
	{
		return myDao.findByNamedParams(hql, params);
	}
	
	
	/*@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly = true)
	public <T> T get(String id) throws Exception{
		return  (T) this.get(getEntityClass(), id);
	}*/
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly = true)
	public <T> T get( Class<T> classOfT,String id) throws Exception{
		
		Object object= this.myDao.get(classOfT, id);
		return Primitives.wrap(classOfT).cast(object);
	}
	
	/*public <T> List<T>   getByCascade(T entity,String id,String table,String idName,String parentIdName) throws Exception{
		
		List<T> children=this.findBySql("from "+table+" t where t."+parentIdName+"='"+id+"'");
		if(children!=null && children.size()>0){
			for(T child:children ){
				getMeAndAllChild(cid,  table,  idName,  parentIdName,list);
			}
		}
		list.add("rom "+table+" t where t."+idName+"='"+id+"'");
		return list;
	}*/
	
	public <T> T load(Class<T> cls,String id) throws Exception{
		return (T) this.myDao.load(cls, id);
	}
	
	

	
	public int execUpdateTX (final String hql ) throws Exception{
		return this.myDao.execUpdate(hql);
	}
	
	
	
	public int execUpdateTX (final String hql ,final Object[] values) throws Exception {
		return this.myDao.execUpdate(hql,values);
	}
	
	
	public int execUpdateBySqlTX (final String sql ) throws Exception{
		return myDao.execUpdateBySql(sql);
	 
	}
	

	public int execUpdateBySql (final String sql,final Object[] values  ) throws Exception{
		return myDao.execUpdateBySql(sql,values);
	 
	}
	 

	
	public Object findUnique(final String hql) throws Exception {
		return myDao.findUnique(hql);
	}
		
	
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly =true)
	public   List findBySql(String sql) throws Exception{
		 
		return myDao.findBySql(sql);
	}
	
	
	public Object findUniqueBySql(String sql) throws Exception{
		return myDao.findUniqueBySql(sql);
	}
	
	public String generateIdForTree(String parentId,String idName,String parentIdName,String tableName) throws Exception{
		return TreeId.getInstatnce().generate(  parentId,  idName,  parentIdName,  tableName,  myDao);
	}
	
	public int[]  cascadDeleteTX(String id,String tableName,String parentidName,String idName) throws Exception{
		List<String> list=getDelSqlOfMeAndAllChild(id,tableName,parentidName,idName);
		if(list!=null && list.size()>0)
			return this.batchUpdateTX(list.toArray(new String[list.size()]));
		return new int[]{0};
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly =true)
	public List<String>   getDelSqlOfAllChild(String id,List<String> list,String tableName,String parentidName,String idName) throws Exception{
		List<String> childIds=this.findBySql("select "+idName+" from "+tableName+" t where t."+parentidName+"='"+id+"'");
		if(childIds!=null && childIds.size()>0){
			for(String childId:childIds ){
				getDelSqlOfAllChild(childId,list,  tableName,  parentidName,  idName);
			}
			list.add("delete from "+tableName+" t where t."+parentidName+"='"+id+"'");
		}
		return list;
	}
	 
	
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly =true)
	public List<String>   getDelSqlOfMeAndAllChild(String id,String tableName,String parentidName,String idName) throws Exception{
		List list=this.getDelSqlOfAllChild(id,new ArrayList<String>(),tableName,parentidName,idName);
		list.add("delete from "+tableName+" t where t."+idName+"='"+id+"'");
		return list;
	}
	
	
	//===========jdbc============
	
	
	
	public int[] batchUpdateTX(final String sql[]){
		return this.myDao.batchUpdate(sql);
	}
	//-----------jdbc query---------
	
	public <T> T getEntity(String sql,Class<T> cls) throws Exception{
		return (T) this.myDao.getEntity(sql, cls);
	}
	
	public <T> T getEntity(String sql,Object[] values,Class<T> cls) throws Exception{
		return (T) this.myDao.getEntity(sql,values, cls);
	}
	public   List queryForList(String sql) {
		return myDao.queryForList(sql);
	}
	/*@Transactional(propagation=Propagation.REQUIRED,readOnly = true)
	public <T>List<T> queryEntityForList(String sql)throws Exception{
		return  this.queryEntityForList(sql, getEntityClass());
	}

	
	@Transactional(propagation=Propagation.REQUIRED,readOnly = true)
	public <T>List<T> queryEntityForList(String sql,Object value)throws Exception{
		return  this.queryEntityForList(sql,value, getEntityClass());
	}*/
 
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly =true)
	public <T> List<T> queryEntityForList(String sql,Class<T> clas){
		return myDao.queryEntityForList(sql, clas);
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly =true)
	public <T> List<T> queryEntityForList(String sql,Object value,Class<T> clas){
		return myDao.queryEntityForList(sql,value, clas);
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly =true)
	public <T> List<T> queryEntityForList(String sql,Object[] values,Class<T> clas){
		return (List<T>)myDao.queryEntityForList(sql,values, clas);
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly =true)
	public   <T> List<T> queryEntityForListWithLimit(String sql, int start,int limit, final Class<T> clas){
		return myDao.queryEntityForListWithLimit(sql, start, limit, clas);
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly =true)
	public   <T> List<T> queryEntityForListWithLimit(String sql, Object value, int start,int limit, final Class<T> clas){
		return myDao.queryEntityForListWithLimit(sql,value, start, limit, clas);
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class,readOnly =true)
	public   <T> List<T> queryEntityForListWithLimit(String sql, Object[] values, int start,int limit, final Class<T> clas){
		return myDao.queryEntityForListWithLimit(sql,values, start, limit, clas);
	}
	
	
 
	
	 
 
	
	public IDao getMyDao() {
		return myDao;
	}

	 
	
	@Resource(name="myDao")
	public void setMyDao(IDao myDao) {
		this.myDao = myDao;
	}
	 
	
	
	
}
