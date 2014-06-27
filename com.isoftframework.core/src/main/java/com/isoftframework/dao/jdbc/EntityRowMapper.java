package com.isoftframework.dao.jdbc;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.isoftframework.common.util.RegexUtil;

public  class EntityRowMapper implements RowMapper  {
		// PropertyUtils.copyProperties(dest, orig);
		// PropertyUtilsBean
		// MethodUtils.invokeExactMethod(object, methodName, args)
		Logger log=Logger.getLogger(getClass());
		Class clas;
		public EntityRowMapper() {
			
		}

		public EntityRowMapper(Class clas) {
			this.clas = clas;
		}

		public Object mapRow(ResultSet rs, int rowNum)  {
			try {
				Object entity = (Object) clas.newInstance();
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					String fieldName = getColumnKey(JdbcUtils.lookupColumnName(rsmd,i));
					//System.out.println("key="+key);
					
					
					PropertyDescriptor p = PropertyUtils.getPropertyDescriptor(entity, fieldName);
					if(p==null){
						log.debug(fieldName+"'s PropertyDescriptor is null!");
						continue;
					}
					Object value=null;
					if(p.getPropertyType().isAssignableFrom(char.class)||p.getPropertyType().isAssignableFrom(Character.class)){
						String temp=rs.getString(i);
						if(temp!=null)
							value=temp.charAt(0);
					}else if(p.getPropertyType().isAssignableFrom(long.class)||p.getPropertyType().isAssignableFrom(Long.class)){
						 
						value=rs.getLong(i);
					}
					else if(p.getPropertyType().isAssignableFrom(int.class)||p.getPropertyType().isAssignableFrom(Integer.class)){
						 
						value=rs.getInt(i);
					}else if(p.getPropertyType().isAssignableFrom(float.class)||p.getPropertyType().isAssignableFrom(Float.class)){
						 
						value=rs.getFloat(i);
						 
					}else if(p.getPropertyType().isAssignableFrom(double.class)||p.getPropertyType().isAssignableFrom(Double.class)){
						 
						value=rs.getDouble(i);
						 
					}else{
						value = JdbcUtils.getResultSetValue(rs, i);
					}
					try{
						p.getWriteMethod().invoke(entity, value);
					}catch(Exception invokeException){
						log.error(  p.getName()+" 赋值错误！/n", invokeException);
					}
					
					//setMappedProperty(entity, key, obj);
				}

				return entity;

			} catch (Exception e) {
				
				e.printStackTrace();
			}

			return null;
		}

		protected String getColumnKey(String columnName) {
			return RegexUtil.replace_WithUpcase(columnName.toLowerCase());
		}
		/**
		 * 
		 * @param columnName
		 * @return
		 */
		@Deprecated
		protected String getColumnKey2(String columnName) {
			//StringUtils.replace(inString, oldPattern, newPattern)
			//System.out.println("处理前："+columnName);
			StringBuffer buffer=new StringBuffer(columnName.toLowerCase());
			int _index=buffer.indexOf("_");
			while(_index>-1){
				buffer.deleteCharAt(_index);
				if(_index>=buffer.length()){
					break;
				}
				buffer.replace(_index, _index+1, (buffer.charAt(_index)+"").toUpperCase());
				_index=buffer.indexOf("_",_index);
			}
			return buffer.toString();
		}

		 
		
	 
		
		public static void main(String[] args) throws Exception{
			EntityRowMapper m=new EntityRowMapper();
			
			System.out.println(m.getColumnKey("pro_category_id"));
		}

	}