package dev.reisdorf.dao;

import java.util.List;

import dev.reisdorf.entities.Transaction;

public interface TransactionDAO {
	
	// CREATE
	Transaction addTransaction(Transaction transaction);
	
	// READ
	Transaction getTransactionByID(int transID);
	
	List<Transaction> getAcctTransactions(int AcctID);
	
	/*List<Transaction> getUserTransactions(int UserID)*/
	List<Transaction> getAllTransactions();
	
	// UPDATE
	Transaction updateTransaction(Transaction transaction);
	
	// DELETE
	boolean deleteTransaction(int transID);
	
}
