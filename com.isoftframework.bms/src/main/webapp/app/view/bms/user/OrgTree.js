Ext.define('FM.view.bms.user.OrgTree', {
    extend: 'FM.view.base.tree.TreeWithSearch',
    alias: 'widget.userOrgTree',
   // title: '组织机构',
    store:'bms.Orgs',
    initComponent : function(){
		var me=this;
		this.dockedItems= [{
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
	    }];
		me.callParent(arguments);
    }
});