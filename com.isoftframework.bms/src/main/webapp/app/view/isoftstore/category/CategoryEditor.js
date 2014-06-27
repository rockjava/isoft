Ext.define('FM.view.isoftstore.category.CategoryEditor', {
	extend : 'Ext.form.Panel',
	requires : [ 'FM.ux.form.IconSelector' ],
	alias : 'widget.icategoryEditor',
	iconWindow : null,
	// fileUpload: true,
	// layout: 'fit',
	bodyPadding: '5',
	defaults : {
		allowBlank : true,
		labelAlign :'right',
		labelWidth:60,
		width: 400
	},
	initComponent : function() {
		var me = this;
		this.items = [ {
			xtype : 'textfield',
			name : 'id',
			fieldLabel : 'ID'
		},{
			xtype : 'textfield',
			name : 'text',
			fieldLabel : '名称',
			allowBlank : false
		}
		/*,{
			xtype : 'iconSelector',
			fieldLabel : '图标'
		}*/
		];

		this.buttons = [ {
			text : ' 保&nbsp;&nbsp;存',
			iconCls: 'save',
			iconAlign: 'left',
			textAlign: 'left',
			action : 'save',
			padding:'3'
			//maxWidth :'60'
			//scope : this
		// handler: this.saveMenu
		} ];

		this.callParent(arguments);
	}

});