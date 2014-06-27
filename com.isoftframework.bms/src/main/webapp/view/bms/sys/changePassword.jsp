<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/view/common/include/public_css_js_include.jsp"%>
<%@ include file="/view/common/include/demo_page_css_include.jsp"%>
<html>
<head>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ckeditor/adapters/jquery.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.core-3.5.js"></script>

<script type="text/javascript">

 $(document).ready(function() {
	
	 
	 $("a[action='changePassword']").click(function(){
		 //window.document.body.focus();  
			
		$( "#saveForm" ).submit();
		
	});
	 
	

 });	
 
 
 
</script>
 

<%
String title="修改密码";
%>

</head>
<body>
<div class="barnavtop">您所在的位置：<%=title %></div>

<div id="workspace">
  
  <div id="container" style="height: auto; width:auto;">
   <div style="text-align:right; height:30px; margin-top:10px">
   <span style="float:left; font-size:14px; font-weight:bold"><%=title %></span>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#"  action="changePassword"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
            <a class="navbutton" href="${context.backURL}"   ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
    </div>
    <div class="editspace">
      <form id="saveForm" name="saveForm" action="changePassword.do" class="cmxform" method="post">
        
        <fieldset>
		<legend><%=title %></legend>
        <ol>
         <li>
            <label>旧密码：</label>
            <span>
            <input name="oldpassword" id="oldpassword" value="${t.oldpassword}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
          <li>
            <label>新密码：</label>
            <span>
            <input name="password" id="password" value="${t.password}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
         
          
        </ol>
        </fieldset>
      </form>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#" action="changePassword"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
            <a class="navbutton" href="${context.backURL}"  ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
   
    </div>
  </div>
</div>
  
  
 
</body>

</html>

