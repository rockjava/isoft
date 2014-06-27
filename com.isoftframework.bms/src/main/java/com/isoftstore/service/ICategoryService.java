package com.isoftstore.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ICategoryService {

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public abstract List listByParentId(String parentId) throws Exception;

	public abstract int[] cascadDeleteTX(String id) throws Exception;

}