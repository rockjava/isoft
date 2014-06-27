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
	
	 
	 
	

 });	
 
 
 
</script>
 

<%
String title="查看留言";

%>

</head>
<body>
<div class="barnavtop">您所在的位置：
<a href="listMessage.do">留言管理</a> <%=title %></div>

<div id="workspace">
  
  <div id="container" style="height: auto; width:auto;">
   <div style="text-align:right; height:30px; margin-top:10px">
   <span style="float:left; font-size:14px; font-weight:bold"><%=title %></span>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="delete.do?id=${t.id}" ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
            <a class="navbutton" href="${context.backURL}"   ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
    </div>
    <div class="editspace">
      <form id="saveForm" name="saveForm" action="delete" class="cmxform" method="post"  enctype="multipart/form-data">
        
        <fieldset>
		<legend><%=title %></legend>
        <ol>
          <li>
            <label>标题：</label>
            <span>
            <input name="id" type="hidden" class="inwidth" value="${t.id}"/>
           ${t.title}
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          <li>
            <label>留言者：</label>
            <span>
            ${t.messager}
            </span>
          </li>
          
           <li>
            <label>地址：</label>
            <span>
            ${t.address}
            </span>
          </li>
          
           <li>
            <label>电话：</label>
            <span>
            ${t.tel}
            </span>
          </li>
          
           <li>
            <label>email：</label>
            <span>
            ${t.email}
            </span>
          </li>
         
          <li>
            <label >内容：</label>
            <span>
            ${t.content}
            </span>
          </li>
          
          <li>
            <label>留言时间：</label>
            <span>
            ${t.messageTime}
            </span>
          </li>
        </ol>
        </fieldset>
      </form>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="delete.do?id=${t.id}"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />删除</span></span></a>
            <a class="navbutton" href="${context.backURL}"  ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
   
    </div>
  </div>
</div>
  
  
 
</body>

</html>

