/**
 * 
 */
package com.isoftframework.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.isoftframework.common.Context;
import com.isoftframework.common.exception.AppException;
import com.isoftframework.common.exception.ExceptionUtil;
import com.isoftframework.common.io.file.ExtensionFileFilter;
import com.isoftframework.common.util.HttpUtil;
import com.isoftframework.service.IService;
/**
 * <ul>
 * <li> <b>目的:</b> <br />
 * <p>
 * 所有Action的基类
 * </p>
 * </li>
 * <li><b>采用的不变量：</b></li>
 * <li><b>并行策略：</b></li>
 * <li> <b>修改历史：</b><br />
 * </li>
 * <li><b>已知问题：</b></li>
 * </ul>
 */
public abstract class BaseController 
{
	public static  String JSON_DEL_SUC="{\"success\":true,\"msg\":\"删除成功！\"}";
	public static  String JSON_SAVE_SUC="{\"success\":true,\"msg\":\"保存成功！\"}";
	
	public final Logger logger =Logger.getLogger(this.getClass());
	
	public Logger getLogger(){
		return Logger.getLogger(this.getClass());
	}
	
	public IService myBaseService=null;
	
	//org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler  responseEntityExceptionHandler ;
	/**
	 * 异常处理
	 */
	@ExceptionHandler(Exception.class)
	protected String handExcpetion( Exception e, HttpServletRequest request,HttpServletResponse response) 
	{
		try{
			logger.error("",  e);
			//System.out.println("accept="+accept);
			if(e instanceof AppException && ((AppException) e).getOutType()==AppException.JSON_OUT){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("success",false);
				map.put("msg", ExceptionUtil.getExceptionStackTrace(e));
				HttpUtil.writeObject(response, map);
				return null;
			} 
			request.setAttribute("errorMsg",ExceptionUtil.getExceptionStackTrace(e));
			return "error";
		
			
		} catch (Exception e1)
		{
			logger.error("handExcpetion error:", e1);
			  
		}
		return null;
	}
	
	 
	/**
	 * 根据数组，返回已选择checkbox的Id的字符串
	 * 
	 * @param String[]
	 * @return String 以逗号间隔
	 */
	public String getSelectedIdString(HttpServletRequest request,
			String paraName) {
		String result = "";
		String[] idList = request.getParameterValues(paraName);
		if (idList != null) {
			for (int i = 0; i < idList.length; i++) {
				if (!idList[i].equals("-1")) { // 舍掉模拟数据
					result = result + "'" + idList[i] + "',";
				}
			}
		}
		if (result.endsWith(","))
			result = result.substring(0, result.length() - 1);
		return result;
	}
	
	public List<File> getChildFiles(String path,HttpServletRequest request){
		String rootPath = HttpUtil.getWebRootPath(request);
		this.logger.debug("filePath="+rootPath);
		File file = new File(rootPath + path);
		
		ExtensionFileFilter fileFilter=new ExtensionFileFilter(ExtensionFileFilter.img_regex);
		List<File> children = getChildFiles(file, new ArrayList(),fileFilter);
		return children;
	}
	
	public List<File> getChildFiles(File parent, List<File> childFiles,FileFilter fileFilter) {

		if (parent.isDirectory()) {
			File[] children = parent.listFiles(fileFilter);
			if (children != null && children.length > 0) {
				for (File child : children) {
					getChildFiles(child, childFiles,fileFilter);
				}

			}
		} else {
			childFiles.add(parent);
		}

		return childFiles;
	}
	
	public Context getContext(HttpServletRequest request){
		Context context = (Context) request.getSession()
				.getAttribute("context");
		if(context==null){
			context=  new Context();
			request.getSession().setAttribute("context",context );
			
		}
		return context;
	}
	/**
	 * 测试用
	 * @param reader
	 */
	public void printReader(BufferedReader reader){
		
		try {
			String line=null;
			while((line=reader.readLine())!=null){
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}
	
	@Resource(name="myBaseService")
	public void setMyBaseService(IService myBaseService) {
		this.myBaseService = myBaseService;
	}
}
