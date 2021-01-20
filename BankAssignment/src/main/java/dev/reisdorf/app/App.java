package dev.reisdorf.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dev.reisdorf.entities.Account;
import dev.reisdorf.entities.User;
import dev.reisdorf.services.AccountService;
import dev.reisdorf.services.AccountServiceImpl;
import dev.reisdorf.services.TransactionService;
import dev.reisdorf.services.TransactionServiceImpl;
import dev.reisdorf.services.UserService;
import dev.reisdorf.services.UserServiceImpl;

public class App {
	
	private static Scanner scanner = new Scanner(System.in);
	private static UserService userServ = new UserServiceImpl();
	private static AccountService acctServ = new AccountServiceImpl();
	private static TransactionService tranServ= new TransactionServiceImpl();
	
	public static void main(String[] args) {
		
		User currUser = null;
		int choice;
		System.out.println("Welcome to 'HelloWorld' bank's app");
		System.out.println("*HelloWorld Bank is legally required to say we are not insured by the FDIC\n");
		
		
		while (currUser == null) {
			
			System.out.println("Choose an option\n1) to login \n2) to register a new user \n0) to exit the program");
			choice = scanner.nextInt();
			
			if(choice == 0) {
				break;
			} else if (choice == 1) {
				System.out.println("Please enter your username");
				String username = scanner.next();
				
				System.out.println("Please enter your password");
				scanner.nextLine();
				String password = scanner.nextLine();
				
				currUser = userServ.login(username, password);
			} else if (choice == 2) {
				System.out.println("Please enter your username");
				scanner.nextLine();
				String username = scanner.nextLine();
				while (!userServ.usernameAvailability(username)) {
					System.out.println("Enter your desired username");
					username = scanner.nextLine();
				}
				
				System.out.println("Please enter your password");
				String password = scanner.nextLine();
				
				System.out.println("Please enter your first name");
				String fname = scanner.nextLine();
				
				System.out.println("Please enter your last name");
				String lname = scanner.nextLine();
				
				currUser = userServ.registerUser(username, password, fname, lname);
				
			} else {
				System.out.println("Invalid choice, please enter a valid number");
			}
			
			while (currUser != null) {
				System.out.println("Select an option from the menu");
				System.out.println("1) Create an account");
				System.out.println("2) Delete an account");
				System.out.println("3) View account");
				System.out.println("4) View an account's transactions");
				System.out.println("5) Make a deposit");
				System.out.println("6) Make a withdrawl");
				System.out.println("7) Make a transfer between accounts");
				if(currUser.isSuper()) {
					System.out.println("Admin options");
					System.out.println("8) View users");
					System.out.println("9) Create User");
					System.out.println("10) Update User");
					System.out.println("11) Delete User");
				}
				System.out.println("0) logout");
				
				
				choice = scanner.nextInt();
				
				if (choice == 1) {
					createAccount(currUser);
				} else if (choice == 2) {
					deleteAccount(currUser);
				} else if (choice == 3) {
					viewAccount(currUser);
				} else if (choice == 4) {
					viewTransactions(currUser);
				} else if (choice == 5) {
					depositTo(currUser);
				} else if (choice == 6) {
					withdrawFrom(currUser);
				} else if (choice == 7) {
					transferBetween(currUser);
				} else if (choice == 8 && currUser.isSuper()) {
					viewUser();
				} else if (choice == 9 && currUser.isSuper() ) {
					createUser();
				} else if (choice == 10 && currUser.isSuper()) {
					updateUser();
				} else if (choice == 11 && currUser.isSuper()) {
					deleteUser(currUser); // not deleting the currUser, using it for deletion validation
				} else if (choice == 0) {
					currUser = null;
				} else {
					System.out.println("invalid menu option, please try again");
				}	
			} // end of user session loop
	
		}

	}
	
	
	
	/***HELPER METHODS FOR THE MENU OPTIONS TO CLEAN UP MENU CODE***/
	// 1)
	private static Account createAccount(User user) {
		System.out.println("Enter the new account's name");
		scanner.nextLine();
		String acctName = scanner.nextLine();
//		while (acctName.indexOf(" ") != -1) {
//			System.out.println("Spaces are not allowed in usernames, please try another name");
//			acctName = scanner.nextLine();
//		}
		Account acct = acctServ.createAcct(user.getUserID(), acctName);
		if (acct == null) { //check for account creation
			return null;
		}
		System.out.println("Enter the amount you wish to deposit for the new accounts initial balance or enter 0 for none.");
		double initBalance = scanner.nextDouble();
		
		acctServ.deposit(acct.getAcctID(), user.getUserID(), initBalance);
		tranServ.createTransaction(acct.getAcctID(), initBalance);
		acctServ.viewAcct(acctName, user.getUserID());
		return acct;
	}
	
