package Interface;
import database_Connection.*;
import AES.*;

import java.sql.SQLException;
import java.util.*;
// Command Line User Interface
public class commandLine {

	static database dat = new database();
	static Scanner sc = new Scanner(System.in);
	static AES aes = new AES();
	static int userID;
	
	public commandLine() {
		
	}
	public static void main(String[] args) throws SQLException {
		startScreen();
	}
	
	public static void startScreen() throws SQLException {
		// Start Screen
		int result = 0;
		do {
			System.out.println("*******************************************");
			System.out.println("*    Hang-Fatima Password Manager 1.0     *");
			System.out.println("* Please choose the following options:    *");
			System.out.println("* 1. Existing Members                     *");
			System.out.println("* 2. New Members                          *");
			System.out.println("* 3. Exit                                 *");
			System.out.println("*******************************************");
			result = sc.nextInt();
			sc.nextLine();
			if (result == 1 || result == 2) {
				if (result == 1)
					existingMember();
				else
					newMember();
			} else if (result != 3) {
				System.out.println("Please input in a valid number.");
			}
		} while (result != 3);
	}
	
	public static void newMember() throws SQLException {
		int input = 0;
		do {
			System.out.println("*******************************************");
			System.out.println("* Do you wish to create a new account?    *");
			System.out.println("* 1. Yes                                  *");
			System.out.println("* 2. No                                   *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			sc.nextLine();
			if (input == 1) {
				createAccount();
				break;
			}
			else {
				System.out.println("Please input in a valid number.");
			}
		} while (input != 2);
	}
	
	public static void createAccount() throws SQLException {
		String username, password;
		System.out.println("Please fill out the following information:");
		System.out.print("Username: ");
		username = sc.nextLine();
		System.out.print("Password: ");
		password = sc.nextLine();
		int ID = dat.insertUser(username);
		String cipher = aes.encrypt(password, ID + username + ID);
		dat.updateUserPassword(cipher, ID);
	}
	public static void existingMember() throws SQLException {
		String username, password;
		System.out.println("Please input in your username and password.");
		System.out.print(  "Username: ");
		username = sc.nextLine();
		System.out.print(  "Password: ");
		password = sc.nextLine();
		
		// Authentication
		int userID = dat.getUserID(username);
		String key = userID + username + userID;
		String cipher = dat.getUserPassword(username);
		String x = aes.decrypt(cipher, key);
		
		if (x.equals(password) == false) {
			System.out.println("Authenication fail");
		}
		else {
			mainScreen(userID);
		}
	}
	public static void mainScreen(int userID) {
		int input = 0;
		do{
			System.out.println("*******************************************");
			System.out.println("* Please choose the following options:    *");
			System.out.println("* 1. View Domain Entries                *");
			System.out.println("* 2. View Wallet Entries                  *");
			System.out.println("* 3. View Note Entries                    *");
			System.out.println("* 4. View Account Info                    *");
			System.out.println("* 5. Exit                                 *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			if(input == 1 || input == 2 || input == 3 || input == 4)
				if(input == 1){ //display passwords
					startDomain(userID);
				}
				if(input == 2){ //display wallet entries
					startWallet(userID);
				} 
				if(input == 3){ //display note entries
					startNote(userID);
				}
				if(input == 4){//display account info
					startAccount(userID);
				}
				else if (input != 5) {
					System.out.println("Please input in a valid number.");
			}
		} while (input != 5);
		
		
	}
	

}
