Ext.define('FM.ux.form.SearchField2', {
    extend: 'Ext.form.field.Trigger',

    alias: 'widget.searchfield2',

    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',

    trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger',

    hasSearch : false,
    paramName : 'query',
    

    initComponent: function() {
        var me = this;

        me.callParent(arguments);
        me.on('specialkey', function(field, e,eOpts ){
            if (e.getKey() == e.ENTER) {
                me.onTrigger2Click(field, e,eOpts );
            }
        });
        me.addEvents(
           'search',
           'clearSearch',
           'continueSearch'
    	);
      
    },

    afterRender: function(){
        this.callParent();
        this.triggerCell.item(0).setDisplayed(false);
    },

    onTrigger1Click : function(){
        var me = this;

        if (me.hasSearch) {
            me.setValue('');
            me.oldValue='';
          //  me.store.clearFilter();
            me.fireEvent('clearSearch',me);
            me.hasSearch = false;
            me.triggerCell.item(0).setDisplayed(false);
            me.updateLayout();
        }
    },

    onTrigger2Click : function(field, e,eOpts ){
        var me = this,
            value = me.getValue();

        if (value.length > 0) {
            // Param name is ignored here since we use custom encoding in the proxy.
            // id is used by the Store to replace any previous filter
        	if(!me.oldValue || (me.oldValue!=value)){
        		me.oldValue=value;
        		me.fireEvent('search',value, field, e,eOpts );
        	}else if(me.oldValue == value){
        		me.fireEvent('continueSearch',value, field, e,eOpts );
        	}
        	
            me.hasSearch = true;
            me.triggerCell.item(0).setDisplayed(true);
            me.updateLayout();
        }
    }
});