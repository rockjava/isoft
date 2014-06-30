Ext.define('FM.view.bms.menursc.MenurscManage',{
    extend:'Ext.panel.Panel',
    layout: 'border',
    title: '系统菜单管理',
    alias:'widget.menurscManagePanel',
	autoDestroy: FM.Constants.get('autoDestroy')  ,
	closeAction:FM.Constants.get('closeAction'),
    items: [
        { xtype: 'menurscTree',region:'west'},
        { xtype: 'menurscEditor', region: 'center'}
        
    ] 
});