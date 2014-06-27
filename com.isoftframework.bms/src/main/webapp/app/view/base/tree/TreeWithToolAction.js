Ext.define('FM.view.base.tree.TreeWithToolAction', {
    extend: 'FM.view.base.tree.TreeWithSearch',
    alias: 'widget.actionTree',
    requires: [
       'Ext.grid.plugin.CellEditing',
       'Ext.tree.plugin.TreeViewDragDrop',
       'Ext.grid.column.Action'
    ],
	initComponent : function(){
		var me=this;
		me.columns = [
           {
               xtype: 'treecolumn',
               dataIndex: 'text',
               flex: 1
           },
           {
               xtype: 'actioncolumn',
               width: 24,
               icon: 'resources/images/btn/delete.png',
               iconCls: 'x-hidden',
               tooltip: 'Delete',
               handler: Ext.bind(me.handleDeleteClick, me)
           }
       ];
		me.listeners = {
			'itemmouseenter': me.showActions,
	        'itemmouseleave': me.hideActions
			 
		};
		me.callParent(arguments);
		me.addEvents(
            /**
             * @event deleteclick
             * Fires when the delete icon is clicked
             * @param {Ext.grid.View} gridView
             * @param {Number} rowIndex
             * @param {Number} colIndex
             * @param {Ext.grid.column.Action} column
             * @param {EventObject} e
             */
            'deleteclick',
            'createNewNode',
            'deleteNode'
        );
	},

    /**
     * Handles a click on a delete icon
     * @private
     * @param {Ext.tree.View} treeView
     * @param {Number} rowIndex
     * @param {Number} colIndex
     * @param {Ext.grid.column.Action} column
     * @param {EventObject} e
     */
    handleDeleteClick: function(gridView, rowIndex, colIndex, column, e) {
        // Fire a "deleteclick" event with all the same args as this handler
        this.fireEvent('deleteclick', gridView, rowIndex, colIndex, column, e);
        this.fireEvent('deleteNode', this, this.getSelectionModel(), [gridView.getRecord(gridView.findTargetByEvent(e))],  e);
    },
    
    handleNewNodeBtnClick:function(btn,e){
    	this.fireEvent('createNewNode', this, this.getSelectionModel(), this.getSelectionModel().getSelection(),  e);
    },
    handleDeleteNodesBtnClick:function(btn,e){
    	this.fireEvent('deleteNode', this, this.getSelectionModel(), this.getSelectionModel().getSelection(),  e);
    },
    /**
     * Handles a mouseenter event on a list tree node.
     * Shows the node's action icons.
     * @param {Ext.tree.View} view
     * @param {SimpleTasks.model.List} list
     * @param {HTMLElement} node
     * @param {Number} rowIndex
     * @param {Ext.EventObject} e
     */
    showActions: function(view, menu, node, rowIndex, e) {
    	//view.getRecord(node)==list
        var icons = Ext.DomQuery.select('.x-action-col-icon', node);
        if(view.getRecord(node).get('id') !='root' ) {
	        Ext.each(icons, function(icon){
	           Ext.get(icon).removeCls('x-hidden');
	        });
        }
       
       
    },

    /**
     * Handles a mouseleave event on a list tree node.
     * Hides the node's action icons.
     * @param {Ext.tree.View} view
     * @param {SimpleTasks.model.List} list
     * @param {HTMLElement} node
     * @param {Number} rowIndex
     * @param {Ext.EventObject} e
     */
    hideActions: function(view, menu, node, rowIndex, e) {
        var icons = Ext.DomQuery.select('.x-action-col-icon', node);
        Ext.each(icons, function(icon){
            Ext.get(icon).addCls('x-hidden');
        });
    }
    
  
   
	 
});