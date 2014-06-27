package com.isoftframework.common.pkgenerator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.isoftframework.dao.IDao;
public class TreeId   {
	
	DecimalFormat format=new DecimalFormat("00");
	
	private String root="root";
	
	private  String seperator="-";
	
	private static TreeId instance;
	
	private TreeId(){}
	
	public TreeId(String root,String seperator){
		this.root=root;
		this.seperator=seperator;
	}
	public static TreeId getInstatnce(String root,String seperator){
		if(instance==null){
			instance=new TreeId(root,seperator);
		}
		return instance;
	}
	public static TreeId getInstatnce(){
		if(instance==null){
			instance=new TreeId();
		}
		return instance;
	}
	
	private String format(int n){
		return format.format(n);
	}
	
	public String generate(String parentId,String idName,String parentIdName,String tableName,IDao  dao)throws Exception{
		if(parentId==null)
			return root;
		
		return (parentId.equals("root")?"" : parentId+seperator)+generateNextBrother(  parentId,  idName,  parentIdName,  tableName,  dao);
	}
	
	
	private String generateNextBrother(String parentId,String idName,String parentIdName,String tableName,IDao dao) throws Exception{
		return(this.format(generateSubBrotherId(getChildSubIds(getChildIds( parentId, idName, parentIdName, tableName, dao),parentId))));
	}

	/**
	 * 没有缺失子项 append
	 * 有缺失的子项 insert
	 * @param ids
	 * @return
	 */
	private int generateSubBrotherId(List<Integer> ids){
		if(ids!=null && ids.size()>0){
			Collections.sort(ids);
			int n=1;
			for(int i=0;i<ids.size();n++,i++){
				if(n<ids.get(i)){
					return n;
				}
			}
			return n++;
		}else{
			return 1;
		}
	}
	 
	
	private List<Integer> getChildSubIds(List<String> ids,String parentId){
		if(ids!=null){
			List<Integer> subIds=new ArrayList<Integer>();
			for(String id:ids){
				subIds.add(Integer.parseInt(this.getSubChildId(id, parentId)));
			}
			return subIds;
		}
		return null;
	}
	private List<String> getChildIds(String parentId,String idName,String parentIdName,String tableName,IDao dao) throws Exception {
		String hql="select  t."+idName+" from "+tableName+" t where t."+parentIdName+"='"+parentId+"'";
		return  dao.find(hql);
		//return myDaoSupport.queryForList(sql);
	}
	
	private String getSubChildId(String childId,String parentId){
		if(childId==null){
			return null;
		}
		if(parentId==null||parentId.equals("root"))
			return childId;
		
		return childId.substring((parentId+seperator).length());
	}
	 
	//select max(t.id) from bms_rsc_menu t where t.parentid='"+parentId+"'"
}
