package com.isoftframework.bms.dao;

import java.util.List;

import com.isoftframework.bms.model.TBmsRscMenuDTO;

public interface IBmsMenuRscDAO {

	public abstract List listByIds(String ids) throws Exception;

	public abstract List listByParentId(String parentId) throws Exception;

	public abstract List listMenuByParentId(String parentId) throws Exception;

	public abstract List listUserCanVisitRsc(String id) throws Exception;

	public abstract void getParents(TBmsRscMenuDTO p, List list)
			throws Exception;

}