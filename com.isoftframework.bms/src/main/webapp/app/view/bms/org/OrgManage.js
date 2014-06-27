Ext.define('FM.view.bms.org.OrgManage',{
    extend:'Ext.panel.Panel',
    alias:'widget.orgManageContainer',
    layout: 'border',
    title: '人力资源组织机构管理',
    items: [
        { xtype: 'orgTree',region:'west'},
        { xtype: 'orgEditor',  region: 'center'}
        
    ]
});