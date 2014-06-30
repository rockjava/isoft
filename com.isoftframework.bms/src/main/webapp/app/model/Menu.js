Ext.define('FM.model.Menu', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id', type: 'string' },
       // { name: 'menuId', type: 'string' },
        { name: 'parentId', type: 'string' },
        { name: 'text', type: 'string' } ,    
        { name: 'url', type: 'string' },
        { name: 'icon', type: 'string' },
        { name: 'type', type: 'char' },
        //containerType比较好
        { name: 'targetContainer', type: 'string' },
        { name: 'menuLeaf', type: 'boolean' } 
        
    ]
});