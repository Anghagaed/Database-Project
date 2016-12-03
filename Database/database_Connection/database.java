package database_Connection;

import java.sql.*;

public class database {
	DBConnection dat;
	static final String path = "jdbc:sqlite:Test/pwMan.db";					// example: "jdbc:sqlite:test.db"
	String sql;
	
	public database() {
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
		dat.bindStringStmt(domainname, 1); //1 is example - need real domainname
		dat.bindIntStmt(userID, 2); //userid start with what #? look up & replace '2'
		ResultSet rs = dat.executeSQL();
		
		String username = rs.getString("p_domainusername");
		String password = rs.getString("p_domainpassword");
		
		System.out.println("Username :" + username);
		System.out.println("Passowrd :" + password);
		rs.close();
		dat.clearStatement();
	}

	// Need to be worked on.
	public int insertAccInfo() {
		return 0;
	}
	
	public int updateAccountStreet(String newStreet, int ID, int userID) {
		sql = "UPDATE accountinfo SET ai_street= ? WHERE ai_id=? AND ai_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(newStreet, 1);
		dat.bindIntStmt(ID, 2);
		dat.bindIntStmt(userID, 3);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int updateAccountCity(String newCity, int ID, int userID) {
		sql = "UPDATE accountinfo SET ai_city= ? WHERE ai_id=? AND ai_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(newCity, 1);
		dat.bindIntStmt(ID, 2);
		dat.bindIntStmt(userID, 3);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int updateAccountState(String newState, int ID, int userID) {
		sql = "UPDATE accountinfo SET ai_state= ? WHERE ai_id=? AND ai_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(newState, 1);
		dat.bindIntStmt(ID, 2);
		dat.bindIntStmt(userID, 3);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int updateAccountEmail(String newEmail, int ID, int userID) {
		sql = "UPDATE accountinfo SET ai_email= ? WHERE ai_id=? AND ai_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(newEmail, 1);
		dat.bindIntStmt(ID, 2);
		dat.bindIntStmt(userID, 3);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	// Change user cipher
	public int updateUserPassword(String cipher, int userID) {
		sql = "UPDATE user SET u_cipher=? WHERE u_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(cipher, 1);
		dat.bindIntStmt(userID, 2);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	
	public int insertUser(String username) throws SQLException {
		// Generate userID by count of all existing user;
		sql = "SELECT count(*) AS ct FROM user;";
		dat.prepStmt(sql);
		ResultSet rs = dat.executeSQL();
		int userID = (rs.getInt("ct")) + 1;
		rs.close();
		dat.clearStatement();
		// 
		sql = "INSERT INTO user (u_userid, u_username) VALUES (?, ?);";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		dat.bindStringStmt(username, 1);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
	}
	public int insertPWEntry(String domainname, int userID, String username, String passw) throws SQLException {
		// Generate ID by count of all existing entry for the user;
		int count, temp, i, id;
		sql = "SELECT count(*) AS ct FROM passwordentry;";
		dat.prepStmt(sql);
		ResultSet rs = dat.executeSQL();
		count = rs.getInt("ct");
		rs.close();
		dat.clearStatement();
		sql = "SELECT p_id FROM passwordentry ORDER BY ASC;";
		dat.prepStmt(sql);
		rs = dat.executeSQL();
		temp = -1;
		i = 1;
		while ( i <= count) {
			temp = rs.getInt("p_id");
			if (i != temp) {
				break;
			}
			++i;
		}		
		if (temp == count)
			id = count + 1;
		else 
			id = i;
		//
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
	
	public int UpdateDomainPass(int passID, int userID) throws SQLException{
		sql = "UPDATE passwordentry SET p_domainpassword = ? WHERE p_id=? AND p_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(passID, 1);
		dat.bindIntStmt(userID, 2);
		int rs = dat.executeUpdateSQL();
		return rs;

	}
}
