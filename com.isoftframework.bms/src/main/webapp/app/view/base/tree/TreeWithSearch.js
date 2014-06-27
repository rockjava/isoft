Ext.define('FM.view.base.tree.TreeWithSearch', {
    extend: 'FM.view.base.tree.BaseTree',
    requires: [
       'FM.ux.form.SearchField2'
    ],
    handleClearSearchOrgTree : function(field) {
		console.log('-------handleClearSearchOrgTree');
		  
		var selectionModel = this.getSelectionModel();
		this.searchedNode = null;
		selectionModel.select(this.getRootNode());
	},
	handleSearchOrgTree : function(value, field, e, eOpts) {
		console.log('-------handleSearchOrgTree');
		this.doSearch(value, this.getRootNode());
	},
	handleContinueSearchOrgTree : function(value, field, e, eOpts) {

		var fromNode = this.searchedNode;

		if (fromNode) {
			console.log('------handleContinueSearchOrgTree');
			this.searchedNode = null;
			this.doSearch(value, null, fromNode);
		} else {
			console.log('------handleContinueSearchOrgTree------redo');
			this.doSearch(value, this.getRootNode());
		}
	},
	/*
	 * 展开并搜索
	 */
	expandAndSearch : function(tree, selectionModel, parentNode,regExp, fromNode) {
		// 匹配节点与搜索条件
		var me = this;
		var matchNode = function(node) {
			if (regExp.test(Ext.String.trim(node.get('text')))) {
				selectionModel.select(node);
				me.searchedNode = node;
				console.log(node.get('text') + "=="+ regExp.test(Ext.String.trim(node.get('text'))));
				return true;
			}
			return false;
		};
		// 搜索节点
		var search = function(tree, selectionModel, parentNode, regExp,fromNode) {

			if (fromNode) {
				// 如果有起始节点，从起始节点查询（不包括起始节点）
				//console.log('--fromNode--' + fromNode.get('text'));
				// search child
				var childNodes = fromNode.childNodes;
				if (childNodes && childNodes.length > 0) {
					for ( var i = 0; i < childNodes.length; i++) {
						if (search(tree, selectionModel, childNodes[i], regExp)) {
							return true;
						}
					}
				}
				// search parent sibling
				var parentFromNode = fromNode;
				while (parentFromNode) {
					var siblingNode = parentFromNode.nextSibling;
					while (siblingNode) {
						if (search(tree, selectionModel, siblingNode, regExp)) {
							return true;
						}

						siblingNode = siblingNode.nextSibling;
					}
					parentFromNode = parentFromNode.parentNode;
				}

				return false;

			} else {

				if (matchNode(parentNode)) {
					return true;
				}
				var childNodes = parentNode.childNodes;
				if (childNodes && childNodes.length > 0) {
					for ( var i = 0; i < childNodes.length; i++) {
						if (search(tree, selectionModel, childNodes[i], regExp)) {
							return true;
						}
					}
				}
				return false;
			}

		};
		tree.expandAll(function() {
			search(tree, selectionModel, parentNode, regExp, fromNode);
		});

	},
	doSearch : function(value, parentNode, fromNode) {

	 
		var selectionModel = this.getSelectionModel();

		/**
		 *（1） g:用作全局标志。如果设置了这个标志，使用这个正则表达式模式对某个文本执行搜索和替换操作时，将对文本中所有匹配的部分起作用。如果没有设置这个标志，则搜索和替换文本中的最早匹配的那部分内容。
		 *（2） i：用作忽略大小写标志。如果设置了此标志，在进行匹配比较时，将忽略大小写。
		 */
		var regExp = new RegExp(  Ext.String.trim(value),'ig');
		//var regExp = new RegExp(Ext.String.trim('(?i)'+value+'(?-i)'));

		this.expandAndSearch(this, selectionModel, parentNode, regExp,fromNode);

	}
});