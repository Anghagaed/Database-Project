package database_Connection;
import java.sql.*;

public class DBConnection {

	static Connection conn;
	static String databaseName;
	static Statement stmt;
	static ResultSet rs;
	
	public DBConnection() {
		conn = null;
		stmt = null;
		databaseName = null;
		rs = null;
	}
	public DBConnection(String input) {
		databaseName = input;
		conn = null;
		stmt = null;
		rs = null;
	}
	public void setdbName(String input) {
		databaseName = input;
	}
	public void createConnection() {
		if (databaseName != null) {
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(databaseName);
				stmt = conn.createStatement();
			} catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }
		} else {
			System.out.println("DatabaseName is empty.");
		}
	}
	private void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch(SQLException se){
				se.printStackTrace();
			}
		} else {
			System.out.println("Connection is empty.");
		}
	}
	private void closeStatement() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		} else {
			System.out.println("Statement is empty.");
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
	public ResultSet executeSQL(String sql) {					
		try {
			rs = stmt.executeQuery(sql);
		} catch(SQLException se) {
			se.printStackTrace();
		}
		return rs;													// Note that result can be null if something fail.
	}
}
