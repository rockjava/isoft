Ext.define('FM.view.isoftstore.category.CategoryTree', {
    extend: 'FM.view.base.tree.TreeWithToolAction',
    alias: 'widget.icategoryTree',
	 
	store:'isoftstore.Categories',
	viewConfig: {
		plugins: {
			ptype: 'treeviewdragdrop',
			containerScroll: true
		}
    },
    initComponent : function(){
		var me=this;
		me.dockedItems= [{
	        dock: 'top',
	        xtype: 'toolbar',
	        items: {
	            width: 200,
	            fieldLabel: '检索',
	            labelWidth: 40,
	            xtype: 'searchfield2',
	            store: me.store,
	            listeners:{
	            	scope:me,
	            	search : me.handleSearchOrgTree,
					continueSearch : me.handleContinueSearchOrgTree,
					clearSearch : me.handleClearSearchOrgTree
	            }
	        } 
	    },{
	      xtype: 'toolbar',
	      dock: 'bottom',
	      items: [
			  {
			    iconCls: 'add',
			    action:'addChild',
			    tooltip: '新建下级类别',
			    handler: Ext.bind(me.handleNewNodeBtnClick, me)
			  },
	          {
	              iconCls: 'delete',
	              action:'delete',
	              tooltip: '删除类别' ,
	              handler: Ext.bind(me.handleDeleteNodesBtnClick, me)
	          } 
	      ]
	    }];
		me.callParent(arguments);
		 
    }
	 
});