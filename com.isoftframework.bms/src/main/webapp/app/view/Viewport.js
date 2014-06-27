Ext.define('FM.view.Viewport',{
	extend: 'Ext.Viewport',
	layout: 'fit',
	hideBorders: true,
	requires : [
		'FM.view.Header',
		'FM.view.Menu',
		'FM.view.TabPanel',
		'FM.view.East',
		'FM.view.South'
	],
	initComponent : function(){
		var me = this;
        Ext.apply(me, {
            items: [{
            	id:'desk',
				layout: 'border',
				items: [
					//{xtype:'fmheader',region:'north'},
					{xtype:'fmNavMenu',region:'west'},
					{xtype:'fmTabPanel',region: 'center'},
					//{xtype:'east',region:'east'},
					
					Ext.create('FM.view.South',{region:"south"})
				]
			}]
        });
        me.callParent(arguments);
	}
});