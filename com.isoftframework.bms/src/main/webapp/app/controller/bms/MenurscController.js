Ext.define('FM.controller.bms.MenurscController',
{
	extend : 'FM.controller.BaseController',
	requires : [ 'FM.view.bms.menursc.MenurscEditor',
			'FM.view.bms.menursc.MenurscTree',
			'FM.view.bms.menursc.ContextMenu',
			'FM.view.bms.menursc.MenurscManage',
			'FM.ux.view.iconChooser.InfoPanel',
			'FM.ux.view.iconChooser.IconBrowser',
			'FM.ux.view.iconChooser.Window', 
			'FM.ux.DataView.Animated',
			'FM.common.pk.TreeID',
			'FM.common.pk.UUID'

	],
	stores : [ 'bms.Menursces' ],
	refs : [ {
		ref : 'menurscTree',
		selector : 'menurscTree'
	}, {
		ref : 'menurscEditor',
		selector : 'menurscEditor'
	}, {
		ref : 'menurscEditorSaveBtn',
		selector : 'menurscEditor button[action=save]'
	}, {
		ref : 'idText',
		selector : 'menurscEditor textfield[name=id]'
	}, {
		ref : 'contextMenu',
		selector : 'menurscContextMenu',
		xtype : 'menurscContextMenu',
		autoCreate : true
	} ],

	init : function() {

		var me = this;
		this.control({
			'[iconCls=new-menu]' : {
				click : me.handleNewMenuClick
			},
			'[iconCls=new-func]' : {
				click : me.handleNewFuncClick
			},
			'[iconCls=new-res]' : {
				click : me.handleNewResClick
			},
			'[iconCls=delete-menu]' : {
				click : me.handleDeleteClick
			},
			'menurscTree' : {
				itemclick : this.handleTreeClick,
				deleteclick : this.handleDeleteIconClick,
				itemcontextmenu : this.showContextMenu,
				selectionchange : this.handleTreeSelectionchange

			},
			'menurscEditor button[action=save]' : {
				click : this.handleSaveBtnClick
			},
			'menurscManagePanel' : {
				afterrender : this.onAfterrender
			},
			'menurscEditor combobox[name=targetContainer]' : {
				change : this.onTargetContainerCmbChange
			}

		});

	},

	handleTreeClick : function(othis, record, item, index, e, eOpts) {
	},
	onTargetContainerCmbChange : function(othis, newValue, oldValue,
			eOpts) {
		// console.log(newValue);
		var urlText = this.getMenurscEditor().down('textfield[name=url]');
		if (newValue == 'panel') {
			urlText.emptyText = '请输入容器对象名...';
		} else if (newValue == 'iframe') {
			urlText.emptyText = '请输入请求路径...';
		}
	},
	onAfterrender : function(cmp, opts) {
		// console.log('The onAfterrender executed');
		this.getMenurscTree().getSelectionModel().select(0);
	},
	showContextMenu : function(view, record, node, rowIndex, e) {
		e.preventDefault();
		e.stopEvent();
		var contextMenu = this.getContextMenu(), newMenuItem = Ext
				.getCmp('new-menu-item'), newFuncItem = Ext
				.getCmp('new-func-item'), newResItem = Ext
				.getCmp('new-res-item'), deleteMenuItem = Ext
				.getCmp('delete-menu-item'), type = record.get('type');

		// newMenuItem.setVisible(false);
		newMenuItem.setVisible(record.isRoot()
				|| (type == '1' && !record.get('menuLeaf')));
		newFuncItem.setVisible(type == '2' || type == '1');
		newResItem.setVisible(type == '2' || type == '1');
		deleteMenuItem.setVisible(!record.isRoot());

		contextMenu.setSysMenu(record);
		contextMenu.showAt(e.getX(), e.getY());
		// e.preventDefault();
	},

	/**
	 * Handles a click on the "New List" button or context menu item.
	 * 
	 * @param {Ext.Component}
	 *          component
	 * @param {Ext.EventObject}
	 *          e
	 */
	handleNewMenuClick : function(component, e) {
		this.addMenu('1');
	},

	handleNewFuncClick : function(component, e) {
		this.addMenu('2');
	},

	handleNewResClick : function(component, e) {
		this.addMenu('3');
	},
	/**
	 * Handles a click on a delete icon in the list tree.
	 * 
	 * @param {Ext.tree.View}
	 *          view
	 * @param {Number}
	 *          rowIndex
	 * @param {Number}
	 *          colIndex
	 * @param {Ext.grid.column.Action}
	 *          column
	 * @param {EventObject}
	 *          e
	 */
	handleDeleteIconClick : function(view, rowIndex, colIndex, column, e) {
		this.deleteMenu(view.getRecord(view.findTargetByEvent(e)));
	},
	/**
	 * Handles a click on the "Delete List" or "Delete Folder" button or
	 * menu item
	 * 
	 * @param {Ext.Component}
	 *          component
	 * @param {Ext.EventObject}
	 *          e
	 */
	handleDeleteClick : function(component, e) {
		this.deleteMenu(this.getMenurscTree().getSelectionModel()
				.getSelection()[0]);
	},
	handleSaveBtnClick : function(button) {
		var el = this.getMenurscEditor().el;
		this.syncMenu();
	},
	handleTreeSelectionchange : function(selMoel, nodes, e) {
		// this.syncMenu();
		this.loadMenuRecord(selMoel, nodes, e);
	},
	/**
	 * Adds an empty list to the lists store and starts editing the new
	 * list
	 * 
	 * @param {Boolean}
	 *          leaf True if the new node should be a leaf node.
	 */
	addMenu : function(type) {

		var othis = this, menuTree = othis.getMenurscTree(), selectionModel = menuTree
				.getSelectionModel(), selectedMenu = selectionModel
				.getSelection()[0], el = othis.getMenurscEditor().el, parentMenu = selectedMenu
				.isLeaf() ? selectedMenu.parentNode : selectedMenu;
		var newMenu = Ext.create('FM.model.Menu', {
			// id:id,
			text : '新建 ' + (type == '3' ? '资源' : type == '2' ? '功能' : '菜单'),
			leaf : (type == '3'),
			type : type,
			parentId : parentMenu.get('id'),
			loaded : true
		// set loaded to true, so the tree won't try to dynamically load
		// children for this node when expanded

		}),

		expandAndSelect = function() {
			if (parentMenu.isExpanded()) {
				parentMenu.appendChild(newMenu);
				selectionModel.select(newMenu);
				othis.syncMenu();

			} else {
				menuTree.on('afteritemexpand', function startSelect(menu) {
					if (menu === parentMenu) {

						selectionModel.select(newMenu);
						// 要在Expand之后append newMenu，因为Excpand会加载数据库，newMenu不在数据库中
						parentMenu.appendChild(newMenu);
						// remove the afterexpand event listener
						menuTree.un('afteritemexpand', startSelect);
						othis.syncMenu();

					}
				});
				parentMenu.set('loaded', false);
				parentMenu.expand();
			}
		};

		if (menuTree.getView().isVisible(true)) {
			expandAndSelect();
		} else {
			menuTree.on('expand', function onExpand() {
				expandAndSelect();
				menuTree.un('expand', onExpand);
			});
			menuTree.expand();
		}

	},
	/**
	 * 同步菜单树到数据库
	 */
	syncMenu : function() {

		var othis = this, form = othis.getMenurscEditor().getForm(), menu = form
				.getRecord();
		// values = form.getValues();

		if (!menu) {
			return;
		}

		if (form.isValid()) {
			// record.set(values);
			if (!menu.get('id')) {
				var id = this.generateId(this.getMenurscTree()
						.getSelectionModel().getSelection()[0].parentNode);
				othis.getIdText().setValue(id);
			}
			form.updateRecord(menu);
			othis.sync({
				store : othis.getMenurscTree().store,
				title : null
			});

		} else {
			Ext.Msg.alert('非法数据', '请输入正确的数据！');
		}
	},
	generateId : function(parentNode) {
		return FM.common.pk.TreeID.generateId(parentNode);
	},
	/**
	 * Deletes a list from the server and updates the view.
	 * 
	 * @param {SimpleTasks.model.List}
	 *          list
	 */
	deleteMenu : function(menu) {
		var me = this, menuTree = me.getMenurscTree(), menuText = menu
				.get('text'), selModel = menuTree.getSelectionModel(),

		menuStore = menuTree.store;
		// console.log(menuStore);

		Ext.Msg.show({
			title : '删除菜单?',
			msg : '您确定要删除 菜单"' + menuText + '"吗?',
			buttons : Ext.Msg.YESNO,
			fn : function(response) {
				if (response === 'yes') {
					// destroy the tree node on the server
					menu.parentNode.removeChild(menu);
					if (menu.get('id') != null && menu.get('id') != '') {
						menuStore.sync({
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
							}
						});
					}

					if (!menuStore.getNodeById(selModel.getSelection()[0]
							.get('id'))) {
						// if the selection no longer exists in the store (it was
						// part of the deleted node(s))
						// change selection to the "All Tasks" list
						selModel.select(0);
					}
				}
			}
		});

	},

	loadMenuRecord : function(selMoel, nodes, e) {
		var record = nodes[0];
		this.getMenurscEditor().loadRecord(record);
		this.getMenurscEditor().down('image').setSrc(
				(record.data.icon == null || record.data.icon == '') ? '#'
						: record.data.icon);

		/* ========设置显示项目和按钮状态=================== */
		var newMenuBtn = Ext.getCmp('new-menu-btn'), newFuncBtn = Ext
				.getCmp('new-func-btn'), newResBtn = Ext.getCmp('new-res-btn'), deleteMenuBtn = Ext
				.getCmp('delete-menu-btn'), type = record.get('type'), saveBtn = this
				.getMenurscEditor().down('button[action=save]'), menuLeafCmb = this
				.getMenurscEditor().down('combobox[name=menuLeaf]'), iconContainer = this
				.getMenurscEditor().down('fieldcontainer[fieldLabel=图标]'), urlText = this
				.getMenurscEditor().down('textfield[name=url]');
		targetContainerCmb = this.getMenurscEditor().down(
				'combobox[name=targetContainer]');

		newMenuBtn.setDisabled(!(record.isRoot() || (type == '1' && !record
				.get('menuLeaf'))));
		newFuncBtn.setDisabled(!(type == '2' || type == '1'));
		newResBtn.setDisabled(!(type == '2' || type == '1'));
		deleteMenuBtn.setDisabled(record.isRoot());

		saveBtn.setDisabled(record.isRoot());

		menuLeafCmb.setVisible(type == '1');
		iconContainer.setVisible(type == '1');
		targetContainerCmb.setVisible(type == '1');
		urlText.setVisible(type == '1' || type == '3');

	}

});