Ext.define('FM.store.isoftstore.Categories',{
	extend: 'Ext.data.TreeStore',
	requires:['FM.model.isoftstore.Category'],
	model:'FM.model.isoftstore.Category',
	storeId: 'icategoryStore',
	root: {
        text: '软件类别',
        id: 'root',
        expanded: true
    },
	autoLoad: false,
	proxy: {
        type: 'ajax',
        api: {
        	create: 'isoftstore/category/create',
	        read: 'isoftstore/category/list',
	        update: 'isoftstore/category/update',
	        destroy: 'isoftstore/category/delete'
	    },
        reader: {
            type: 'json' 
        }
   },
   folderSort: true,
   sorters: [{
       property: 'id',
       direction: 'ASC'
   }]
   
	
});