<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/jsp/common/include/public_css_js_include.jsp"%>
<%@ include file="/jsp/common/include/demo_page_css_include.jsp"%>
<html>
<head>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ckeditor/adapters/jquery.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.core-3.5.js"></script>

<script type="text/javascript">

 $(document).ready(function() {
	
	 $( 'textarea#content' ).ckeditor();
	 
	 $("a[action='save']").click(function(){
		 //window.document.body.focus();  
			
		$( "#saveForm" ).submit();
		
	});
	 
	

 });	
 
 
 
</script>
 

<%
String title="新增公司动态";
String action="create.do";
if(request.getAttribute("t")!=null){
	title="修改公司";
	action="update.do";
}
%>

</head>
<body>
<div class="barnavtop">您所在的位置：
<a href="listTrends.do">公司动态管理</a> <%=title %></div>

<div id="workspace">
  
  <div id="container" style="height: auto; width:auto;">
   <div style="text-align:right; height:30px; margin-top:10px">
   <span style="float:left; font-size:14px; font-weight:bold"><%=title %></span>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#"  action="save"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
            <a class="navbutton" href="${context.backURL}"   ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
    </div>
    <div class="editspace">
      <form id="saveForm" name="saveForm" action="<%=action%>" class="cmxform" method="post"  enctype="multipart/form-data">
        
        <fieldset>
		<legend><%=title %></legend>
        <ol>
          <li>
            <label>标题：</label>
            <span>
            <input name="id" type="hidden" class="inwidth" value="${t.id}"/>
            <input name="title" id="title" value="${t.title}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          <li>
            <label>来源：</label>
            <span>
            <input name="source" id="source" value="${t.source}" type="text" class="inwidth"/></span>
          </li>
         
          <li>
            <label >内容：</label>
            <span>
            <textarea rows="5" cols="80" id="content" name="content">${t.content}</textarea>
            </span>
            </li>
          
        </ol>
        </fieldset>
      </form>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#" action="save"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
            <a class="navbutton" href="${context.backURL}"  ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
   
    </div>
  </div>
</div>
  
  
 
</body>

</html>

