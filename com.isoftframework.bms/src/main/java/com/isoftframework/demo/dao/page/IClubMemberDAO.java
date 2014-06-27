package com.isoftframework.demo.dao.page;

import com.isoftframework.demo.model.page.TClubMemberDTO;

public interface IClubMemberDAO {

	public abstract TClubMemberDTO getMemberByCode(String code)
			throws Exception;

}