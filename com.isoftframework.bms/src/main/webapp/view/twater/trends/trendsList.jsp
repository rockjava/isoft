<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/jsp/common/include/public_css_js_include.jsp"%>
<%@ include file="/jsp/common/include/demo_page_css_include.jsp"%>

<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/css/demo.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/TreePicker.js"></script>

<script  type="text/javascript">
var myform=null;
 
$(document).ready(function() {
	//alert( FM.Constant.currentContainer);
	var selAll=new SellectAllCheckboxes('checkallObj','id');
	
	$("a[action='add']").click(function(){
		window.location.href="beforeAdd.do";
	});
	
	//查询事件
	myform=new FormElements('queryForm');
	myform.attachElesEvent();
	 
	//批量删除
	$("a[action=batchDelete]").click(function(){  
		var ids=selAll.getValue();
		if(ids==null || ids==""){
			alert("请选择要删除的产品");
			return ;
		}else{
			window.location.href="batchDelete.do?ids="+ids;
		}
	});
	 
	 
});


function queryAll(){
	myform.doReset();
	myform.submit();
}
	
</script>
</head>
<body>
	<div class="barnavtop">您所在的位置：公司动态管理</div>
	<div id="workspace">

		<div
			style="overflow-x: hidden; overflow-y: auto; height: auto; width: 94%;"
			id="container">
			
			<div style="text-align: right; height: 30px; margin-top: 10px">
				<span style="float: left; font-size: 14px; font-weight: bold">公司动态管理
				</span>
			</div>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="40px">
					<a class="navbutton" action='add' href="#">
					<span><span>
							<img src="${pageContext.request.contextPath}/resources/images/demo/page/edit-find.gif"
									style="margin: 4px 0px 0px -20px; *margin: 0px 0px 0px -20px; position: absolute;" />新增</span>
						</span>
					 </a> 
					</td>
					<td align="right"><a action="batchDelete"
						style="text-decoration: underline; cursor: pointer;">批量删除</a>&nbsp;&nbsp;
						 
				</tr>
			</table>


			<div class="eXtremeTable">
				<table id="CmsSiteList_table" class="tableRegion" width="100%"
					border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td width="3%" class="tableHeader" title="全选"
								onmouseover="this.className='tableHeaderSort';this.style.cursor='pointer'"
								onmouseout="this.className='tableHeader';this.style.cursor='default'">
								<input id="checkallObj" name="checkallObj" type="checkbox"
								value="" style="margin-left: 11px; width: auto; height: auto;" />
							</td>
							<td width="15%" class="tableHeader" javascript:void(0)><a
								href="javascript:void(0)" id="t_title"> 标题</a></td>
							<td width="17%" class="tableHeader" title="来源"><a href="javascript:void(0)"
								id="t_proCategory">来源</a></td>
							<td width="17%" class="tableHeader" title="发布时间"><a
								href="javascript:void(0)" id="t_image">发布时间</a></td>
						 
							<td class="tableHeader" width="14%">操作</td>
						</tr>
						<tr>
							<form action="listTrends.do" name="queryForm" id="queryForm"
								method="post">
								<td class="statusBar">&nbsp;</td>
								
								<td align="center" class="statusBar"><input type="text"
									value="${param.title}" name="title"
									class="x_inputjing" /></td>
									
								<td align="center" class="statusBar">
								<input type="text"
									value="${param.source}" name="source" id="source"  class="x_inputjing"/>
								</td>
								
								<td align="center" class="statusBar">
								<input type="text"
									value="${param.publishTime}" name="publishTime" id="publishTime"  class="x_inputjing" />
								</td>
								 
								<td align="center" class="statusBar"><a class="sexybutton"
									href="#" onclick="queryForm.submit();"><span><span>搜索</span>
									</span> </a>
									<a class="sexybutton"
									href="#" onclick="javascript:queryAll();"><span><span>清空</span>
									</span> </a>
								</td>
							</form>
						</tr>
					</thead>
					<tbody class="tableBody" id="idList">
					<form action="">
						<c:forEach items="${result}" var="item" varStatus="status">
							<tr class="odd" onmouseover="this.className='highlight'"
								onmouseout="this.className='odd'">
								<td><input name="id" type="checkbox" value="${item.id }" /> <br /></td>
								<td align="center">${item.title}</td>
								<td align="center">${item.source}</td>
								<td align="center">
									<fmt:formatDate value="${item.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								 
								<td align="center">
								<a class="sexybutton"
									href="delete.do?id=${item.id}&state=0">
									<span><span>删除</span></span>
								 </a>
								 <a class="sexybutton"
									href="beforeUpdate.do?id=${item.id}&state=0">
									<span><span>修改</span></span>
								 </a>
								</td>
							</tr>
						</c:forEach>
					</form>
					<tr style="padding: 0px;">
					<td colspan="7">
						<%@include file="/jsp/common/turnpage_include.jsp"%>
					</td>
					</tr>
					</tbody>
				</table>
			</div>

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="40px"><a class="navbutton"
						action='add'><span><span><img
									src="${pageContext.request.contextPath}/resources/images/demo/page/edit-find.gif"
									style="margin: 4px 0px 0px -20px; *margin: 0px 0px 0px -20px; position: absolute;" />新增</span>
						</span> </a> 
					</td>
					<td align="right"><a  action="batchDelete"
						style="text-decoration: underline; cursor: pointer;">批量删除</a> </td>
				</tr>
			</table>
		</div>
	</div>

</body>



</html>
