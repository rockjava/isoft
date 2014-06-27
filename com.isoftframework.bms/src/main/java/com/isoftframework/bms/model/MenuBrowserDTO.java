package com.isoftframework.bms.model;

import com.isoftframework.model.DtoSupport;


public class MenuBrowserDTO extends DtoSupport{

	public MenuBrowserDTO(){}
	/**
	 * 
	 */
	private String menuId;
	private int sort;
	private TBmsRscMenuDTO menu;
	
	
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public TBmsRscMenuDTO getMenu() {
		return menu;
	}

	public void setMenu(TBmsRscMenuDTO menu) {
		this.menu = menu;
	}

 

	 
}
