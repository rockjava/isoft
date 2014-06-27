package com.isoftframework.common.exception;
/**
 * 该异常的处理是以json格式输出的前台
 * @author wzq
 * @time: 2014-3-6 下午1:47:19
 */
public class JsonOutException extends AppException {

	public JsonOutException(String message) {
		super(message);
		this.outType = JSON_OUT;
	}
	
	public JsonOutException(String message, Throwable cause) {
		super(message,cause);
		this.outType = JSON_OUT;
	}
	public JsonOutException(Throwable cause) {
		super(cause);
		this.outType = JSON_OUT;
	}
}