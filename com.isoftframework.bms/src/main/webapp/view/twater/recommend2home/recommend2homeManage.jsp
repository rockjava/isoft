<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.frm.common.page.toolbar.*,com.frm.common.page.pageInfo.AbstractPageInfo" language="java"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/jsp/common/include/public_css_js_include.jsp"%>
<%@ include file="/jsp/common/include/demo_page_css_include.jsp"%>
<%

String encoding=com.frm.web.filter.EncodeFilter.getEncoding();
String proCategory="";
if(request.getParameter("proCategory")!=null){
	proCategory= java.net.URLDecoder.decode(request.getParameter("proCategory") , encoding); 
}

String proCategory2="";
if(request.getParameter("proCategory2")!=null){
	proCategory2= java.net.URLDecoder.decode(request.getParameter("proCategory2") , encoding); 
}
	
%>
<html>
<head>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/TreePicker.js"></script>

<script type="text/javascript">
var queryNotRecommendForm=null;
var queryCommendedForm=null;
var selAllNotRecommend=null;
var selAllRecommended=null;
var proCategoryTreePicker;
var proCategoryTreePicker2;

$(document).ready(function() {
	//查询事件
	queryNotRecommendForm=new FormElements('queryNotRecommendForm');
	queryNotRecommendForm.attachElesEvent();
	
	queryCommendedForm=new FormElements('queryCommendedForm');
	queryCommendedForm.attachElesEvent();
	//批量推荐
	selAllNotRecommend=new SellectAllCheckboxes('checkallNotRecommendObj','notRecommendId');
	$("a[action=batchRecommend]").click(function(){  
		var ids=selAllNotRecommend.getValue();
		if(ids==null || ids==""){
			alert("请选择要推荐到首页产品");
			return ;
		}else{
			window.location.href="batchRecommend.do?ids="+ids;
		}
	});
	
	//批量取消推荐
	selAllRecommended=new SellectAllCheckboxes('checkallRecommendedObj','recommendedId');
	$("a[action=batchCancelRecommend]").click(function(){  
		var ids=selAllRecommended.getValue();
		if(ids==null || ids==""){
			alert("请选择要取消推荐的产品");
			return ;
		}else{
			window.location.href="batchCancelRecommend.do?ids="+ids;
		}
	});
	
	proCategoryTreePicker=new TreePicker('proCategory','proCategoryId',"${pageContext.request.contextPath}/jsp/twater/procategory/${requrl.procategory_listProCategory}");
	
	proCategoryTreePicker2=new TreePicker('proCategory2','proCategoryId2',"${pageContext.request.contextPath}/jsp/twater/procategory/${requrl.procategory_listProCategory}");
	
	
});

function queryNotRecommendAll(){
	queryNotRecommendForm.doReset();
	queryNotRecommendForm.submit();
}

function queryCommendedAll(){
	queryCommendedForm.doReset();
	queryCommendedForm.submit();
}

