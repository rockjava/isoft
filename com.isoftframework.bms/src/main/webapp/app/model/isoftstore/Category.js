Ext.define('FM.model.isoftstore.Category', {
    extend: 'Ext.data.Model',
    
    fields: [
        { name: 'id', type: 'string' },
        { name: 'text', type: 'string' },
        { name: 'sort', type: 'int' } 
       
    ] 
});