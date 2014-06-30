
Ext.define('FM.view.isoftstore.category.ContextMenu', {
    extend: 'Ext.menu.Menu',
    xtype: 'icategoryContextMenu',
    items: [
		{
		    text: '新建下级类别',
		    iconCls: 'new',
		    action:'addChild'
		},
        '-',
        {
            text: '删除类别',
            iconCls: 'delete',
            action:'delete'
        }
    ] 


});