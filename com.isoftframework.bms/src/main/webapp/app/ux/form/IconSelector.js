Ext.define('FM.ux.form.IconSelector', {
	extend : 'Ext.form.FieldContainer',
	requires : [ 'FM.ux.view.iconChooser.Window' ],
	alias : 'widget.iconSelector',
	layout : 'hbox',
	combineErrors : true,
	name:'icon',
	defaults : {
		hideLabel : 'true'
	},
	initComponent: function() {
    	var me=this;
    	//name=iconFieldName
    	 
    	this.items = [ {
    		xtype : 'hiddenfield',
    		name : me.name?me.name:'icon'

    	}, {
    		xtype : 'image',
    		tag : 'img',
    		name : 'img_show',
    		src : '#'
    	}, {
    		xtype : 'button',
    		text : '选择一个图标',
    		//action : 'insertIcon',
    		scope : me,
    		handler : me.showSelectIconWin
    	} ];
    	this.callParent(arguments);
	},
	
	showSelectIconWin : function(insertButton) {
		var me = this;
			
		if (me.iconWindow == null) {
			me.iconWindow = Ext.create('FM.ux.view.iconChooser.Window', {
				url :me.url?me.url:FM.RequestURL.get('bms_menu_getMenuIcons'),
				animateTarget : insertButton.getEl(),
				listeners : {
					scope : me,
					selected : me.insertSelectedImage 
				}
			});
		}
		me.iconWindow.show();
	},
	insertSelectedImage : function(img) {
		this.setValue(img,true);
	},
	setValue : function(value,animate) {
		var me = this,
		url=typeof value == "string" ? value : value.get('url');
		var image = this.down('image');
		image.setSrc(url);
		// hiddenfield 
		this.geIconField().setValue(url);
        if(animate){
        	// image.getEl()通过Ext组件获取Ext元素
    		var imageEl = image.getEl();
    		// hide it straight away then fade it in over 500ms, finally use the
    		// frame animation to give emphasis
    		imageEl.hide().show({
    			duration : 500
    		}).frame();

    		// this will make the window animate back to the newly inserted
    		// image element
    		me.iconWindow.animateTarget = imageEl;
        }
	},
	getValue:function(){
		this.geIconField().getValue();
	},
	geIconField:function(){
		//this.down('hiddenfield[name=icon]');
		return this.down('hiddenfield[name='+this.name+']');
	}
});