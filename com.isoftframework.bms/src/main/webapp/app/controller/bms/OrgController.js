Ext.define('FM.controller.bms.OrgController',
{
	extend : 'FM.controller.BaseController',
	stores : [ 'bms.Orgs' ],
	requires : [ 'FM.view.bms.org.OrgTree', 'FM.view.bms.org.OrgEditor',
			'FM.view.bms.org.ContextMenu',
			'FM.ux.view.iconChooser.InfoPanel',
			'FM.ux.view.iconChooser.IconBrowser',
			'FM.ux.view.iconChooser.Window', 'FM.ux.DataView.Animated' 

	],
	refs : [ {
		ref : 'orgTree',
		selector : 'orgTree'
	}, {
		ref : 'orgTreeView',
		selector : 'treeview'
	}, {
		ref : 'orgEditor',
		selector : 'orgEditor'
	}, {
		ref : 'iconwindow',
		selector : 'iconwindow'
	}, {
		ref : 'contextMenu',
		selector : 'orgContextMenu',
		xtype : 'orgContextMenu',
		autoCreate : true
	} ],
	init : function() {
		var me = this;
		this.control({
			'[iconCls=org-new]' : {
				click : me.handleNewClick
			},
			'[iconCls=org-delete]' : {
				click : me.handleDeleteClick
			},
			'orgTree' : {
				deleteclick : this.handleDeleteIconClick,
				itemcontextmenu : me.showContextMenu,
				selectionchange : me.loadRecord
			},
			'treeview' : {
				drop : me.handleNodeDrop
			},
			'orgEditor button[action=save]' : {
				click : this.handleSaveBtnClick
			},
			'orgManageContainer':{
				afterrender:this.onAfterrender
			}
		});
	},
	onAfterrender : function(cmp, opts) {
		// console.log('The onAfterrender executed');
		this.getOrgTree().getSelectionModel().select(0);
	},
	handleNodeDrop : function(node, data, overModel, dropPosition, eOpts) {
		var me = this;
		console.log("overModel=" + overModel);
		console.log("data=" + data);
		console.log("dropPosition=" + dropPosition);
		// me.getOrgTree().getStore().reload();
	},

	/**
	 * 
	 */
	loadRecord : function(selMoel, nodes, e) {
		var record = nodes[0];
		this.getOrgEditor().loadRecord(record);
		this.getOrgEditor().down('iconSelector').setValue(record.data.icon ? record.data.icon:'#');

		/* ===============工具按钮状态============= */
		var newOrgBtn = Ext.getCmp('new-org-btn'), 
			deleteOrgBtn = Ext.getCmp('delete-org-btn'),
			saveBtn=this.getOrgEditor().down('button[action=save]');
				
		deleteOrgBtn.setDisabled(record.isRoot());
		newOrgBtn.setDisabled(record.isLeaf());
		saveBtn.setDisabled(record.isRoot());

	},
	handleDeleteIconClick : function(view, rowIndex, colIndex, column, e) {
		this.deleteOrg(view.getRecord(view.findTargetByEvent(e)));
	},
	handleDeleteClick : function(component, e) {
		this.deleteOrg(this.getOrgTree().getSelectionModel().getSelection()[0]);
				
	},

	/**
	 * Deletes a list from the server and updates the view.
	 * 
	 * @param {SimpleTasks.model.List}
	 *          list
	 */
	deleteOrg : function(org) {
		var me = this, orgTree = me.getOrgTree(), orgText = org.get('text'), selModel = orgTree
				.getSelectionModel(),

		orgStore = orgTree.store;
		// console.log(menuStore);

		Ext.Msg.show({
			title : '删除菜单?',
			msg : '您确定要删除 菜单"' + orgText + '"吗?',
			buttons : Ext.Msg.YESNO,
			fn : function(response) {
				if (response === 'yes') {
					// destroy the tree node on the server
					org.parentNode.removeChild(org);
					orgStore.sync({
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
					if (!orgStore.getNodeById(selModel.getSelection()[0]
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
	handleNewClick : function(component, e) {
		this.addOrg();
	},

	addOrg : function() {

		var othis = this;
		orgTree = this.getOrgTree(), selectionModel = orgTree
				.getSelectionModel(), selectedItem = selectionModel
				.getSelection()[0],
				parentItem = selectedItem.isLeaf() ? selectedItem.parentNode
						: selectedItem, newItem = Ext.create('FM.model.bms.Org', {
					// id:othis.generateId(parentItem),
					text : '新建组织机构 ',
					parentId : parentItem.data.id,
					loaded : true
				// set loaded to true, so the tree won't try to dynamically load
				// children for this node when expanded

				}),

				expandAndSelect = function() {
					if (parentItem.isExpanded()) {
						parentItem.appendChild(newItem);
						selectionModel.select(newItem);
						othis.saveItem();
					} else {
						orgTree.on('afteritemexpand', function startSelect(item) {
							if (item === parentItem) {
								selectionModel.select(newItem);

								// 要在Expand之后append
								// newMenu，因为Excpand会加载数据库，newMenu不在数据库中
								parentItem.appendChild(newItem);
								// remove the afterexpand event listener
								orgTree.un('afteritemexpand', startSelect);
								othis.saveItem();
							}
						});
						parentItem.set('loaded', false);
						parentItem.expand();
					}
				};

		// alert(newMenu.get('id'));
		if (orgTree.getView().isVisible(true)) {
			expandAndSelect();
		} else {
			orgTree.on('expand', function onExpand() {
				expandAndSelect();
				menuTree.un('expand', onExpand);
			});
			orgTree.expand();
		}

	},
	handleSaveBtnClick:function(btn){
		this.saveItem();
	},
	saveItem : function() {
		var othis = this, form = this.getOrgEditor().getForm(), record = form
				.getRecord(), values = form.getValues(), orgStore = this
				.getOrgTree().store;

		if (!record) {
			return;
		}
		if (record.isValid()) {
			// record.set(values);
			if (!record.get('id')) {
				var id = this.generateId(this.getOrgTree().getSelectionModel()
						.getSelection()[0].parentNode), idText = othis
						.getOrgEditor().down('textfield[name=id]');
				idText.setValue(id);

			}
			form.updateRecord(record);
			othis.sync({
				store : orgStore,
				title : null,
				cmp : othis.getOrgEditor()
			});
		} else {
			Ext.Msg.alert('非法数据', '请输入正确的数据！');
		}
	},
	generateId : function(parentNode) {
		return FM.common.pk.TreeID.generateId(parentNode);
	},
	showContextMenu : function(view, org, node, rowIndex, e) {
		e.preventDefault();
		e.stopEvent();
		var contextMenu = this.getContextMenu(), newOrgItem = Ext
				.getCmp('new-org-item'), deleteOrgItem = Ext
				.getCmp('delete-org-item');

		if (org.isLeaf()) {
			newOrgItem.hide();
			deleteOrgItem.show();
		} else {
			newOrgItem.show();
			if (org.isRoot()) {
				deleteOrgItem.hide();
			} else {
				deleteOrgItem.show();
			}

		}

		contextMenu.setOrg(org);
		contextMenu.showAt(e.getX(), e.getY());
	} 
});