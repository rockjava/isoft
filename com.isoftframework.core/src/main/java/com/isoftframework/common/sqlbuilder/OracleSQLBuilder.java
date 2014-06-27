/*
 * Created on 2005-6-17
 */
package com.isoftframework.common.sqlbuilder;

import java.io.CharArrayReader;
import java.sql.PreparedStatement;
import java.util.List;

import com.isoftframework.dao.IDao;

public class OracleSQLBuilder implements SQLBuilder {

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
			result = fieldName + operator + " to_date('" + dateStr + time
					+ "','yyyy-MM-dd HH24:MI:SS')";
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
		return dateToStr(field,"yyyy-mm-dd hh24:mi:ss");
	}

	/**
	 * to_char(a.occur_time,'yyyy-mm-dd hh24:mi:ss')
	 */
	public String dateToStr(String field,String format) {
		return "to_char(" + field + ",'"+format+"')";
	}
	/**
	 * 数字转化为字符串
	 * 
	 * @param field
	 * @return
	 */
	public String numToStr(String field) {
		return "to_char(" + field + ")";
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
		if (content == null)
			content = " ";
		CharArrayReader bufReader = new CharArrayReader(content.toCharArray());
		ps.setCharacterStream(i, bufReader, 2000000);
	}

	/**
	 * 获取特定数据库的大字段编码转换
	 * 
	 * @return
	 */
	public String longTextCharset() {
		return "UTF-8";
	}

	/**
	 * 获取空判断的sql语句
	 * 
	 * @param fdName
	 *            字段名称
	 * @return
	 */
	public String getNullSql(String fdName) {
		return fdName + " is null ";
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
	
	public String buildLimitSql(String sql,long start,int limit){
		String limitSql=null;
		if(start<=0){
			limitSql="select * "+
					 " from ("+sql+") "+
					 "where rownum <="+limit;
		}else{
			limitSql=
				  "select * "+
				  "  from (select row_.*, rownum rownum_ "+
				  "       from ("+sql+") row_  "+
				  "       where rownum <= "+(start+limit)+") "+
				  "where rownum_ >"+start;
		}
		
		return limitSql;
	}
	
	@Override
	public List findAllChildIdByParentId(IDao dao,String parentId,String idName,String parentIdName,String tableName) throws Exception{
		return dao.findBySql(
			"select t."+idName+" from "+tableName+" t "+
			"start with t."+idName+"='"+parentId+"' "+
			"connect by prior t."+idName+"=t."+parentIdName);
		
	}

	 
}
