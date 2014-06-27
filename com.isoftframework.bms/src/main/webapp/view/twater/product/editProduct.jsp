<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/jsp/common/include/public_css_js_include.jsp"%>
<%@ include file="/jsp/common/include/demo_page_css_include.jsp"%>
<html>
<head>
<title>会员消费后台-新增用户</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/css/zTreeStyle/zTreeStyle.css" type="text/css">
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ckeditor/adapters/jquery.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/ZTree3.5.15/js/TreePicker.js"></script>

<script type="text/javascript">
var treePicker;
function tempClick(){
		
	/**
	 * 火狐浏览器实现点击图片出现文件上传界面
	 * var a=document.createEvent("MouseEvents"); 
	 * a.initEvent("click", true, true);  
	 * document.getElementById("upload_main_img").dispatchEvent(a); 
	 */
	
	//IE浏览器实现点击图片出现文件上传界面
	document.getElementById('main_img').click();			//调用main_img的onclick事件
}


/**
 * 预览图片
 * @param fileInputObj
 * @returns {Boolean}
 */
function PreviewImg(fileInputObj) {
	
	 if (fileInputObj.files && fileInputObj.files[0]) { 
		 
            var reader = new FileReader(); 

            reader.onload = function (e) { $('#img_prev').attr('src', e.target.result).width(133).height(133); }; 

            reader.readAsDataURL(fileInputObj.files[0]); 

        } else { 
         fileInputObj.select(); 
         //解决IE9下document.selection拒绝访问的错误 
         window.parent.document.body.focus();
         var imgPath = document.selection.createRange().text; 
         //var imgPath = fileInputObj.value;
         var newPreview = document.getElementById("img_2"); 
       	 alert(imgPath);
       	 $('#img_2').width(133).height(133); //必须设置初始大小 
        //IE下，使用滤镜 
         newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";      
         newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgPath;
         $('#img_prev').hide();
	} 
   
}  

/**
 * 得到图片绝对路径
 * @param fileInputObj
 * @returns
 */
function getPath(fileInputObj){ 
    if(fileInputObj) {   
        if(navigator.userAgent.indexOf("MSIE")>0) {  
            fileInputObj.select();  
           // var blurObj=document.getElementById("name"); 
          // fileInputObj.blur(); 
           window.parent.document.body.focus();
            //IE下取得图片的本地路径   
            return document.selection.createRange().text;  
        } else if(isFirefox=navigator.userAgent.indexOf("Firefox")>0) {  
             if (fileInputObj.files) {  // Firefox下取得的是图片的数据   
                //return fileInputObj.files.item(0).getAsDataURL();  
				return window.URL.createObjectURL(fileInputObj.files[0]);				
				//return window.URL.createObjectURL(fileInputObj.files[0]);
             }   
             return fileInputObj.value;   
         }  
         return fileInputObj.value;   
    }   
}  
function showMenu(){
	treePicker.showMenu();
}
 $(document).ready(function() {
	
	 $("a[action='save']").click(function(){
		 //window.document.body.focus();  
			
		$( "#saveForm" ).submit();
	});
	 
	$( 'textarea#description' ).ckeditor();
	
	treePicker=new TreePicker('proCategory','proCategoryId');
	treePicker.init("${pageContext.request.contextPath}/jsp/twater/procategory/${requrl.procategory_listProCategory}");
	 

 });	
 
 
 
</script>
 

<%
String title="新增产品";
String action="create.do";
if(request.getAttribute("p")!=null){
	title="修改产品";
	action="update.do";
}
%>

</head>
<body>
<div class="barnavtop">您所在的位置：
<a href="listProduct.do">产品管理</a> <%=title %></div>

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
            <label>类别：</label>
            <span>
            <input name="id" type="hidden" class="inwidth" value="${p.id}"/>
            <input id="proCategoryId" name="proCategoryId" type="hidden" value="${p.proCategoryId}"/>
            <input readonly id="proCategory"  name="proCategory" type="text" value="${p.proCategoryName}" class="inwidth" onclick="showMenu(); return false;"/>&nbsp;
            <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a></span>      
          </li>
          <li>
            <label>名称：</label>
            <span><input name="name" id="name" value="${p.name}" type="text" class="inwidth"/></span><span  style="color:#999999; margin-left:10px">*</span>        </li>
          <li>
            <label>图片：</label>
            <span  style="display:inline;">
            <!-- style="position: absolute; filter: alpha(opacity = 0); opacity: 0; width: 30px;"
            <INPUT name=filename TYPE="file" onchange=setTimeout(showimg(),500)>
				<IMG name='Myimg' SRC="" BORDER="0">  
				<img id="img_2" style="cursor:pointer;" src="${pageContext.request.contextPath}/resources/images/upload-img.jpg" onclick="tempClick()">-->
				<input type="file" style="position: absolute; filter: alpha(opacity = 0); opacity: 0; width: 30px;" size="1" id="main_img" name="img" onchange="PreviewImg(this)">
				
				<span id="img_2" style="cursor:pointer;" onclick="tempClick()" >
					<img id="img_prev" src='<c:if test="${not empty p.imagePath}">${pageContext.request.contextPath}${p.imagePath}</c:if>
					<c:if test="${empty p.imagePath}">${pageContext.request.contextPath}/resources/images/upload-img.jpg</c:if>' width="133px" style="cursor:pointer;" >
				</span>
			 </span>         
          </li>
          <li>
            <label >描述：</label>
            <span>
            <textarea rows="5" cols="80" id="description" name="description">${p.description}</textarea>
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

