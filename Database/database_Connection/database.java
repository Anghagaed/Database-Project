package database_Connection;

import java.sql.*;

import AES.*;

public class database {
	DBConnection dat;
	static final String path = "jdbc:sqlite:Test/pwMan.db";					// example: "jdbc:sqlite:test.db"
	String sql;
	static AES aes = new AES();
	
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
	public int getDomainExist(String domainname, int userID) throws SQLException {
		sql = "SELECT count(*) AS ct FROM passwordentry WHERE p_domainname=? AND p_userID=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domainname, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		int result = rs.getInt("ct");
		rs.close();
		return result;
	}
	public boolean getUserEncryptionStatus(String domainname, int userID) throws SQLException {
		sql = "SELECT p_encryptstatus FROM passwordentry WHERE p_domainname=? AND p_userID=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domainname, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		boolean output = false;rs.getBoolean("p_encryptstatus");
		if (rs.next())
			output = rs.getBoolean("p_encryptstatus");
		rs.close();
		return output;
	}
	public int getUserID(String username) throws SQLException {
		sql = "SELECT u_userid FROM user WHERE u_username=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(username, 1);
		ResultSet rs = dat.executeSQL();
		int result = rs.getInt("u_userid");
		rs.close();
		dat.clearStatement();
		return result;
	}
	public String getUserName(int userID) throws SQLException {
		sql = "SELECT u_username FROM user WHERE u_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		String result = rs.getString("u_username");
		rs.close();
		dat.clearStatement();
		return result;
	}
	public String getUserPassword(String username) throws SQLException {
		sql = "SELECT u_cipher FROM user WHERE u_username=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(username, 1);
		ResultSet rs = dat.executeSQL();
		String result = rs.getString("u_cipher");
		rs.close();
		dat.clearStatement();
		return result;
	}
	public int listOfNotes(int userID, String key) throws SQLException {
		sql = "SELECT count(*) AS ct FROM notes WHERE n_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		int result = rs.getInt("ct");
		rs.close();
		dat.clearStatement();
		if (result != 0) {
			sql = "SELECT n_title, n_entry FROM notes WHERE n_userid=?;";
			dat.prepStmt(sql);
			dat.bindIntStmt(userID, 1);
			rs = dat.executeSQL();
			String output = "";
			while (rs.next()) {
				output += rs.getString("n_title");
				output += ": ";
				String cipher = rs.getString("n_entry");
				String content = aes.decrypt(cipher, key);
				output += content;
				output += "\n";
			}
			System.out.println(output);
			return 1;
		} else {
			System.out.println("There are no notes information available");
			return 0;
		}
	}
	public void displayDomainInformation(String domainname, int userID, String userKey) throws SQLException {
		sql = "SELECT p_encryptstatus FROM passwordentry WHERE p_domainname=? AND p_userID=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domainname, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		int encryptStatus = -1;
		if (rs.next()) {
			encryptStatus = rs.getInt("p_encryptstatus");
		}
		rs.close();
		dat.clearStatement();
		if (encryptStatus == 1) {
			sql = "SELECT p_domainusername, p_domainpassword FROM passwordentry WHERE p_domainname=? AND p_userID=?;";
			dat.prepStmt(sql);
			dat.bindStringStmt(domainname, 1);
			dat.bindIntStmt(userID, 2);
			rs = dat.executeSQL();
			dat.clearStatement();
			String dUsername = rs.getString("p_domainusername");
			String cipher = rs.getString("p_domainpassword");
			rs.close();
			sql = "SELECT u_username FROM user WHERE u_userid=?;";
			dat.prepStmt(sql);
			dat.bindIntStmt(userID, 1);
			rs = dat.executeSQL();
			String userName = rs.getString("u_username");
			rs.close();
			dat.clearStatement();
			String password = aes.decrypt(aes.decrypt(cipher, userKey), userID + userName + userID);
			System.out.println("Username: " + dUsername);
			System.out.println("Password: " + password);
		} else if (encryptStatus == 0){
			sql = "SELECT p_domainusername, p_domainpassword FROM passwordentry WHERE p_domainname=? AND p_userID=?;";
			dat.prepStmt(sql);
			dat.bindStringStmt(domainname, 1);
			dat.bindIntStmt(userID, 2);
			rs = dat.executeSQL();
			dat.clearStatement();
			String dUsername = rs.getString("p_domainusername");
			String cipher = rs.getString("p_domainpassword");
			rs.close();
			sql = "SELECT u_username FROM user WHERE u_userid=?;";
			dat.prepStmt(sql);
			dat.bindIntStmt(userID, 1);
			rs = dat.executeSQL();
			String userName = rs.getString("u_username");
			rs.close();
			dat.clearStatement();
			String password = aes.decrypt(cipher, userID + userName + userID);
			System.out.println("Username: " + dUsername);
			System.out.println("Password: " + password);
		} else if (encryptStatus == -1){
			System.out.println("Input domain does not exist in the system.");
		}
	}
	
