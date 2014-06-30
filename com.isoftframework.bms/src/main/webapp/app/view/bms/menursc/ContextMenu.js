/**
 * @class FM.view.bms.sysMenuManage.ContextMenu
 * @extends Ext.menu.Menu
 */
Ext.define('FM.view.bms.menursc.ContextMenu', {
	extend : 'Ext.menu.Menu',
	xtype : 'menurscContextMenu',
	items : [ {
		text : '新建菜单',
		iconCls : 'new-menu',
		id : 'new-menu-item'
	}, {
		text : '新建功能',
		iconCls : 'new-func',
		id : 'new-func-item'
	}, {
		text : '新建资源',
		iconCls : 'new-res',
		id : 'new-res-item'
	}, '-', {
		text : '删除',
		iconCls : 'delete-menu',
		id : 'delete-menu-item'
	} ],

	/**
	 * Associates this menu with a specific sysmenu.
	 * 
	 * @param {SimpleTasks.model.List}
	 *          list
	 */
	setSysMenu : function(sysMenu) {
		this.sysMenu = sysMenu;
	},

	/**
	 * Gets the sysmenu associated with this menu
	 * 
	 * @return {Task.model.List}
	 */
	getSysMenu : function() {
		return this.sysMenu;
	}

});