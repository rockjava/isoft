package com.isoftframework.demo.dao.page.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.isoftframework.dao.IDao;
import com.isoftframework.dao.OrmAndJdbcDao;
import com.isoftframework.demo.dao.page.IClubMemberDAO;
import com.isoftframework.demo.model.page.TClubMemberDTO;
public class ClubMemberDAO implements IClubMemberDAO{
	@Autowired
	IDao myDao;
	 

	/* (non-Javadoc)
	 * @see com.isoftframework.demo.dao.page.IClubMemberDAO#getMemberByCode(java.lang.String)
	 */
	@Override
	public TClubMemberDTO getMemberByCode(String code) throws Exception {
		String hql = "from TClubMemberDTO Z where Z.scode='" + code + "'";
		List list = myDao.find(hql);
		if (list.size() != 0) {
			return (TClubMemberDTO) list.get(0);
		} else {
			return null;
		}
	}

	 

 

}