	public int listOfDomain(int userID) throws SQLException {
		sql = "SELECT count(*) AS ct FROM passwordentry WHERE p_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		int result = rs.getInt("ct");
		rs.close();
		dat.clearStatement();
		if (result != 0) {
			sql = "SELECT p_domainname FROM passwordentry WHERE p_userid=?;";
			dat.prepStmt(sql);
			dat.bindIntStmt(userID, 1);
			rs = dat.executeSQL();
			String output = "";
			while (rs.next()) {
				output += rs.getString("p_domainname");
				output += "\n";
			}
			System.out.println(output);
			return 1;
		} else {
			return 0;
		}
	}
	
	public int insertAccount(int userID, String name, String email, String state, String city, String street) throws SQLException {
		sql = "SELECT COUNT(*) as count FROM accountinfo WHERE ai_userid = ?";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		int count = rs.getInt("count");
		rs.close();
		dat.clearStatement();
		
		sql = "INSERT INTO accountinfo (ai_id, ai_userid, ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus) VALUES (?, ?, ?, ?, ?, ?, ?, 0);";
		dat.prepStmt(sql);
		count = count + 1;
		dat.bindIntStmt(count, 1);
		dat.bindIntStmt(userID, 2);
		dat.bindStringStmt(name, 3);
		dat.bindStringStmt(email, 4);
		dat.bindStringStmt(state, 5);
		dat.bindStringStmt(city, 6);
		dat.bindStringStmt(street, 7);
		
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		return result;
		
		
	}
	
	public void displayAccountinfo(int userID) throws SQLException {
		/*sql = "SELECT ai_substatus AS sub FROM accountinfo WHERE ai_userid = ?";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		int substat = rs.getInt("sub");
		dat.clearStatement();
		rs.close();
		
		if(substat == 1)
		{*/
			sql = "SELECT ai_customername, ai_email, ai_state, ai_city, ai_street, ai_substatus, ai_subexpdate FROM accountinfo WHERE ai_userid = ?";
			dat.prepStmt(sql);
			dat.bindIntStmt(userID, 1);
			ResultSet rs = dat.executeSQL();
			
			if(rs.next())
			{
				String name = rs.getString("ai_customername");
				String email = rs.getString("ai_email");
				String state = rs.getString("ai_state");
				String city = rs.getString("ai_city");
				String street = rs.getString("ai_street");
				int substatus = rs.getInt("ai_substatus");
				Date subExpDate = rs.getDate("ai_subexpdate");
				
				System.out.println("Name: " + name);
				System.out.println("Email: "+ email);
				System.out.println("State: " + state);
				System.out.println("City: " + city);
				System.out.println("Street: " + street);
				
				rs.close();
				dat.clearStatement();
				
			}
			else
				System.out.println("No Account inforation to provide.");
		
		
	}
	public int getAccID(int userID) throws SQLException{
		sql = "SELECT ai_id as ID FROM accountinfo WHERE ai_userid = ?";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		
		int id = (rs.getInt("ID"));
		if(id ==0) { System.out.println("No Account information to edit.");}
		rs.close();
		return id;
	}
	

