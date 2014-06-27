package com.isoftframework.web.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.isoftframework.common.io.file.FileOperateUtil;
import com.isoftframework.common.util.HttpUtil;

public class JsCssFilter implements Filter {
	String url = "resources/js/??kissy/k/1.4.1/seed-min.js,mui/seed/1.3.1/seed.js,mui/seed-g/1.0.2/seed.js,mui/globalmodule/1.3.9/global-module.js,mui/global/1.2.12/global.js";
	Logger log = Logger.getLogger(this.getClass());

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		
		String queryStr = HttpUtil.getQueryString(req);
		if (queryStr.startsWith("?")) {
			String servletPath= req.getServletPath();
			if(servletPath.startsWith("/")){
				servletPath=servletPath.substring(1);
			}
			File tempFile = File.createTempFile("temp", System.currentTimeMillis()+".temp");
			queryStr = queryStr.substring(1);
			String webroot = HttpUtil.getWebRootPath(req);
			if (queryStr.contains(",")) {

				String[] fpathList = queryStr.split(",");
				String[] files = new String[fpathList.length];
				for (int i = 0; i < fpathList.length; i++) {
					String fpath=getFilePath(fpathList[i]);
					files[i] = (webroot+servletPath + fpath).replace("/", File.separator);
				}
				FileOperateUtil.mergeTxtFiles(tempFile, files);

			} else {
				
				tempFile = new File((webroot+servletPath + getFilePath(queryStr)).replace("/", File.separator));
			}

			log.info("file="+tempFile);
			this.outputFile(req,response, tempFile);
			return;
		}else{
			chain.doFilter(request, response);
		}
	

	}

	private String getFilePath(String queryParam){
		String fpath=queryParam;
		if(fpath.contains("?")){
			fpath=fpath.substring(0,fpath.indexOf("?"));
		}
		return fpath;
	}
	 
	private void outputFile(HttpServletRequest request,
			ServletResponse response, File file) {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);

			String accept = request.getHeader("Accept");
			if (accept.contains("text/css")) {
				response.setContentType("text/css;charset="
						+ EncodeFilter.getEncoding());
			} else {
				response.setContentType("text/javascript;charset="
						+ EncodeFilter.getEncoding());
			}
			ServletOutputStream output = response.getOutputStream();
			
			byte[] buf = new byte[1024];
			int readedLen = -1;
			while ((readedLen = fin.read(buf)) != -1) {
				output.write(buf, 0, readedLen);
				output.flush();
			}
			output.flush();

			// output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (fin != null) {
					fin.close();
				}
			} catch (IOException ignore) {

			}
		}

	}
	
	

}
