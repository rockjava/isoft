package com.isoftframework.bms.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.isoftframework.bms.dao.IBmsMenuRscDAO;
import com.isoftframework.bms.model.TBmsRscMenuDTO;
import com.isoftframework.dao.IDao;
public class BmsMenuRscDAO implements IBmsMenuRscDAO  
{

	@Autowired
	IDao myDao;
	
	public IDao getMyDao() {
		return myDao;
	}

	public void setMyDao(IDao myDao) {
		this.myDao = myDao;
	}

	/* (non-Javadoc)
	 * @see com.isoftframework.bms.dao.IBmsMenuRscDAO#listByIds(java.lang.String)
	 */
	@Override
	public List listByIds(String ids) throws Exception
	{
		return myDao.find(
				"from TBmsRscMenuDTO Z where Z.id in (" + ids + ")");
	}

	/* (non-Javadoc)
	 * @see com.isoftframework.bms.dao.IBmsMenuRscDAO#listByParentId(java.lang.String)
	 */
	@Override
	public List listByParentId(String parentId) throws Exception{
		return myDao.find(
				"from TBmsRscMenuDTO Z where Z.parentId = '" + parentId + "'");
	}
	/* (non-Javadoc)
	 * @see com.isoftframework.bms.dao.IBmsMenuRscDAO#listMenuByParentId(java.lang.String)
	 */
	@Override
	public List listMenuByParentId(String parentId) throws Exception{
		return myDao.find(
				"select Z from TBmsRscMenuDTO Z where Z.parentId = '" + parentId + "' and Z.type='1'");
	}
	

	/* (non-Javadoc)
	 * @see com.isoftframework.bms.dao.IBmsMenuRscDAO#listUserCanVisitRsc(java.lang.String)
	 */
	@Override
	public List listUserCanVisitRsc(String id) throws Exception
	{
		String hql = "select b.TBmsRscMenuDTO from TBmsRoleFuncDTO b where b.tbTBmsRoleDTO in("
				+ "select c.tbTBmsRoleDTO from TBmsUserRoleDTO c where c.tbTBmsUserDTO.id = ?"
				+ ")";
		return myDao.find(hql, id);
	}

	/* (non-Javadoc)
	 * @see com.isoftframework.bms.dao.IBmsMenuRscDAO#getParents(com.isoftframework.bms.model.TBmsRscMenuDTO, java.util.List)
	 */
	@Override
	public void getParents(TBmsRscMenuDTO p, List list) throws Exception
	{
		if (p == null || list.contains(p))
			return;

		if (p.getParentId() != null)
		{
			TBmsRscMenuDTO parent = (TBmsRscMenuDTO) myDao.get(TBmsRscMenuDTO.class,
							p.getParentId());
			getParents(parent, list);
		}

		list.add(p);
	}

}