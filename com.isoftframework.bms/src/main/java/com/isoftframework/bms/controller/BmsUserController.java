package com.isoftframework.bms.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftframework.bms.model.TBmsUserDTO;
import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.common.util.HttpUtil;
import com.isoftframework.common.util.SQLUtil;
@Controller
@RequestMapping("/bms/user")  
public class BmsUserController extends BmsController{

//bms/user/listUsers
	@RequestMapping(value="/listUsers")
	@ResponseBody
	public Object listUsers(HttpServletRequest request)throws Exception {
			AbstractPageInfo pageInfo=listUserByJdbc(request);
			// 调用业务方法
			List<TBmsUserDTO> result = this.myBaseService.findAsPageList(pageInfo,TBmsUserDTO.class);
			Map map=new LinkedHashMap();
			map.put("totalCount", getContext(request).getPageInfo().getTotalSize());
			map.put("users", result);
			return map;
	} 
	
	@RequestMapping(value="/createUser")
	@ResponseBody
	public Object createUser(@RequestBody TBmsUserDTO bmsUser  )throws Exception {
		logger.debug("--create menu");
		this.myBaseService.saveTX(bmsUser);
		return JSON_SAVE_SUC;
	}
	
	@RequestMapping(value="/updateUser")
	@ResponseBody
	public Object updateUser(@RequestBody TBmsUserDTO bmsUser )throws Exception {
		logger.debug("--create menu");
		this.myBaseService.updateTX(bmsUser);
		return JSON_SAVE_SUC;
	}
	@RequestMapping(value="/deleteUser")
	@ResponseBody
	public Object deleteUser(@RequestBody TBmsUserDTO bmsUser )throws Exception {
		//Thread.currentThread().sleep(1000*5);
		this.myBaseService.deleteTX(bmsUser);
		return JSON_DEL_SUC;
	}
	@RequestMapping(value="/deleteUsers")
	@ResponseBody
	public Object deleteUsers(@RequestParam String ids )throws Exception {
		//Thread.currentThread().sleep(1000*5);
		this.myBaseService.execUpdateTX("delete from TBmsUserDTO dto where dto.id in("+ids+")");
		return JSON_DEL_SUC;
	}
	
	

	private AbstractPageInfo listUserByJdbc(HttpServletRequest request) throws Exception{
		String querySql=" select a.id,"+
		       "a.sex,"+
		       "a.real_name,"+
		       "a.mobile,"+
		       "a.email,"+
		       "a.fax,"+
		       "a.phone,"+
		       "a.address,"+
		       "a.enabled,"+
		       "a.remark,"+
		       "a.password,"+
		       "a.orgid,"+
		       "a.username,"+
		       "b.name as org_name "+
		  "from bms_user a "+
		  "join bms_org b on a.orgid = b.id "+
		  " where 1 = 1 ";
		String orgid=HttpUtil.getParameter(request, "orgid");
		if(orgid!=null && !orgid.equals("")&& !orgid.equals("root")){
			String orgids=SQLUtil.getInstance().generateIdsStrForSqlIn(myBmsOrgService.findAllChildIdByParentId(orgid));
			querySql+=" and a.orgid in("+orgids+")";
		}
		String loginName=HttpUtil.getParameter(request, "loginName");
		if(loginName!=null && !loginName.trim().equals("")){
			querySql+=" and a.login_name like '%"+loginName+"%'";
		}
		
		String sex=HttpUtil.getParameter(request, "sex");
		if(sex!=null && !sex.trim().equals("")){
			querySql+=" and sex ="+sex;
		}
		
		String realName=HttpUtil.getParameter(request, "realName");
		if(realName!=null && !realName.trim().equals("")){
			querySql+=" and a.real_name like('%"+realName+"%')";
		}
		
		querySql+=" order by a.orgid";
		logger.info("querySql==="+querySql);
		// 查询前计算分页数据
		return computeJdbcPageInfoBefore(request, querySql);
	}
	private AbstractPageInfo listUserByHibernate(HttpServletRequest request) throws Exception{
		String querySql="select dto from BmsUserDTO dto where 1=1 ";
		String orgid=HttpUtil.getParameter(request, "orgid");
		if(orgid!=null && !orgid.equals("")&& !orgid.equals("root")){
			String orgids=SQLUtil.getInstance().generateIdsStrForSqlIn(myBmsOrgService.findAllChildIdByParentId(orgid));
			querySql+=" and orgid in("+orgids+")";
		}
		//querySql+=" order by dto.orgid";
		logger.info("querySql==="+querySql);
		// 查询前计算分页数据
		return computeHibernatePageInfoBefore(request, querySql);
	}
	
	
}
