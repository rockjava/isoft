<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - Custom Icon </TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery.form-3.46.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/json2.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/myForm.js"></script>

	
	<SCRIPT type="text/javascript">
		var zTree, 
			rMenu,
		 	form;
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onRightClick: OnRightClick,
				selectionchange:OnSelectionchange
				
			}
		};
		
		function OnSelectionchange( treeId, node){
			//alert(treeId+","+node.name);
			
			form.loadRecord(node);
		}
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}

		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			if (type=="root") {
				$("#m_del").hide();
				$("#m_check").hide();
				$("#m_unCheck").hide();
			} else {
				$("#m_del").show();
				$("#m_check").show();
				$("#m_unCheck").show();
			}
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		var addCount = 1;
		function addTreeNode() {
			hideRMenu();
			var newNode = { name:"增加" + (addCount++)};
			if (zTree.getSelectedNodes()[0]) {
				newNode.checked = zTree.getSelectedNodes()[0].checked;
				zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
			} else {
				zTree.addNodes(null, newNode);
			}
		}
		
		function addNextSiblingTreeNode() {
			
			hideRMenu();
			var selectedNode=zTree.getSelectedNodes()[0],
				parentNode=selectedNode?selectedNode.getParentNode():null,
				parentId=parentNode==null?'root':parentNode.id;
			$.ajax({
				url:'${requrl.procategory_generateId}',
				data:{parentId:parentId},
				type:'GET',
				//dataType:'json',
				success:function(data){
					//alert(data.id);
					var newNode = {id:data.id,parentId:parentId, name:"增加产品类别" + (addCount++),newNode:true };
					newNode.checked = true;
					zTree.addNodes(parentNode, newNode);
					zTree.selectNode(newNode);
				},
	            error: function (msg) {
	                alert("error="+msg);
	            }
			});
			
		}
		function removeTreeNode() {
			hideRMenu();
			var nodes = zTree.getSelectedNodes();
			var deleteNode=function(node){
				$.ajax({
					   type: "POST",
					   url: "${requrl.procategory_delete}",
					   data:{id:node.id},
					   success: function(data){
						   zTree.removeNode(node);
							//alert(data.msg);
					   }
				});
			};
			if (nodes && nodes.length>0) {
				if (nodes[0].children && nodes[0].children.length > 0) {
					var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
					if (confirm(msg)==true){
						deleteNode(nodes[0]);
					}
				} else {
					deleteNode(nodes[0]);
				}
			}
		}
		function checkTreeNode(checked) {
			var nodes = zTree.getSelectedNodes();
			if (nodes && nodes.length>0) {
				zTree.checkNode(nodes[0], checked, true);
			}
			hideRMenu();
		}
		function resetTree() {
			hideRMenu();
			$.fn.zTree.init($("#treeProCategory"), setting, zNodes);
		}

		function submitClik(oform){
			//settings.data=this.getJsonData().toJSONString(),
			var categoryObj=form.getJsonData();
			var data=JSON.stringify(categoryObj);
			 
			form.ajax({
				url:'${requrl.procategory_saveProCategory}',
				data:data,
				success:function(msg){
					//alert(msg.id);
					var node=zTree.getNodeByParam('id',msg.id);
					//$.extend(node,categoryObj);
					node.name=categoryObj.name;
					//node.newNode=false;
					zTree.updateNode(node);
				}
			});
		}

		$(document).ready(function(){
			form=new FormElements('procategory_form');
			$.ajax({
				   type: "GET",
				   url: "${requrl.procategory_listProCategory}",
				   success: function(nodes){
					// var zNodes ={id:'root',name:'root', isHidden:true,children:nodes};
					 $.fn.zTree.init($("#treeProCategory"), setting, nodes);
					 zTree = $.fn.zTree.getZTreeObj("treeProCategory");
					 zTree.selectNode(zTree.getNodes()[0]);
					 rMenu = $("#rMenu");

				   }
			});
			
			/* $('#procategory_form').submit(function() {
			    // 提交表单
			    $(this).ajaxSubmit();
			    // 为了防止普通浏览器进行表单提交和产生页面导航（防止页面刷新？）返回false
			    return false;
			}); */
			/* $('#procategory_form').ajaxForm(function() { 
                alert("Thank you for your comment!"); 
            }); */
			
		});
		
	</SCRIPT>
	<style type="text/css">
		div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
		div#rMenu ul li{
			margin: 1px 0;
			padding: 0 5px;
			cursor: pointer;
			list-style: none outside none;
			background-color: #DFDFDF;
		}
	</style>
	
</HEAD>

<BODY>
<h1>产品类别管理</h1>

<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeProCategory" class="ztree"></ul>
	</div>
	<div class="right" >
		<form name="procategory_form"  enctype="multipart/form-data" id="procategory_form" method="post" action="${requrl.procategory_saveProCategory}" >
		<table>
			
			 
			<tr>
				<td  align="right">类别名称:</td>
				<td>
				 <input type="hidden" name="parentId"/>
				  <input type="hidden" name="newNode"/>
				  <input type="hidden" name="tId"/>
				  <input type="hidden" name="id"/>
				<input type="text" name="name"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
				 	<input type="button" value="提交" onclick='submitClik(this)'/> 
				</td>
			</tr>
			
		</table>
		</form>
	</div>
</div>

<div id="rMenu">
	<ul>
		<li id="m_add" onclick="addNextSiblingTreeNode();">增加兄弟节点</li>
		<li id="m_del" onclick="removeTreeNode();">删除节点</li>
	 
	</ul>
</div>

</BODY>
</HTML>