package com.isoftframework.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class GZipFilter implements Filter {

	  Logger logger=Logger.getLogger(getClass());
    public void init(FilterConfig filterConfig) throws ServletException {}
    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	 
        HttpServletResponse resp = (HttpServletResponse)response;
        ByteArrayResponseWrapper wrapper = new ByteArrayResponseWrapper(resp);
       // logger.info("gzip---before filter");
        chain.doFilter(request, wrapper);
      //  logger.info("gzip---after filter");
        byte[] gzipData = gzip(wrapper.getResponseData());
        resp.addHeader("Content-Encoding", "gzip");
        resp.setContentLength(gzipData.length);
        ServletOutputStream output = response.getOutputStream();
        output.write(gzipData);
        output.flush();
    }

    private byte[] gzip(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
        GZIPOutputStream output = null;
        try {
            output = new GZIPOutputStream(byteOutput);
            output.write(data);
        }
        catch (IOException e) {}
        finally {
            if(output!=null) {
                try {
                    output.close();
                }
                catch (IOException e) {}
            }
        }
        return byteOutput.toByteArray();
    }
}
