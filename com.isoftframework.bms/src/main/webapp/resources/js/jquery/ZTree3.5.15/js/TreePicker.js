/**
 * 事件绑定 例：this.onClick.bindEvent(this,{foo:123});
 * 
 * function onClick(e,me,param){
 *  }
 */
Function.prototype.bindEvent = function(object, newPara) {
	var __method = this;
	return function(event) {
		// null直接调用方法，event||window.event,object,newPara为参数
		__method.call(null, event || window.event, object, newPara);
		return null;
	};
};
/**
 * 
 * @param textid
 * @param hiddenid
 * @returns 例：treePicker=new TreePicker('proCategory','proCategoryId');
 *          treePicker.init("listProCategory.do");
 * 
 */

var TreePicker = Class.create();

TreePicker.prototype = {
	initialize : function(textid, hiddenid, url) {
		var me = this;
		this.textObj = $("#" + textid);
		this.hiddenObj = $("#" + hiddenid);
		this.treeContainerId="treeContainer_"+textid;
		this.treeContainer=$('<div id="'+this.treeContainerId+'" class="menuContent" style="display:none; position: absolute;"></div>');
		this.treeRenderTargetId="treeRenderTarget_"+textid;
		this.treeRenderTarget=$('<ul id="'+this.treeRenderTargetId+'" class="ztree" style="margin-top:0; width:160px;"></ul>');
		this.treeContainer.append(this.treeRenderTarget);
		$("body").append(this.treeContainer);
		this.textObj.on("click", function() {
			me.showMenu();
		});
		if (url) {
			me.init(url);
		}
	},
	init : function(url) {
		// "${pageContext.request.contextPath}/jsp/twater/procategory/${requrl.procategory_listProCategory}"
		var me = this;
		var setting = {
			view : {
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				 onClick: me.onItemClick.bindEvent(me)
			}
		};
		$.ajax({
			type : "GET",
			url : url,
			success : function(nodes) {
				$.fn.zTree.init(me.treeRenderTarget, setting, nodes);

			}
		});
	},
	onItemClick : function(e, me) {
		var zTree = $.fn.zTree.getZTreeObj(me.treeRenderTargetId), nodes = zTree
				.getSelectedNodes(), v = "", hv = "";

		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		for ( var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			hv += nodes[i].id + ",";
		}
		if (v.length > 0) {
			v = v.substring(0, v.length - 1);
			hv = hv.substring(0, hv.length - 1);
		}
		var textObj = me.textObj;
		textObj.attr("value", v);
		me.hiddenObj.attr("value", hv);
		me.hideMenu();
	},
	showMenu : function() {
		var textObj = this.textObj;
		var textOffset = this.textObj.offset();
		this.treeContainer.css({
			left : textOffset.left + "px",
			top : textOffset.top + textObj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", {
			me : this
		}, this.onBodyDown);
	},
	hideMenu : function() {
		this.treeContainer.fadeOut("fast");
		$("body").unbind("mousedown", {
			me : this
		}, this.onBodyDown);
	},
	onBodyDown : function(event) {
		var me = event.data.me;
		if (!(event.target.id == "menuBtn" || event.target.id == me.treeContainerId || $(
				event.target).parents("#"+me.treeContainerId).length > 0)) {
			me.hideMenu();
		}
	}
}

document.writeln('<style>');
document.writeln('body {color: #2f332a;font: 15px/21px Arial, Helvetica, simsun, sans-serif;background: #f0f6e4 \9;}');
document.writeln('h1, h2, h3, h4, h5, h6 {color: #2f332a;font-weight: bold;font-family: Helvetica, Arial, sans-serif;padding-bottom: 5px;}');
document.writeln('h1 {font-size: 24px;line-height: 34px;text-align: center;}');
document.writeln('h2 {font-size: 14px;line-height: 24px;padding-top: 5px;}');
document.writeln('h6 {font-weight: normal;font-size: 12px;letter-spacing: 1px;line-height: 24px;text-align: center;}');
document.writeln('a {color:#3C6E31;text-decoration: underline;}');
document.writeln('a:hover {background-color:#3C6E31;color:white;}');
document.writeln('input.radio {margin: 0 2px 0 8px;}');
document.writeln('input.radio.first {margin-left:0;}');
document.writeln('input.empty {color: lightgray;}');
document.writeln('code {color: #2f332a;}');
document.writeln('.highlight_red {color:#A60000;}');
document.writeln('.highlight_green {color:#A7F43D;}');
document.writeln('li {list-style: circle;font-size: 12px;}');
document.writeln('li.title {list-style: none;}');
document.writeln('ul.list {margin-left: 17px;}');

document.writeln('div.content_wrap {width: 600px;height:380px;}');
document.writeln('div.content_wrap div.left{float: left;width: 250px;}');
document.writeln('div.content_wrap div.right{float: right;width: 340px;}');
document.writeln('div.content_wrap2 {margin:10px auto;height:380px;}');
document.writeln('div.content_wrap div.left2{float: left;width: 250px;clear:left}');
document.writeln('div.content_wrap div.right2{float: right;margin:10px auto}');
document.writeln('div.zTreeDemoBackground {width:250px;height:362px;text-align:left;}');

document.writeln('ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}');
document.writeln('ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}');
document.writeln('ul.log.small {height:45px;}');
document.writeln('ul.log li {color: #666666;list-style: none;padding-left: 10px;}');
document.writeln('ul.log li.dark {background-color: #E3E3E3;}');

document.writeln('/* ruler */');
document.writeln('div.ruler {height:20px; width:220px; background-color:#f0f6e4;border: 1px solid #333; margin-bottom: 5px; cursor: pointer}');
document.writeln('div.ruler div.cursor {height:20px; width:30px; background-color:#3C6E31; color:white; text-align: right; padding-right: 5px; cursor: pointer}');
document.writeln('</style>');
