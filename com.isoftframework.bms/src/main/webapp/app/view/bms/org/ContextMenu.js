/**
 * @class FM.view.bms.sysMenuManage.ContextMenu
 * @extends Ext.menu.Menu
 */
Ext.define('FM.view.bms.org.ContextMenu', {
    extend: 'Ext.menu.Menu',
    xtype: 'orgContextMenu',
    items: [
		{
		    text: '新建下级组织机构',
		    iconCls: 'org-new',
		    id:'new-org-item'
		},
        '-',
        {
            text: '删除组织机构',
            iconCls: 'org-delete',
            id:'delete-org-item'
        }
    ],
    setOrg:function(org){
		this.org=org;
	},
	getOrg:function(){
		return this.org;
	}


});