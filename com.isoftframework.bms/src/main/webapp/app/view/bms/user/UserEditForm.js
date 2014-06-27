Ext.define('FM.view.bms.user.UserEditForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.userEditForm',
    layout: 'anchor',
    bodyPadding: 10,
    border: false,
    defaults: {
    	labelAlign :'right',
        margin: '0 15 5 0'
        //labelWidth: 60
    },
    initComponent: function() {
        Ext.apply(this, {
        	items: [
        	        {
        	            xtype: 'hiddenfield',
        	            name: 'id' 
        	        },
        	        {
        	            xtype: 'fieldset',
        	            layout: 'hbox',
        	            anchor: '100%',
        	            padding: 0,
        	            border: false,
        	            defaults: {
        	            	labelAlign :'right',
        	                margin: '0 15 0 0'
        	            },
        	            items: [
        	                {
        	                    xtype: 'textfield',
        	                    name: 'username',
        	                    fieldLabel: '用户名',
        	                    width: 185,
        	                    flex:1
        	                },
        	                {
        	                    xtype: 'textfield',
        	                    inputType:'password',
        	                    name: 'password',
        	                    fieldLabel: '密码',
        	                   // width: 185,
        	                    flex:1
        	                } 
        	            ]
        	        },
        			{
        			    xtype: 'treepicker',
        			    name: 'orgid',
        			    fieldLabel: '组织机构',
        			    displayField: 'text',
        			    store: Ext.create('FM.store.bms.Orgs', {storeId: 'text' }),
        			  //  store:'FM.store.bms.Orgs',
        			    anchor: '100%',
        			    flex: 1
        			},
        	        {
        	            xtype: 'fieldset',
        	            layout: 'hbox',
        	            anchor: '100%',
        	            padding: 0,
        	            //margin: '0 0 5',
        	            border: false,
        	            defaults: {
        	            	labelAlign :'right',
        	                margin: '0 15 5 0'
        	            },
        	            items: [
        	                 
        	                {
        	                    xtype: 'textfield',
        	                    name: 'realName',
        	                    fieldLabel: '姓名',
        	                   // width: 185,
        	                    flex:1
        	                },
        	                {
        	                    xtype: 'radiogroup',
        	                    //anchor: 'none',
        	                    name: 'sexGroup',
        	                    fieldLabel: '性别',
        	                    flex:1,
        	                    layout: {
        	                        autoFlex: false
        	                    },
        	                    defaults: {
        	                        name: 'sex',
        	                        style: 'margin-right:15px'
        	                    },
        	                    items: [{
        	                        inputValue: '1',
        	                        boxLabel: '男',
        	                        checked: true
        	                    }, {
        	                        inputValue: '0',
        	                        boxLabel: '女'
        	                    }]
        	                    
        	                }
        	                 
        	            ]
        	        },
        	        {
        	            xtype: 'fieldset',
        	            layout: 'hbox',
        	            anchor: '100%',
        	            padding: 0,
        	            border: false,
        	            defaults: {
        	            	labelAlign :'right',
        	                margin: '0 15 0 0'
        	            },
        	            items: [
        	                {
        	                    xtype: 'textfield',
        	                    name: 'phone',
        	                    fieldLabel: '电话',
        	                    width: 185,
        	                    flex:1
        	                },
        	                {
        	                    xtype: 'textfield',
        	                    name: 'mobile',
        	                    fieldLabel: '手机',
        	                   // width: 185,
        	                    flex:1
        	                } 
        	            ]
        	        },
        	        {
        	            xtype: 'fieldset',
        	            layout: 'hbox',
        	            anchor: '100%',
        	            padding: 0,
        	            border: false,
        	            defaults: {
        	            	labelAlign :'right',
        	                margin: '0 15 0 0'
        	            },
        	            items: [
        	                {
        	                    xtype: 'textfield',
        	                    name: 'fax',
        	                    fieldLabel: '传真',
        	                    //width: 185,
        	                    flex:1
        	                },
        	                
        	                {
        	                    xtype: 'textfield',
        	                    name: 'email',
        	                    fieldLabel: '邮件',
        	                   // width: 185,
        	                    flex:1
        	                    	
        	                }
        	            ]
        	        },
        	        {
        	            xtype: 'textfield',
        	            name: 'adress',
        	            fieldLabel: '家庭住址',
        	            anchor: '100%'
        	        },
        	        {
        	        	fieldLabel: '备注',
        	            xtype: 'htmleditor',
        	            name: 'remark',
        	            anchor: '100%'
        	        } 
        	    ],
        	    buttons:[
        	        {
        	            text: '保存',
        	            action:'save',
        	            id: 'save-user-edit-btn'
        	        },
        	        {
        	            text: '取消',
        	            action:'cancel',
        	            id: 'cancel-user-edit-btn'
        	        }
        	    ]
			
        });
        this.callParent(arguments);
    }
    
});

