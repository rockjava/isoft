<%@ page import="com.isoftframework.common.Context" language="java"%>
<%@ page import="com.isoftframework.common.page.toolbar.AbstractPagetoolbar,com.isoftframework.common.page.pageInfo.AbstractPageInfo" language="java"%>
<%

	com.isoftframework.common.Context  _context = (com.isoftframework.common.Context) session.getAttribute("context");
	AbstractPageInfo pageInfo=(AbstractPageInfo)_context.getPageInfo();
	out.println(AbstractPagetoolbar.getPagetoolbar().getPageToolBarHtml(request,pageInfo));
%>	