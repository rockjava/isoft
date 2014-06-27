<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="../../common/include/public_css_js_include.jsp"%>
<%@ include file="../../common/include/demo_page_css_include.jsp"%>
<%
 String sname="";
String encoding="UTF-8";
if(request.getParameter("sname")!=null){
	sname= java.net.URLDecoder.decode(request.getParameter("sname") , encoding); 
	// sname=new String(request.getParameter("sname").getBytes("ISO-8859-1"),"UTF-8") ;
}
	
%>
<html>
<head>
<title>会员消费后台-会员管理</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.10.2.js"></script>
<script language="javascript" type="text/javascript">
var myform=null;
function queryAll(){
	myform.doReset();
	myform.submit();
}
	
$(document).ready(function() {
	//alert( FM.Constant.currentContainer);
	$("a[action='addMember']").click(function(){
		window.location.href="${requrl.page_beforeAddMember}";
	});
	//查询事件
	myform=new FormElements('queryForm');
	myform.attachElesEvent();
	 
	//表格排序
	var to = new TableOrder("idList"); 
	to.addOrderColumn("t_createTime", 1, {DataType: "string" });
	to.addOrderColumn("t_scode", 2, {DataType: "int" }); 
	to.addOrderColumn("t_sname", 3, {DataType: "string" }); 
	to.addOrderColumn("t_clubChannelDTOId", 4, {Attribute: "_order",DataType: "string" }); 
	to.addOrderColumn("t_isLocked", 5, {Attribute: "_order",DataType: "string" }); 
	//全选
	var selAll=new SellectAllCheckboxes('checkallObj','itemid');
	
	//批量解锁
	$("a[action=batchUnLock]").click(function(){
		//updateAll?state=0
		updateLockstatus('0');
	 	
	});
	//批量锁定
	$("a[action=batchLock]").click(function(){  
		updateLockstatus('1');
	});
	
	function updateLockstatus(status){
		var ids=selAll.getValue();
		if(ids==null || ids==""){
			alert("请选择要解锁的用户");
			return ;
		}else{
			window.location.href="${requrl.page_batchUpdate}?state="+status+"&ids="+ids;
		}
	}
	
});
	


	
</script>
</head>
<body>
	<div class="barnavtop">您所在的位置：用户管理</div>

	<div id="workspace">

		<div
			style="overflow-x: hidden; overflow-y: auto; height: auto; width: 94%;"
			id="container">

			<div style="text-align: right; height: 30px; margin-top: 10px">
				<span style="float: left; font-size: 14px; font-weight: bold">用户管理
				</span>
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="40px"><a class="navbutton" action='addMember'
						href="#"><span><span><img 
									src="${pageContext.request.contextPath}/resources/images/demo/page/edit-find.gif"
									style="margin: 4px 0px 0px -20px; *margin: 0px 0px 0px -20px; position: absolute;" />新增用户</span>
						</span> </a> <a class="navbutton" href="exportExcel"><span><span><img
									src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif"
									style="margin: 4px 0px 0px -20px; *margin: 0px 0px 0px -20px; position: absolute;" />导出用户信息</span>
						</span> </a></td>
					<td align="right"><a action="batchLock"
						style="text-decoration: underline; cursor: pointer;">批量锁定</a>&nbsp;&nbsp;
						<a action="batchUnLock"
						style="text-decoration: underline; cursor: pointer;">批量解锁</a></td>
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
								href="javascript:void(0)" id="t_createTime"> 创建时间</a></td>
							<td width="17%" class="tableHeader" title="工号"><a href="javascript:void(0)"
								id="t_scode">工号</a></td>
							<td width="17%" class="tableHeader" title="姓名"><a
								href="javascript:void(0)" id="t_sname">姓名</a></td>
							<td class="tableHeader" width="18%"><a
								href="javascript:void(0)" id="t_clubChannelDTOId" > 所属绿色通道</a></td>
							<td class="tableHeader" width="16%"><a
								href="javascript:void(0)" id="t_isLocked">用户状态</a></td>
							<td class="tableHeader" width="14%">操作</td>
						</tr>
						<tr>
							<form action="${requrl.page_queryClubMember}" name="queryForm" id="queryForm"
								method="post">
								<td class="statusBar">&nbsp;</td>
								<td align="center" class="statusBar"><input type="text"
									value="${param.createTime}" name="createTime"
									class="x_inputjing" /></td>
								<td align="center" class="statusBar"><input type="text"
									value="${param.scode}" name="scode" class="x_inputjing" /></td>
								<td align="center" class="statusBar"><input type="text"
									value="<%=sname%>" name="sname" class="x_inputjing" /></td>
								<td align="center" class="statusBar"><select
									name="clubChannelDTOId" style="width: 98%">
										<option value="" selected>请选择所属绿色通道</option>
										<c:forEach items="${channelList}" var="item"
											varStatus="status">
											<option value="${item.id}"
												<c:if test="${param.clubChannelDTOId==item.id}">selected</c:if>>
												${item.sname}</option>
										</c:forEach>
								</select></td>
								<td align="center" class="statusBar"><select
									style="width: 98%" name="isLocked">
										<option value="" selected>全部</option>
										<option value="0"
											<c:if test="${param.isLocked=='0'}">selected</c:if>>
											未锁定</option>
										<option value="1"
											<c:if test="${param.isLocked=='1'}">selected</c:if>>
											已锁定</option>
								</select></td>
								<td align="center" class="statusBar"><a class="sexybutton"
									href="#" onclick="queryForm.submit();"><span><span>搜索</span>
									</span> </a>
									<a class="sexybutton"
									href="#" onclick="queryAll()"><span><span>清空</span>
									</span> </a></td>
							</form>
						</tr>
					</thead>
					<tbody class="tableBody" id="idList">
					<form action="">
						<c:forEach items="${result}" var="item" varStatus="status">
							<tr class="odd" onmouseover="this.className='highlight'"
								onmouseout="this.className='odd'">
								<td><input name="itemid" type="checkbox" value="${item.id }" /> <br /></td>
								<td align="center"><fmt:formatDate pattern="yyyy-MM-dd"
										value="${item.createTime}" /></td>
								<td align="center">${item.scode}</td>
								<td align="center">${item.sname}</td>
								<td align="center" _order="${item.clubChannelDTO.id}">

									${item.clubChannelDTO.sname}</td>
								<td align="center" _order="${item.isLocked}"><c:choose>
										<c:when test="${item.isLocked==1}">已锁定</c:when>
										<c:otherwise>未锁定</c:otherwise>
									</c:choose></td>
								<td align="center"><c:choose>
										<c:when test="${item.isLocked==1}">
											<a class="sexybutton"
												href="updateLockState?id=${item.id }&state=0"><span><span>解锁</span>
											</span> </a>
										</c:when>
										<c:otherwise>
											<a class="sexybutton"
												href="updateLockState?id=${item.id }&state=1"><span><span>锁定</span>
											</span> </a>
											<a class="sexybutton"
												href="beforeEditMemberInfo?id=${item.id }"><span><span>修改</span>
											</span> </a>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</form>
					<tr style="padding: 0px;">
					<td colspan="7">
						<%@include file="../../common/page/AutosizePagetoolbar.jsp"%>
					</td>
					</tr>
					</tbody>
				</table>
			</div>

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="40px">
					<a class="navbutton" action='addMember'>
						<span><span>
						<img src="${pageContext.request.contextPath}/resources/images/demo/page/edit-find.gif"
									style="margin: 4px 0px 0px -20px; *margin: 0px 0px 0px -20px; position: absolute;" />
									新增用户
						</span></span> </a> 
						
						<a class="navbutton" href="exportExcel"><span><span><img
									src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif"
									style="margin: 4px 0px 0px -20px; *margin: 0px 0px 0px -20px; position: absolute;" />导出用户信息</span>
						</span> </a></td>
					<td align="right"><a  action="batchLock"
						style="text-decoration: underline; cursor: pointer;">批量锁定</a> <a
						 action="batchUnLock"
						style="text-decoration: underline; cursor: pointer;">批量解锁</a></td>
				</tr>
			</table>
		</div>
	</div>

</body>



</html>
