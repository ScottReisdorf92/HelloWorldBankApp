package dev.reisdorf.dao;

import java.util.List;

import dev.reisdorf.entities.Account;

public interface AccountDAO {

	// CREATE
	Account createAccount(Account acct);
	
	// Read
	Account getAccountByID(int id);
	
	Account getAccountByName(String name, int userID);
	
	List<Account> getAllAccts();
	
	List<Account> getUsersAccts(int userID);
	
	// UPDATE
	Account updateAccount(Account acct);
	
	// DELETE
	boolean deleteAccount(int id);
}
