package com.isoftframework.bms.service;

import java.util.List;

public interface IBmsMenuRscService {

	public abstract List listByIds(String ids) throws Exception;

	public abstract List listByParentId(String parentId) throws Exception;

	public abstract List listMenuByParentId(String parentId) throws Exception;

	/**
	 * 删除节点 和 其子节点
	 * @param id
	 * @throws Exception 
	 */
	public abstract void cascadDelete(String id) throws Exception;

	public abstract List<String> getMeAndAllChild(String id, List<String> list)
			throws Exception;

	public abstract String generateMenuId(String parentId) throws Exception;

}