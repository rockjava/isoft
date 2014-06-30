Ext.define('FM.view.Header', {
    extend: 'Ext.Component',
    xtype: 'box',
    alias: 'widget.fmheader',
	cls: 'header',
	height: 70,
	minHeight: 60,
	border : false,
    margins: '0 0 0 0',
	//split: true,
	//collapsible: true,
    initComponent: function() {
        Ext.apply(this, {
          
            
            html: '<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">'+
				  '<tr><td width="254px" style="background:url(resources/images/top_left.jpg);background-repeat:no-repeat;">&nbsp;</td>'+
				  '<td style="background:url(resources/images/top_center.jpg)">&nbsp;</td>'+
				  '<td width="544px" style="background:url(resources/images/top_right.jpg);background-repeat:no-repeat;">'+
				  '<span id="clock" class="clock"></span> <a href="j_spring_security_logout">注销</a></td>'+
				  '</tr>'+
				'</table>'
			
        });
        this.callParent(arguments);
    }
});