package com.isoftframework.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

public class SQLUtil {

	

	
	private SQLUtil(){}
	
	private static SQLUtil instance;
	
	public static SQLUtil getInstance(){
		 
		if(instance==null){
			instance=new SQLUtil();
		}
		return instance;
	}
	/**
	 * 组装sql in查询字符串
	 * @param idsList
	 * @return
	 */
	public String generateIdsStrForSqlIn(List<String> idsList){
		if(idsList==null || idsList.size()==0){
			return null;
		}
		int len=0;
		String idsStr="'";
		for(String id:idsList){
			if(len>0)
				idsStr+="','";
			idsStr+=id;
			len++;
		}
		idsStr+="'";
		return idsStr;
	}
	
	public String generateIdsStrForSqlIn(List list,String fname){
		if(list==null || list.size()==0){
			return null;
		}
		try {
			Class type=list.get(0).getClass().getDeclaredField(fname).getType();
			
			String idsStr="";
			if(type.isAssignableFrom(String.class)){
				idsStr="'";
			}
			int len=0;
			for(Object obj:list){
				if(len>0){
					if(type.isAssignableFrom(String.class)){
						idsStr+="','";
					}else{
						idsStr+=",";
					}
					
				}
					
				idsStr+=getValue(obj,fname);
				len++;
			}
			if(type.isAssignableFrom(String.class)){
				idsStr+="'";
			}
			
			return idsStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public Object getValue(Object obj,String fname){
		
		try {
			PropertyDescriptor p = PropertyUtils.getPropertyDescriptor(obj, fname); 
			 
			/*Class type=field.getType();
			if(type.isAssignableFrom(int.class)||type.isAssignableFrom(Integer.class)){
				return field.getInt(obj);
			}else{
				return field.get(obj);
			}*/
			return p.getReadMethod().invoke(obj, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
	}
	
	
	 
}
