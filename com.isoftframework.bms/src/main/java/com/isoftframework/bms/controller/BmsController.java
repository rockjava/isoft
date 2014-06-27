package com.isoftframework.bms.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.isoftframework.bms.service.IBmsMenuRscService;
import com.isoftframework.bms.service.IBmsOrgService;
import com.isoftframework.common.util.HttpUtil;
import com.isoftframework.controller.PageController;

public class BmsController extends PageController{

   @Autowired
   IBmsMenuRscService myBmsMenuRscService=null;
   @Autowired
   IBmsOrgService myBmsOrgService=null;
	
  
	/**
	 * 
	 * @param path
	 * @param request
	 * @return
	 */
	public List<IconNode> getIconNodes(String path,HttpServletRequest request){
		//String contexPath = request.getSession().getServletContext().getContextPath();
		String rootPath =  HttpUtil.getWebRootPath(request);
		List<File> children=this.getChildFiles(path, request);
		List<IconNode> iconNodes = new ArrayList<IconNode>();
		if (children != null && children.size() > 0) {
			for (File f : children) {
				String url = ( f.getPath().substring(rootPath.length()) ).replace("\\", "/");
				IconNode node = new IconNode();
				node.setThumb(url);
				node.setUrl(url);
				node.setType(f.getName().substring(f.getName().lastIndexOf(".")+1));
				node.setName(f.getName());
				iconNodes.add(node);
			}

		}
		return iconNodes;
	}

	
	
	 

	class IconNode {
		String name;
		String thumb;
		String url;
		String type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getThumb() {
			return thumb;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	
	
}
