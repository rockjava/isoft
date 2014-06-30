package com.isoftframework.demo.service.page.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.isoftframework.common.page.pageInfo.hibernate.HibernatePageInfo;
import com.isoftframework.demo.dao.page.IClubMemberDAO;
import com.isoftframework.demo.model.page.TClubMemberDTO;
import com.isoftframework.demo.service.page.IClubMemberService;
import com.isoftframework.service.AbstractService;
import com.isoftframework.service.IService;

@Transactional
public class ClubMemberService implements IClubMemberService
{	
	@Autowired
	private IService myBaseService;
	
	private IClubMemberDAO myClubMemberDAO;

	/* (non-Javadoc)
	 * @see com.isoftframework.demo.service.page.IClubMemberService#deleteTX(java.lang.String)
	 */
	@Override
	public void deleteTX(String ids)throws Exception
	{
		String[] id = ids.split(",");
		
		List list = new ArrayList();
		for (int i = 0; i < id.length; i++)
		{
			list.add(new TClubMemberDTO(id[i]));
		}
		if (list.size() > 0)
		{
			this.myBaseService.deleteAllTX(list);
		}
	}

	
	

	/* (non-Javadoc)
	 * @see com.isoftframework.demo.service.page.IClubMemberService#getMemberByCode(java.lang.String)
	 */
	@Override
	public TClubMemberDTO getMemberByCode(String code) throws Exception{
		return this.myClubMemberDAO.getMemberByCode(code);
	}

	
 //===========setSpring注射方法
	
	/* (non-Javadoc)
	 * @see com.isoftframework.demo.service.page.IClubMemberService#setMyClubMemberDAO(com.isoftframework.demo.dao.page.IClubMemberDAO)
	 */
	@Override
	@Resource(name="myClubMemberDAO")
	public void setMyClubMemberDAO(IClubMemberDAO myClubMemberDAO)
	{
		this.myClubMemberDAO=myClubMemberDAO;
	}

	 
}