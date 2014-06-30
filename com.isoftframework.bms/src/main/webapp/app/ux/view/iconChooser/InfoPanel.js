/**
 * @class Ext.chooser.InfoPanel
 * @extends Ext.panel.Panel
 * @author Ed Spencer
 * 
 * This panel subclass just displays information about an image. We have a simple template set via the tpl property,
 * and a single function (loadRecord) which updates the contents with information about another image.
 */
Ext.define('FM.ux.view.iconChooser.InfoPanel', {
    extend: 'Ext.panel.Panel',
    alias : 'widget.infopanel',
   // id: 'img-detail-panel',
   // name:'img-chooser-dlg',
    width: 200,
    minWidth: 200,

    tpl: [
        '<div name="img-chooser-detail" class="details">',
            '<tpl for=".">',
                    (!Ext.isIE6? '<img src="{url}" />' : 
                    '<div style="width:74px;height:74px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'{url}\')"></div>'),
                '<div class="details-info">',
                    '<span><b>图标名称:</b>{name}</span>',
                    '<span><b>图标 URL:</b><a href="{url}" target="_blank">{url}</a></span>',
                    '<span><b>图标类型:</b>{type}</span>',
                '</div>',
            '</tpl>',
        '</div>'
    ],
    
    afterRender: function(){
        this.callParent();
        if (!Ext.isWebKit) {
            this.el.on('click', function(){
                alert('The Sencha Touch examples are intended to work on WebKit browsers. They may not display correctly in other browsers.');
            }, this, {delegate: 'a'});
        }    
    },

    /**
     * Loads a given image record into the panel. Animates the newly-updated panel in from the left over 250ms.
     */
    loadRecord: function(image) {
        this.body.hide();
        this.tpl.overwrite(this.body, image.data);
        this.body.slideIn('l', {
            duration: 250
        });
    }
});