package database_Connection;

public class database {
	DBConnection dat;
	static final String path = ".\\pwMan.db";					// example: "jdbc:sqlite:test.db"
	String sql;
	
	database() {
		dat = new DBConnection(path);
		dat.createConnection();
		sql = null;
	}
}
