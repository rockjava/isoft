package com.isoftframework.bms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.isoftframework.controller.BaseController;

@Controller
public class LoginOrOutAction extends BaseController{

	 
	/**
	 * 登陆
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String home(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		return ("home");
	}
	public String login(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		return ("home");
	}
	public String logout(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		return ("login");
	}
}
