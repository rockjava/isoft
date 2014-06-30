package com.isoftframework.bms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftframework.bms.model.TBmsOrgDTO;
import com.isoftframework.common.exception.JsonOutException;
@Controller
public class BmsOrgController extends BmsController {


	 
	@RequestMapping(value="/bms/org/listOrgs")
	@ResponseBody
	public Object listOrgs(@RequestParam("node") String parentNode)
			throws Exception {

		return myBmsOrgService.listByParentId(parentNode);
		 
	} 
	
	@RequestMapping(value="/bms/org/getOrgIcons")
	@ResponseBody
	public Object getOrgIcons(HttpServletRequest request)
			throws Exception {
		return getIconNodes("resources/images/org/",request);
	}
	
	
	
	@RequestMapping(value="/bms/org/generateOrgId")
	@ResponseBody
	public Object generateOrgId(@RequestParam("parentId") String parentId)
	throws Exception {
		String orgid=this.myBmsOrgService.generateOrgId(parentId);
		return ( "{'id':'"+orgid+"'}");
	}
	
	@RequestMapping(value="/bms/org/createOrg",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object createOrg(@RequestBody TBmsOrgDTO treeNode,BindingResult result)throws Exception {
		if (result.hasErrors()) {
	        logger.error("type convert error");
	        throw new JsonOutException("type convert error");
	    }
		this.myBaseService.saveTX(treeNode);
		return ( "{success:true,'msg':'保存成功！'}");
	}
	
	@RequestMapping(value="/bms/org/updateOrg")
	@ResponseBody
	public Object updateOrg(@RequestBody TBmsOrgDTO treeNode,BindingResult result)throws Exception {
		logger.debug("--update menu");
		if (result.hasErrors()) {
	        logger.error("type convert error");
	        throw new JsonOutException("type convert error");
	    }
		this.myBaseService.updateTX(treeNode);
		return ("{success:true,'msg':'保存成功！'}");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/bms/org/deleteOrg")
	@ResponseBody
	public Object deleteOrg(@RequestBody TBmsOrgDTO treeNode)throws Exception {
		/**
		 * TODO
		 * 没有对组织机构下关联用户的情况作提示
		 */
		logger.debug("--delete menu");
		this.myBmsOrgService.cascadDeleteTX(treeNode.getId());
		return ("{success:true,'msg':'删除成功！'}");
	}
	
	 
}
