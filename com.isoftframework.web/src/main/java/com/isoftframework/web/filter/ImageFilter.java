package com.isoftframework.web.filter;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.isoftframework.common.io.image.ImageHandler;

public class ImageFilter implements Filter {
	
	  Logger logger=Logger.getLogger(getClass());

    private static final List<String> contentTypes = new ArrayList<String>();
    private static final Map<String, String> imageTypes = new HashMap<String, String>();

   
    @Override
    public void init(FilterConfig config) throws ServletException {

        

        contentTypes.add("image/bmp");
        imageTypes.put("image/bmp", "bmp");
        contentTypes.add("image/gif");
        imageTypes.put("image/gif", "gif");
        contentTypes.add("image/jpeg");
        imageTypes.put("image/jpeg", "jpeg");
        contentTypes.add("image/png");
        imageTypes.put("image/png", "png");
        contentTypes.add("image/vnd.wap.wbmp");
        imageTypes.put("image/vnd.wap.wbmp", "bmp");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (isImageRequest(req) ) {
        	 HttpServletResponse resp = (HttpServletResponse)response;
           ByteArrayResponseWrapper wrapper = new ByteArrayResponseWrapper(resp);
           logger.info("image---before filter");
           chain.doFilter(request, wrapper);
           logger.info("image---after filter");
           byte[] datas = wrapper.getResponseData();
           System.out.println("datas.length=="+datas.length);
           ImageHandler imageHandler=  ImageHandler.getInstance();
           BufferedImage image= imageHandler.copy(new ByteArrayInputStream(datas), 16);
           if(image!=null){
          	 imageHandler.writeImage(image,  getImageType(req), resp.getOutputStream());
           }else{
          	 resp.getOutputStream().write(datas);
           }
           
           return;
        }else{
        	 chain.doFilter(request, response);
        }
       
    }

    private String getImageType(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        System.out.println("accept="+accept);
        for (String c : contentTypes) {
            if (accept.contains(c)) {
                return imageTypes.get(c);
            }
        }
        return "jpeg";
    }

    private boolean isImageRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        for (String c : contentTypes) {
            if (accept.contains(c)) {
              
                logger.info( request.getRequestURI()+" is image request,Accept:"+accept);
                
                return true;
            }
        }
        return false;
    }

    

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("MM-dd").format(new Date()));
    }
}