	// 2)
	private static boolean deleteAccount(User user) {
		System.out.println("Enter the account ID or account Name of the account you wish to delete");
		if (scanner.hasNextInt()) {
			int acctID = scanner.nextInt();
		
		// TODO: Add a userID field to ensure its the users acct
			return acctServ.deleteAcct(acctID, user.getUserID());
		} else {
			scanner.nextLine();
			String acctName = scanner.nextLine();
			return acctServ.deleteAcct(acctName, user.getUserID()); // TODO create deleteAcctbyName service
		}
		
	}
	
	// 3)
	private static boolean viewAccount(User user) {
		System.out.println("Enter the account ID or the account name of the account you wish to view, or enter 'all' to view all accounts");
		if (scanner.hasNextInt()) {
			int acctChoice = scanner.nextInt();
			acctServ.viewAcct(acctChoice, user.getUserID());
			return true;
		} else {
			scanner.nextLine();
			String acctChoice = scanner.nextLine();
			
			if (acctChoice.toUpperCase().equals("ALL") ) {
				// TODO: only the users accts
				//acctServ.viewAllAccts();
				acctServ.viewUserAccts(user);
				return true;
			} else {
				acctServ.viewAcct(acctChoice, user.getUserID());
				return true;
			}
			
		}
	}
	
	// 4)
	private static boolean viewTransactions(User user) {
		System.out.println("Enter the account ID of the account's transactions you wish to view");
		if (scanner.hasNextInt()) {
			int acctChoice = scanner.nextInt();
			Account acct = acctServ.getAcct(acctChoice, user.getUserID());
			if (acct == null) {
				return false;
			}
			tranServ.viewAcctTransactions(acctChoice);
			return true;
		}
		return false;
		
	}
	
	// 5)
	private static double depositTo(User user) {
		Account acct;
		System.out.println("Enter the account ID or account name of the account you wish to deposit into");
		if (scanner.hasNextInt()) {
			int acctChoice = scanner.nextInt();
			acct = acctServ.viewAcct(acctChoice, user.getUserID());
			System.out.println("Enter the amount you wish to deposit");
			if (scanner.hasNextDouble()) {
				double amt = scanner.nextDouble();
				acctServ.deposit(acctChoice, user.getUserID(), amt);
				tranServ.createTransaction(acct.getAcctID(), amt);
				return amt;
			} else {
				System.out.println("Invalid amount entered");
				return 0;
			}
		} else {
			scanner.nextLine();
			String acctChoice = scanner.nextLine();
			
			acct = acctServ.viewAcct(acctChoice, user.getUserID());
			if (acct == null) {
				return 0;
			}
			System.out.println("Enter the amount you wish to deposit");
			if (scanner.hasNextDouble()) {
				double amt = scanner.nextDouble();
				acctServ.deposit(acct.getAcctID(), user.getUserID(), amt);
				tranServ.createTransaction(acct.getAcctID(), amt);
				return amt;
			} else {
				System.out.println("Invalid amount entered");
				return 0;
			}
			
		}
	}
	
	// 6)
	private static double withdrawFrom(User user) {
		System.out.println("Enter the account ID or name of the account you wish to withdraw from");
		Account acct;
		if (scanner.hasNextInt()) {
			int acctChoice = scanner.nextInt();
			acct = acctServ.viewAcct(acctChoice, user.getUserID());
			System.out.println("Enter the amount you wish to withdraw");
			if (scanner.hasNextDouble()) {
				double amt = scanner.nextDouble();
				acctServ.withdraw(acctChoice, user.getUserID(), amt);
				tranServ.createTransaction(acct.getAcctID(), -amt);
				return amt;
			} else {
				System.out.println("Invalid amount entered");
				return 0;
			}
		} else {
			scanner.nextLine();
			String acctChoice = scanner.nextLine();
			
			acct = acctServ.viewAcct(acctChoice, user.getUserID());
			
			System.out.println("Enter the amount you wish to withdraw");
			if (scanner.hasNextDouble()) {
				double amt = scanner.nextDouble();
				acctServ.withdraw(acct.getAcctID(), user.getUserID(), amt);
				tranServ.createTransaction(acct.getAcctID(), -amt);
				return amt;
			} else {
				System.out.println("Invalid amount entered");
				return 0;
			}
			
		}
	}
	
	// 7)
	private static boolean transferBetween(User user) {
		System.out.println("Enter the account ID of the account you wish to transfer money from");
		try {	
			int fromID = scanner.nextInt();
			
			System.out.println("Enter the account ID of the account you are transferring funds too");
			int toID = scanner.nextInt();
			
			System.out.println("Transfer from account:");
			Account from = acctServ.viewAcct(fromID);
			System.out.println("To account: ");
			Account to = acctServ.viewAcct(toID);
			
			System.out.println("\nEnter an amount you wish transfered between the accounts");
			double amt = scanner.nextDouble();
			
			 if(acctServ.transfer(fromID, user.getUserID(), toID, amt)) {
				 tranServ.createTransaction(from.getAcctID(), -amt);
				 tranServ.createTransaction(to.getAcctID(), amt);
				 return true;
			 } else {
				 return false;
			 }
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Make sure you are entering valid IDs and amounts to withdraw and deposit");
			return false;
		}
	}
	
