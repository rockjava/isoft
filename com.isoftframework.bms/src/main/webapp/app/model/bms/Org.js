Ext.define('FM.model.bms.Org', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id', type: 'string' },
        { name: 'parentId', type: 'string' },
        { name: 'text', type: 'string' } ,    
        { name: 'icon', type: 'string' }
    ] 
});