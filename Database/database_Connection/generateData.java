package database_Connection;
//import java.sql.*;

public class generateData {
	DBConnection dat;
	String sql;
	static final String path = ".\\pwMan.db";			// Need to figure out how path works for eclipse and java
	
	public generateData() {
		dat = new DBConnection(path);
		sql = null;
		dat.createConnection();
	}
	
	public void generateTuser() {
		sql = "INSERT INTO user VALUES (?, ?, ?)";
		dat.prepStmt(sql);
		dat.bindStmt("1", 1);
		dat.bindStmt("testUser1", 2);
		dat.bindStmt("abcdefg", 3);
		dat.executeSQL();
		dat.bindStmt("2", 1);
		dat.bindStmt("testUser2", 2);
		dat.bindStmt("abcdefgi", 3);
		dat.executeSQL();
	}
	
	
}
