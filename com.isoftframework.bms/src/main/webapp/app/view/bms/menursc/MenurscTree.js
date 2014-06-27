Ext.define('FM.view.bms.menursc.MenurscTree', {
    extend: 'FM.view.base.tree.TreeWithToolAction',
    alias: 'widget.menurscTree',
 // title: '系统菜单管理',
	store:'bms.Menursces',
	autoDestroy: FM.Constants.get('autoDestroy')  ,
	closeAction:FM.Constants.get('closeAction'),
    dockedItems: [{
      xtype: 'toolbar',
      dock: 'bottom',
      items: [
		  {
			  
			iconCls: 'new-menu',
		    tooltip: '新建菜单',
		    id:'new-menu-btn'
		  },
          {
              iconCls: 'new-func',
              tooltip: '新建功能',
              id:'new-func-btn'
          },
          {
              iconCls: 'new-res',
              tooltip: '新建资源',
              id:'new-res-btn'
          },
          {
              iconCls: 'delete-menu',
              id: 'delete-menu-btn',
              tooltip: '删除'
          } 
      ]
    }] 
    
   
    	
	 
});