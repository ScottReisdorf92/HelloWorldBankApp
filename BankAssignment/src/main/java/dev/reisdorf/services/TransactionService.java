package dev.reisdorf.services;

import java.util.List;

import dev.reisdorf.entities.Transaction;

public interface TransactionService {
	
	Transaction createTransaction(int acctID, double amt);
	
	List<Transaction> viewAcctTransactions(int acctID);
	
	boolean deleteTransaction(int id);
}
