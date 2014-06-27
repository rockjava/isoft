Ext.Loader.setPath('FM', 'app');
//FM={Constants:null};
//FM.Constants=Ext.create('FM.Constants');
//RequestURL=Ext.create('FM.RequestURL');
Ext.data.Types.MENU = {
    convert: function(v, data) {
    	console.log(v);
    	//console.log("icon="+v.get('icon'));
    	return v;
    },
    sortType: function(v) {
        return v.id;  // When sorting, order by latitude
    },
    type: 'menu'
};
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
	name: 'FM',
	appFolder: 'app',
	autoCreateViewport: true,
    requires:['FM.Constants','FM.RequestURL'],
	controllers: [
        'MenuController',
        'bms.MenurscController',
        'bms.OrgController',
        'bms.UserController',
        'isoftstore.CategoryController'
    ],
    launch: function() {
    	Ext.QuickTips.init();
    	
    }
});