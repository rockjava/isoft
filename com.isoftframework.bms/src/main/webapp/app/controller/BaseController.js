Ext.define('FM.controller.BaseController',{
	extend : 'Ext.app.Controller',

	/**
	 * {record,title,cmp}
	 * 
	 * @param options
	 */
	save : function(options) {
		var maskTarget = options.maskTarget,
			el =( maskTarget ? maskTarget.getEl() : null), 
			record = options.record,
			title = options.title;
		if (el)
			el.mask('正在保存...');
		record
				.save({
					success : function(record, operation) {
						console.log(operation.response.responseText);
						if (el)
							el.unmask();
					},
					failure : function(record, operation) {
						var error = operation.getError(), returnObj = operation.response.responseText, msg = Ext
								.isObject(error) ? error.status + ' '
								+ error.statusText
								: Ext.isObject(returnObj) ? returnObj.msg : error;
						if (!title)
							title = '保存失败';
						Ext.MessageBox.show({
							title : title,
							msg : msg,
							icon : Ext.Msg.ERROR,
							buttons : Ext.Msg.OK
						});
						if (el)
							el.unmask();
					}
				});
	},
	/**
	 * options {store,title,cmp}
	 */
	sync : function(options) {
		var maskTarget = options.maskTarget, 
			el = (maskTarget ? maskTarget.getEl() : null),
			store = options.store, 
			title = options.title;
		if (el)
			el.mask('正在保存...');
		store.sync({
			callback : function(batch, options) {
				console.log(store.getProxy().getReader().jsonData);

			},
			success : function(batch, options) {
				console.log(store.getProxy().getReader().jsonData);
				if (el) {
					el.unmask();
					console.log('------unmask finished');
				}

			},
			failure : function(batch, options) {
				var error = batch.exceptions[0].getError(), returnObj = store
						.getProxy().getReader().jsonData, msg = Ext
						.isObject(error) ? error.status + ' '
						+ error.statusText
						: Ext.isObject(returnObj) ? returnObj.msg : error;
				if (!title)
					title = '保存失败';
				Ext.MessageBox.show({
					title : title,
					msg : msg,
					icon : Ext.Msg.ERROR,
					buttons : Ext.Msg.OK
				});
				if (el)
					el.unmask();
			}
		});
	}

});