	// 8)
	private static void viewUser() {
		   System.out.println("Please enter the username or ID of the user youd like to view or enter 'all' to view all users");
		   if (scanner.hasNextInt()) {
				int userID = scanner.nextInt();
				userServ.viewUser(userID);
				return;
			} else {
				String userStr = scanner.next();
				if (userStr.toUpperCase().equals("ALL")) {
					userServ.viewAllUsers();
					return;
				}
				userServ.viewUser(userStr);
				return;
			}
		   
	}
	
	// 9)
	private static User createUser() {
		String username;
		while (true) { // get and check username availability
			System.out.println("Enter the username");
			username = scanner.next();
			if (userServ.usernameAvailability(username)) {
				break;
			} else {
				System.out.println("Username already in use, please choose another username");
			}
		}
		
		System.out.println("Enter the password");
		String password = scanner.next();
		
		System.out.println("Enter the First name");
		String fname = scanner.next();
		
		System.out.println("Enter the last name");
		String lname = scanner.next();
		
		System.out.println("Is this going to be a SuperUser?");
		System.out.println("1) Yes");
		System.out.println("2) No");
		int superChoice = scanner.nextInt();
		boolean isSuper;
		while (true) {
			if (superChoice == 1) {
				isSuper = true;
				break;
			} else if (superChoice == 2) {
				isSuper = false;
				break;
			} else {
				System.out.println("Please enter a valid option");
			}
		}
		
		User newUser = userServ.registerUser(username, password, fname, lname, isSuper);
		
		// Now give the option to create some accounts for the new user
		System.out.println("Would you like to create a new account for the user?");
		System.out.println("1) Yes");
		System.out.println("2) No");
		
		int makeAcct = scanner.nextInt();
		while(makeAcct == 1) {
			createAccount(newUser);
			System.out.println("Would you like to create another account?");
			System.out.println("1) Yes");
			System.out.println("2) No");
			makeAcct = scanner.nextInt();
		}
		
		return newUser;
		
	}
	
	// 10)
	private static User updateUser() {
		
		System.out.println("Enter the ID of user you would like to update");
		if (scanner.hasNextInt()) {
				int id = scanner.nextInt();
				
				User user = userServ.viewUser(id);
				
				System.out.println("Enter the desired username");
				String username = scanner.next();
				
				while (true) { // get and check username availability
					
					if (username.equals(user.getUsername()) || userServ.usernameAvailability(username)) {
						user.setUsername(username);
						break;
					}
					System.out.println("Enter the desired username");
					username = scanner.next();
			}
			
			System.out.println("Enter the desired password");
			user.setPassword(scanner.next());
			
			System.out.println("Enter the First name");
			user.setFname(scanner.next());
			
			System.out.println("Enter the last name");
			user.setLname(scanner.next());
			
			if (!user.isSuper()) {
				System.out.println("Want to upgrade user to superUser?");
				System.out.println("1) Yes");
				System.out.println("2) No");
				int superChoice = scanner.nextInt();
				while (true) {
					if (superChoice == 1) {
						user.setSuper(true);
						break;
					} else if (superChoice == 2) {
						user.setSuper(false);
						break;
					} else {
						System.out.println("Please enter a valid option");
					}
				}
			}
			
			User updatedUser = userServ.updateUser(user);
			return updatedUser;
		} else {
			System.out.println("not a valid entry, please enter a valid user ID");
			return null;
		}
	}
	
	// 11) delete a user
	private static boolean deleteUser(User user) {
		
		System.out.println("Enter the ID or username of the user you wish to delete");
		if (scanner.hasNextInt()) {
			double total = 0;
			int deleteID = scanner.nextInt();
			if (user.getUserID() == deleteID) {
				System.out.println("SuperUser may not delete self");
				return false;
			}
			User delUser = userServ.viewUser(deleteID);
			if (delUser == null) {
				System.out.println("That user does not exist");
				return false;
			}
			List<Account> accts = acctServ.viewUserAccts(delUser);
			for (Account a : accts) {
				total += acctServ.withdrawAll(a.getAcctID(),a.getUserID());
			}
			System.out.println("Total amount withdrawn from accounts: $" + total);
			return userServ.deleteUser(deleteID, user);
			
		} else {
			double total =0;
			String deleteUsername = scanner.next();
			if (user.getUsername().equals(deleteUsername)) {
				System.out.println("SuperUser may not delete self");
				return false;
			}
			User delUser = userServ.viewUser(deleteUsername);
			if (delUser == null) {
				System.out.println("That user does not exist");
				return false;
			}
			List<Account> accts = acctServ.viewUserAccts(delUser);
			for (Account a : accts) {
				total += acctServ.withdrawAll(a.getAcctID(),a.getUserID());
			}
			System.out.println("Total amount withdrawn from accounts: $" + total);
			return userServ.deleteUser(delUser.getUserID(), user);
		}
		
	}

}
