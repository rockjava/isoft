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
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/widget/datetime_widget/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

 $(document).ready(function() {
	 var msg="${msg}";
	 if(msg){
		 alert(msg);
	 }
	 
	 $( 'textarea#introduction' ).ckeditor();
	 $( 'textarea#culture' ).ckeditor();
	 
	 $("a[action='save']").click(function(){
		 //window.document.body.focus();  
			
		$( "#saveForm" ).submit();
		
	});
	 
	

 });	
 
 
 
</script>
 

<%
String title="修改公司信息";

%>

</head>
<body>
<div class="barnavtop">您所在的位置：修改公司信息</div>

<div id="workspace">
  
  <div id="container" style="height: auto; width:auto;">
   <div style="text-align:right; height:30px; margin-top:10px">
   <span style="float:left; font-size:14px; font-weight:bold"><%=title %></span>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#"  action="save"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
    </div>
    <div class="editspace">
      <form id="saveForm" name="saveForm" action="save.do" class="cmxform" method="post"  enctype="multipart/form-data">
        
        <fieldset>
		<legend><%=title %></legend>
        <ol>
         <li>
            <label>公司名称：</label>
            <span>
            <input name="id" type="hidden" class="inwidth" value="${t.id}"/>
            <input name="cmpName" id="cmp_name" value="${t.cmpName}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
          <li>
            <label>地址：</label>
            <span>
            <input name="address" id="address" value="${t.address}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
          <li>
            <label>联系人：</label>
            <span>
            <input name="linkman" id="linkman" value="${t.linkman}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
          <li>
            <label>联系电话：</label>
            <span>
            <input name="tel" id="tel" value="${t.tel}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
           <li>
            <label>移动电话：</label>
            <span>
            <input name="mobile" id="mobile" value="${t.mobile}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
           <li>
            <label>QQ：</label>
            <span>
            <input name="qq" id="qq" value="${t.qq}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
           <li>
            <label>传真：</label>
            <span>
            <input name="fax" id="fax" value="${t.fax}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
           <li>
            <label>email：</label>
            <span>
            <input name="email" id="email" value="${t.email}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
          
           <li>
            <label>邮编：</label>
            <span>
            <input name="postalCode" id="postalCode" value="${t.postalCode}" type="text" class="inwidth"/>
           </span>    
           <span  style="color:#999999; margin-left:10px">*</span>      
          </li>
           <li>
            <label>主营：</label>
            <span>
            <textarea rows="4" cols="100" id="majorBusiness" name="majorBusiness">${t.majorBusiness}</textarea>
            </span>
           </li>
          <li>
            <label>公司简介：</label>
            <span>
            <textarea rows="5" cols="10" id="introduction" name="introduction">${t.introduction}</textarea>
            </span>
           </li>
           
            <li>
            <label>公司文化：</label>
            <span>
            <textarea rows="5" cols="10" id="culture" name="culture">${t.culture}</textarea>
            </span>
           </li>
        </ol>
        </fieldset>
      </form>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#" action="save"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
   
    </div>
  </div>
</div>
  
  
 
</body>

</html>

