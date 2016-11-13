package database_Connection;
//import java.sql.ResultSet;

import database_Connection.DBConnection;

//import java.sql.*;

public class generateData {
	DBConnection dat;
	String sql;
	static final String path = "jdbc:sqlite:Test/pwMan.db";			// Need to figure out how path works for eclipse and java
	
	public generateData() {
		dat = new DBConnection(path);
		sql = null;
		dat.createConnection();
	}
	
	public void generateTuser() {
		sql = "INSERT INTO user (u_userid, u_username, u_cipher) VALUES (?, ?, ?);";
		dat.prepStmt(sql);
		for (int i = 1; i <= 500; ++i) {
			dat.clearStatement();
			dat.bindIntStmt(i, 1);
			dat.bindStringStmt("testUser" + i, 2);
			dat.bindStringStmt("abcdefg" + i, 3);
			dat.executeUpdateSQL();
		} 
	}
	
	public void generateTnotes() {
		sql = "INSERT INTO notes (n_id, n_entry, n_userid, n_encryptid, n_cipher) VALUES (?, ?, ? , ?, ?);";
		dat.prepStmt(sql);
		for (int i = 1; i <= 500; ++i) {
			dat.clearStatement();
			dat.bindIntStmt();
			dat.bindStringStmt(data, order);
			dat.bindIntStmt();
			dat.bindIntStmt();
			dat.bindStringStmt(data, order);
		}
	}
	
	 public void close() {
		 dat.close();
	 }
	
}
