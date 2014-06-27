Ext.define('FM.view.Menu', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.fmNavMenu',
	id : 'menu-panel',
	title : '导航菜单',
	iconCls : 'icon-navmenu',
	margins : '0 0 -1 1',
	border : false,
	width : 212,
	minSize : 130,
	maxSize : 300,
	
	animate : true,// 动画效果

	split : true,
	
	collapsible : true,
	// floatable : false,

	rootVisible : false, // 隐藏根节点

	autoScroll : false,// 自动滚动
	containerScroll : true,
	store : 'Menus',
	viewConfig : {
		plugins : {
			ddGroup : 'menubrowser',
			ptype : 'gridviewdragdrop',
			enableDrop : false
		}
	}
});