</script>
</head>
<body>
<table width="100%">
	<tr>
		<td valign="top">
		<div class="eXtremeTable">
				<table id="CmsSiteList_table" class="tableRegion" width="100%"
					border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td width="3%" class="tableHeader" title="全选"
								onmouseover="this.className='tableHeaderSort';this.style.cursor='pointer'"
								onmouseout="this.className='tableHeader';this.style.cursor='default'">
								<input id="checkallNotRecommendObj" name="checkallObj" type="checkbox"
								value="" style="margin-left: 11px; width: auto; height: auto;" />
							</td>
							<td width="15%" class="tableHeader" javascript:void(0)><a
								href="javascript:void(0)" id="t_name"> 名称</a></td>
							<td width="17%" class="tableHeader" title="类别"><a href="javascript:void(0)"
								id="t_proCategory">类别</a></td>
							 
							<td class="tableHeader" width="14%">操作</td>
						</tr>
						<tr>
							<form action="recommend2homeManage.do" name="queryNotRecommendForm" id="queryNotRecommendForm"
								method="post">
								<td class="statusBar">&nbsp;</td>
								
								<td align="center" class="statusBar"><input type="text"
									value="${param.name}" name="name"
									class="x_inputjing" /></td>
									
								<td align="center" class="statusBar">
								<input type="hidden" id="proCategoryId"  name="proCategoryId"/>
								<input type="text"
									value="<%=proCategory%>" name="proCategory" id="proCategory"  class="x_inputjing" /></td>
								 
								<td align="center" class="statusBar"><a class="sexybutton"
									href="#" onclick="queryNotRecommendForm.submit();"><span><span>搜索</span>
									</span> </a>
									<a class="sexybutton"
									href="#" onclick="javascript:queryNotRecommendAll();"><span><span>清空</span>
									</span> </a></td>
							</form>
						</tr>
					</thead>
					<tbody class="tableBody" id="idList">
					<form action="">
						<c:forEach items="${notRecommendList}" var="item" varStatus="status">
							<tr class="odd" onmouseover="this.className='highlight'"
								onmouseout="this.className='odd'">
								<td><input name="notRecommendId" type="checkbox" value="${item.id }" /> <br /></td>
								<td align="center">${item.name}</td>
								<td align="center">${item.proCategoryName}</td>
								  
								<td align="center">
								<a class="sexybutton"
									href="recommend.do?id=${item.id}">
									<span><span>推荐到首页</span></span>
								 </a>
								
								</td>
							</tr>
						</c:forEach>
					</form>
					<tr style="padding: 0px;">
						<td colspan="7">
						 <%
						AbstractPageInfo notRecommendPageInfo=(AbstractPageInfo)request.getAttribute("notRecommendPageInfo");
						out.println(new CommonPagetoolbar("notRecommendPageTool").getPageToolBarHtml(request,notRecommendPageInfo));
						%>
						</td>
					</tr>
					</tbody>
					
				</table>
			</div>
		</td>
		<td width="5%" align="center" valign="middle">
			<div>
			 <a class="sexybutton" href="#" action="batchRecommend" title="推荐到首页" >
				<span><span>&gt;&gt; </span></span>
			 </a>
			 </div>	
			 <br>
			 <div>
			 <a class="sexybutton" href="#" action="batchCancelRecommend" title="取消推荐">
				<span><span>&lt;&lt; </span></span>
			 </a>
			</div>
		</td>
		<td width="47%" valign="top">
		<div class="eXtremeTable"  >
				<table id="CmsSiteList_table" class="tableRegion" width="100%"  
					border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td width="3%" class="tableHeader" title="全选"
								onmouseover="this.className='tableHeaderSort';this.style.cursor='pointer'"
								onmouseout="this.className='tableHeader';this.style.cursor='default'">
								<input id="checkallRecommendedObj" name="checkallObj" type="checkbox"
								value="" style="margin-left: 11px; width: auto; height: auto;" />
							</td>
							<td width="15%" class="tableHeader" javascript:void(0)><a
								href="javascript:void(0)" id="t_name"> 名称</a></td>
							<td width="17%" class="tableHeader" title="类别"><a href="javascript:void(0)"
								id="t_proCategory">类别</a></td>
							 
						 
							<td class="tableHeader" width="14%">操作</td>
						</tr>
						<tr>
							<form action="recommend2homeManage.do" name="queryCommendedForm" id="queryCommendedForm"
								method="post">
								<td class="statusBar">&nbsp;</td>
								
								<td align="center" class="statusBar"><input type="text"
									value="${param.name2}" name="name2"
									class="x_inputjing" /></td>
									
								<td align="center" class="statusBar">
								<input type="hidden"   name="proCategoryId2" id="proCategoryId2"/>
								<input type="text"
									value="<%=proCategory2%>" name="proCategory2" id="proCategory2" class="x_inputjing" /></td>
								 
								<td align="center" class="statusBar"><a class="sexybutton"
									href="#" onclick="queryCommendedForm.submit();"><span><span>搜索</span>
									</span> </a>
									<a class="sexybutton"
									href="#" onclick="javascript:queryCommendedAll();"><span><span>清空</span>
									</span> </a></td>
							</form>
						</tr>
					</thead>
					<tbody class="tableBody" id="idList">
					<form action="">
						<c:forEach items="${recommendedList}" var="item" varStatus="status">
							<tr class="odd" onmouseover="this.className='highlight'"
								onmouseout="this.className='odd'">
								<td><input name="recommendedId" type="checkbox" value="${item.id }" /> <br /></td>
								<td align="center">${item.name}</td>
								<td align="center">${item.proCategoryName}</td>
								 
								<td align="center">
								<a class="sexybutton"
									href="cancelRecommend.do?id=${item.id}">
									<span><span>取消推荐</span></span>
								 </a>
								
								</td>
							</tr>
						</c:forEach>
					</form>
					<tr style="padding: 0px;">
						<td colspan="7">
						<%
						AbstractPageInfo recommendedPageInfo=(AbstractPageInfo)request.getAttribute("recommendedPageInfo");
						out.println(new CommonPagetoolbar("recommendedForm").getPageToolBarHtml(request,recommendedPageInfo));
						%>
						</td>
					</tr>
					</tbody>
					
				</table>
			</div>
		
		</td>
	</tr>
</table>
</body>
</html>