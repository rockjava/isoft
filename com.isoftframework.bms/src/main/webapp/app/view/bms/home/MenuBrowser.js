/**
 * @class Ext.chooser.IconBrowser
 * @extends Ext.view.View
 * @author Ed Spencer
 * 
 * This is a really basic subclass of Ext.view.View. All we're really doing here is providing the template that dataview
 * should use (the tpl property below), and a Store to get the data from. In this case we're loading data from a JSON
 * file over AJAX.
 */
Ext.define('FM.view.bms.home.MenuBrowser', {
    extend: 'Ext.view.View',
    alias: 'widget.menubrowser',
    requires:['FM.model.bms.BrowserMenu','Ext.data.Store'],
    
	  singleSelect: true,
    overItemCls: 'x-view-over',
    itemSelector: 'div.thumb-wrap',
    tpl: [
        // '<div class="details">',
            '<tpl for=".">',
                '<div class="thumb-wrap">',
                    '<div class="thumb">',
                    (!Ext.isIE6? '<img src="{icon}" width="150" height="150" />' : 
                    '<div style="width:150px;height:150px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'{icon}\')"></div>'),
                    '</div>',
                    '<span>{text}</span>',
                '</div>',
            '</tpl>'
        // '</div>'
    ],
    
    initComponent: function() {
       this.store = Ext.create('Ext.data.Store', {
            autoLoad: true,
            model : 'FM.model.Menu',
          
            proxy: {
                type: 'ajax',
                
              	api : {
            			create : FM.RequestURL.get('bms_menu_createBrowseMenu'),
            			read : this.url 
            		},
                reader: {
                    type: 'json',
                    root: ''
                }
            }
        });
        
        this.callParent(arguments);
        this.store.sort();
    }

});