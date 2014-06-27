Ext.define('FM.controller.isoftstore.CategoryController',{
	extend : 'FM.controller.BaseController',
	requires : [ 
	    'FM.view.isoftstore.category.CategoryManage',
		'FM.view.isoftstore.category.CategoryEditor',
		'FM.view.isoftstore.category.CategoryTree',
		'FM.view.isoftstore.category.ContextMenu'
 	],
	stores : [ 'isoftstore.Categories' ],
	refs : [ {
		ref : 'icategoryTree',
		selector : 'icategoryTree'
	}, {
		ref : 'icategoryEditor',
		selector : 'icategoryEditor'
	}, {
		ref : 'contextMenu',
		selector : 'icategoryContextMenu',
		xtype : 'icategoryContextMenu',
		autoCreate : true
	} ],
	init : function() {
		var me = this;
		this.control({
			
			'icategoryTree' : {
				//deleteclick : this.handleDeleteIconClick,
				itemcontextmenu : me.showContextMenu,
				selectionchange : me.loadRecord,
				createNewNode:me.handleCreateNewNode,
				deleteNode:me.handleDeleteNode
			},
			'icategoryContextMenu menuitem[action=addChild]':{
				click: me.handleAddChildMenuItemClick
			},
			'icategoryContextMenu menuitem[action=delete]':{
				click: me.handleDeleleMenuItemClick
			},
			'icategoryEditor button[action=save]' : {
				click : this.handleSaveBtnClick
			},
			'icategoryManage':{
				afterrender:this.onAfterrender
			}
			 
		});
	},
	onAfterrender : function(cmp, opts) {
		// console.log('The onAfterrender executed');
		this.getIcategoryTree().getSelectionModel().select(0);
	},
	handleDeleleMenuItemClick:function(item,e){
		var tree = this.getIcategoryTree(), 
			selectionModel = tree.getSelectionModel(),
			selectedNode = selectionModel.getSelection()[0];
		this.deleteNode(tree,selectionModel,selectedNode);
	},
	handleDeleteNode:function(tree,selModel,selectedRecords){
		this.deleteNode(tree,selModel,selectedRecords[0]);
	},
	
	deleteNode : function(tree,selModel,node) {
		var me = this, 
			name = node.get('text'), 
			store = tree.store;
		// console.log(menuStore);

		Ext.Msg.show({
			title : '删除菜单?',
			msg : '您确定要删除 菜单"' + name + '"吗?',
			buttons : Ext.Msg.YESNO,
			fn : function(response) {
				if (response === 'yes') {
					// destroy the tree node on the server
					node.parentNode.removeChild(node);
					store.sync({
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
						success :function(batch, options) {
							if (!store.getNodeById(selModel.getSelection()[0].get('id'))) {
								// if the selection no longer exists in the store (it was
								// part of the deleted node(s))
								// change selection to the "All Tasks" list
								selModel.select(0);
							}
						}
					});
				}
			}
		});

	},
	handleCreateNewNode:function(tree,selectionModel,selectedRecords){
		this.addChild(tree,selectionModel,selectedRecords[0]);
	},
	handleAddChildMenuItemClick : function(component, e) {
		var tree = this.getIcategoryTree(), 
		selectionModel = tree.getSelectionModel(),
		selectedItem = selectionModel.getSelection()[0];
		this.addChild(tree,selectionModel,selectedItem);
	},
	addChild : function(tree,selectionModel,selectedItem) {

		var othis = this,
			parentItem = selectedItem.isLeaf() ? selectedItem.parentNode: selectedItem, 
			newItem = Ext.create('FM.model.isoftstore.Category', {
				// id:othis.generateId(parentItem),
				text : '新建类别 ',
			//	parentid : parentItem.data.id,
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
					tree.on('afteritemexpand', function startSelect(item) {
						if (item === parentItem) {
							selectionModel.select(newItem);

							// 要在Expand之后append
							// newMenu，因为Excpand会加载数据库，newMenu不在数据库中
							parentItem.appendChild(newItem);
							// remove the afterexpand event listener
							tree.un('afteritemexpand', startSelect);
							othis.saveItem();
						}
					});
					parentItem.set('loaded', false);
					parentItem.expand();
				}
			};

		// alert(newMenu.get('id'));
		if (tree.getView().isVisible(true)) {
			expandAndSelect();
		} else {
			tree.on('expand', function onExpand() {
				expandAndSelect();
				menuTree.un('expand', onExpand);
			});
			tree.expand();
		}

	},
	handleSaveBtnClick:function(btn){
		this.saveItem();
	},
	saveItem : function() {
		var othis = this, 
		form = this.getIcategoryEditor().getForm(), 
		record = form.getRecord(),
		values = form.getValues(),
		treeStore = this.getIcategoryTree().store;

		if (!record) {
			return;
		}
		if (record.isValid()) {
			// record.set(values);
			if (!record.get('id')) {
				var id = FM.common.pk.TreeID.generateId(this.getIcategoryTree().getSelectionModel().getSelection()[0].parentNode),
					idText = othis.getIcategoryEditor().down('textfield[name=id]');
				idText.setValue(id);

			}
			form.updateRecord(record);
			othis.sync({
				store : treeStore,
				title : null,
				cmp : othis.getIcategoryEditor()
			});
		} else {
			Ext.Msg.alert('非法数据', '请输入正确的数据！');
		}
	},
	loadRecord : function(selMoel, nodes, e) {
		var record = nodes[0];
		this.getIcategoryEditor().loadRecord(record);
	/*	this.getIcategoryEditor().down('image[name=img_show]').setSrc(
				(record.data.icon == null || record.data.icon == '') ? '#'
						: record.data.icon);*/

		/* ===============工具按钮状态============= */
		var addChildBtn = this.getIcategoryTree().down('button[action=addChild]'), 
			deleteBtn =this.getIcategoryTree().down('button[action=delete]'),
			saveBtn	=this.getIcategoryEditor().down('button[action=save]');
		 
		deleteBtn.setDisabled(record.isRoot());
		saveBtn.setDisabled(record.isRoot());

	},
	showContextMenu : function(view, record, node, rowIndex, e) {
		e.preventDefault();
		e.stopEvent();
		var contextMenu = this.getContextMenu(),
		    addChildItem = contextMenu.down('menuitem[action=addChild]'),
		    deleteItem = contextMenu.down('menuitem[action=delete]');
	 
		deleteItem.setVisible(!record.isRoot());

		contextMenu.showAt(e.getX(), e.getY());
		// e.preventDefault();
	}
	
});