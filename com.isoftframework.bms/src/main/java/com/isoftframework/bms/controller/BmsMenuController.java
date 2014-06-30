package com.isoftframework.bms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftframework.bms.model.MenuBrowserDTO;
import com.isoftframework.bms.model.TBmsRscMenuDTO;

@Controller
@RequestMapping(value = "/bms/menu")
public class BmsMenuController extends BmsController {

	@RequestMapping(value = "/listMenus")
	@ResponseBody
	public Object listMenus(@RequestParam("node") String parentNode)
			throws Exception {
		return myBmsMenuRscService.listMenuByParentId(parentNode);
	}

	/**
	 * 获取菜单和菜单资源
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/listMenuFuncRes")
	@ResponseBody
	public Object listMenuFuncRes(@RequestParam("node") String parentNode,
			HttpServletResponse response) throws Exception {
		return myBmsMenuRscService.listByParentId(parentNode);
	}

	@RequestMapping(value = "/updateMenu")
	@ResponseBody
	public Object updateMenu(@RequestBody TBmsRscMenuDTO treeNode,
			BindingResult result) throws Exception {
		logger.debug("--update menu");
		if (result.hasErrors()) {
			logger.error("type convert error!");
		}
		this.myBaseService.updateTX(treeNode);

		return ("{success:true,'msg':'保存成功！'}");
	}

	@RequestMapping(value = "/createMenu")
	@ResponseBody
	public Object createMenu(@RequestBody TBmsRscMenuDTO treeNode)
			throws Exception {
		logger.debug("--create menu");
		this.myBaseService.saveTX(treeNode);

		return ("{\"success\":true,\"msg\":\"保存成功！\"}");
	}

	@RequestMapping(value = "/deleteMenu")
	@ResponseBody
	public Object deleteMenu(@RequestBody TBmsRscMenuDTO treeNode)
			throws Exception {
		logger.debug("--delete menu");

		this.myBmsMenuRscService.cascadDelete(treeNode.getMenuId());
		return ("{\"success\":true,\"msg\":\"删除成功！\"}");
	}

	@RequestMapping(value = "/generateMenuId")
	@ResponseBody
	public Object generateMenuId(@RequestParam("parentId") String parentId)
			throws Exception {
		String menuId = this.myBmsMenuRscService.generateMenuId(parentId);
		return ("{'id':'" + menuId + "'}");
	}

	@RequestMapping(value = "/getMenuIcons")
	@ResponseBody
	public Object getMenuIcons(HttpServletRequest request) throws Exception {

		return getIconNodes("resources/images/menu/", request);
	}

	// ==========browseMenu==========

	@RequestMapping(value = "/browseMenu")
	@ResponseBody
	public Object browseMenu(HttpServletResponse response) throws Exception {
		// return myBmsMenuRscService.find("from MenuBrowserDTO dto ");
		return myBaseService
				.queryEntityForList("select m.* from BMS_RSC_MENU m right join BMS_MENU_BROWSER bm on m.id=bm.menu_id",TBmsRscMenuDTO.class);
	}

	@RequestMapping(value = "/createBrowseMenu")
	@ResponseBody
	public Object createBrowseMenu(@RequestBody MenuBrowserDTO dto)
			throws Exception {
		dto.setMenu(new TBmsRscMenuDTO(dto.getMenuId()));
		dto.save();
		// this.myBmsMenuRscService.saveTX(dto);

		return JSON_SAVE_SUC;
	}

	@RequestMapping(value = "/deleteBrowseMenu")
	@ResponseBody
	public Object deleteBrowseMenu(@RequestBody MenuBrowserDTO dto)throws Exception {
		dto.delete();

		return JSON_DEL_SUC;
	}

}
