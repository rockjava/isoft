<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="../../common/include/public_css_js_include.jsp"%>
<%@ include file="../../common/include/demo_page_css_include.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>会员消费后台-客服登录密码重置</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.10.2.js"></script>
<script>
function submitForm(fid){
	ValidationFramework.init("${pageContext.request.contextPath}/resources/js/JSValidation/validation-config.xml");
	if(doValidate(fid)) 
		document.getElementById(fid).submit();
}
</script>

</head>
<body>
<div class="barnavtop">您所在的位置：<a href="manage.html">用户管理</a> 会员信息修改</div>

<div id="workspace">
  
  <div id="container" style="height: auto; width:auto;">
   <div style="text-align:right; height:30px; margin-top:10px">
   <span style="float:left; font-size:14px; font-weight:bold">会员信息修改</span>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#" onclick="submitForm('saveForm');"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
            <a class="navbutton" href="${backurl}"   ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
    </div>
    <div class="editspace">
      <form id="saveForm" name="saveForm" action="${requrl.page_editClubMember}"  class="cmxform" method="post">
        <input type="hidden" value="${member.id }" name="id" />
        
        <fieldset>
		<legend>会员信息修改</legend>
        <ol>
          <li>
            <label>工号：</label>
            <span><input type="text" id="scode" name="scode" class="inwidth" value="${member.scode }"/></span><span  style="color:#999999; margin-left:10px">*只能输入数字和字母</span></li>
          <li>
            <label>登录密码：</label>
            <span><input type="password" id="spass" name="spass" class="inwidth" value="${member.spass }"/></span><span  style="color:#999999; margin-left:10px">*只能输入数字、字母和下划线</span></li>
          <li>
            <label>确认登录密码：</label>
            <span><input type="password" id="confirm" name="confirm" class="inwidth"  value="${member.spass }"/></span><span  style="color:#999999; margin-left:10px">*只能输入数字、字母和下划线</span></li>
          <li>
            <label>姓名：</label>
            <span><input type="text" id="sname" name="sname" class="inwidth" value="${member.sname }"/></span> </li>
          <li>
            <label>所属绿色通道：</label>
            <span>
            <select name="clubChannelDTOId" id="clubChannelDTOId" style="width:206px">
            	<option value="" >请选择所属绿色通道</option>
            	<c:forEach items="${channelList}" var="item" varStatus="status">
            		<option value="${item.id}" 
            		<c:if test="${member.clubChannelDTO.id==item.id}">selected=selected</c:if>
            		>${item.sname}</option>
            	</c:forEach>
          </select>
            </span> </li>
        </ol>
        </fieldset>
      </form>
    </div>
    <div class="toolbar">
            <a class="navbutton" href="#" onclick="submitForm('saveForm');"><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/back.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />提交</span></span></a>
            <a class="navbutton" href="${context.backURL}"   ><span><span><img src="${pageContext.request.contextPath}/resources/images/demo/page/jiantou.gif" style="margin:4px 0px 0px -20px;*margin:0px 0px 0px -20px;position:absolute;" />返回</span></span></a>
    </div>
  </div>
</div>
</body>
</html>
