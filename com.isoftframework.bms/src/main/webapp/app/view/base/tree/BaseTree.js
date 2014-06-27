Ext.define('FM.view.base.tree.BaseTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.baseTree',
	//iconCls:'icon-navmenu',
	margins : '0 0 -1 1',
	border : false,
	enableDD : false,
	split: true,
	width : 212,
	minSize : 130,
	maxSize : 300,
	rootVisible: true,
	containerScroll : true,
	hideHeaders: true,
	autoScroll: false,
    
	 
    /**
     * Triggers the list tree to refresh its view.  This is necessary in two scenarios:
     * 1) Since the lists and tasks are loaded asyncrounously, The Lists store may have finished
     *    loading before the tasks store.  In this case, the tasks data would not be available so all
     *    of the task counts would be rendered as (0).
     * 2) When a task is dragged and dropped onto a list, or when a list is deleted the task count won't automatially be updated
     *    because none of the data in the lists store actually changed (the renderer gets the count
     *    from the tasks store).
     *    
     * In both situations refreshing the lists view we ensure that the task counts are accurate.
     */
    refreshView: function() {
        // refresh the data in the view.  This will trigger the column renderers to run, making sure the task counts are up to date.
        this.getView().refresh();
    }	
	 
});