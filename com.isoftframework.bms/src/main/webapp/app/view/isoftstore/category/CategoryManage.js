Ext.define('FM.view.isoftstore.category.CategoryManage',{
    extend:'Ext.panel.Panel',
    layout: 'border',
    alias : 'widget.icategoryManage',
    title: '软件类别管理',
    items: [
        { xtype: 'icategoryTree',region:'west'},
        { xtype: 'icategoryEditor',  region: 'center'}
        
    ]
});