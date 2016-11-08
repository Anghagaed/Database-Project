package database_Connection;
import java.sql.*;
import java.util.Scanner;

public class DBConnection {

	static Connection conn;
	static String databaseName;
	static Statement stmt;
	static PreparedStatement pstmt;
	static ResultSet rs;
	
	public DBConnection() {
		conn = null;
		stmt = null;
		databaseName = null;
		rs = null;
		pstmt = null;
	}
	public DBConnection(String input) {
		databaseName = input;
		conn = null;
		stmt = null;
		rs = null;
		pstmt = null;
	}
	public void setdbName(String input) {
		databaseName = input;
	}
	public void createConnection() {
		if (databaseName != null) {
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(databaseName);
			} catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }
		}
	}
	private void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch(SQLException se){
				se.printStackTrace();
			}
		} 
	}
	private void closeStatement() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		} 
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		} 
	}
	private void closeResultSet() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
	}
	public void close() {
		closeConnection();
		closeStatement();
		closeResultSet();
	}
	public void prepStmt(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
	public void bindStmt(String type, int order) {
		Scanner sc = new Scanner(System.in);
		if (type == "int") {
			try {
				int input = sc.nextInt();
				pstmt.setInt(order, input);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} else if (type == "string") {
			try {
				String input = sc.nextLine();
				pstmt.setString(order, input);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} 
		sc.close();
	}
	public ResultSet executeSQL() {
		if (rs != null) {
			try {
				rs.close();
			} catch(SQLException se) {
			} finally {
				rs = null;
			}
		}
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return rs;
	}
	public ResultSet executeSQL(String sql) {					
		if (rs != null) {
			try {
				rs.close();
			} catch(SQLException se) {
			} finally {
				rs = null;
			}
		}
		try {
			rs = stmt.executeQuery(sql);
		} catch(SQLException se) {
			se.printStackTrace();
		}
		return rs;													// Note that result can be null if something fail.
	}
}
