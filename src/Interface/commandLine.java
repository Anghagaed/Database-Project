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
		dat.close();
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
	public static void mainScreen(int userID) throws SQLException {
		int input = 0;
		do{
			System.out.println("*******************************************");
			System.out.println("* Please choose the following options:    *");
			System.out.println("* 1. View Domain Entries                  *");
			System.out.println("* 2. View Wallet Entries                  *");
			System.out.println("* 3. View Note Entries                    *");
			System.out.println("* 4. View Account Info                    *");
			System.out.println("* 5. Exit                                 *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			if (input == 1) {
				startDomain(userID);
			} else if (input == 2) {
				//startWallet(userID);
			} else if (input == 3) {
				startNote(userID);
			} else if (input == 4) {
				//startAccount(userID);
			} else if (input != 5) {
				System.out.println("Please input in a valid number.");
			}
		} while (input != 5);
	}
	public static void startDomain(int userID) throws SQLException {
		int input; 
		do {
			System.out.println("*******************************************");
			System.out.println("* 1. View Domain Information              *");
			System.out.println("* 2. Add New Domain Information           *");
			System.out.println("* 3. Delete Domain Information            *");
			System.out.println("* 4. Exit to Main Screen                  *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			sc.nextLine();
			if (input == 1) {
				viewDomain(userID);
			} else if (input == 2) {
				addNewDomain(userID);
			} else if (input == 3) {
				deleteExistingDomain(userID);
			} else if (input != 4) {
				System.out.println("Please input in a valid number.");
			}
		} while (input != 4);
	}
	
	public static void viewDomain(int userID) throws SQLException {
		if(dat.listOfDomain(userID) == 1) {
			int input = 0;
			String input2 = "";
			do {
				System.out.println("*******************************************");
				System.out.println("* Do you want to view a specific domain   *");
				System.out.println("* 1. Yes                                  *");
				System.out.println("* 2. No                                   *");
				System.out.println("*******************************************");
				input = sc.nextInt();
				sc.nextLine();
				if (input == 1) {
					System.out.println("Please enter the domain name you wish to view: ");
					input2 = sc.nextLine();
					boolean eStatus = dat.getUserEncryptionStatus(input2, userID);
					if (eStatus == true) {
						System.out.print("This information is locked. Please enter key: ");
						String userKey = sc.nextLine();
						dat.displayDomainInformation(input2, userID, userKey);
					} else {
						dat.displayDomainInformation(input2, userID, "");
					}
					break;
					
				} else if (input != 2) {
					System.out.println("Please input in a valid number");
				}
			} while (input != 2);
		} else {
			System.out.println("There are no domain information available");
		}
	}
	
	public static void addNewDomain(int userID) throws SQLException {
		String UN = dat.getUserName(userID);
		String key = userID + UN + userID;
		System.out.print("Please input in the domain name: ");
		String domainName = sc.nextLine();
		System.out.print("Please input in the username: ");
		String username = sc.nextLine();
		System.out.print("Please input in the password: ");
		String password = sc.nextLine();
		System.out.println("Do you wish to encrypt the password? (1 for Yes, 0 for No):");
		int encryptStatus = sc.nextInt();
		sc.nextLine();
		if (encryptStatus == 1) {
			System.out.print("Please input in the encryption key: ");
			String encryptionKey = sc.nextLine();
			dat.insertPWEntry(domainName, userID, username, password, 1, key, encryptionKey);
		} else {
			dat.insertPWEntry(domainName, userID, username, password, 0, key, "");
		}
	}
	
	public static void deleteExistingDomain(int userID) throws SQLException {
		if(dat.listOfDomain(userID) == 1) {
			System.out.print("Please enter the domain name to delete: ");
			String input = sc.nextLine();
			dat.deletePWEntry(input, userID);
		}
		else {
			System.out.println("Nothing to delete");
		}
	}
	
	public static void startNote(int userID) throws SQLException {
		int input; 
		do {
			System.out.println("*******************************************");
			System.out.println("* 1. View Note Information                *");
			System.out.println("* 2. Add New Note Information             *");
			System.out.println("* 3. Delete Note Information              *");
			System.out.println("* 4. Exit to Main Screen                  *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			sc.nextLine();
			if (input == 1) {
				viewNote(userID);
			} else if (input == 2) {
				addNewNote(userID);
			} else if (input == 3) {
				deleteExistingNote(userID);
			} else if (input != 4) {
				System.out.println("Please input in a valid number.");
			}
		} while (input != 4);
	}
	
	public static void viewNote(int userID) throws SQLException {
		String UN = dat.getUserName(userID);
		String key = userID + UN + userID;
		dat.listOfNotes(userID, key);
	}
	public static void addNewNote(int userID) throws SQLException {
		String UN = dat.getUserName(userID);
		String key = userID + UN + userID;
		System.out.print("Please input in the title of the note: ");
		String title = sc.nextLine();
		System.out.print("Please input in the content of the note: ");
		String content = sc.nextLine();
		String cipher = aes.encrypt(content, key);
		dat.insertNoteEntry(userID, cipher, title);
	}
	public static void deleteExistingNote(int userID) throws SQLException {
		String UN = dat.getUserName(userID);
		String key = userID + UN + userID;
		if(dat.listOfNotes(userID, key) == 1) {
			System.out.print("Please enter the note title to delete: ");
			String input = sc.nextLine();
			dat.deleteNote(input, userID);
		}
		else {
			System.out.println("Nothing to delete");
		}
	}
}
