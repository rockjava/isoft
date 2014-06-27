package com.isoftframework.demo.dao.page;

import java.util.List;

import com.isoftframework.demo.model.page.TClubChannelDTO;

public interface IClubChannelDAO {

	/**
	 * 查找一条记录
	 * 
	 * @param pSql
	 * @return
	 * @throws Exception 
	 */
	public abstract TClubChannelDTO findEntity(String pSql, String[] objs)
			throws Exception;

	public abstract List listByIds(String ids) throws Exception;

}