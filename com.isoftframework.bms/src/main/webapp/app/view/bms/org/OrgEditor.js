Ext.define('FM.view.bms.org.OrgEditor', {
	extend : 'Ext.form.Panel',
	alias : 'widget.orgEditor',

	fileUpload : true,
	// layout: 'fit',
	bodyPadding: '5',
	defaults : {
		allowBlank : true,
		labelAlign :'right',
		labelWidth:60,
		width: 400
	},
	initComponent : function() {

		this.items = [{
			xtype : 'textfield',
			// xtype: 'textfield',
			name : 'id',
			fieldLabel : 'ID'

		}, {
			xtype : 'hiddenfield',
			// xtype: 'textfield',
			name : 'parentId'

		}, {
			xtype : 'textfield',
			name : 'text',
			fieldLabel : '名&nbsp;&nbsp;&nbsp;&nbsp;称',
			allowBlank : false
		}, {
			xtype : 'iconSelector',
		 	url:FM.RequestURL.get('bms_org_getOrgIcons'),
			fieldLabel : '图&nbsp;&nbsp;&nbsp;&nbsp;标'
		}];
		
		this.buttons = [ {
			text : '保存',
			action : 'save',
			command : '',
			scope : this
		// handler: this.saveMenu
		} ]

		this.callParent(arguments);
	}

});