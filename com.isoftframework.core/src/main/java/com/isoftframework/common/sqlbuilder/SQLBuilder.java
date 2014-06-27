/*
 * Created on 2005-6-17
 */
package com.isoftframework.common.sqlbuilder;

import java.sql.PreparedStatement;
import java.util.List;

import com.isoftframework.dao.IDao;

 
public interface SQLBuilder {

	/**
	 * 构造日期格式的函数
	 * 
	 * @param dateStr
	 * @param fieldName
	 * @param Operator
	 * @return
	 */
	public String buildDate(String dateStr, String fieldName, String Operator);

	/**
	 * 日期转化为字符串
	 * 
	 * @param field
	 * @return
	 */
	public String dateToStr(String field);
	
	public String dateToStr(String field,String format) ;

	/**
	 * 数字转化为字符串
	 * 
	 * @param field
	 * @return
	 */
	public String numToStr(String field);

	/**
	 * 设置jdbc 操作大文本的参数,用于处理模板和文章大字段操作
	 * 
	 * @param ps
	 * @param i
	 * @param content
	 * @return
	 */
	public void setLongStringParam(PreparedStatement ps, int i, String content)
			throws Exception;

	/**
	 * 获取特定数据库的大字段编码转换
	 * 
	 * @return
	 */
	public String longTextCharset();

	/**
	 * 获取空判断的sql语句
	 * 
	 * @param fdName
	 *            字段名称
	 * @return
	 */
	public String getNullSql(String fdName);

	/**
	 * 获取连接字符串的sql语句
	 * 
	 * @param s1
	 *            字符串1
	 * @param s2
	 *            字符串2
	 * @return 返回特定数据库的连接的字符串函数
	 */
	public String concat(String s1, String s2);

	/**
	 * 获取字段长度的sql语句
	 * 
	 * @param fdName
	 *            字段名称
	 * @return
	 */
	public String lengthToStr(String fdName);

	/**
	 * 获取舍去order by条件的sql语句
	 * 
	 * @param sql
	 *            语句
	 * @return
	 */
	public String delOrderByToStr(String sql);

	/**
	 * 获取求子串的函数
	 * 
	 * @param str
	 *            字符串
	 * @param satrt
	 *            起始位置
	 * @param len
	 *            子串长度
	 * @return
	 */
	public String subString(String str, int satrt, int len);

	/**
	 * 获取求子串的函数
	 * 
	 * @param str
	 *            字符串
	 * @param satrt
	 *            起始位置
	 * @param len
	 *            子串长度
	 * @return
	 */
	public String subString(String str, String satrt, String len);

	/**
	 * 获取求子串的函数
	 * 
	 * @param str
	 *            字符串
	 * @param satrt
	 *            起始位置
	 * @return
	 */
	public String subString(String str, int satrt);

	/**
	 * 获取求子串的函数的SQL语句
	 * 
	 * @param sql
	 *            字符串
	 * @param satrtNum
	 *            起始位置的 SQL语句（该SQL语句查询结果返回int型）
	 * @return
	 */
	public String subString(String sql, String satrtNum);
	
	public String buildLimitSql(String sql,long start,int limit);
	
	public List findAllChildIdByParentId(IDao dao,String parentId,String idName,String parentIdName,String tableName) throws Exception;

}
