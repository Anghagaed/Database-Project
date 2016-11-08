package database_Connection;

public class database {
	DBConnection dat =  null;
	static final String path = "";					// example: "jdbc:sqlite:test.db"
	String sql;
	
	database() {
		dat = new DBConnection(path);
		dat.createConnection();
	}
	// query 1
	public void query1() {
		
	}
	public void close() {
		dat.close();
	}
	
}
