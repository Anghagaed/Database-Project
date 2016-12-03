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
		dat.clearStatement();
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
