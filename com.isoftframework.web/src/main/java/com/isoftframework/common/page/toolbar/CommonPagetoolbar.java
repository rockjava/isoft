package com.isoftframework.common.page.toolbar;

/*
 * 
 * 翻页处理
 *  
 */

import javax.servlet.http.HttpServletRequest;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.common.util.HttpUtil;

public class CommonPagetoolbar extends AbstractPagetoolbar {
	 
	String toolbarName;
	public CommonPagetoolbar(String toolbarName){
		this.toolbarName=toolbarName;
	}
	public CommonPagetoolbar(){
		this("pageform");
	}
	
	public   String getPageToolBarHtml(HttpServletRequest request,AbstractPageInfo pageInfo){
		return getNavigateHtml(request,pageInfo);
	}
	 
	 
	/**
	 * 翻页处理
	 * 
	 * @param request
	 *            请求对象，传递session中的用户登录对象
	 * @param pagesize
	 *            每页的大小
	 * @param uStr
	 *            参数
	 * @return
	 */
	public   String getNavigateHtml(HttpServletRequest request,AbstractPageInfo pageInfo) {

		//String retStr = "";

		int _pageNum = pageInfo.getPageNum();

		int _totalPage = pageInfo.getTotalPage();

		int  pageSize=pageInfo.getLimit();
//System.out.println("pageSize="+pageSize);
		int[] pageSizes=new int[]{2,10,20,50,100};
		String _first = "";
		String _last = "";
		String _next = "";
		String _front = "";

		// if(_pageNum == 0) _pageNum = 1;

		String urlStr =pageInfo.getCurURL();
		log.info("getNavigateHtml urlStr="+urlStr);
		 
		// 首页
		if (_pageNum > 1 && _totalPage > 1)
			_first = "<a href=" + HttpUtil.replaceParam(urlStr,"page","1") + ">首页</a>";
		else
			_first = "首页";
		// 末页
		if (_pageNum < _totalPage && _totalPage > 1)
			_last = "<a href=" + HttpUtil.replaceParam(urlStr,"page",_totalPage) + ">末页</a>";
		else
			_last = "末页";
		// 下一页
		if (_pageNum < _totalPage && _pageNum >= 0 && _totalPage > 1)
			_next = "<a href=" + HttpUtil.replaceParam(urlStr,"page",_pageNum + 1) + ">下一页</a>";
		else
			_next = "下一页";
		// 上一页
		if ((_pageNum > 1) && (_pageNum <= _totalPage && _totalPage > 1))
			_front = "<a href=" + HttpUtil.replaceParam(urlStr,"page",_pageNum - 1) + ">上一页</a>";
		else
			_front = "上一页";

		

		
		String goPageUrl=HttpUtil.removeParam(urlStr,"page");
		goPageUrl = goPageUrl +(goPageUrl.indexOf("?") >-1 ? "&":"?");
		
		StringBuffer goPageScript=new StringBuffer();
		goPageScript.append("<script>function goPage_"+toolbarName+"(){");
		goPageScript.append("var theform = document."+toolbarName+" ;\n\r");
		goPageScript.append("var val = theform.page.value;\n\r");
		goPageScript.append("if ( !val ){alert(\"请输入页号！\"+val);\n\r");
		goPageScript
				.append("theform.page.focus();return false ;}else if ( val.indexOf( \" \" ) != -1 ){alert(\"");
		goPageScript.append(("页号不能有空格！"));
		goPageScript
				.append("\");\n\r theform.page.focus();return false ;} var isNumber = /^\\d+$/;if(!isNumber.test(val)) {alert(\"");
		goPageScript.append(("页号必须是整数！"));
		goPageScript
				.append("\"); theform.page.focus();return false ; }var num = parseInt(val) ; if ( num <1 ){ alert( \"");
		goPageScript.append(("页号必须大于零！"));
		goPageScript
				.append("\");\n\rtheform.page.focus();return false ;}else if ( num > ");
		goPageScript.append(_totalPage);
		goPageScript.append(" ) {alert ( \"");
		goPageScript.append(("页号不能大于" + _totalPage + "！"));
		goPageScript
				.append("\");theform.page.focus();return false;}\nwindow.location=\"");
		goPageScript.append(goPageUrl);
		goPageScript.append("page=\"+");
		goPageScript.append("num;");
		goPageScript.append("}</script>");
		
		StringBuffer bf = new StringBuffer();
		bf.append(goPageScript);
		bf
				.append("<table width=\"98%\"  border=\"0\" align=\"center\" cellpadding=\"2\" cellspacing=\"1\" bgcolor=\"#eeeeee\">");
		bf.append("<form name='"+toolbarName+"'  id='"+toolbarName+"' action='' method='post'>");
		bf.append("<tr>");
		bf.append("<td align='center'>");
		bf.append("第&nbsp;" + (_pageNum) + "&nbsp;页       共&nbsp;"
				+ (_totalPage) + "&nbsp;页       ");
		bf.append(_first);
		bf.append("      ");
		bf.append(_front);
		bf.append("      ");
		bf.append(_next);
		bf.append("      ");
		bf.append(_last);
		String chagePageSizeUrl= HttpUtil.removeParams(urlStr,new String[]{"page","pageSize"});
		chagePageSizeUrl = chagePageSizeUrl +(chagePageSizeUrl.indexOf("?") >-1 ? "&":"?");
		
		bf.append("&nbsp;每页<select name='pageSize' onChange='location.href=\""+chagePageSizeUrl+ "page=1&pageSize=\"+this.value; '>");
		for(int i=0;i<pageSizes.length;i++){
			bf.append("<option value=\"").append(pageSizes[i]).append("\" ");
			if(pageSize==pageSizes[i]){
				bf.append(" selected ");
			}
			bf.append(">").append(pageSizes[i]).append("</option>");
		}
		bf.append("</select>条");
		
		
		bf
				.append("    &nbsp;跳转到<input  type=\"text\" class=\"input3\" size=\"6\" style=\"width:30px\"  name=\"page\" onkeyup='this.value=this.value.replace(/[^0-9]/g,\"\");if(event.keyCode==13){goPage_"+toolbarName+"();} ' > 页&nbsp;<input type='button'  class='button' value='Go' style=\"cursor:hand;width:30px\" onclick='goPage_"+toolbarName+"();'>");
		bf.append("&nbsp;");
		bf.append("</td>");
		bf.append("</tr>");
		bf.append("</form>");
		bf.append("</table>");
		return  bf.toString();
	}
	
	
	
	 
}