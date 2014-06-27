package com.isoftframework.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.isoftframework.common.spring.SpringContextUtil;

import com.isoftframework.service.IService;

public abstract class DtoSupport implements Serializable {
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public void save() {

		try {
			IService myBaseService = SpringContextUtil.getBean("myBaseService");
			myBaseService.saveTX(this);
		} catch (Exception e) {
			logger.error("save", e);
		}
	}

	public void delete() {

		try {
			IService myBaseService = SpringContextUtil.getBean("myBaseService");
			myBaseService.deleteTX(this);
		} catch (Exception e) {
			logger.error("delete", e);
		}
	}

	public void update() {

		try {
			IService myBaseService = SpringContextUtil.getBean("myBaseService");
			myBaseService.updateTX(this);
		} catch (Exception e) {
			logger.error("update", e);
		}
	}
}
