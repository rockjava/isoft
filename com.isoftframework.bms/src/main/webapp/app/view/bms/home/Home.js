Ext.define('FM.view.bms.home.Home', {
	extend : 'Ext.panel.Panel',
	uses : [ 'Ext.layout.container.Border', 'Ext.form.field.Text',
			'Ext.form.field.ComboBox', 'Ext.toolbar.TextItem',
			'FM.view.bms.home.MenuBrowser', 'FM.view.bms.home.ContextMenu' ],
	alias : 'widget.home',
	id : 'menu-dlg',
	layout : 'fit',
	autoScroll : false,
	containerScroll : false,
	// modal: true,
	border : false,
	bodyBorder : false,

	/**
	 * initComponent is a great place to put any code that needs to be run when
	 * a new instance of a component is created. Here we just specify the items
	 * that will go into our Window, plus the Buttons that we want to appear at
	 * the bottom. Finally we call the superclass initComponent.
	 */
	initComponent : function() {
		this.items = [ {
			xtype : 'menubrowser',
			url : this.url,
			id : 'menu-view',
			listeners : {
				scope : this,
				selectionchange : this.onIconSelect,
				itemdblclick : this.fireImageSelected,
				itemcontextmenu : this.showContextMenu

			}

		} ];
		this.callParent(arguments);

		/**
		 * Specifies a new event that this component will fire when the user
		 * selects an item. The event is fired by the fireImageSelected function
		 * below. Other components can listen to this event and take action when
		 * it is fired
		 */
		this.addEvents(

		'selected');
	},
	getContextMenu : function() {
		return Ext.widget('menuBrowserContextMenu', {
			listeners : {
				scope : this,
				openMenu : this.onOpenMenuClick,
				deleteBrowseMenu : this.onDeleteBrowseMenu
			}
		});
	},
	showContextMenu : function(thisView, record, item, index, e, eOpts) {
		this.setSelectIcon(index);

		e.preventDefault();
		e.stopEvent();
		var contextMenu = this.getContextMenu();

		contextMenu.setSysMenu(record);
		contextMenu.showAt(e.getX(), e.getY());
	},
	onOpenMenuClick : function(component, e) {
		this.fireImageSelected();
	},
	onDeleteBrowseMenu : function(component, e) {
		var record = this.getMenuBrowser().selModel.getSelection()[0];
		console.log(record);
		if (record) {
			this.getMenuBrowserStore().remove(record);
			Ext.Ajax.request({
				url : FM.RequestURL.get('bms_menu_deleteBrowseMenu'),
				jsonData : {
					menuId : record.get('id')
				},
				success : function(response) {
					var text = response.responseText;
					return true;
				},
				failure : function(response) {
					Ext.MessageBox.show({
						title : '删除失败',
						msg : response.responseText,
						icon : Ext.Msg.ERROR,
						buttons : Ext.Msg.OK
					});
				}
			});
		}
	},
	/**
	 * Called whenever the user clicks on an item in the DataView. This tells
	 * the info panel in the east region to display the details of the image
	 * that was clicked on
	 */
	onIconSelect : function(dataview, selections) {
		var selected = selections[0];
		if (selected) {
		}
	},

	setSelectIcon : function(idx) {
		this.down('menubrowser').selModel.select(idx);

		this.fireEvent('selectionchange', this.getMenuBrowser().selModel, this
				.getMenuBrowser().selModel.getSelection());
	},

	/**
	 * Fires the 'selected' event, informing other components that an image has
	 * been selected
	 */
	fireImageSelected : function() {
		var record = this.getMenuBrowser().selModel.getSelection()[0];

		if (record) {
			this.fireEvent('selected', record);
		}
	},
	getMenuBrowser : function() {
		return this.down('menubrowser');
	},
	getMenuBrowserStore : function() {
		return this.getMenuBrowser().store;
	},
	setUrl : function(url) {
		this.getMenuBrowserStore().getProxy().url = url;
	}

});