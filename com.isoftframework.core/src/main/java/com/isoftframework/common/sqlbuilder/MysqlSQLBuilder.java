/*
 * Created on 2005-6-17
 */
package com.isoftframework.common.sqlbuilder;

import java.sql.PreparedStatement;
import java.util.List;

import com.isoftframework.dao.IDao;

public class MysqlSQLBuilder implements SQLBuilder {

	/**
	 * 构造日期格式的函数
	 * 
	 * @param dateStr
	 * @param fieldName
	 * @param Operator
	 * @return
	 */
	public String buildDate(String dateStr, String fieldName, String operator) {
		String result = "";
		String time = " 00:00:00";
		if ("<".equals(operator) || "<=".equals(operator)) {
			time = " 23:59:59";
		}
		if (dateStr != null) {
			result = fieldName + operator + " date_format('" + dateStr + time
					+ "','%Y-%m-%d %H:%i:%s')";
		}
		return result;
	}

	/**
	 * 日期转化为字符串
	 * 
	 * @param field
	 * @return
	 */
	public String dateToStr(String field) {
		return "date_format(" + field + ",'%Y-%m-%d')";
	}

	/**
	 * 数字转化为字符串
	 * 
	 * @param field
	 * @return
	 */
	public String numToStr(String field) {
		return "char(" + field + ")";
	}

	/**
	 * 设置jdbc 操作大文本的参数
	 * 
	 * @param ps
	 * @param i
	 * @param content
	 * @return
	 */
	public void setLongStringParam(PreparedStatement ps, int i, String content)
			throws Exception {
		ps.setString(i, content);
	}

	/**
	 * 获取特定数据库的大字段编码转换
	 * 
	 * @return
	 */
	public String longTextCharset() {
		return "GBK";
	}

	/**
	 * 获取空判断的sql语句
	 * 
	 * @param fdName
	 *            字段名称
	 * @return
	 */
	public String getNullSql(String fdName) {
		return "(" + fdName + " is null or " + fdName + " = '')";
	}

	/**
	 * 获取连接字符串的sql语句
	 * 
	 * @param s1
	 *            字符串1
	 * @param s2
	 *            字符串2
	 * @return 返回特定数据库的连接的字符串函数
	 */
	public String concat(String s1, String s2) {
		return "concat(" + s1 + "," + s2 + ")";
	}

	/**
	 * 获取字段长度的sql语句
	 * 
	 * @param fdName
	 *            字段名称
	 * @return
	 */
	public String lengthToStr(String fdName) {
		return "length(" + fdName + ")";
	}

	/**
	 * 获取舍去order by条件的sql语句
	 * 
	 * @param sql
	 *            语句
	 * @return
	 */
	public String delOrderByToStr(String sql) {
		return sql;
	}

	/**
	 * 获取求子串的函数
	 * 
	 * @param sql
	 *            字符串
	 * @param satrtNum
	 *            起始位置
	 * @param lenNum
	 *            子串长度
	 * @return
	 */
	public String subString(String sql, int satrtNum, int lenNum) {
		return "substr(" + sql + "," + satrtNum + "," + lenNum + ")";
	}

	/**
	 * 获取求子串的函数
	 * 
	 * @param sql
	 *            字符串
	 * @param satrtNum
	 *            起始位置
	 * @param lenNum
	 *            子串长度
	 * @return
	 */
	public String subString(String sql, String satrtNum, String lenNum) {
		return "substr(" + sql + "," + satrtNum + "," + lenNum + ")";
	}

	/**
	 * 获取求子串的函数
	 * 
	 * @param sql
	 *            字符串
	 * @param satrtNum
	 *            起始位置
	 * @return
	 */
	public String subString(String sql, int satrtNum) {
		return "substr(" + sql + "," + satrtNum + ")";
	}

	/**
	 * 获取求子串的函数的SQL语句
	 * 
	 * @param sql
	 *            字符串
	 * @param satrtNum
	 *            起始位置的 SQL语句（该SQL语句查询结果返回int型）
	 * @return
	 */
	public String subString(String sql, String satrtNum) {
		return "substr(" + sql + "," + satrtNum + ")";
	}

	 

	@Override
	public String buildLimitSql(String sql, long start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findAllChildIdByParentId(IDao dao, String parentId,
			String idName, String parentIdName, String tableName)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dateToStr(String field, String format) {
		// TODO Auto-generated method stub
		return null;
	}

	 
	 

	 

}
