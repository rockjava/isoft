package com.isoftframework.bms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isoftframework.bms.dao.IBmsMenuRscDAO;
import com.isoftframework.bms.model.TBmsRscMenuDTO;
import com.isoftframework.bms.service.IBmsMenuRscService;
import com.isoftframework.common.pkgenerator.TreeId;
import com.isoftframework.service.IService;
 
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class BmsMenuRscService implements IBmsMenuRscService
{ 
	private IBmsMenuRscDAO myBmsMenuRscDAO;
	private IService myBaseService;

	
	
	
	

	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsMenuRscService#listByIds(java.lang.String)
	 */
	@Override
	public List listByIds(String ids) throws Exception
	{
		ids = "'" + ids.replaceAll(",", "','") + "'";
		return myBmsMenuRscDAO.listByIds(ids);
	}
	
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsMenuRscService#listByParentId(java.lang.String)
	 */
	@Override
	public List listByParentId(String parentId) throws Exception{
		List<TBmsRscMenuDTO> list= myBmsMenuRscDAO.listByParentId(parentId);
		if(list!=null && list.size()>0){
			for(TBmsRscMenuDTO m:list){
				m.setLeaf(m.getType()=='3');
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsMenuRscService#listMenuByParentId(java.lang.String)
	 */
	@Override
	public List listMenuByParentId(String parentId) throws Exception{
		
		List<TBmsRscMenuDTO>  list= myBmsMenuRscDAO.listMenuByParentId(parentId);
		if(list!=null && list.size()>0){
			for(TBmsRscMenuDTO m:list){
				m.setLeaf(m.isMenuLeaf());
			}
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsMenuRscService#cascadDelete(java.lang.String)
	 */
	@Override
	public void  cascadDelete(String id) throws Exception{
		List<String> list=getMeAndAllChild(id,new ArrayList<String>());
		if(list!=null && list.size()>0)
			myBaseService.batchUpdateTX(list.toArray(new String[list.size()]));
	}
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsMenuRscService#getMeAndAllChild(java.lang.String, java.util.List)
	 */
	@Override
	public List<String>   getMeAndAllChild(String id,List<String> list) throws Exception{
		
		List<String> child=myBaseService.findBySql("select id from bms_rsc_menu t where t.parentid='"+id+"'");
		if(child!=null && child.size()>0){
			for(String arr:child ){
				getMeAndAllChild(arr,list);
			}
		}
		list.add("delete from bms_rsc_menu t where t.id='"+id+"'");
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsMenuRscService#generateMenuId(java.lang.String)
	 */
	@Override
	public String generateMenuId(String parentId) throws Exception{
		
		return myBaseService.generateIdForTree(parentId, "id", "parentId", "TBmsRscMenuDTO");
		
	}
	
	//=======================================
	@Resource(name="myBmsMenuRscDAO")
	public void setMyBmsMenuRscDAO(IBmsMenuRscDAO myBmsMenuRscDAO) {
		this.myBmsMenuRscDAO = myBmsMenuRscDAO;
	}

	@Resource(name="myBaseService")
	public void setMyBaseService(IService myBaseService) {
		this.myBaseService = myBaseService;
	}

	 

	 


	 
  

}