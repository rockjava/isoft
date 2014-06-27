package com.isoftframework.dao.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.LobRetrievalFailureException;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.FileCopyUtils;

import com.isoftframework.common.sqlbuilder.SQLBuilder;
import com.isoftframework.common.sqlbuilder.SQLBuilderFactory;

public class JdbcSupport extends JdbcDaoSupport implements IJdbcDaoSupport {

	private LobHandler lobHandler;

	// ① 定义 LobHandler 属性
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public Connection getNativeConn() {
		try {
			Connection conn = DataSourceUtils.getConnection(getJdbcTemplate()
					.getDataSource());
			// ① 使用 DataSourceUtils 从模板类中获取连接
			// ② 使用模板类的本地 JDBC 抽取器获取本地的 Connection
			conn = getJdbcTemplate().getNativeJdbcExtractor()
					.getNativeConnection(conn);
			// OracleConnection db2conn = (OracleConnection) conn; //③
			// 这时可以强制进行类型转换了
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @example int rowCount =
	 *          this.jdbcTemplate.queryForObject("select count(*) from t_actor",
	 *          Integer.class);
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> c) {
		return this.getJdbcTemplate().queryForObject(sql, c);

	}

	@Override
	public int queryForInt(String querySql) {

		return this.getJdbcTemplate().queryForInt(querySql);
	}

	/**
	 * @example String lastName = this.jdbcTemplate.queryForObject(
	 *          "select last_name from t_actor where id = ?", new
	 *          Object[]{1212L}, String.class);
	 * @param sql
	 * @param values
	 * @param c
	 * @return
	 */
	public <T> T queryForObject(String sql, Object[] values, Class<T> c) {
		return this.getJdbcTemplate().queryForObject(sql, values, c);

	}

	public List queryForList(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}

	
	public <T> List<T> queryEntityForList(String sql, final Class<T> clas) {
		return this.getJdbcTemplate().query(sql, new EntityRowMapper(clas));
	}

	public <T> List<T> queryEntityForList(String sql, Object value, Class<T> clas) {
		return this.getJdbcTemplate().query(sql, new Object[]{value},new EntityRowMapper(clas));
	}
	public <T> List<T> queryEntityForList(String sql, Object values[], Class<T> clas) {
		return this.getJdbcTemplate().query(sql, values,new EntityRowMapper(clas));
	}
	public <T> List<T> queryEntityForListWithLimit(String sql, long start, int limit,
			final Class<T> clas) {

		return this.getJdbcTemplate().query(
				sqlBuilder.buildLimitSql(sql, start, limit),
				new EntityRowMapper(clas));
	}
	public <T> List<T> queryEntityForListWithLimit(String sql,Object value, long start, int limit,
			final Class<T> clas) {
		return this.queryEntityForListWithLimit(sql, new Object[]{value}, start, limit, clas);
	}
	public <T> List<T> queryEntityForListWithLimit(String sql,Object[] values, long start, int limit,
			final Class<T> clas) {
		return this.getJdbcTemplate().query(
				sqlBuilder.buildLimitSql(sql, start, limit),
				values,
				new EntityRowMapper(clas));
	}
	public <T> T getEntity(String sql, final Class<T> clas){
		List<T> list= queryEntityForList(sql,clas);
		if(list.size()>0)
			return list.get(0);
		return (T)null;
	}
	
	public <T> T getEntity(String sql,Object values[], final Class<T> clas){
		List<T> list= this.queryEntityForList(sql, values, clas);
		if(list.size()>0)
			return list.get(0);
		return (T)null;
	}
	

	public void outLob(String sql,Object values[],final int postId, final OutputStream os) {
		// ① 用于接收 LOB 数据的输出流
		getJdbcTemplate().query(sql, values,
				new AbstractLobStreamingResultSetExtractor() {
					// ③ 处理未找到数据行的情况
					@Override
					protected void handleNoRowFound()
							throws LobRetrievalFailureException {
						System.out.println("Not Found result!");
					}

					// ④ 以流的方式处理 LOB 字段
					@Override
					public void streamData(ResultSet rs) throws SQLException,
							IOException {
						InputStream is = lobHandler
								.getBlobAsBinaryStream(rs, 1);
						if (is != null) {
							FileCopyUtils.copy(is, os);
						}
					}
				});
	}

	public void insertLob(String sql) {
		getJdbcTemplate().execute(
				sql,
				new AbstractLobCreatingPreparedStatementCallback(
						this.lobHandler) {
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException {
						ps.setInt(1, 1);
						ps.setInt(2, 12);
						// ③ 设置 CLOB 字段
						lobCreator.setClobAsString(ps, 3, "");

						// ④ 设置 BLOB 字段
						lobCreator.setBlobAsBytes(ps, 4, new byte[2]);
					}
				});
	}

	public int[] batchUpdate(final String sqls[]) {
		return this.getJdbcTemplate().batchUpdate(sqls);
	}

	public SQLBuilder sqlBuilder = SQLBuilderFactory.getSQLBuilder();

}
