Ext.define('FM.view.East',{
	extend: 'Ext.tab.Panel',
	requires:['Ext.grid.property.Grid'],
	alias: 'widget.east',
	title: 'East Side',
    animCollapse: true,
    collapsible: true,
    split: true,
    width: 225, // give east and west regions a width
    minSize: 175,
    maxSize: 400,
    margins: '0 5 0 0',
    activeTab: 1,
    tabPosition: 'bottom',
    dockedItems: [{
        dock: 'top',
        xtype: 'toolbar',
        items: [ '->', {
           xtype: 'button',
           text: 'test',
           tooltip: 'Test Button'
        }]
    }],
    items: [{
        html: '<p>A TabPanel component can be a region.</p>',
        title: 'A Tab',
        autoScroll: true
    }, Ext.create('Ext.grid.property.Grid', {
        title: 'Property Grid',
        closable: true,
        source: {
            "(name)": "Properties Grid",
            "grouping": false,
            "autoFitColumns": true,
            "productionQuality": false,
            "created": Ext.Date.parse('10/15/2006', 'm/d/Y'),
            "tested": false,
            "version": 0.01,
            "borderWidth": 1
        }
    })]
});

               