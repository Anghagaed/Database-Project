package database_Connection;
import java.sql.*;
import java.util.Scanner;

public class DBConnection {

	static Connection conn;
	static String path;
	static Statement stmt;
	static PreparedStatement pstmt;
	static ResultSet rs;
	
	public DBConnection() {
		conn = null;
		stmt = null;
		path = null;
		rs = null;
		pstmt = null;
	}
	public DBConnection(String input) {
		path = input;
		conn = null;
		stmt = null;
		rs = null;
		pstmt = null;
	}
	public void setPath(String input) {
		path = input;
	}
	/*
	 * Create database connection to specific path IF path is initiliaze. 
	 */
	public void createConnection() {
		if (path != null) {
			try {
				conn = DriverManager.getConnection(path);
			} catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }
		}
	}
	/*
	 * Close database connection if connection was open
	 */
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch(SQLException se){
				se.printStackTrace();
			}
		} 
	}
	/*
	 * Close ALL statements if they were open
	 */
	public void closeStatement() {
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
	/*
	 * Close ResultSet (Table) if they were open
	 */
	public void closeResultSet() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
	}
	/*
	 * General destructor for the class when done
	 * Java does not allows for destructors like in C++
	 */
	public void close() {
		closeConnection();
		closeStatement();
		closeResultSet();
	}
	/*
	 * Prepare for Dynamic SQL
	 */
	public void prepStmt(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
	/*
	 * Bind dynamic SQL parameter. 
	 * This method takes data from the command line
	 * First parameter is type of data, second parameter is the order of the parameter according to the dynamic SQL 
	 */
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
	/*
	 * Bind dynamic SQL parameter that has type int.
	 * This generic method takes data from its arguments.
	 * WIP: Need to figure out how to use Java Generic to do this instead of making individuals method
	 */
	public void bindIntStmt(int data, int order) {
		try {
			pstmt.setInt(order, data);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	/*
	 * Bind dynamic SQL parameter that has type String.
	 * This generic method takes data from its arguments.
	 * WIP: Need to figure out how to use Java Generic to do this instead of making individuals method
	 */
	public void bindStringStmt(String data, int order) {
		try {
			pstmt.setString(order, data);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	/*
	 * Bind dynamic SQL parameter that has type bool.
	 * This generic method takes data from its arguments.
	 * WIP: Need to figure out how to use Java Generic to do this instead of making individuals method
	 */
	public void bindBoolStmt(boolean data, int order) {
		try {
			pstmt.setBoolean(order, data);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	/*
	 * Bind dynamic SQL parameter that has type Date.
	 * This generic method takes data from its arguments.
	 * WIP: Need to figure out how to use Java Generic to do this instead of making individuals method
	 */
	public void bindDateStmt(java.sql.Date data, int order) {
		try {
			pstmt.setDate(order, data);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	/*
	* Use this method for SELECT statements
	* For Dynamic SQL
	* Returns a ResultSet object
	*/
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
	/*
	* Use this method for INSERT, UPDATE, DELETE statements
	* Returns the number of rows affected by the execution of the SQL statement.
	* Return of a negative number means executions fails. 
	*/
	public int executeUpdateSQL() {
		int columnsAffected = -1;
		try {
			columnsAffected = pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return columnsAffected;
	}
	/*
	* Use this method for SELECT statements
	* For Static SQL
	* Returns a ResultSet object
	*/
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
	/*
	 * Clear Prepare Statement parameters and readies it for another set of parameter data
	 */
	public void clearStatement() {
		if (pstmt != null) {
			try {
				pstmt.clearParameters();
			} catch (SQLException se) {
				se.printStackTrace();
			} 	
		}
	}

}