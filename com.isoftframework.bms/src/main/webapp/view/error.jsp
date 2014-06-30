<%@ page contentType="text/html;charset=UTF-8"%>
<%
	//String url=(String)request.getAttribute("backURL");
	String msg=(String)request.getAttribute("errorMsg");
%>
<center>系统故障</center><br/>
<center><%=msg%></center>