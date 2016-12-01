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
	
	public void displayUserNPass(String domainname, int userID) throws SQLException {
		sql = "SELECT p_domainusername, p_domainpassword FROM passwordentry WHERE p_domainname=? AND p_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domainname, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		
		String username = rs.getString("p_domainusername");
		String password = rs.getString("p_domainpassword");
		
		System.out.println("Username :" + username);
		System.out.println("Passowrd :" + password);
		rs.close();
		dat.clearStatement();
	}

	public int insertPWEntry(int id, String domainname, int userID, String username, String passw) {
		sql = "INSERT INTO passwordentry" + 
				" (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword)" +
				" VALUES (?, ?, ?, ?, ?);";
		dat.prepStmt(sql);
		dat.bindIntStmt(id, 1);
		dat.bindStringStmt(domainname, 2);
		dat.bindIntStmt(userID, 3);
		dat.bindStringStmt(username, 4);
		dat.bindStringStmt(passw, 5);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int deletePWEntry(int id, int userID) {
		sql = "DELETE FROM passwordentry WHERE p_id=? AND p_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(id, 1);
		dat.bindIntStmt(userID, 2);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int deleteWallet(int id, int userID) {
		sql = "DELETE FROM wallet WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(id, 1);
		dat.bindIntStmt(userID, 2);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int deleteNote(int id, int userID) {
		sql = "DELETE FROM notes WHERE n_id=? AND n_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(id, 1);
		dat.bindIntStmt(userID, 2);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int deleteAccount(int id, int userID) {
		sql = "DELETE FROM accountinfo WHERE ai_id=? AND ai_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(id, 1);
		dat.bindIntStmt(userID, 2);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
}
