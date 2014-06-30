Ext.define('FM.controller.MenuController',{
	extend : 'Ext.app.Controller',
	models:['Menu'],
	stores:['Menus'],
	/**
	 * 引用对象实例 如: var navMenu=this.getFmNavMenu() selector 是select query,语法同CSS选择器
	 */
	refs : [ {
		ref : 'fmNavMenu',
		selector : 'fmNavMenu'
	}, {
		ref : 'fmTabPanel',
		selector : 'fmTabPanel'
	} ],
	init : function() {
		this.control({
			// 'fmNavMenu' 是select query
			'fmNavMenu' : {
				//itemmousedown : this.loadMenu
				//itemdblclick::this.loadMenu
				itemclick:this.handleNavMenuClick
			},
			'fmTabPanel' : {
				remove : this.onTabRemoved,
				beforeremove : this.onTabBeforeremove
			},
			'home':{
				afterrender:this.initMenuDD,
				selected:this.handelBrowserMenuSelected
				
			}

		});
		this.tabMap = new Ext.util.HashMap();
	},
	onTabRemoved : function(container, component, eOpts) {
		// Ext.destroy( Ext.ComponentQuery.query('menurscTree'));
		// Ext.destroy(component);

	},
	onTabBeforeremove : function(container, component, eOpts) {
		if (this.tabMap == null) {
			this.tabMap = new Ext.util.HashMap();
		}
		this.tabMap.add(component.id, component);

	},
	handelBrowserMenuSelected:function(record){
		this.loadMenu(record);
	},
	handleNavMenuClick : function(thisView, record,item,index,e,eOpts ) {
		this.loadMenu(record);
	},
	loadMenu : function( record) {
		var tabPanel = this.getFmTabPanel(), 
				tablePanelEl = tabPanel.getEl();
				
		//if (record.isLeaf()) {
		if (record.get('url')) {
			tablePanelEl.mask('正在加载...');
			var tabId = "tab_" + record.get('id');
			var panel = tabPanel.getComponent(tabId);

			if (!panel) {
				/**
				 * Ext.panel.Panel
				 */
				panel = this.tabMap.get(tabId);
				if (!panel) {
					panel = Ext.widget('container',{
						id : tabId,
						//xtype : '',
						autoDestroy : FM.Constants.get('autoDestroy'),
						closeAction : FM.Constants.get('closeAction'),
						closable : true,
						title : record.data.text,
						icon : record.data.icon,
						autoScroll : false,
						border : false,
						layout : 'fit'
					/*
					 * style: {
					 * 
					 * background: "url('resources/images/loading/loading.gif')
					 * no-repeat center" }
					 */

					});
					if (record.get('targetContainer') == 'iframe') {
						// 采用iframe方式
						var url = record.get('url');
						var openDocument = function() {
							document.open();
							document.domain = '"+ document.domain + "';
							document.close()
						};
						panel.html = '<iframe class="funcIFrame" frameborder="0" style="width: 100%; overflow: auto; height: 100%;" src="'
								+ url + '"/>';
					}

					else if (record.get('targetContainer') === 'panel') {
						var objectName = record.get('url');
						panel.add(Ext.create(objectName));
					} else {
						panel.autoLoad = {
							// 采用autoload方式
							url : record.get('url')
									+ (record.data.url.indexOf('?') === -1 ? '?' : '&')
									+ 'containerid=' + tabId,
							// href:'http://www.baidu.com',
							scope : this,
							scripts : true
						};
					}
				}

				this.openTab(panel, tabId);
			} else {

				if (tabPanel.getActiveTab() != panel) {
					tabPanel.setActiveTab(panel);

				}

			}
			tablePanelEl.unmask();
		}

	},
	openTab : function(panel, id) {
		var panelId = (typeof panel == "string" ? panel : id || panel.id);
		var tabPanel = this.getFmTabPanel();
		var tab = tabPanel.getComponent(panelId);
		if (tab) {
			tabPanel.setActiveTab(tab);
		} else if (typeof panel != "string") {
			panel.id = panelId;
			var p = tabPanel.add(panel);
			tabPanel.setActiveTab(p);
		}
	},
	/**
	 * 实现拖曳
	 * @param thisCmp
	 * @param eOpts
	 */
	initMenuDD:function(thisCmp,eOpts){
		 // This will make sure we only drop to the view container
		var dropTargetCmp=thisCmp;
	//	console.log(dropTargetCmp);
    var dropTargetEl =  dropTargetCmp.getEl();

    var dropTarget = Ext.create('Ext.dd.DropTarget', dropTargetEl, {
        ddGroup: 'menubrowser',
        notifyEnter: function(ddSource, e, data) {

            //Add some flare to invite drop.
        	dropTargetCmp.stopAnimation();
        	dropTargetEl.highlight();
        },
        onDragEnter:function(){
        	return false;
        },
        notifyDrop  : function(ddSource, e, data){

            // Reference the record (single selection) for readability
            var selectedRecord = ddSource.dragData.records[0];
            
            var checkDropEnable=function(record,datas){
            	if(datas.length>=8){
            		return false;
            	}
            	if(!record.get('url') ){
            		return false;
            	}
            	if(!datas || datas.length==0){
            		return true;
            	}
          		for(var i=0;i<datas.length;i++){
          			if( datas[i].get('id')==record.get('id')){
          				return false;
          			}
          		}
            	return true;
            };
           
            var datas=dropTargetCmp.getMenuBrowserStore().data.items;
            if(!checkDropEnable(selectedRecord,datas)){
            	 return false;
            }
            var menuBrowserStore= dropTargetCmp.getMenuBrowserStore();
           
            Ext.Ajax.request({
              url: FM.RequestURL.get('bms_menu_createBrowseMenu'),
              jsonData:{menuId:selectedRecord.get('id'),sort:datas.length},
              success: function(response){
                  var text = response.responseText;
                  menuBrowserStore.add(selectedRecord);
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
    });
	}

});