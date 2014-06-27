package com.isoftframework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
//@ControllerAdvice("com.frm.controller")
public class MyExceptionHandler{

	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // prepare responseEntity
		System.out.println("----handle exception----");
		ResponseEntity responseEntity=new ResponseEntity("{status:'0',statusText:'"+e.getMessage()+"'}",HttpStatus.CREATED);
        return responseEntity;
    }
}
