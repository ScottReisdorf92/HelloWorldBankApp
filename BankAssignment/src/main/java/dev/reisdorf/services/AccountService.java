package dev.reisdorf.services;

import java.util.List;

import dev.reisdorf.entities.Account;
import dev.reisdorf.entities.User;

public interface AccountService {

	Account createAcct(int userID, String name);
	
	Account getAcct(int acctID, int userID);
	
	double deposit(int acctID, int userID, double amt);
	
	Account deposit(int acctID, double amt);
	
	double withdraw(int accountID, int userID, double amt);
	
	Double withdrawAll(int accountID, int userID);
	
	boolean transfer(int fromAcctID, int fromUserID, int toAcctID, double amt);
	
	boolean deleteAcct(int acctID);
	
	boolean deleteAcct(int acctID, int userID);
	
	boolean deleteAcct(String acctName, int userID);
	
	Account viewAcct(int acctID);
	
	Account viewAcct(int acctID, int userID);
	
	Account viewAcct(String acctName, int userID);
	
	List<Account> viewAllAccts();
	
	List<Account> viewUserAccts(User user);
	
	
}
