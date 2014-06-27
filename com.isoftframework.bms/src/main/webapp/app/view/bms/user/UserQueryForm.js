Ext.define('FM.view.bms.user.UserQueryForm', {
	extend : 'Ext.form.Panel',
	alias : [ 'widget.userQueryForm' ],
	requires : [ 'Ext.layout.container.HBox', 'Ext.form.field.Date' ],
	layout : {
		type : 'hbox',
		padding : '2',
		pack : 'start',
		align : 'middle'
	},
	// cls: 'users-new-form',

	initComponent : function() {
		this.items = [
		/*
		 * { xtype: 'treepicker', name: 'orgid', fieldLabel: '组织机构', displayField:
		 * 'text', store: Ext.create('FM.store.bms.Orgs', {storeId: 'text' }), //
		 * anchor: '100%', // flex: 1, labelAlign :'right', minWidth :200,
		 * labelWidth:60 },
		 */
		{
			xtype : 'textfield',
			name : 'loginName',
			fieldLabel : '用户名',
			labelAlign : 'right',
			minWidth : 200,
			labelWidth : 50
		}, {
			xtype : 'textfield',
			name : 'realName',
			fieldLabel : '真实姓名',
			labelAlign : 'right',
			minWidth : 200,
			labelWidth : 60
		}, {
			xtype : 'combobox',
			name : 'sex',
			fieldLabel : '性别',
			minWidth : 100,
			maxWidth : 100,
			width : 100,
			labelWidth : 30,
			labelAlign : 'right',
			store : {
				fields : [ 'text', {
					name : 'value',
					type : 'char'
				} ],
				data : [ {
					text : '男',
					value : '1'
				}, {
					text : '女',
					value : '0'
				}

				]
			},
			valueField : 'value',
			displayField : 'text',
			typeAhead : true,
			queryMode : 'local'
		},

		{
			width : 20,
			border : 0,
			flex : 3
		},

		{
			xtype : 'button',
			iconCls : 'query',
			action : 'query',
			text : '查询',
			tooltip : '查询用户'

		}, {
			width : 10,
			border : 0
		}, {
			xtype : 'button',
			iconCls : 'reset',
			action : 'reset',
			text : '重置',
			tooltip : '重置查询条件'
		/*
		 * handler: function() { // this.up('form').getForm().reset(); }
		 */
		}

		];

		this.callParent(arguments);
	}

});