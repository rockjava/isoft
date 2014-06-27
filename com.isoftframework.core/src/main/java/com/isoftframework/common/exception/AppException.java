/**
 * 
 */
package com.isoftframework.common.exception;

/**
 * <ul>
 * <li><b>目的:</b> <br />
 * <p>
 * 异常类： 异常可分为程序异常与业务逻辑异常，程序异常如NullPointException, IOException等，<br />
 * 业务逻辑也常如可否删除已经发布文章的用户，业务逻辑异常也可以理解为消息提示，本类设计的思路如下：<br />
 * 1、方便对异常进行统一处理<br />
 * 2、对方便各层之间的消息传递<br />
 * </p>
 * <li><b>已知问题：</b></li>
 * </ul>
 */
public class AppException extends Exception {
	
	public static final int JSON_OUT=0;
	public static final int JSP_OUT=1;
	/**
	 * 输出方式
	 */
	int outType=JSP_OUT;
	
	public AppException(String message){
		super(message);
	}
	
	public AppException(String message, Throwable cause) {
		super(message,cause);
	}
	public AppException(Throwable cause) {
		super(cause);
	}
	public AppException(int outType,Exception e) {
		super(e);
		this.outType=outType;
	}
	public AppException(Exception e) {
		super(e);
		this.outType=JSP_OUT;
	}
	public int getOutType() {
		return outType;
	}
	public void setOutType(int outType) {
		this.outType = outType;
	}
 
	

}
