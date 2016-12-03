package Interface;
import database_Connection.*;
import java.util.*;
// Command Line User Interface
public class commandLine {

	static database Dat = new database();
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		startScreen();
	}
	
	public static void startScreen() {
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
	
	public static void newMember() {
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
	
	public static void createAccount() {
		
	}
	public static void existingMember() {
		String username, password;
		System.out.println("Please input in your username and password.");
		System.out.print(  "Username: ");
		username = sc.nextLine();
		System.out.print(  "Password: ");
		password = sc.nextLine();
		
		// Authentication
	}
	

}
