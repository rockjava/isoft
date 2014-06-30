Ext.define('FM.store.bms.Users',{
	extend: 'Ext.data.Store',
	storeId: 'usersStore',
	requires:['FM.model.bms.User'],
	pageSize: FM.Constants.get('pageSize'),
	model:'FM.model.bms.User',
	autoLoad: false,
	remoteSort: true,
	proxy: {
        type: 'ajax',
        api: {
			create: 'bms/user/createUser',
	        read: 'bms/user/listUsers',
	        update: 'bms/user/updateUser',
	        destroy: 'bms/user/deleteUser'
	    },
        reader: {
            type: 'json',
            root: 'users',
            totalProperty: 'totalCount'
        }
   }
	
});