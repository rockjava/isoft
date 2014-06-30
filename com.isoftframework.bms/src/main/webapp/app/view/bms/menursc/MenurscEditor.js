Ext.define('FM.view.bms.menursc.MenurscEditor', {
	extend: 'Ext.form.Panel',
	alias: 'widget.menurscEditor',
	autoDestroy: FM.Constants.get('autoDestroy'),
	closeAction:FM.Constants.get('closeAction'),
	fileUpload: true, 
   // layout: 'fit',
   // margins : '10 10 -1 1',
    bodyPadding: '5',
	defaults:{
		allowBlank: true,
		labelAlign :'right',
		labelWidth:60,
		width: 400
	},
	items: [
	   
	    {
	        xtype: 'textfield',
	        // xtype: 'textfield',
	        name: 'id',
	        fieldLabel:'ID'
	        
	    },
	    {
	        xtype: 'hiddenfield',
	        // xtype: 'textfield',
	        name: 'parentId'
	        
	    },
		{
		    xtype: 'textfield',
		    name : 'text',
		    fieldLabel: '名称',
		    allowBlank: false
		},
		{
		    xtype: 'combobox',
		    name: 'targetContainer',
		    fieldLabel: '目标容器',
		    value:'iframe',
		    store: {
		        fields: ['text',{name: 'value', type: 'string'}],
		        data : [ 
		            {text: 'iframe', value: 'iframe'},
		            {text: 'panel', value: 'panel'} ,
		            {text: 'div', value: 'div'} 
		        ]
		    },
		    valueField: 'value',
		    displayField: 'text',
		    typeAhead: true,
		    queryMode: 'local',
		    allowBlank: true,
		    forceSelection: false
		},
		{
		    xtype: 'textfield',
		    emptyText: '请输入请求路径...',
		    name : 'url',
		    fieldLabel: '路径' 
		},
		{
			xtype : 'iconSelector',
		 	url:FM.RequestURL.get('bms/menu/getMenuIcons'),
			fieldLabel : '图标'
		},
		{
			xtype: 'combobox',
			name: 'menuLeaf',
			fieldLabel: '末级菜单?',
			store: {
				fields: ['text',{name: 'value', type: 'boolean'}],
				data : [ 
					{text: '否', value: false},
					{text: '是', value: true}
				   
				]
			},
			valueField: 'value',
			displayField: 'text',
			typeAhead: true,
			queryMode: 'local',
			allowBlank: false,
			forceSelection: true
		},
	    {
	        xtype: 'combobox',
	        name: 'type',
	        fieldLabel: '类型',
	        store: {
	            fields: ['text',{name: 'value', type: 'char'}],
	            data : [ 
	                {text: '菜单', value: '1'},
	                {text: '功能', value: '2'},
	                {text: '资源', value: '3'}
	            ]
	        },
	        valueField: 'value',
	        displayField: 'text',
	        typeAhead: true,
	        queryMode: 'local',
	        disabled: true,
	        allowBlank: false,
	        forceSelection: true
	    }
		 
	],
	initComponent: function() {
		 
	     this.buttons =[
	       {
	           text: '保存',
	           action: 'save',
	           command:'',
	               scope:this
	           }
	         ];
	
	        this.callParent(arguments);
	    }
	
	   
	}); 