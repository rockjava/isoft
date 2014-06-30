Ext.define('FM.store.Menus', {
	extend : 'Ext.data.TreeStore',
	requires : [ 'FM.model.Menu', 'FM.RequestURL','FM.Constants' ],
	model : 'FM.model.Menu',
	root : {
		text : 'root',
		id : 'root',
		expanded : true
	},
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url : FM.Constants.get('useLocalStorage') ? 'data/menu.json' : 'bms/menu/listMenus',
		reader : {
			type : 'json'
		}
	}

});