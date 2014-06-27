package com.isoftframework.common.page.toolbar;

/*
 * 创建日期 2005-1-24
 * 
 * 翻页处理
 *  
 */

import javax.servlet.http.HttpServletRequest;

import com.isoftframework.common.page.pageInfo.AbstractPageInfo;
import com.isoftframework.common.util.HttpUtil;
 

public class AutoSizePagetoolbar extends AbstractPagetoolbar {
	 
	public static void main(String[] args){
		AutoSizePagetoolbar t=new AutoSizePagetoolbar();
		
		//String r=t.replaceParams("localhost:8080/long.do?name=1&page=1&age=20", "page", null);
		String url="/framework/jsp/twater/product/productManage.do?page=1&pageSize=2";
		String r=HttpUtil.removeParam(url, "page");
		System.out.println(r);
	}
	
	public String getPageToolBarHtml(HttpServletRequest request,AbstractPageInfo pageInfo){
		return getAutoSizeNavigateHtml(request,pageInfo);
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
	public   String getAutoSizeNavigateHtml (HttpServletRequest request,AbstractPageInfo pageInfo) {

		String tableIconPath=request.getContextPath()+"/resources/images/demo/page/table";

				
		 
		int _pageNum = pageInfo.getPageNum();

		int _totalPage = pageInfo.getTotalPage();

		int  pageSize=pageInfo.getLimit();
//System.out.println("pageSize="+pageSize);
//可选的当前页显示条数
		int[] pageSizes=new int[]{2,10,20,30,50,100};
		String _first = "";
		String _last = "";
		String _next = "";
		String _front = "";
		StringBuffer _select=new StringBuffer();

		String urlStr =pageInfo.getCurURL();
		//urlStr = urlStr +(urlStr.indexOf("?") >-1 ? "&":"?");
		log.info("urlStr="+urlStr);
		 
		// 首页
		if (_pageNum > 1 && _totalPage > 1)
			_first="<a href="+HttpUtil.replaceParam(urlStr,"page","1")+"><img src=\""+tableIconPath+"/firstPage.gif\" style=\"border: 0pt none;\" alt=\"首页\"></a>";
			//_first = "<a href=" + urlStr + "page=1>首页</a>";
		else
			_first = "<img src=\""+tableIconPath+"/firstPageDisabled.gif\" style=\"border: 0pt none;\" alt=\"首页\">";
		// 末页
		if (_pageNum < _totalPage && _totalPage > 1)
			_last="<a href="+HttpUtil.replaceParam(urlStr,"page",_totalPage+"")+"><img src=\""+tableIconPath+"/lastPage.gif\" style=\"border: 0pt none;\" alt=\"末页\"></a>";
			//_last = "<a href=" + urlStr + "page=" + (_totalPage) + ">末页</a>";
		else
			_last = "<img src=\""+tableIconPath+"/lastPageDisabled.gif\" style=\"border: 0pt none;\" alt=\"末页\">";
		// 下一页
		if (_pageNum < _totalPage && _pageNum >= 0 && _totalPage > 1)
			_next="<a href="+ HttpUtil.replaceParam(urlStr,"page",_pageNum + 1) +"><img src=\""+tableIconPath+"/nextPage.gif\" style=\"border: 0pt none;\" alt=\"下一页\"></a>";
			//_next = "<a href=" + urlStr + "page=" + (_pageNum + 1) + ">下一页</a>";
		else
			_next = "<img src=\""+tableIconPath+"/nextPageDisabled.gif\" style=\"border: 0pt none;\" alt=\"下一页\">";
		// 上一页
		if ((_pageNum > 1) && (_pageNum <= _totalPage && _totalPage > 1))
			_front="<a href="+ HttpUtil.replaceParam(urlStr,"page",_pageNum - 1) + "><img src=\""+tableIconPath+"/prevPage.gif\" style=\"border: 0pt none;\" alt=\"上一页\"></a>";
			//_front = "<a href=" + urlStr + "page=" + (_pageNum - 1)+ ">上一页</a>";
		else
			_front = "<img src=\""+tableIconPath+"/prevPageDisabled.gif\" style=\"border: 0pt none;\" alt=\"上一页\">";
	//--	
		String chagePageSizeUrl= HttpUtil.removeParams(urlStr,new String[]{"page","pageSize"});
		chagePageSizeUrl = chagePageSizeUrl +(chagePageSizeUrl.indexOf("?") >-1 ? "&":"?");
		
		_select.append("&nbsp;每页显示<select name='pageSize' onChange='location.href=\""+chagePageSizeUrl+ "page=1&pageSize=\"+this.value; '>");
		for(int i=0;i<pageSizes.length;i++){
			_select.append("<option value=\"").append(pageSizes[i]).append("\" ");
			if(pageSize==pageSizes[i]){
				_select.append(" selected ");
			}
			_select.append(">").append(pageSizes[i]).append("</option>");
		}
		_select.append("</select>条记录,");
		
	//--
		String goPageUrl=HttpUtil.removeParam(urlStr,"page");
		goPageUrl = goPageUrl +(goPageUrl.indexOf("?") >-1 ? "&":"?");
		log.info("goPageUrl=="+goPageUrl);
		StringBuffer goScript=new StringBuffer();
		goScript.append("var theform = document.getElementById(\"pageform\" );")
		  .append("var val = theform.page.value; if ( val == \"\" ){alert(\"")
		  .append(("请输入页号！"))
		  .append("\");\n\r theform.page.focus();return false ;}else if ( val.indexOf( \" \" ) != -1 ){alert(\"")
		  .append(("页号不能有空格！"))
		 .append("\");\n\r theform.page.focus();return false ;} var isNumber = /^\\d+$/;if(!isNumber.test(val)) {alert(\"")
		 .append(("页号必须是整数！"))
		 .append("\"); theform.page.focus();return false ; }var num = parseInt(val) ; if ( num <1 ){ alert( \"")
		 .append(("页号必须大于零！"))
		 .append("\");\n\rtheform.page.focus();return false ;}else if ( num > ")
		 .append(_totalPage)
		 .append(" ) {alert ( \"")
		 .append(("页号不能大于" + _totalPage + "！"))
		 .append("\");theform.page.focus();return false;}\nwindow.location=\"")
		 .append(goPageUrl)
		 .append("page=\"+")
		 .append("num; ");
		
		StringBuffer bf = new StringBuffer();
		 bf.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">")
        .append("<tbody>")
        .append("<tr>")
        .append(" <td class=\"statusBar\">找到"+pageInfo.getTotalSize()+"条记录, 显示 " +(pageInfo.getStart()+1)+" 到 "+(pageInfo.getEnd()+1)+" ,当前第&nbsp;" + (_pageNum) + "&nbsp;页       共&nbsp;"+ (_totalPage) + "&nbsp;页       "+" </td>")
         
        .append(" <td class=\"compactToolbar\" align=\"right\"><table border=\"0\" cellpadding=\"1\" cellspacing=\"2\">")
        .append("<tbody>")
		.append("<form name='pageform' id='pageform' action='' method='post'>")
		.append("<tr>")
		.append("<td  align=\"center\">")
		.append(_first)
		.append("</td>")
		.append("<td  align=\"center\">")
		 .append(_front)
		.append("</td>")
		.append("<td  align=\"center\">")
		.append(_next)
		.append("</td>")
		.append("<td  align=\"center\">")
		.append(_last)
		.append("</td>")
		.append("<td  align=\"center\" ><a href=\"#\"><img src=\""+tableIconPath+"/separator.gif\" style=\"border: 0pt none;\" alt=\"\"></a></td>")
		.append("<td class=\"statusBar\" align=\"center\">")
		.append(_select.toString());
		
		bf.append("    &nbsp;跳转到第<input  type=\"text\" class=\"input3\" size=\"6\" style=\"width:30px\" name='page' onkeyup='this.value=this.value.replace(/[^0-9]/g,\"\");if(event.keyCode==13){"+goScript.toString()+"} '   /> 页&nbsp;" );
		
		bf.append("<a href=\"#\"  onclick='"+goScript.toString()+"'><img src=\""+tableIconPath+"/go.gif\" align=\"top\"  ></a>");
	    
		bf.append("");
		bf.append("</td>");
		bf.append("</tr>");
		bf.append("</form>")
		.append(" </tbody></table></td></tr></tbody> </table>");
		 
		return bf.toString();
	}
	
	
	
	 
	
	
	
	 
}