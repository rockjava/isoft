package com.isoftframework.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.isoftframework.common.util.DateUtil;
import com.isoftframework.common.util.HttpUtil;
import com.isoftframework.web.filter.EncodeFilter;

public final class HttpUtil {

	public static final Gson gson = new Gson();

	static String encoding =  EncodeFilter.getEncoding();

	public static String getURL(HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString != null)
			return sb.toString() + "?" + queryString;
		return sb.toString();
	}

	public static String getWrapURL(HttpServletRequest request) {
		String url = getRequestURL(request);
		String paramStr = getQueryString(request);
		if (!StringUtils.isEmpty(paramStr)) {
			url = url + "?" + paramStr;
		}
		return url;

	}

	/**
	 * or you can use request.getRequestURL()
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		// or you can use request.getRequestURL()
		return request.getContextPath() + request.getServletPath();
	}

	/**
	 * 返回查询的字符串 格式：
	 * 
	 * @例如 param1=value1&param2=value2
	 * @param request
	 * @return 查询的字符串
	 */
	public static String getQueryString(HttpServletRequest request) {
		if (request.getQueryString() != null) {
			return request.getQueryString();
		} else {
			return getQueryStringFromPost(request);
		}

	}

	/**
	 * 返回查询的字符串 格式：
	 * 
	 * @例如 param1=value1&param2=value2 this method can not by replace with
	 *     request.getQueryString() because the method request.getQueryString()
	 *     can not get the queryString from post request
	 * @param request
	 * @return 查询的字符串
	 */
	public static String getQueryStringFromPost(HttpServletRequest request) {

		String queryString = "";
		Enumeration names = request.getParameterNames();
		if (!names.hasMoreElements()) {
			return "";
		}
		int i = 0;
		while (names.hasMoreElements()) {
			String name = "";
			String value = "";

			name = ((String) names.nextElement()).trim();
			value = (String) request.getParameter(name);

			if (name.equals("pNum") || name.equals("pageNO")
					|| name.equals("pageSize") || name.equals("page")
					|| name.equals("start") || name.equals("limit")) {
				continue;
			}

			if (queryString == null || queryString.equals("")) {
				queryString = name + "=" + value;
			} else {
				queryString = queryString + "&" + name + "=" + value;
			}

			i++;
		}
		return queryString;
	}

	/**
	 * remove the url param
	 * 
	 * @param url
	 * @param key
	 * @return
	 */
	public static String removeParam(String url, String key) {
		return replaceParam(url, key, null);
	}

	/**
	 * remove the url param
	 * 
	 * @param url
	 * @param keys
	 * @return
	 */
	public static String removeParams(String url, String[] keys) {
		return replaceParams(url, keys, new String[keys.length]);
	}

	/**
	 * replace the url param value whith the new param value, if the param not
	 * exit,it will be add to the end of url
	 * 
	 * @param url
	 * @param keys
	 * @param values
	 * @return
	 */
	public static String replaceParams(String url, String[] keys,
			Object[] values) {
		String newUrl = url;
		for (int i = 0; i < keys.length; i++) {
			newUrl = replaceParam(newUrl, keys[i], values[i]);
		}
		return newUrl;
	}

	public static String replaceParam(String url, String key, Object value) {
		StringBuffer buf = new StringBuffer(url);
		int keyIdx = buf.indexOf(key + "=");
		if (keyIdx != -1) {
			int paramEndIdx = buf.indexOf("&", keyIdx);
			if (paramEndIdx == -1) {
				paramEndIdx = buf.length();
			}
			if (value != null) {
				buf.replace(keyIdx, paramEndIdx, key + "=" + value);
			} else {
				//buf.replace(keyIdx,(paramEndIdx + 1 > buf.length()) ? paramEndIdx: paramEndIdx + 1, "");
				buf.replace(keyIdx-1,paramEndIdx , "");
			}

		} else {
			if (value != null)
				buf.append((buf.indexOf("?") > -1 ? "&" : "?")).append(
						key + "=" + value);
		}

		return buf.toString();
	}

	/**
	 * Get Integer parameter from request. If specified parameter name is not
	 * found, the default value will be returned.
	 */
	public static int getInt(HttpServletRequest request, String paramName,
			int defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		return Integer.parseInt(s);
	}

	/**
	 * Get Integer parameter from request. If specified parameter name is not
	 * found, an Exception will be thrown.
	 */
	public static int getInt(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Integer.parseInt(s);
	}

	/**
	 * Get String parameter from request. If specified parameter name is not
	 * found, the default value will be returned.
	 */
	public static String getString(HttpServletRequest request,
			String paramName, String defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		return s;
	}

	/**
	 * Get String parameter from request. If specified parameter name is not
	 * found or empty, an Exception will be thrown.
	 */
	public static String getString(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);

		return s;
	}

	/**
	 * Get Boolean parameter from request. If specified parameter name is not
	 * found, an Exception will be thrown.
	 */
	public static boolean getBoolean(HttpServletRequest request,
			String paramName) {
		String s = request.getParameter(paramName);
		return Boolean.parseBoolean(s);
	}

	/**
	 * Get Boolean parameter from request. If specified parameter name is not
	 * found, the default value will be returned.
	 */
	public static boolean getBoolean(HttpServletRequest request,
			String paramName, boolean defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		return Boolean.parseBoolean(s);
	}

	/**
	 * Get float parameter from request. If specified parameter name is not
	 * found, an Exception will be thrown.
	 */
	public static float getFloat(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Float.parseFloat(s);
	}

	/**
	 * Create a FormBean and bind data to it. Example: If found a parameter
	 * named "age", the object's setAge() method will be invoked if this method
	 * exists. If a setXxx() method exists but no corrsponding parameter, this
	 * setXxx() method will never be invoked.<br/>
	 * <b>NOTE:</b> only public setXxx() method can be invoked successfully.
	 */
	public static Object createFormBean(HttpServletRequest request, Class c) {
		Object bean;
		try {
			bean = c.newInstance();
		} catch (Exception e) {
			return new Object();
		}
		Method[] ms = c.getMethods();
		for (int i = 0; i < ms.length; i++) {
			String name = ms[i].getName();
			if (name.startsWith("set")) {
				Class[] cc = ms[i].getParameterTypes();
				if (cc.length == 1) {
					String type = cc[0].getName(); // parameter type
					try {
						// get property name:
						String prop = Character.toLowerCase(name.charAt(3))
								+ name.substring(4);
						// get parameter value:
						String param = getString(request, prop);
						if (param != null && !param.equals("")) {
							if (type.equals("java.lang.String")) {
								ms[i].invoke(bean,
										new Object[] { htmlEncode(param) });
							} else if (type.equals("int")
									|| type.equals("java.lang.Integer")) {
								ms[i].invoke(bean, new Object[] { new Integer(
										param) });
							} else if (type.equals("long")
									|| type.equals("java.lang.Long")) {
								ms[i].invoke(bean, new Object[] { new Long(
										param) });
							} else if (type.equals("boolean")
									|| type.equals("java.lang.Boolean")) {
								ms[i].invoke(bean,
										new Object[] { Boolean.valueOf(param) });
							} else if (type.equals("float")
									|| type.equals("java.lang.Float")) {
								ms[i].invoke(bean, new Object[] { new Float(
										param) });
							} else if (type.equals("java.util.Date")) {
								Date date = null;
								if (param.indexOf(':') != (-1))
									date = DateUtil.parseDateTime(param);
								else
									date = DateUtil.parseDate(param);
								if (date != null)
									ms[i].invoke(bean, new Object[] { date });
								else
									System.err
											.println("WARNING: date is null: "
													+ param);
							}
						}
					} catch (Exception e) {
						System.err.println("WARNING: Invoke method "
								+ ms[i].getName() + " failed: "
								+ e.getMessage());
					}
				}
			}
		}
		return bean;
	}

	private static String htmlEncode(String text) {
		if (text == null || "".equals(text))
			return "";
		text = text.replace("<", "&lt;");
		text = text.replace(">", "&gt;");
		text = text.replace(" ", "&nbsp;");
		text = text.replace("\"", "&quot;");
		text = text.replace("\'", "&apos;");
		return text.replace("\n", "<br/>");
	}

	/**
	 * 获取j2ee根目录
	 * 
	 * @param request
	 * @return
	 */
	public static String getWebRootPath(HttpServletRequest request) {
		return getRealPath(request, "/");
	}

	/**
	 * 获取j2ee绝对路径
	 * 
	 * @param request
	 * @param relativepath
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request,
			String relativepath) {
		return request.getSession().getServletContext()
				.getRealPath(relativepath);
	}
	public String getContextPath(HttpServletRequest request){
		return request.getSession().getServletContext().getContextPath();
	}
	/**
	 * 获取URL参数
	 * @param request
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getParameter(HttpServletRequest request, String name)
			throws UnsupportedEncodingException {
		if (request.getParameter(name) != null) {
			return java.net.URLDecoder.decode(request.getParameter(name),
					encoding);
		}
		return null;
	}

	/**
	 * 获取属性，按pageScope, requestScope, sessionScope,ApplicationScope顺序查找
	 * 
	 * @param request
	 * @param name
	 * @return
	 * @throws Exception
	 */
	protected Object getAttribute(HttpServletRequest request, String name)
			throws Exception {
		Object value = HttpUtil.getParameter(request, name);
		if (value == null) {
			value = request.getAttribute(name);
		} else {
			return value;
		}

		if (value == null) {
			value = request.getSession().getAttribute(name);
		} else {
			return value;
		}

		if (value == null) {
			value = request.getServletContext().getAttribute(name);
		} else {
			return value;
		}

		return value;
	}

	/**
	 * 以json格式输出对象
	 * 
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void writeObject(HttpServletResponse response, Object obj)
			throws IOException {
		writeJson(response, gson.toJson(obj));
	}

	/**
	 * 输出json
	 * 
	 * @param response
	 * @param str
	 * @throws IOException
	 */
	public static void writeJson(HttpServletResponse response, String str)
			throws IOException {
		response.setContentType("applicaton/json;charset="
				+ EncodeFilter.getEncoding());
		response.getWriter().write(str);
	}

	/**
	 * 对象转换为json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	public static void main(String[] args){
		String url="http://localhost:9000/isoftstore/product/search_product?cat=01-04";
		System.out.println(HttpUtil.replaceParam(url, "cat", null));
	}

}
