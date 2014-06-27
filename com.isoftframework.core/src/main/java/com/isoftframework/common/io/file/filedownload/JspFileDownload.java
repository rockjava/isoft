package com.isoftframework.common.io.file.filedownload;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;

public class JspFileDownload {
	private HttpServlet serlvet;
	private HttpServletResponse response;
	private HttpServletRequest request;

	private java.io.InputStream in = null;
 
	private String filedisplay = " ";

	public JspFileDownload(HttpServlet serlvet, HttpServletRequest request,HttpServletResponse response) {
		this.serlvet = serlvet;
		this.request = request;
		this.response = response;
	}

	public void process() {
		javax.servlet.jsp.PageContext pageContext = JspFactory.getDefaultFactory() 
				.getPageContext(serlvet, request, response, null, true, 8192, true);
		JspWriter out = pageContext.getOut();
		// application.getRealPath("/main/mvplayer/CapSetup.msi");
		response.reset();// 可以加也可以不加
		response.setContentType("application/x-download");

		try {
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filedisplay);

		java.io.OutputStream outp = null;
		try {
			outp = response.getOutputStream();
			//in = new FileInputStream(filedownload);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			//  
			outp.flush();
			// 要加以下两句话，否则会报错
			// java.lang.IllegalStateException: getOutputStream() has already been
			// called for //this response
			out.clear();
			out = pageContext.pushBody();
		} catch (Exception e) {
			System.out.println("取消下载!");
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in = null;
			}
			// 这里不能关闭
			// if(outp != null)
			// {
			// outp.close();
			// outp = null;
			// }
		}
	}
	
	public HttpServlet getSerlvet() {
		return serlvet;
	}

	public void setSerlvet(HttpServlet serlvet) {
		this.serlvet = serlvet;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public java.io.InputStream getIn() {
		return in;
	}

	public void setIn(java.io.InputStream in) {
		this.in = in;
	}

	public String getFiledisplay() {
		return filedisplay;
	}

	public void setFiledisplay(String filedisplay) {
		this.filedisplay = filedisplay;
	}

}