	// Need to be worked on.
	/*public int insertAccInfo(int userID, String cName, String email, String state, String city, String street, int subStatus) throws SQLException {
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
		dat.bindIntStmt(subStatus, 8);
		int result = dat.executeUpdateSQL();
		dat.clearStatement();
		
		return result;
	}*/
	
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
	public int updatePWEPass(String newPass, String domain, int userID) throws SQLException {
		sql = "SELECT p_id FROM passwordentry WHERE p_domainname=? AND p_userID=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domain, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		int id = rs.getInt("p_id");
		rs.close();
		dat.clearStatement();
		sql = "UPDATE passwordentry SET p_domainpassword=? WHERE p_id=? AND p_userid=?;";
		dat.prepStmt(sql);
		//dat.bindStringStmt(newPass)
		return 0;
	}
	public int updatePWEDName(String newDomain, String domain, int userID) throws SQLException {
		sql = "SELECT p_id FROM passwordentry WHERE p_domainname=? AND p_userID=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domain, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		int id = rs.getInt("p_id");
		rs.close();
		dat.clearStatement();
		sql = "UPDATE passwordentry SET p_domainname=? WHERE p_id=? AND p_userid=?;";
		return 1;
	}
	public int updatePWEUName(String newName, String domain, int userID) {
		sql = "UPDATE passwordentry SET p_domainusername=? WHERE p_id=? AND p_userid=?;";
		return 1;
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
		dat.bindStringStmt(username, 2);
		dat.executeUpdateSQL();
		dat.clearStatement();
		return userID;
	}
	public int insertPWEntry(String domainname, int userID, String username, String passw, int encryptStatus, String encryptionKey1, String encryptionKey2) throws SQLException {
		// Generate ID by count of all existing entry for the user;
		// EncryptionKey1 is standard encryption
		// EncryptionKey2 is user input additional encryption
		int count, temp, i, id;
		sql = "SELECT count(*) AS ct FROM passwordentry WHERE p_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		count = rs.getInt("ct");
		rs.close();
		dat.clearStatement();
		sql = "SELECT p_id FROM passwordentry WHERE p_userid=? ORDER BY p_id ASC;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		rs = dat.executeSQL();
		temp = -1;
		i = 1;
		while ( i <= count && rs.next()) {
			temp = rs.getInt("p_id");
			//System.out.println("i is " + i +"\ntemp is " + temp);
			if (i != temp) {
				break;
			}
			++i;
		}
		//System.out.println("count " + count + "\ntemp " + temp +"\ni "+i);
		if (i == count + 1) {
			//System.out.println("true");
			id = i;
		} else {
			//System.out.println("false");
			id = i;
		}
		//System.out.println("count is " + count);
		//System.out.println("id is " + id);
		if (encryptStatus == 1) {			// two layer encryption
			sql = "INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptstatus) "
			+ "VALUES (?, ?, ?, ?, ?, 1);";
			String cipher = aes.encrypt(aes.encrypt(passw, encryptionKey1), encryptionKey2);
			dat.prepStmt(sql);
			dat.bindIntStmt(id, 1);
			dat.bindStringStmt(domainname, 2);
			dat.bindIntStmt(userID, 3);
			dat.bindStringStmt(username, 4);
			dat.bindStringStmt(cipher, 5);
			int result = dat.executeUpdateSQL();
			dat.clearStatement();
			return result;
		} else {							// one layer encryption
			sql = "INSERT INTO passwordentry (p_id, p_domainname, p_userid, p_domainusername, p_domainpassword, p_encryptstatus) "
			+ "VALUES (?, ?, ?, ?, ?, 0);";
			String cipher = aes.encrypt(passw, encryptionKey1);
			dat.prepStmt(sql);
			dat.bindIntStmt(id, 1);
			dat.bindStringStmt(domainname, 2);
			dat.bindIntStmt(userID, 3);
			dat.bindStringStmt(username, 4);
			dat.bindStringStmt(cipher, 5);
			int result = dat.executeUpdateSQL();
			dat.clearStatement();
			return result;
		}

	}
	
