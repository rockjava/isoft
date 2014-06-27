Ext.define('FM.view.bms.user.UserManage',{
    extend:'Ext.panel.Panel',
    alias:'widget.userManagePanel',
    layout: 'border',
    title: '用户管理',
    items: [
        { 
        	xtype: 'userOrgTree',
        	region:'west'
        } ,
        {
        	xtype:'container',
        	region:'center',
        	layout: 'border',
        	items:[
               { 
               	xtype: 'userQueryForm',
               	region: 'north'
               },
               { 
               	xtype: 'userList',
               	region: 'center'
               }
           ]
        }
       
       
        
    ]
});