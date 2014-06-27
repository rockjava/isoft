Ext.define('FM.RequestURL',{
	singleton: true,
	
	//bms_org_generateOrgId: 'bms/org/generateOrgId',
	bms_org_getOrgIcons:'bms/org/getOrgIcons',
	
	//bms_menu_generateMenuId:'bms/menu/generateMenuId',
	//bms_menu_listMenus:'bms/menu/listMenus',
	bms_menu_getMenuIcons:'bms/menu/getMenuIcons',
	
	bms_menu_browseMenu:'bms/menu/browseMenu',
	bms_menu_deleteBrowseMenu:'bms/menu/deleteBrowseMenu',
	bms_menu_createBrowseMenu:'bms/menu/createBrowseMenu',
	bms_user_deleteUsers:'bms/user/deleteUsers',
	
  get: function(field) {
      return this[field];
  }
});
 