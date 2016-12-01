package database_Connection;
//import java.sql.ResultSet;

import java.sql.Date;
import java.text.SimpleDateFormat;

import database_Connection.DBConnection;

//import java.sql.*;

public class generateData {
	DBConnection dat;
	String sql;
	static final String path = "jdbc:sqlite:Test/pwMan.db";			// Need to figure out how path works for eclipse and java
	/*
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
		//String sql2 = "INSERT INTO notes (n_id, n_entry, n_userid) VALUES (?, ?, ?);";
		dat.prepStmt(sql);
		for (int i = 1; i <= 250; ++i) {
			String entry = "This is test note entry #" + (i);
			String cipher = "abcdefg" + i;
			dat.clearStatement();
			dat.bindIntStmt(i,1);
			dat.bindStringStmt(entry, 2);
			dat.bindIntStmt(i, 3);
			dat.bindIntStmt(i, 4);
			dat.bindStringStmt(cipher, 5);
		}
		dat.closeStatement();
		sql = "INSERT INTO notes (n_id, n_entry, n_userid) VALUES (?, ?, ?);";
		dat.prepStmt(sql);
		for (int i = 1; i <= 250; ++i) {
			String entry = "This is test note entry #" + (i + 250);
			dat.clearStatement();
			dat.bindIntStmt(i + 250,1);
			dat.bindStringStmt(entry, 2);
			dat.bindIntStmt(i + 250, 3);
		}
	}
	
	public void generateTwallet() {
		sql = "INSERT INTO wallet (w_bankname, w_cardtype, w_cardnum, w_nameoncard, w_billingaddress, w_securitycode, " 
				+ "w_expirationdate, w_userid, w_id) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,?);";
		dat.prepStmt(sql);
		for (int i = 1; i <= 250; ++i) {
			String bname = "abcd" + i;
			int ctype = i % 4;
			String cnum;
			if (i < 10)
				cnum = "123412341234000" + i;
			else if (i < 100)
				cnum = "12341234123400" + i;
			else 
				cnum = "1234123412340" + i;
			String name = "abcdefghijk" + i;
			String address = "abcd" + (i%3) + " efgh" + (i%4) + " ijkl" + (i%5);
			String secCode = ((i*i*i)%10) + "5" + ((i*i*i*i*i)%10);
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
			//java.sql.Date expDate = new Date(sdf1.parse("00-11-2018").getTime());
			int userid = i;
			int w_id = i;
			
			dat.clearStatement();
			dat.bindStringStmt(bname, 1);
			dat.bindIntStmt(ctype, 2);
			dat.bindStringStmt( cnum, 3);
			dat.bindStringStmt(name, 4);
			dat.bindStringStmt(address, 5);
			dat.bindStringStmt(secCode, 6);
			dat.bindDateStmt(expDate, 7);
			dat.bindIntStmt(userid, 8);
			dat.bindIntStmt(w_id, 9);
		}
		dat.clearStatement();
		
		sql = "INSERT INTO wallet (w_bankname, w_cardtype, w_cardnum, w_nameoncard, w_billingaddress, w_securitycode, " 
				+ "w_expirationdate, w_userid, w_id, w_encryptedid, ) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,?, ?, ?);";
	}
	
	 public void close() {
		 dat.close();
	 }
	 */
	
}
