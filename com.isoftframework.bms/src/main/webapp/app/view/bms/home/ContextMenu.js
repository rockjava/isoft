Ext.define('FM.view.bms.home.ContextMenu', {
	extend : 'Ext.menu.Menu',
	xtype : 'menuBrowserContextMenu',

	initComponent : function(arguments) {
		this.items = [ {
			text : '打开',
			iconCls : 'open-menu',
			action : 'openMenu',
			listeners : {
				scope : this,
				click : this.onOpenMenuClick
			}
		}, {
			text : '删除',
			iconCls : 'delete',
			action : 'deleteBrowseMenu',
			listeners : {
				scope : this,
				click : this.onDeleteMenuClick
			}
		} ];
		this.callParent(arguments);
		this.addEvents(

		'openMenu', 'deleteBrowseMenu');
	},
	onOpenMenuClick : function(component, e) {
		this.fireEvent('openMenu', component, e);
	},
	onDeleteMenuClick : function(component, e) {
		this.fireEvent('deleteBrowseMenu', component, e);
	},
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