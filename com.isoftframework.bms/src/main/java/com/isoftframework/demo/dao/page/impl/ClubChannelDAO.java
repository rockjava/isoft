package com.isoftframework.demo.dao.page.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.isoftframework.dao.IDao;
import com.isoftframework.dao.OrmAndJdbcDao;
import com.isoftframework.demo.dao.page.IClubChannelDAO;
import com.isoftframework.demo.model.page.TClubChannelDTO;

public class ClubChannelDAO implements IClubChannelDAO  {
	@Autowired
	IDao myDao;
	/* (non-Javadoc)
	 * @see com.isoftframework.demo.dao.page.IClubChannelDAO#findEntity(java.lang.String, java.lang.String[])
	 */
	@Override
	public TClubChannelDTO findEntity(String pSql,String[] objs) throws Exception {
		TClubChannelDTO retDto = null;
		List list = myDao.find(pSql, objs);
		if (list != null && list.size() > 0) {
			retDto = (TClubChannelDTO) list.get(0);
		}
		return retDto;
	}

 

	/* (non-Javadoc)
	 * @see com.isoftframework.demo.dao.page.IClubChannelDAO#listByIds(java.lang.String)
	 */
	@Override
	public List listByIds(String ids) throws Exception {
		return myDao.find(
				"from TClubChannelDTO Z where Z.id in (" + ids + ")");
	}

	 
}