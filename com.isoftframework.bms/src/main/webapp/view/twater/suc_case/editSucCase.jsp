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
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/widget/datetime_widget/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

 $(document).ready(function() {
	
	 $( 'textarea#description' ).ckeditor();
	 
	 $("a[action='save']").click(function(){
		 //window.document.body.focus();  
			
		$( "#saveForm" ).submit();
		
	});
	 
	

 });	
 
 
 
</script>
 

<%
String title="新增成功案例";
String action="create.do";
if(request.getAttribute("t")!=null){
	title="修改成功案例";
	action="update.do";
}
%>

</head>
<body>
<div class="barnavtop">您所在的位置：
<a href="listSucCase.do">成功案例管理</a> <%=title %></div>

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
            <label>名称：</label>
            <span>
            <input name="id" type="hidden" class="inwidth" value="${t.id}"/>
            <input name="name" id="name" value="${t.name}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          <li>
            <label>发生时间：</label>
            <span>
            <input name="occurTime" id="occurTime" value="<fmt:formatDate value="${t.occurTime}" pattern="yyyy-MM-dd" />" type="text" class="inwidth" onclick="WdatePicker()" readOnly="true"/>
            <img onclick="WdatePicker({el:'occurTime'})" src="${pageContext.request.contextPath}/resources/js/widget/datetime_widget/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          <li>
            <label>描述：</label>
            <span>
            <textarea rows="5" cols="80" id="description" name="description">${t.description}</textarea>
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

