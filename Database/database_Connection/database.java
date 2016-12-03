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
	public int insertAccInfo(int userID, String cName, String email, String state, String city, String street, boolean subStatus) throws SQLException {
		sql = "SELECT count(*) as count FROM accountinfo;";
		dat.prepStmt(sql);
		ResultSet rs = dat.executeSQL();
		int ID = (rs.getInt("ct")) + 1;
		rs.close();
		dat.clearStatement();

		sql = "INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		dat.prepStmt(sql);
		dat.bindIntStmt(ID, 1);
		dat.bindIntStmt(userID, 2);
		dat.bindStringStmt(cName, 3);
		dat.bindStringStmt(email, 4);
		dat.bindStringStmt(state, 5);
		dat.bindStringStmt(city, 6);
		dat.bindStringStmt(street, 7);
		dat.bindBoolStmt(subStatus, 8);
		int result = dat.executeUpdateSQL();
		
		return result;
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
		dat.executeUpdateSQL();
		dat.clearStatement();
		return userID;
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
	
	// Fatima Queries
	public void displayNote(int userID, int noteID) throws SQLException {
		sql = "SELECT n_entry FROM notes ;WHERE n_userid=? AND n_id=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		dat.bindIntStmt(noteID, 2);
		ResultSet rs = dat.executeSQL();
		
		String noteEntry = rs.getString("n_entry");
		
		System.out.println("Note: " + noteEntry);
		dat.clearStatement();
	}
	
	public void displayWallet(int walletID, int userID) throws SQLException {
		sql = "SELECT n_entry FROM notes WHERE n_userid=? AND n_id=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(walletID, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		
		String bank = rs.getString("w_bankname");
		String cardType = rs.getString("w_cardtype");
		String nameOnCard = rs.getString("w_nameoncard");
		String expDate = rs.getString("w_expirationdate");
		String billAddress = rs.getString("w_billingaddress");
		String cardNum = rs.getString("w_cardnum");
		String secCode = rs.getString("w_securitycode");
		
		System.out.println("Bank: " + bank);
		System.out.println("Card type: " + cardType);
		System.out.println("Name on Card: " + nameOnCard);
		System.out.println("Expiration Date: " + expDate);
		System.out.println("Billing Address: " + billAddress);
		System.out.println("Card Number: " + cardNum);
		System.out.println("Security Code: " + secCode);
		
		dat.clearStatement();
	}
	
	public void displayCardInfo(int walletID, int userID) throws SQLException {
		sql = "SELECT w_cardtype, w_nameoncard, w_expirationdate, w_billingaddress, w_cardnum, w_securitycode FROM wallet WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(walletID, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		
		String cardType = rs.getString("w_cardtype");
		String nameOnCard = rs.getString("w_nameoncard");
		String expDate = rs.getString("w_expirationdate");
		String billAddress = rs.getString("w_billingaddress");
		String cardNum = rs.getString("w_cardnum");
		String secCode = rs.getString("w_securitycode");
		
		System.out.println("Card type: " + cardType);
		System.out.println("Name on Card: " + nameOnCard);
		System.out.println("Expiration Date: " + expDate);
		System.out.println("Billing Address: " + billAddress);
		System.out.println("Card Number: " + cardNum);
		System.out.println("Security Code: " + secCode);
		
		dat.clearStatement();
		
	}
	
	//any issues here?
	public int updateNoteEntry(String noteEntry, int userID) throws SQLException {
		sql = "UPDATE notes SET n_entry = ? WHERE n_id = (SELECT n_id FROM notes WHERE n_userid=? AND n_entry=?) ORDER BY n_id ASC;";
		dat.prepStmt(sql);
		dat.bindStringStmt(noteEntry, 1);
		dat.bindIntStmt(userID, 2);
		
		int rs = dat.executeUpdateSQL();
		return rs;
	}
	
	public int updateDomainName(String name, int pID, int pUserID) throws SQLException {
		sql = "UPDATE passwordentry SET p_domainname = ? WHERE p_id =? AND p_userid = ?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(name, 1);
		dat.bindIntStmt(pID, 2);
		dat.bindIntStmt(pUserID, 3);
		
		int rs = dat.executeUpdateSQL();
		return rs;
	}
	
	public int updateDomainUserName(String username, int pID, int pUserID) throws SQLException {
		sql = "UPDATE passwordentry SET p_domainname = ? WHERE p_id =? AND p_userid = ?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(username, 1);
		dat.bindIntStmt(pID, 2);
		dat.bindIntStmt(pUserID, 3);
		
		int rs = dat.executeUpdateSQL();
		return rs;
	}

	public int UpdateBankName(String bankName, int walletID, int userID) throws SQLException{
		sql = "UPDATE wallet SET w_bankname = ? WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(bankName, 1);
		dat.bindIntStmt(walletID, 2);
		dat.bindIntStmt(userID, 3);
		int rs = dat.executeUpdateSQL();
		return rs;
	}


	public int UpdateCardType(String cardType, int walletID, int userID) throws SQLException{
		sql = "UPDATE wallet SET w_cardtype = ? WHERE w_id = ? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(cardType, 1);
		dat.bindIntStmt(walletID, 2);
		dat.bindIntStmt(userID, 3);
		int rs = dat.executeUpdateSQL();
		return rs;
	}


	public int UpdateCardNum(String cardNum, int walletID, int userID) throws SQLException{
		sql = "UPDATE wallet SET w_cardnum = ? WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(cardNum, 1);
		dat.bindIntStmt(walletID, 2);
		dat.bindIntStmt(userID, 3);
		int rs = dat.executeUpdateSQL();
		return rs;
	}



	public int UpdateNameOnCard(String nameOnCard, int walletID, int userID) throws SQLException{
		sql = "UPDATE wallet SET w_nameoncard = ? WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(nameOnCard, 1);
		dat.bindIntStmt(walletID, 2);
		dat.bindIntStmt(userID, 3);
		int rs = dat.executeUpdateSQL();
		return rs;
	}



	public int UpdateBillAddress(String billAddress, int walletID, int userID) throws SQLException{
		sql = "UPDATE wallet SET w_billingaddress = ? WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(billAddress, 1);
		dat.bindIntStmt(walletID, 2);
		dat.bindIntStmt(userID, 3);
		int rs = dat.executeUpdateSQL();
		return rs;
	}



	public int UpdateSecCode(int secCode, int walletID, int userID) throws SQLException{
		sql = "UPDATE wallet SET w_securitycode = ? WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(secCode, 1);
		dat.bindIntStmt(walletID, 2);
		dat.bindIntStmt(userID, 3);
		int rs = dat.executeUpdateSQL();
		return rs;
	}



	public int UpdateExpDate(Date expDate, int walletID, int userID) throws SQLException{
		sql = "UPDATE wallet SET w_expirationdate = ? WHERE w_id=? AND w_userid=?;";
		dat.prepStmt(sql);
		dat.bindDateStmt(expDate, 1);
		dat.bindIntStmt(walletID, 2);
		dat.bindIntStmt(userID, 3);
		int rs = dat.executeUpdateSQL();
		return rs;
	}

	//NOT SURE IF THIS ONE IS CORRECT //num 7
	public int insertNoteEntry(int userID, String noteEntry) throws SQLException {
		sql = "SELECT count(*) AS count  FROM notes WHERE n_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		
		int noteID = rs.getInt("count"); //takes in 1st column of resultset as int
		noteID = noteID + 1;
		dat.clearStatement();
			
		sql = "INSERT INTO notes (n_id, n_entry, n_userid) VALUES (?, ?, ?);";
		dat.prepStmt(sql);
		dat.bindIntStmt(noteID, 1);
		dat.bindStringStmt(noteEntry, 2);
		dat.bindIntStmt(userID, 3);
		int result = dat.executeUpdateSQL();
		return result;
	}


	//NOT SURE IF THIS IS CORRECT EITHER // num 8
	public int insertWalletEntry(int userID, String bankName, String cardType, int cardNum, String nameOnCard, String billAddress, int secCode, Date expDate) throws SQLException {
		sql = "SELECT count(*) FROM wallet WHERE w_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		
		dat.clearStatement();
		int walletID = rs.getInt(1); //gets first column from resultset as int
		walletID = walletID + 1;
		rs.close();
		sql = "INSERT INTO wallet (w_bankname, w_cardtype, w_cardnum, w_nameoncard, w_billingaddress, w_securitycode, w_expirationdate, w_userid, w_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
		dat.prepStmt(sql);
			
		dat.bindStringStmt(bankName, 1);
		dat.bindStringStmt(cardType, 2);
		dat.bindIntStmt(cardNum, 3);
		dat.bindStringStmt(nameOnCard, 4);
		dat.bindStringStmt(billAddress, 5);
		dat.bindIntStmt(secCode, 6);
		dat.bindDateStmt(expDate, 7);
		dat.bindIntStmt(userID, 8);
		dat.bindIntStmt(walletID, 9);
			
		int result = dat.executeUpdateSQL();
		return result;
		}
}
