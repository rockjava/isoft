Ext.define('FM.store.bms.Orgs',{
	extend: 'Ext.data.TreeStore',
	requires:['FM.model.bms.Org'],
	model:'FM.model.bms.Org',
	storeId:'orgStore',
	root: {
        text: '组织机构',
        id: 'root',
        expanded: true
    },
	autoLoad: false,
	proxy: { 
		type: 'ajax', 
		api: {
			create: 'bms/org/createOrg',
	        read: 'bms/org/listOrgs',
	        update: 'bms/org/updateOrg',
	        destroy: 'bms/org/deleteOrg'
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