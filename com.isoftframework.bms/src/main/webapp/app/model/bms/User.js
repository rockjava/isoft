Ext.define('FM.model.bms.User', {
    extend: 'Ext.data.Model',
    
    fields: [
        { name: 'id', type: 'string' },
        { name: 'username', type: 'string' },
        { name: 'password', type: 'string' } , 
        { name: 'orgid', type: 'string' },
        { name: 'orgName', type: 'string' },
        { name: 'realName', type: 'string' },
        { name: 'sex', type: 'char' },
        { name: 'phone', type: 'string' },
        { name: 'mobile', type: 'string' },
        { name: 'email', type: 'string' },
        { name: 'fax', type: 'string' },
        { name: 'address', type: 'string' },
        { name: 'enabled', type: 'char' },
        { name: 'remark', type: 'string' }
       
    ],
    idProperty: 'id',
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