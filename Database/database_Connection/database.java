package database_Connection;

import java.sql.*;

public class database {
	DBConnection dat;
	static final String path = "jdbc:sqlite:Test/pwMan.db";					// example: "jdbc:sqlite:test.db"
	String sql;
	
	database() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dat = new DBConnection(path);
		dat.createConnection();
		sql = null;
	}
	
	public void displayUserNPass(String domainname, int userID) {
		sql = "SELECT p_domainusername, p_domainpassword FROM passwordentry WHERE p_domainname=? AND p_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domainname, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		
		String username = rs.getString("p_domainusername");
		String password = rs.getString("p_domainpassword");
		
		System.out.println("Username :" + username);
		System.out.println("Passowrd :" + password);
		dat.clearStatement();
	}
}
