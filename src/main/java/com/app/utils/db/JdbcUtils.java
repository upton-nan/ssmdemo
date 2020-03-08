package com.app.utils.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 
 * @ClassName: JdbcUtils
 * @Description: Jdbc工具类
 * @author lyn
 * @date 2019年8月7日
 *
 */
public class JdbcUtils {

	private static String DB_NAME = "DB_NAME";

	/**
	 * 
	 * @Title: getConnection
	 * @Description: 获取Connection
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public Connection getConnection(String driver, String url, String username, String password) {
		if (driver == null || url == null || username == null || password == null) {
			throw new IllegalArgumentException("The parameters must not be null");
		}

		if ("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driver)) {
			DB_NAME = "sqlserver";
		} else if ("oracle.jdbc.driver.OracleDriver".equals(driver)) {
			DB_NAME = "oracle";
		} else if ("com.mysql.jdbc.Driver".equals(driver)) {
			DB_NAME = "mysql";
		}

		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @Title: close
	 * @Description: 关闭Connection
	 * @param conn
	 */
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: execute
	 * @Description: 执行一条sql语句(insert, update, delete)
	 * @param conn
	 * @param sql
	 * @param obj
	 */
	public void execute(Connection conn, String sql, Object[] obj) {
		PreparedStatement pst = null;
		try {
			if (conn == null || conn.isClosed()) {
				throw new IllegalArgumentException("Connection has bean closed!");
			}
			if (sql == null) {
				throw new IllegalArgumentException("The sql must not be null");
			}

			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			if (obj != null && obj.length > 0) {
				for (int i = 0; i < obj.length; i++) {
					pst.setObject(i + 1, obj[i]);
				}
			}
			pst.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: executeBatch
	 * @Description: 批量执行多条sql语句(insert, update, delete)
	 * @param conn
	 * @param sql
	 * @param objList
	 */
	public void executeBatch(Connection conn, String sql, List<Object[]> objList) {
		PreparedStatement pst = null;
		try {
			if (conn == null || conn.isClosed()) {
				throw new IllegalArgumentException("Connection has bean closed!");
			}
			if (sql == null) {
				throw new IllegalArgumentException("The sql must not be null");
			}

			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			if (objList != null && !objList.isEmpty()) {
				for (Object[] obj : objList) {
					if (obj != null && obj.length > 0) {
						for (int i = 0; i < obj.length; i++) {
							pst.setObject(i + 1, obj[i]);
						}
						pst.addBatch();
					}
				}
				pst.executeBatch();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 
	 * @Title: executeProcedure
	 * @Description: 执行存储过程
	 * @param conn
	 * @param sql
	 * @param in 输入参数, key:参数位置(从1开始), value:参数值
	 * @param out 输出参数, key:参数位置(从1开始), value:参数类型
	 * @return
	 */
	public Map<Integer, Object> executeProcedure(Connection conn, String sql, Map<Integer, Object> in, Map<Integer, Integer> out) {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			if (conn == null || conn.isClosed()) {
				throw new IllegalArgumentException("Connection has bean closed!");
			}
			if (sql == null) {
				throw new IllegalArgumentException("The sql must not be null");
			}

			// {call test_pcd(?, ?)}
			cs = conn.prepareCall(sql);
			// 输入参数
			if (in != null && !in.isEmpty()) {
				for (Entry<Integer, Object> entry : in.entrySet()) {
					cs.setObject(entry.getKey(), entry.getValue());
				}
			}
			// 输出参数
			if (out != null && !out.isEmpty()) {
				for (Entry<Integer, Integer> entry : out.entrySet()) {
					cs.registerOutParameter(entry.getKey(), entry.getValue());
				}
			}
			cs.execute();

			// 设置输出结果
			if (out != null && !out.isEmpty()) {
				for (Integer index : out.keySet()) {
					Object obj = cs.getObject(index);

					if (obj instanceof ResultSet) {
						List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
						rs = (ResultSet) obj;
						ResultSetMetaData rsmd = rs.getMetaData();
						while (rs.next()) {
							Map<String, Object> columns = new HashMap<String, Object>();
							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								String fieldName = rsmd.getColumnName(i);
								Object fieldValue = rs.getObject(fieldName);
								columns.put(fieldName.toUpperCase(), fieldValue);
							}
							rows.add(columns);
						}
						rs.close();
						map.put(index, rows);
					} else if (obj instanceof java.sql.Clob) {
						java.sql.Clob clob = cs.getClob(index);
						map.put(index, clob.getSubString((long) 1, (int) clob.length()));
					} else if (obj instanceof java.sql.Date || obj instanceof java.sql.Timestamp) {
						java.sql.Timestamp timestamp = cs.getTimestamp(index);
						map.put(index, timestamp);
					} else {
						map.put(index, obj);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (cs != null) cs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 
	 * @Title: findAll
	 * @Description: 查询一条或多条记录(不带分页)
	 * @param conn
	 * @param sql
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> findAll(Connection conn, String sql, Object[] obj) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			if (conn == null || conn.isClosed()) {
				throw new IllegalArgumentException("Connection has bean closed!");
			}
			if (sql == null) {
				throw new IllegalArgumentException("The sql must not be null");
			}

			pst = conn.prepareStatement(sql);
			if (obj != null && obj.length > 0) {
				for (int i = 0; i < obj.length; i++) {
					pst.setObject(i + 1, obj[i]);
				}
			}
			rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String fieldName = rsmd.getColumnName(i);
					Object fieldValue = rs.getObject(fieldName);
					map.put(fieldName.toUpperCase(), fieldValue);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 
	 * @Title: find
	 * @Description: 查询一条或多条记录(带分页) - oracle, mysql
	 * @param conn
	 * @param sql
	 * @param obj
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Map<String, Object>> find(Connection conn, String sql, Object[] obj, int firstResult, int maxResults) {
		try {
			if (conn == null || conn.isClosed()) {
				throw new IllegalArgumentException("Connection has bean closed!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (sql == null) {
			throw new IllegalArgumentException("The sql must not be null");
		}

		StringBuilder sb = new StringBuilder();
		if (DB_NAME.equals("oracle")) {
			sb.append("SELECT * FROM (");
			sb.append("SELECT TEMP_TABLE_.*, ROWNUM ROWNUM_ FROM (").append(sql).append(") TEMP_TABLE_");
			sb.append(" WHERE ROWNUM <= ").append(firstResult + maxResults).append(")");
			sb.append(" WHERE ROWNUM_ > ").append(firstResult);
		} else if (DB_NAME.equals("mysql")) {
			sb.append("SELECT * FROM (").append(sql).append(") TEMP_TABLE_");
			sb.append(" LIMIT ").append(firstResult).append(",").append(maxResults);
		}
		return this.findAll(conn, sb.toString(), obj);
	}

	public static void main(String[] args) {
		JdbcUtils jdbc = new JdbcUtils();
		Connection conn = jdbc.getConnection("", "", "admin", "admin");
		jdbc.execute(conn, "", null);
		jdbc.findAll(conn, "", null);
		jdbc.close(conn);
	}

}
