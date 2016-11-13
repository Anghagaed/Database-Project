package database_Connection;
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
		/*
		dat.bindIntStmt(1, 1);
		dat.bindStringStmt("testUser1", 2);
		dat.bindStringStmt("abcdefg", 3);
		dat.executeUpdateSQL();
		*/
		for (int i = 1; i <= 500; ++i) {
			dat.clearStatement();
			dat.bindIntStmt(i, 1);
			dat.bindStringStmt("testUser" + i, 2);
			dat.bindStringStmt("abcdefg" + i, 3);
			dat.executeUpdateSQL();
		} 
	
	}
	
	
}
