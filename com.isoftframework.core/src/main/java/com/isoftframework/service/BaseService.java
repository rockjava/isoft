package com.isoftframework.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
final public class BaseService extends AbstractService {

 

	 

	/*@Override
	final public Class getEntityClass() {
		return Object.class;
	}*/

}
