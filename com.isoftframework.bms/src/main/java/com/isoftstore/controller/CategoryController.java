package com.isoftstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftframework.controller.BaseController;
import com.isoftstore.model.Category;
import com.isoftstore.service.ICategoryService;

@Controller
@RequestMapping(value = "/isoftstore/category")
public class CategoryController extends BaseController {

	@Autowired
	ICategoryService myCategoryService=null;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@RequestParam("node") String parentNode)
			throws Exception {
		return myCategoryService.listByParentId(parentNode);
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(@RequestBody Category category,
			BindingResult result) throws Exception {
		logger.debug("--update menu");
		if (result.hasErrors()) {
			logger.error("type convert error!");
		}
		category.update();

		return ("{success:true,'msg':'保存成功！'}");
	}

	@RequestMapping(value = "/create")
	@ResponseBody
	public Object create(@RequestBody Category category)
			throws Exception {
		logger.debug("--create menu");
		category.save();

		return ("{\"success\":true,\"msg\":\"保存成功！\"}");
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestBody Category category)
			throws Exception {
		logger.debug("--delete --");
		myCategoryService.cascadDeleteTX(category.getId());
		return ("{\"success\":true,\"msg\":\"删除成功！\"}");
	}

	

}
