Ext.define('FM.view.bms.org.OrgTree', {
    extend: 'FM.view.base.tree.TreeWithToolAction',
    alias: 'widget.orgTree',
	 
	store:'bms.Orgs',
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
	  		    iconCls: 'org-new',
	  		    tooltip: '新建下级组织机构',
	  		    id:'new-org-btn'
	  		  },
	  		  {
                iconCls: 'org-delete',
                tooltip: '删除组织机构',
                id:'delete-org-btn'
	  		  } 
	        ]
	    }];
		me.callParent(arguments);
    },
   
	 
});