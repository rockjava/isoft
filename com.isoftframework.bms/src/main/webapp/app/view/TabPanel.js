Ext.define('FM.view.TabPanel', {
	extend : 'Ext.tab.Panel',
	requires : [ 'FM.ux.TabScrollerMenu', 'FM.ux.TabCloseMenu',
			'FM.view.bms.home.Home' ],
	alias : 'widget.fmTabPanel',
	id : 'content-panel',
	plain: true,
	autoDestroy : FM.Constants.get('autoDestroy'),
	closeAction : FM.Constants.get('closeAction'),
	resizeTabs : true,
	enableTabScroll : true,
	defaults : {
		autoScroll : true,
		bodyPadding : 10
	},
	activeTab : 0,
	border : false,

	initComponent : function() {
		Ext.apply(this, {
			plugins : [ Ext.create('FM.ux.TabCloseMenu')
			/*
			 * { ptype: 'tabscrollermenu', maxText : 15, pageSize : 2 }
			 */
			],
			items : [ {
				xtype : 'home',
				url : FM.RequestURL.get('bms_menu_browseMenu'),
				id : 'HomePage',
				title : '首页',
				iconCls : 'home',
				layout : 'fit'
			} ]
		});
		this.callParent(arguments);
	}
});