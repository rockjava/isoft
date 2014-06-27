Ext.define('FM.view.bms.user.UserList',{
	extend:'Ext.grid.Panel',
	alias: ['widget.userList'],
	store: 'bms.Users',
	loadMask: true,
	//title:"用户",
	height:'100%',
	dockedItems: [
	   {
	      xtype: 'toolbar',
	      items:[ 
	        '->',
	  	    {
	            iconCls: 'user-add',
	            id: 'user-add-btn',
	            action:'addUser',
	            tooltip: '新增用户' 
	        } ,
	        {
	            iconCls: 'user-delete',
	            id: 'user-delete-btn',
	            action:'deleteUsers',
	            tooltip: '删除用户' 
	        }
	      ],
	      
	      dock: 'top'
	   } ,
	   {
	        xtype: 'pagingtoolbar',
	        store: 'bms.Users',   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }
	],
	
	initComponent: function() {
		 var me = this;
		 me.columns=[{
	         id: 'id',
	         text: "ID",
	         dataIndex: 'id',
	         flex: 1,
	         width: 24,
	         hidden: true,
	         sortable: false
	     },{
	         text: "组织机构ID",
	         dataIndex: 'orgid',
	         align: 'left',
	         sortable: true,
	         hidden: true
	     },{
	         text: "组织机构",
	         dataIndex: 'orgName',
	         align: 'left',
	         sortable: true
	     },{
	         text: "用户名",
	         dataIndex: 'username',
	         width: 100,
	         align: 'left',
	         sortable: true
	     },{
	         text: "真实姓名",
	         dataIndex: 'realName',
	         width: 70,
	         align: 'left',
	         sortable: true
	     },{
	         text: "性别",
	         dataIndex: 'sex',
	         width: 150,
	         align: 'center',
	         sortable: true,
	         renderer: function(value){
	             if (value ==='1') {
	                 return '男';
	             }
	             return '女';
	         }
	     },{
	         xtype: 'actioncolumn',
	         text: "修改",
	       //  cls: 'tasks-icon-column-header tasks-edit-column-header',
	         width: 30,
	         icon: 'resources/images/btn/fam/edit.png',
	        // iconCls: 'x-hidden',
	         tooltip: 'Edit',
	         menuDisabled: true,
	         sortable: false,
	         draggable: false,
	         resizable: false,
	         align: 'center',
	         handler: Ext.bind(me.handleEditClick, me)
	     },
	     {
	         xtype: 'actioncolumn',
	         text: "删除",
	        // cls: 'tasks-icon-column-header tasks-delete-column-header',
	         width: 30,
	         icon: 'resources/images/btn/fam/delete.gif',
	       //  iconCls: 'x-hidden',
	         tooltip: 'Delete',
	         menuDisabled: true,
	         sortable: false,
	         draggable: false,
	         resizable: false,
	         align: 'center',
	         handler: Ext.bind(me.handleDeleteClick, me)
	     }];
		 me.addEvents(
            /**
             * @event editclick
             * Fires when an edit icon is clicked
             * @param {Ext.grid.View} view
             * @param {Number} rowIndex
             * @param {Number} colIndex
             * @param {Ext.grid.column.Action} column
             * @param {EventObject} e
             */
            'editclick',

            /**
             * @event deleteclick
             * Fires when a delete icon is clicked
             * @param {Ext.grid.View} view
             * @param {Number} rowIndex
             * @param {Number} colIndex
             * @param {Ext.grid.column.Action} column
             * @param {EventObject} e
             */
            'deleteclick'
		 );
		 /*
		 me.listeners = {
			'itemmouseenter': me.showActions,
	        'itemmouseleave': me.hideActions
			 
		};*/
		 me.callParent(arguments);
	},
	 /**
     * Handles a click on the edit icon
     * @private
     * @param {Ext.grid.View} gridView
     * @param {Number} rowIndex
     * @param {Number} colIndex
     * @param {Ext.grid.column.Action} column
     * @param {EventObject} e
     */
    handleEditClick: function(gridView, rowIndex, colIndex, column, e) {
        // Fire a "deleteclick" event with all the same args as this handler
        this.fireEvent('editclick', gridView, rowIndex, colIndex, column, e);
    },

    /**
     * Handles a click on a delete icon
     * @private
     * @param {Ext.grid.View} gridView
     * @param {Number} rowIndex
     * @param {Number} colIndex
     * @param {Ext.grid.column.Action} column
     * @param {EventObject} e
     */
    handleDeleteClick: function(gridView, rowIndex, colIndex, column, e) {
        // Fire a "deleteclick" event with all the same args as this handler
        this.fireEvent('deleteclick', gridView, rowIndex, colIndex, column, e);
    },
    /**
     * Handles a mouseenter event on a task grid item.
     * Shows the item's action icons.
     * @param {Ext.grid.View} view
     * @param {SimpleTasks.model.Task} task
     * @param {HTMLElement} node
     * @param {Number} rowIndex
     * @param {Ext.EventObject} e
     */
    showActions: function(view, task, node, rowIndex, e) {
        var icons = Ext.DomQuery.select('.x-action-col-icon', node);
        Ext.each(icons, function(icon){
            Ext.get(icon).removeCls('x-hidden');
        });
    },

    /**
     * Handles a mouseleave event on a task grid item.
     * Hides the item's action icons.
     * @param {Ext.grid.View} view
     * @param {SimpleTasks.model.Task} task
     * @param {HTMLElement} node
     * @param {Number} rowIndex
     * @param {Ext.EventObject} e
     */
    hideActions: function(view, task, node, rowIndex, e) {
        var icons = Ext.DomQuery.select('.x-action-col-icon', node);
        Ext.each(icons, function(icon){
            Ext.get(icon).addCls('x-hidden');
        });
    },
    /**
     * Reapplies the store's current filters. This is needed because when data in the store is modified
     * after filters have been applied, the filters do not automatically get applied to the new data.
     */
    refreshFilters: function() {
        var store = this.store,
            filters = store.filters;

        // save a reference to the existing task filters before clearing them
        filters = filters.getRange(0, filters.getCount() - 1);

        // clear the tasks store's filters and reapply them.
        store.clearFilter();
        store.filter(filters);
    }
   
});