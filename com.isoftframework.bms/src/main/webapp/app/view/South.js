Ext.define('FM.view.South',{
	extend: 'Ext.Toolbar',
	id:"bottom",
	//frame:true,
	height:23,
	initComponent : function(){
		Ext.apply(this,{
			
			items:["当前用户：Guest",'->',"技术支持:<a href='973598066@qq.com' target='_blank' style='text-decoration:none;'><font color='#0000FF'>973598066@qq.com</font></a>&nbsp;&nbsp;"]
		});
		this.callParent(arguments);
	}
})