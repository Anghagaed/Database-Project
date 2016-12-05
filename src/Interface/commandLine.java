package Interface;
import database_Connection.*;
import AES.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
// Command Line User Interface
public class commandLine {

	static database dat = new database();
	static Scanner sc = new Scanner(System.in);
	static AES aes = new AES();
	static int userID;
	
	public commandLine() {
		
	}
	public static void main(String[] args) throws SQLException, ParseException {
		startScreen();
		dat.close();
	}
	
	public static void startScreen() throws SQLException, ParseException {
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
	public static void existingMember() throws SQLException, ParseException {
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
	public static void mainScreen(int userID) throws SQLException, ParseException {
		int input = 0;
		do{
			System.out.println("*******************************************");
			System.out.println("* Please choose the following options:    *");
			System.out.println("* 1. View Domain Entries                  *");
			System.out.println("* 2. View Wallet Entries                  *");
			System.out.println("* 3. View Note Entries                    *");
			System.out.println("* 4. Account Info                         *");
			System.out.println("* 5. Exit                                 *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			if (input == 1) {
				startDomain(userID);
			} else if (input == 2) {
				startWallet(userID);
			} else if (input == 3) {
				startNote(userID);
			} else if (input == 4) {
				startAccount(userID);
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
			System.out.println("* 4. Edit Domain Information              *");
			System.out.println("* 5. Exit to Main Screen                  *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			sc.nextLine();
			if (input == 1) {
				viewDomain(userID, 1);
			} else if (input == 2) {
				addNewDomain(userID);
			} else if (input == 3) {
				deleteExistingDomain(userID);
			} else if (input == 4) {
				editExistingDomain(userID);
			} else if (input != 5) {
				System.out.println("Please input in a valid number.");
			}
		} while (input != 5);
	}
	
	public static void editExistingDomain(int userID) throws SQLException {
		viewDomain(userID, 0);
		int input = -1;
		do {
			System.out.println("*******************************************");
			System.out.println("* Do you want to edit any existing domain?*");
			System.out.println("* 1. Yes                                  *");
			System.out.println("* 2. No                                   *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			sc.nextLine();
			if (input == 1) {
				System.out.println("Please input in the domain name you wish to edit: ");
				String domainname = sc.nextLine();
				int exist = dat.getDomainExist(domainname, userID);
				if (exist != 0) {
					int input2 = -1;
					do {
						System.out.println("*******************************************");
						System.out.println("* Please choose what to edit:             *");
						System.out.println("* 1. Domain Name                          *");
						System.out.println("* 2. Username                             *");
						System.out.println("* 3. Password                             *");
						System.out.println("* 4. Exit                                 *");
						System.out.println("*******************************************");
						input2 = sc.nextInt();
						sc.nextLine();
						if (input2 == 1) {
							
						} else if (input2 == 2) {
							
						} else if (input2 == 3) {
							
						} else if (input 2 != 4) {
							System.out.println("Please input in a valid option.");
						}
					} while (input2 != 4);
				} else {
					System.out.println("This domain does not exist in the database!");
				}
				break;
			} else if (input != 2) {
				System.out.println("Please input in a valid number.");
			}
		} while (input != 2)
	}
	
	public static void viewDomain(int userID, int which) throws SQLException {
		if(dat.listOfDomain(userID) == 1 && which == 1) {
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
			System.out.println("* 4. Edit Note Information                *");
			System.out.println("* 5. Exit to Main Screen                  *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			sc.nextLine();
			if (input == 1) {
				viewNote(userID);
			} else if (input == 2) {
				addNewNote(userID);
			} else if (input == 3) {
				deleteExistingNote(userID);
			} else if (input == 4) {
				editExistingNote(userID);
			} else if (input != 5) {
				System.out.println("Please input in a valid number.");
			}
		} while (input != 5);
	}
	public static void viewNote(int userID) throws SQLException {
		String UN = dat.getUserName(userID);
		String key = userID + UN + userID;
		dat.listOfNotes(userID, key);
	}
	public static void editExistingNote(int userID) {
		
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
	
	public static void startWallet(int userID) throws SQLException, ParseException{
		int input = 0;
		do{
			System.out.println("*******************************************");
			System.out.println("* What do you wish to do?                 *");
			System.out.println("* 1. View Wallets                         *");
			System.out.println("* 2. Insert New Wallet                    *");
			System.out.println("* 3. Delete Wallet                        *");
			System.out.println("* 4. Exit                                 *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			if(input == 1 || input == 2 || input == 3)
			{
				if(input == 1) //done but not checked
					viewWallets(userID);
				if(input == 2)
					insertWallet(userID);
				if(input == 3)
					deleteWallet(userID);
				else if (input > 4 || input <= 0)
					System.out.println("Pleas input valid number.");
				
			}
		} while (input != 4);
	}
	
	public static void viewWallets(int userID) throws SQLException {
		int i; int input = 0;
		int count = dat.howManyWallets(userID);
		System.out.println("*******************************************");
		for(i=1; i <= count; i++)
		{
			System.out.println("* Wallet " + i + "                                *");
		}
		System.out.println("*******************************************");
		input = sc.nextInt();
		if(input <= count && input > 0) { dat.displayWallet(input, userID);}
		
		else if (input <= 0)
			System.out.println("Please input valid number.");
	}

	public static void insertWallet(int userID) throws SQLException, ParseException{
		String UN = dat.getUserName(userID);
		String key = userID + UN + userID;
		int walletID = dat.howManyWallets(userID);
		walletID = walletID + 1;
		String input;
		sc.nextLine();
		
		
		System.out.println("Input Bank Name: ");
		String bankName = sc.nextLine();
		
		System.out.println("Input Card Type: ");
		String cardType = sc.nextLine();
		
		System.out.println("Input Card Number: ");
		String cardNum = sc.nextLine();
		
		System.out.println("Input Name on Card: ");
		String nameOnCard = sc.nextLine();
		
		System.out.println("Input Billing Address: ");
		String billAddress = sc.nextLine();
		
		System.out.println("Input Security Code: ");
		int secCode = sc.nextInt();
		sc.nextLine();
		System.out.println("Input Expiration Date: ");
		input = sc.nextLine();
		//get date input convert to sql date from java string
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-yyyy");
		java.util.Date date = sdf1.parse(input);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		java.sql.Date expDate = sqlDate;
		
		System.out.println("Would you like to encrypt this wallet? 1 for yes, 0 for no");
		int encryptStatus = sc.nextInt();
		sc.nextLine();
		if(encryptStatus == 1){
			System.out.println("Please input a key: ");
			String encryptionKey = sc.nextLine();
			dat.insertWalletEntry(userID, bankName, cardType, cardNum, nameOnCard, billAddress, secCode, expDate, 1, key, encryptionKey);
		}
		else {
			dat.insertWalletEntry(userID, bankName, cardType, cardNum, nameOnCard, billAddress, secCode, expDate, 0, key, "");
		}
		

		System.out.println("* New Wallet Created.                     *");
		System.out.println("*******************************************");
		
	}
	
	public static void deleteWallet(int userID) throws SQLException, ParseException {
		System.out.println("*******************************************");
		System.out.println("* Please select which wallet to delete     *");
		System.out.println("*******************************************");
		int i; int walletNo = 0;
		int count = dat.howManyWallets(userID);
		
		for(i=1; i <= count; i++)
		{
			System.out.println("*******************************************");
			System.out.println("* Wallet " + i + "                             *");
			System.out.println("*******************************************");
		}
		
		walletNo = sc.nextInt();
		sc.nextLine();
		if(walletNo <= count || walletNo > 0) { 
			dat.deleteWallet(walletNo, userID);
			
			System.out.println(" Wallet " + walletNo + " deleted.");
			//System.out.println("*******************************************");
		}
		else if(walletNo <= 0)
		{
			System.out.println("Please input a valid number.");
		}
		
	}
	public static void startAccount(int userID) throws SQLException {
		int input = 0;
		do{
			System.out.println("*******************************************");
			System.out.println("* Please choose an option:                *");
			System.out.println("* 1. Add/Update Account Info              *");
			System.out.println("* 2. View Account Info                    *");
			System.out.println("* 3. Edit Account Info                    *");
			System.out.println("* 4. Delete Account                       *");
			System.out.println("* 5. Exit                                 *");
			System.out.println("*******************************************");
			input = sc.nextInt();
			if(input == 1)
			{
				insertAccount(userID);
			}
			else if(input == 2)
			{
				displayAccount(userID);
			}
			else if(input == 3)
			{
				updateAccount(userID);
			}
			else if(input == 4)
			{
				deleteAccount(userID);
			}
			else if(input != 5)
			{
				System.out.println("Please choose a valid number.");
			}
		} while (input > 0 && input != 4);
	}
	
	public static void insertAccount(int userID) throws SQLException{
		String in;
		sc.nextLine();
		System.out.println("Enter Name: ");
		String name = sc.nextLine();
		
		System.out.println("Enter Email: ");
		String email = sc.nextLine();
		
		System.out.println("Enter State: ");
		String state = sc.nextLine();
		
		System.out.println("Enter City: ");
		String city = sc.nextLine();
		
		System.out.println("Enter Street: ");;
		String street = sc.nextLine();
		
		
		dat.insertAccount(userID, name, email, state, city, street);
		
		System.out.println("Account has been added.");
	}
	
	public static void displayAccount(int userID) throws SQLException{
		dat.displayAccountinfo(userID);
	}
	
	public static void updateAccount(int userID){
		
	}
	public static void deleteAccount(int userID){
		
	}
	
}
