Ext.define('FM.store.bms.Menursces', {
	extend : 'FM.store.Menus',
	root : {
		text : '系统菜单资源',
		id : 'root',
		expanded : true
	},
	autoLoad : false,
	proxy : {
		type : 'ajax',
		api : {
			create : 'bms/menu/createMenu',
			read : 'bms/menu/listMenuFuncRes',
			update : 'bms/menu/updateMenu',
			destroy : 'bms/menu/deleteMenu'
		},

		reader : {
			type : 'json'
		}
	}

});