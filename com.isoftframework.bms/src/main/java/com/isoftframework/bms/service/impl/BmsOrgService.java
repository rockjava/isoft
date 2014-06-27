package com.isoftframework.bms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isoftframework.bms.model.TBmsOrgDTO;
import com.isoftframework.bms.service.IBmsOrgService;
import com.isoftframework.dao.IDao;
import com.isoftframework.service.AbstractService;
import com.isoftframework.service.IService;

@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class BmsOrgService implements IBmsOrgService{
	
	@Autowired
	private IService myBaseService;
	
	@Autowired
	IDao myDao;
	
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsOrgService#listByParentId(java.lang.String)
	 */
	@Override
	public List<TBmsOrgDTO> listByParentId(String parentId) throws Exception{
		return myBaseService.find(
				"from TBmsOrgDTO Z where Z.parentId = '" + parentId + "'");
		 
	}
	
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsOrgService#findAllChildIdByParentId(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> findAllChildIdByParentId(String parentId) throws Exception{
		return myDao.findAllChildIdByParentId( parentId, "id", "parentid", "bms_org");
	 
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsOrgService#cascadDeleteTX(java.lang.String)
	 */
	@Override
	public int[]  cascadDeleteTX(String id) throws Exception{
		return myBaseService.cascadDeleteTX(id, "BMS_ORG", "parentid", "id");
	}
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.service.IBmsOrgService#generateOrgId(java.lang.String)
	 */
	@Override
	public String generateOrgId(String parentId) throws Exception {
		return myBaseService.generateIdForTree(parentId, "id", "parentId", "TBmsOrgDTO");
	}
	
	
}
