Ext.define('FM.Constants',{
	singleton: true,
	pageSize: 10,
	currentContainer:null ,
	
	useLocalStorage: false,
	/**
	 * 窗口关闭动作
	 */
	autoDestroy: false ,
	closeAction:'hide',
	
	
	get: function(field) {
        return this[field];
    }
});
 