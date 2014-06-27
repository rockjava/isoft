Ext.define('FM.controller.bms.UserController',{
	extend : 'FM.controller.BaseController',
	stores : [ 'bms.Users' ],
	requires : [ 'FM.view.bms.user.OrgTree', 'FM.view.bms.user.UserList',
			'FM.view.bms.user.UserQueryForm',
			'FM.view.bms.user.UserEditWindow', 'FM.view.bms.user.UserManage',
			'FM.ux.window.SlideWindow', 'FM.view.bms.user.UserEditForm'

	],
	refs : [ {
		ref : 'userList',
		selector : 'userList'
	}, {
		ref : 'userOrgTree',
		selector : 'userOrgTree'
	}, {
		ref : 'userManagePanel',
		selector : 'userManagePanel'
	}, {
		ref : 'userEditForm',
		selector : 'userEditForm'
	}, {
		ref : 'userQueryForm',
		selector : 'userQueryForm'
	}, {
		ref : 'userEditWindow',
		selector : 'userEditWindow',
		xtype : 'userEditWindow',
		autoCreate : true
	}

	],
	init : function() {
		var me = this;
		var userStore = Ext.data.StoreManager.lookup('bms.Users');
		// me.getUserList().on('beforeload',handleUserStoreBeforeload);
		this.control({
			'userList' : {
				render : me.onUserListRendered,
				editclick : me.handleEditIconClick,
				deleteclick : me.handleDeleteIconClick
			// selectionchange:me.handleUserListSelectionchange
			},
			'#user-add-btn' : {
				click : this.handleUserAddBtnClick
			},
			'userList button[action=deleteUsers]' : {
				click : me.handleDeleteUserBtnClick
			},
			'#save-user-edit-btn' : {
				click : me.handleSaveUserClick
			},
			'userEditForm button[action=cancel]' : {
				click : me.handleCancelEditUser
			},
			'userQueryForm textfield' : {
				specialkey : me.handleSpecialKey
			},
			'userQueryForm button[action=query]' : {
				click : me.handQueryUser
			},
			'userQueryForm button[action=reset]' : {
				click : me.handleResetClick
			},
			'userOrgTree' : {
				render : me.onUserOrgTreeRender,
				selectionchange : me.handleSelectionchange
			},
			'userList > pagingtoolbar' : {
				beforechange : me.handlePageBeforechange

			} 

		});

		// userStore.on('beforeload',me.handleUserStoreBeforeload);
	},

	handleResetClick : function(btn) {
		btn.up('form').getForm().reset();
		this.refresh();
	},
	handleUserListSelectionchange : function(selectionModel,
			selectedRecords, eOpts) {
		var me = this;
		if (!me.myWindow) {
			me.myWindow = Ext.create('FM.ux.window.SlideWindow', {
				width : 500,
				height : 300,
				items : [ {
					xtype : 'userEditForm'
				} ]
			});
		}
		me.myWindow.down('form').loadRecord(selectedRecords[0]);
		me.myWindow.slideShow(this.getUserList());
	},

	handlePageBeforechange : function(pagetool, page, eOpts) {
		console.log('----handleUserStoreBeforeload execed----- ');

	},
	onUserOrgTreeRender : function(ths, ops) {
		// this.getUserEditForm().down('treepicker[name=orgid]').store=ths.getStore();
	},
	onUserListRendered : function(ths, ops) {
		var me = this, orgTree = me.getUserOrgTree();
		ths
				.getStore()
				.on(
						'beforeload',
						function(store, operation, eOpts) {
							console.log('----beforeload execed----- ');
							var orgs = orgTree.getSelectionModel().getSelection(), queryParams = {
								orgid : (orgs != null && orgs.length > 0) ? orgs[0]
										.get('id') : null,
								loginName : me.getUserQueryForm().down(
										'textfield[name=loginName]').getValue(),
								realName : me.getUserQueryForm().down(
										'textfield[name=realName]').getValue(),
								sex : me.getUserQueryForm().down('combobox[name=sex]')
										.getValue()

							};
							Ext.apply(store.proxy.extraParams, queryParams);
						});

		ths.getStore().loadPage(1);
		// this.getUserList().store.loadPage(1);
	},
	handleDeleteUserBtnClick : function(btn) {
		var me = this, users = this.getUserList().getSelectionModel()
				.getSelection();

		if (users && users.length > 0) {
			Ext.Msg.show({
				title : '删除菜单?',
				msg : '您确定要删除 选择的' + users.length + '个用户吗?',
				buttons : Ext.Msg.YESNO,
				fn : function(response) {
					if (response === 'yes') {
						// destroy the tree node on the server
						var ids = "'";
						for ( var i = 0; i < users.length; i++) {
							if (i > 0) {
								ids += "','";
							}
							ids += users[i].get('id');
						}
						ids += "'";
						Ext.Ajax.request({
							url : FM.RequestURL.get('bms_user_deleteUsers'),
							params : {
								ids : ids
							},
							failure : function(batch, options) {
								var error = batch.exceptions[0].getError(), msg = Ext
										.isObject(error) ? error.status + ' '
										+ error.statusText : error;

								Ext.MessageBox.show({
									title : '删除菜单失败',
									msg : msg,
									icon : Ext.Msg.ERROR,
									buttons : Ext.Msg.OK
								});
							},
							success : function(batch, options) {
								me.refresh();
							}
						});
					}
				}
			});

		} else {
			Ext.MessageBox.show({
				title : '请选择要删除的用户',
				msg : '请选择要删除的用户！',
				buttons : Ext.MessageBox.OK,
				// animateTarget: 'mb9',
				// fn: showResult,
				icon : Ext.Msg.INFO
			});
		}

	},
	handleDeleteIconClick : function(view, rowIndex, colIndex, column, e) {
		this.deleteUser(view.getRecord(view.findTargetByEvent(e)));
	},
	deleteUser : function(user) {
		var me = this, userStore = me.getUserList().getStore();
		Ext.Msg.show({
			title : '删除菜单?',
			msg : '您确定要删除 "' + user.get('username') + '"吗?',
			buttons : Ext.Msg.YESNO,
			fn : function(response) {
				if (response === 'yes') {
					// destroy the tree node on the server
					userStore.remove(user);
					userStore.sync({
						failure : function(batch, options) {
							var error = batch.exceptions[0].getError(), msg = Ext
									.isObject(error) ? error.status + ' '
									+ error.statusText : error;

							Ext.MessageBox.show({
								title : '删除菜单失败',
								msg : msg,
								icon : Ext.Msg.ERROR,
								buttons : Ext.Msg.OK
							});
						},
						success : function(batch, options) {
							me.refresh();
						}
					});
				}
			}
		});
	},
	handleEditIconClick : function(view, rowIndex, colIndex, column, e) {
		this.showUserEditWindow(view.getRecord(view.findTargetByEvent(e)));
	},
	showUserEditWindow : function(user) {

		var me = this, userEditWindow = me.getUserEditWindow();
		// userEditWindow.down('form').down('treepicker[name=orgid]').store=this.getUserList().getStore();
		// Set the title of the edit window
		userEditWindow.setTitle('新增用户 ');
		// load the user data into the form
		if (user) {
			userEditWindow.down('form').loadRecord(user);
		}
		userEditWindow.show();

	},
	handleUserAddBtnClick : function(button, e) {
		// ,{status:1}
		this.showUserEditWindow(Ext.create('FM.model.bms.User', {
			status : 1
		}));
	},
	handleSaveUserClick : function(button, e) {
		this.saveUser();
	},
	saveUser : function() {

		var me = this, userEditWindow = this.getUserEditWindow(), windowEl = userEditWindow
				.getEl(), form = userEditWindow.down('form').getForm(), user = form
				.getRecord(), isAdd = !user.getId();

		// console.log(user);
		if (form.isValid()) {
			windowEl.mask('正在保存...');
			form.updateRecord(user);
			// console.log(user);
			user
					.save({
						success : function(user, operation) {
							user.set('orgName', userEditWindow.down(
									'userEditForm [name=orgid]').getRawValue());
							if (isAdd) {
								// me.getUserList().getStore().add(user);
								me.refresh();
								me.getUserList().getStore().sort();
							}
							console.log(operation.response.responseText);
							windowEl.unmask();
							userEditWindow.close();

						},
						failure : function(user, operation) {
							var error = operation.getError(), msg = Ext
									.isObject(error) ? error.status + ' '
									+ error.statusText : error;

							Ext.MessageBox.show({
								title : '新增用户',
								msg : msg,
								icon : Ext.Msg.ERROR,
								buttons : Ext.Msg.OK
							});
							windowEl.unmask();
						}
					});
		} else {
			Ext.Msg.alert('非法数据', '请输入正确的数据！');
		}

	},
	/**
	 * 
	 * @param selModel
	 * @param orgs
	 */
	handleSelectionchange : function(selModel, selectedRecords, eOpts) {

		this.queryUsers();
		// this.getUserEditForm().query('[name=orgid]')[0].setValue(selectedRecords[0].get('orgid'));
	},
	refresh : function() {
		this.getUserList().store.loadPage(1);
		// refresh the task filters
		this.getUserList().refreshFilters();
		// refresh the lists org view so that the task counts will be
		// correct
		this.getUserOrgTree().refreshView();
	},

	/**
	 * Handles a "specialkey" event on an field on the task form. Creates
	 * a new task if the enter key is pressed.
	 * 
	 * @param {Ext.form.field.Text}
	 *          field
	 * @param {Ext.EventObject}
	 *          e
	 */
	handleSpecialKey : function(field, e) {
		if (e.getKey() === e.ENTER) {
			this.queryUsers();
		}
	},
	/**
	 * 
	 */
	handQueryUser : function(btn, e, eOpts) {
		this.queryUsers();
	},
	handleCancelEditUser : function(btn, e, eOpts) {
		this.getUserEditWindow().hide()
	},
	queryUsers : function() {
		this.getUserList().store.loadPage(1);
	}

});