	public int deletePWEntry(String domainname, int userID) {
		sql = "DELETE FROM passwordentry WHERE p_domainname=? AND p_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(domainname, 1);
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
	
	public int deleteNote(String title, int userID) {
		sql = "DELETE FROM notes WHERE n_title=? AND n_userid=?;";
		dat.prepStmt(sql);
		dat.bindStringStmt(title, 1);
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
		sql = "SELECT w_encryptstatus FROM wallet WHERE w_id =? AND w_userid = ?";
		dat.prepStmt(sql);
		dat.bindIntStmt(walletID, 1);
		dat.bindIntStmt(userID, 2);
		ResultSet rs = dat.executeSQL();
		int encryptstat = -1;
		if(rs.next()) {
		encryptstat = rs.getInt("w_encryptstatus");
		}
		rs.close();
		dat.clearStatement();
		if(encryptstat == 1)
		{
			sql = "SELECT w_bankname, w_cardtype, w_nameoncard, w_expirationdate, w_billingaddress, w_cardnum, w_securitycode FROM wallet WHERE w_id =? AND w_userid = ?";
			dat.prepStmt(sql);
			dat.bindIntStmt(walletID, 1);
			dat.bindIntStmt(userID, 2);
			rs = dat.executeSQL();
			dat.clearStatement();
			String bank = rs.getString("w_bankname");
			String cardType = rs.getString("w_cardtype");
			String nameOnCard = rs.getString("w_nameoncard");
			String expDate = rs.getString("w_expirationdate");
			String billAddress = rs.getString("w_billingaddress");
			String cardNum = rs.getString("w_cardnum");
			String secCode = rs.getString("w_securitycode");
			rs.close();
			System.out.println("Bank: " + bank);
			System.out.println("Card type: " + cardType);
			System.out.println("Name on Card: " + nameOnCard);
			System.out.println("Expiration Date: " + expDate);
			System.out.println("Billing Address: " + billAddress);
			System.out.println("Card Number: " + cardNum);
			System.out.println("Security Code: " + secCode);
			
			rs.close();
		}
		else if (encryptstat == 0) {
			sql = "SELECT w_bankname, w_cardtype, w_nameoncard, w_expirationdate, w_billingaddress, w_cardnum, w_securitycode FROM wallet WHERE w_id =? AND w_userid = ?";
			dat.prepStmt(sql);
			dat.bindIntStmt(walletID, 1);
			dat.bindIntStmt(userID, 2);
			rs = dat.executeSQL();
			dat.clearStatement();
			
			String bank = rs.getString("w_bankname");
			String cardType = rs.getString("w_cardtype");
			String nameOnCard = rs.getString("w_nameoncard");
			String expDate = rs.getString("w_expirationdate");
			String billAddress = rs.getString("w_billingaddress");
			String cardNum = rs.getString("w_cardnum");
			String secCode = rs.getString("w_securitycode");
			rs.close();
			System.out.println("Bank: " + bank);
			System.out.println("Card type: " + cardType);
			System.out.println("Name on Card: " + nameOnCard);
			System.out.println("Expiration Date: " + expDate);
			System.out.println("Billing Address: " + billAddress);
			System.out.println("Card Number: " + cardNum);
			System.out.println("Security Code: " + secCode);
			
			rs.close();
		}
	
		
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
	public int insertNoteEntry(int userID, String noteContent, String noteTitle) throws SQLException {
		int count, temp, i, id;
		sql = "SELECT count(*) AS ct FROM notes WHERE n_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		count = rs.getInt("ct");
		rs.close();
		dat.clearStatement();
		sql = "SELECT n_id FROM notes WHERE n_userid=? ORDER BY n_id ASC;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		rs = dat.executeSQL();
		temp = -1;
		i = 1;
		
		while ( i <= count && rs.next()) {
			temp = rs.getInt("n_id");
			if (i != temp) {
				break;
			}
			++i;
		}
		if (i == count + 1) {
			id = i;
		} else {
			id = i;
		}
		sql = "INSERT INTO notes (n_id, n_entry, n_userid, n_title, n_encryptstatus) VALUES (?, ?, ?, ?, 0);";
		dat.prepStmt(sql);
		dat.bindIntStmt(id, 1);
		dat.bindStringStmt(noteContent, 2);
		dat.bindIntStmt(userID, 3);
		dat.bindStringStmt(noteTitle, 4);
		int result = dat.executeUpdateSQL();
		return result;
	}


	//NOT SURE IF THIS IS CORRECT EITHER // num 8
	public int insertWalletEntry(int userID, String bankName, String cardType, String cardNum, String nameOnCard, String billAddress, int secCode, String expDate, int encryptStatus, String encryptionKey1, String encryptionKey2) throws SQLException {
		int count, temp, i, id;
		sql = "SELECT count(*) AS ct FROM wallet WHERE w_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		count = rs.getInt("ct");
		rs.close();
		dat.clearStatement();
		sql = "SELECT w_id FROM wallet WHERE w_userid=? ORDER BY w_id ASC;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		rs = dat.executeSQL();
		temp = -1;
		i = 1;
		while ( i <= count && rs.next()) {
			temp = rs.getInt("w_id");
			//System.out.println("i is " + i +"\ntemp is " + temp);
			if (i != temp) {
				break;
			}
			++i;
		}
		//System.out.println("count " + count + "\ntemp " + temp +"\ni "+i);
		if (i == count + 1) {
			//System.out.println("true");
			id = i;
		} else {
			//System.out.println("false");
			id = i;
		}
		sql = "INSERT INTO wallet (w_bankname, w_cardtype, w_cardnum, w_nameoncard, w_billingaddress, w_securitycode, w_expirationdate, w_userid, w_id, w_encryptstatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
		dat.prepStmt(sql);
			
		dat.bindStringStmt(bankName, 1);
		dat.bindStringStmt(cardType, 2);
		dat.bindStringStmt(cardNum, 3);
		dat.bindStringStmt(nameOnCard, 4);
		dat.bindStringStmt(billAddress, 5);
		dat.bindIntStmt(secCode, 6);
		dat.bindStringStmt(expDate, 7);
		dat.bindIntStmt(userID, 8);
		dat.bindIntStmt(id, 9);
		dat.bindIntStmt(encryptStatus, 10);
		int result = dat.executeUpdateSQL();
		return result;
		
	}
	
	public int howManyWallets(int userID) throws SQLException {
		sql = "SELECT count(*) as count FROM wallet WHERE w_userid=?;";
		dat.prepStmt(sql);
		dat.bindIntStmt(userID, 1);
		ResultSet rs = dat.executeSQL();
		int result = rs.getInt("count");
		
		return result;
	}

	
	public void close() {
		dat.closeConnection();
	}
}
