package com.isoftstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.isoftframework.service.AbstractService;
import com.isoftframework.service.IService;
import com.isoftstore.model.Category;
import com.isoftstore.service.ICategoryService;
 
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class CategoryService implements ICategoryService
{ 
	@Autowired
	private IService myBaseService;
	
	/* (non-Javadoc)
	 * @see com.isoftstore.service.ICategoryService#listByParentId(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly = true)
	public List listByParentId(String parentId) throws Exception{
		 
		List<Category>  list= myBaseService.find("from Category Z where Z.parentId = '" + parentId + "'");
		 
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.isoftstore.service.ICategoryService#cascadDeleteTX(java.lang.String)
	 */
	@Override
	public int[]  cascadDeleteTX(String id) throws Exception{
		return myBaseService.cascadDeleteTX(id, "I_CATEGORY", "PARENT_ID", "id");
	}

  

}