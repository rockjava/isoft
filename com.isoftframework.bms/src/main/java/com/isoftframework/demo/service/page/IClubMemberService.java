package com.isoftframework.demo.service.page;

import javax.annotation.Resource;

import com.isoftframework.demo.dao.page.IClubMemberDAO;
import com.isoftframework.demo.model.page.TClubMemberDTO;

public interface IClubMemberService {

	public abstract void deleteTX(String ids) throws Exception;

	public abstract TClubMemberDTO getMemberByCode(String code)
			throws Exception;

	@Resource(name = "myClubMemberDAO")
	public abstract void setMyClubMemberDAO(IClubMemberDAO myClubMemberDAO);

}