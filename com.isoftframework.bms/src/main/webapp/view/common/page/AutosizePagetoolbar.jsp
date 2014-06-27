<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ page import="com.isoftframework.common.Context" language="java"%>
<%@ page import="com.isoftframework.common.page.toolbar.AbstractPagetoolbar,
com.isoftframework.common.page.pageInfo.AbstractPageInfo,
com.isoftframework.common.util.HttpUtil" language="java"%>
<%

	com.isoftframework.common.Context _context = (com.isoftframework.common.Context ) session.getAttribute("context");
	AbstractPageInfo pageInfo=(AbstractPageInfo)_context.getPageInfo();
	
	String tableIconPath=request.getContextPath()+"/resources/images/demo/page/table";
	 
	int _pageNum = pageInfo.getPageNum();

	int _totalPage = pageInfo.getTotalPage();

	int  pageSize=pageInfo.getLimit();
//System.out.println("pageSize="+pageSize);
//可选的当前页显示条数
	int[] pageSizes=new int[]{2,10,20,30,50,100};
	String _first = "";
	String _last = "";
	String _next = "";
	String _front = "";
	StringBuffer _select=new StringBuffer();

	String urlStr =pageInfo.getCurURL();
	
	String chagePageSizeUrl= HttpUtil.removeParams(urlStr,new String[]{"page","pageSize"});
	chagePageSizeUrl = chagePageSizeUrl +(chagePageSizeUrl.indexOf("?") >-1 ? "&":"?");
	
	
	String goPageUrl=HttpUtil.removeParam(urlStr,"page");
	goPageUrl = goPageUrl +(goPageUrl.indexOf("?") >-1 ? "&":"?");
%>	
<script type="text/javascript">
function checkInputNumber(event,text) {
	var e=event||window.event;
	text.value = text.value.replace(/[^0-9]/g, "");
	if (e.keyCode == 13) {
		goPage(text);
	}
}

function goPage(text) {
	var val = text.value;
	var _totalPage=parseInt("<%=_totalPage%>");
	if (val == "") {
		alert("请输入页号！");

		text.focus();
		return false;
	} else if (val.indexOf(" ") != -1) {
		alert("页号不能有空格！");

		text.focus();
		return false;
	}
	var isNumber = /^\d+$/;
	if (!isNumber.test(val)) {
		alert("页号必须是整数！");
		text.focus();
		return false;
	}
	var num = parseInt(val);
	if (num < 1) {
		alert("页号必须大于零！");

		text.focus();
		return false;
	} else if (num > _totalPage) {
		//alert("页号不能大于 "+_totalPage+"！");
		//text.focus();
		//return false;
		num=_totalPage;
	}
	window.location = "<%=goPageUrl%>page="+ num;
}
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td class="statusBar">找到<%=pageInfo.getTotalSize() %>条记录, 显示 <%=(pageInfo.getStart()+1) %> 到 <%=(pageInfo.getEnd()+1) %> ,当前第&nbsp;<%=(_pageNum) %>&nbsp;页
				共&nbsp;<%=(_totalPage) %>&nbsp;页</td>
			<td class="compactToolbar" align="right"><table border="0"
					cellpadding="1" cellspacing="2">
					<tbody>
						<form name='pageform' id='pageform' action='' method='post'>
							<tr>
								<td align="center">
								<%if (_pageNum > 1 && _totalPage > 1) {%>
								<a href="<%=HttpUtil.replaceParam(urlStr,"page","1") %>"><img src="<%=tableIconPath %>/firstPage.gif" style="border: 0pt none;" alt="首页"></a>
								<%}else {%>
								<img
									src="<%=tableIconPath%>/firstPageDisabled.gif"
									style="border: 0pt none;" alt="首页">
								<%} %>
								</td>
							
								<td align="center">
								<%if ((_pageNum > 1) && (_pageNum <= _totalPage && _totalPage > 1)){ %>
								<a href="<%=HttpUtil.replaceParam(urlStr,"page",_pageNum - 1) %> "><img src="<%=tableIconPath%>/prevPage.gif" style="border: 0pt none;" alt="上一页"></a>
								<%}else{ %>
								<img
									src="<%=tableIconPath%>/prevPageDisabled.gif"
									style="border: 0pt none;" alt="上一页">
								<%} %>
								</td>
								<td align="center">
								<%if (_pageNum < _totalPage && _pageNum >= 0 && _totalPage > 1){ %>
								<a href="<%=HttpUtil.replaceParam(urlStr,"page",_pageNum + 1) %>"><img src="<%=tableIconPath%>/nextPage.gif" style="border: 0pt none;" alt="下一页"></a>
								<%}else{ %>
								<img
									src="<%=tableIconPath%>/nextPageDisabled.gif"
									style="border: 0pt none;" alt="下一页">
								<%} %>
								</td>
								<td align="center">
								<%if (_pageNum < _totalPage && _totalPage > 1){ %>
								<a href="<%=HttpUtil.replaceParam(urlStr,"page",_totalPage)%>"><img src="<%=tableIconPath%>/lastPage.gif" style="border: 0pt none;" alt="末页"></a>
								<%}else{ %>
								<img
									src="<%=tableIconPath%>/lastPageDisabled.gif"
									style="border: 0pt none;" alt="末页">
								<%} %>
								</td>
								<td align="center"><a href="#"><img
										src="<%=tableIconPath%>/separator.gif"
										style="border: 0pt none;" alt=""></a></td>
								<td class="statusBar" align="center">&nbsp;每页显示<select
									name='pageSize'
									onChange='location.href="<%=chagePageSizeUrl%>page=1&pageSize="+this.value; '>
									<%
									for(int i=0;i<pageSizes.length;i++){
										_select.append("<option value=\"").append(pageSizes[i]).append("\" ");
										if(pageSize==pageSizes[i]){
											_select.append(" selected ");
										}
										_select.append(">").append(pageSizes[i]).append("</option>");
									}
									out.println(_select);
									%>
									</select>条记录, &nbsp;跳转到第<input
									type="text" class="input3" size="6" style="width: 30px"
									name='page'
									onkeyup='checkInputNumber(event,this)' />
									页&nbsp;<a href="#"
									onclick='goPage(document.getElementById("pageform").page)'><img
										src="<%=tableIconPath%>/go.gif" align="top"></a></td>
							</tr>
						</form>
					</tbody>
				</table></td>
		</tr>
	</tbody>
</table>