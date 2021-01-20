package dev.reisdorf.services;

import java.util.List;

import dev.reisdorf.dao.AccountDAO;
import dev.reisdorf.dao.AccountDAOImpl;
import dev.reisdorf.entities.Account;
import dev.reisdorf.entities.User;

public class AccountServiceImpl implements AccountService {
	
	private static AccountDAO adao = new AccountDAOImpl();

	public Account createAcct(int userID, String name) {
		Account acct = new Account(userID, name);
		List<Account> userAccounts = adao.getUsersAccts(userID);
		for (Account a : userAccounts) {
			if (a.getName().equals(name)) {
				System.out.println("You already have an account with that name");
				return null;
			}
		}
		return adao.createAccount(acct);
	}
	
	public Account getAcct(int acctID, int userID) {
		Account acct = adao.getAccountByID(acctID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that ID");
			return null;
		}
		return acct;
	}
	
	public Account deposit(int acctID, double amt) {
		Account acct = adao.getAccountByID(acctID);
		if (amt < 0) {
			System.out.println("Amount to deposit must be positive!");
			return acct;
		}
		acct.setBalance((acct.getBalance() + amt));
		adao.updateAccount(acct);
		System.out.println("Success!");
		System.out.println("New balance of $" + acct.getBalance());
		return acct;
	}

	public double deposit(int acctID, int userID, double amt) {
		Account acct = adao.getAccountByID(acctID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that ID");
			return 0;
		}
		if (amt < 0) {
			System.out.println("Amount to deposit must be positive!");
			return 0;
		}
		acct.setBalance((acct.getBalance() + amt));
		adao.updateAccount(acct);
		System.out.println("Success!");
		System.out.println("New balance of $" + acct.getBalance());
		return acct.getBalance();
	}

	public double withdraw(int acctID, int userID, double amt) {
		Account acct = adao.getAccountByID(acctID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that ID");
			return 0;
		}
		double balance = acct.getBalance();
		if (amt < 0) {
			System.out.println("Amount to withdraw must be positive!");
			return 0;
		} else if (balance < amt) {
			System.out.println("Withdrawl failed due to insufficient funds!");
			return 0;
		}
		acct.setBalance(balance - amt);
		adao.updateAccount(acct);
		System.out.println("Success!");
		System.out.println("New balance of $" + acct.getBalance());
		return acct.getBalance();
	}
	
	public Double withdrawAll(int acctID, int userID) {
		Account acct = adao.getAccountByID(acctID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that ID");
			return null;
		}
		double amt = acct.getBalance() ;
		acct.setBalance(0);
		adao.updateAccount(acct);
		System.out.println("Success!");
		System.out.println("$" + amt + " has been withdrawn. Account is now empty");
		return amt;
	}
	
	public boolean transfer(int fromAcctID, int fromUserID, int toAcctID, double amt) {
		Account fromAcct = adao.getAccountByID(fromAcctID);
		if (fromAcct == null || fromAcct.getUserID() != fromUserID) {
			System.out.println("You have no account with that ID");
			return false;
		}
		Account toAcct = adao.getAccountByID(toAcctID);
		if (amt < 0) {
			System.out.println("Amount to transfer must be positive!");
			return false;
		} else if (fromAcct.getBalance() < amt) {  // Check funds
			System.out.println("Insufficient funds!");
			return false;
		} else if(toAcct == null) {
			System.out.println("The account you wish to transfer to does not exist");
			return false;
		}else {  // complete transaction
			fromAcct.setBalance(fromAcct.getBalance() - amt);
			toAcct.setBalance(toAcct.getBalance() + amt);
			adao.updateAccount(fromAcct);
			adao.updateAccount(toAcct);
			return true;
		}
	}
	
	public boolean deleteAcct(String acctName, int userID) {
		Account acct = adao.getAccountByName(acctName, userID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that name");
			return false;
		} else if (acct.getBalance() != 0) {
			System.out.println("Please withdraw all money before deleting the account");
			return false;
		}
		
		
		if (adao.deleteAccount(acct.getAcctID())) {
			System.out.println("Succesfully Deleted");
			return true;
		} else {
			System.out.println("Deletion failed");
			return false;
		}
	};

	public boolean deleteAcct(int acctID, int userID) {
		Account acct = adao.getAccountByID(acctID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that ID");
			return false;
		} else if (acct.getBalance() != 0) {
			System.out.println("Please withdraw all money before deleting the account");
			return false;
		}
		
		
		if (adao.deleteAccount(acctID)) {
			System.out.println("Succesfully Deleted");
			return true;
		} else {
			System.out.println("Deletion failed");
			return false;
		}
	}
	
	public boolean deleteAcct(int acctID) {
		Account acct = adao.getAccountByID(acctID);
		if (acct == null) {
			System.out.println("No account with that ID exists");
			return false;
		}
		if (adao.deleteAccount(acctID)) {
			System.out.println("Succesfully Deleted");
			return true;
		} else {
			System.out.println("Deletion failed");
			return false;
		}
	}
	
	public Account viewAcct(int acctID) {
		Account acct = adao.getAccountByID(acctID);
		
		if (acct == null) {
			System.out.println("Could not find the account");
		} else {
			System.out.println("-----Account Info--------------------------------------------------");
			System.out.println("Account ID: " + acct.getAcctID() + " | Account Name: " + acct.getName() + " | Balance: $" + acct.getBalance());
			System.out.println("-------------------------------------------------------------------");
		}
		return acct;
	}
	
	public Account viewAcct(int acctID, int userID) {
		Account acct = adao.getAccountByID(acctID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that ID");
			return null;
		} else {
			System.out.println("-----Account Info--------------------------------------------------");
			System.out.println("Account ID: " + acct.getAcctID() + " | Account Name: " + acct.getName() + " | Balance: $" + acct.getBalance());
			System.out.println("-------------------------------------------------------------------");
		}
		return acct;
	}
	
	public Account viewAcct(String acctName, int userID) {
		Account acct = adao.getAccountByName(acctName, userID);
		if (acct == null || acct.getUserID() != userID) {
			System.out.println("You have no account with that name");
			return null;
		} else {
			System.out.println("-----Account Info--------------------------------------------------");
			System.out.println("Account ID: " + acct.getAcctID() + " | Account Name: " + acct.getName() + " | Balance: $" + acct.getBalance());
			System.out.println("-------------------------------------------------------------------");
		}
		return acct;
	}

	public List<Account> viewAllAccts() {
		
		List<Account> accts = adao.getAllAccts();
		
		if (accts != null) {
			for (Account acct : accts) {
				System.out.println("-----Account Info--------------------------------------------------");
				System.out.println("Account ID: " + acct.getAcctID() + "| Account Name: " + acct.getName() + " | Balance: $" + acct.getBalance());
				System.out.println("-------------------------------------------------------------------\n");
			}
		} else {
			System.out.println("You currently do not have any accounts");
		}
		
		return accts;
	}
	
	public List<Account> viewUserAccts(User user) {
		
		List<Account> accts = adao.getUsersAccts(user.getUserID());
		
		if (accts != null) {
			for (Account acct : accts) {
				System.out.println("-----Account Info--------------------------------------------------");
				System.out.println("Account ID: " + acct.getAcctID() + " | Account Name: " + acct.getName() + " | Balance: $" + acct.getBalance());
				System.out.println("-------------------------------------------------------------------\n");
			}
		} else {
			System.out.println("You currently do not have any accounts");
		}
		
		return accts;
	}
	

}
