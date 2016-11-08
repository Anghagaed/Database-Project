package database_Connection;

public class database {
	DBConnection dat =  null;
	static final String path = "";					// example: "jdbc:sqlite:test.db"
	String sql;
	
	database() {
		dat = new DBConnection(path);
	}
	
}
