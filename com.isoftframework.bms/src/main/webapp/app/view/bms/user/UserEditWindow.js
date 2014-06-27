/**
 * @class SimpleTasks.view.tasks.EditWindow
 * @extends Ext.window.Window
 */
Ext.define('FM.view.bms.user.UserEditWindow', {
    extend: 'Ext.window.Window',
    xtype: 'userEditWindow',
    requires: [
        'FM.ux.TreePicker'
        
    ],
    closeAction: 'hide',
    modal: true,
    width: 500,
    height: 380,
    layout: 'fit',

    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'top',
            items: [
               
                {
                    iconCls: 'user-delete',
                    text: '删除用户' 
                }
            ]
        }
    ],

    initComponent: function() {

        this.items = [{xtype:'userEditForm'}];

        this.callParent(arguments);

    }

});