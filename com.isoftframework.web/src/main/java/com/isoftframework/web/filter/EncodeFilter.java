/**
 * 
 */
package com.isoftframework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
/**
  
 */
public class EncodeFilter implements Filter
{
	public static String encoding = "UTF-8";

	protected FilterConfig filterConfig = null;


	public void init(FilterConfig filterConfig) throws ServletException
	{

		this.filterConfig = filterConfig;
		encoding = filterConfig.getInitParameter("encoding");
		

	}
	

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{   HttpServletRequest httpreq = (HttpServletRequest) request;
		 
	    
		if ((request.getCharacterEncoding() == null))
		{
           
            if(encoding != null){
            	request.setCharacterEncoding(encoding);
            }
            
		}

		// Pass control on to the next filter
		chain.doFilter(request, response);
	}

	public void destroy()
	{
		encoding = null;
		this.filterConfig = null;
	}

	public static String getEncoding()
	{
		return  encoding;
	}
	 

}
