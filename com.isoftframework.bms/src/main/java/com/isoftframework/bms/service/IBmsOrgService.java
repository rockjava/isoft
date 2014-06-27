package com.isoftframework.bms.service;

import java.util.List;

import com.isoftframework.bms.model.TBmsOrgDTO;

public interface IBmsOrgService {

	public abstract List<TBmsOrgDTO> listByParentId(String parentId)
			throws Exception;

	@SuppressWarnings("unchecked")
	public abstract List<String> findAllChildIdByParentId(String parentId)
			throws Exception;

	public abstract int[] cascadDeleteTX(String id) throws Exception;

	public abstract String generateOrgId(String parentId) throws Exception;

}