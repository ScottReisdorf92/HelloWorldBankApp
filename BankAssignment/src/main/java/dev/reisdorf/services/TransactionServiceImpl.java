package dev.reisdorf.services;

import java.util.List;

import dev.reisdorf.dao.TransactionDAO;
import dev.reisdorf.dao.TransactionDAOImpl;
import dev.reisdorf.entities.Transaction;

public class TransactionServiceImpl implements TransactionService {

	TransactionDAO tdao = new TransactionDAOImpl();
	
	public Transaction createTransaction(int acctID, double amt) {
		Transaction transaction = new Transaction(acctID, amt);
		
		return tdao.addTransaction(transaction);
	}

	public List<Transaction> viewAcctTransactions(int acctID) {
		List<Transaction> transactions = tdao.getAcctTransactions(acctID);
		System.out.println("Transactions for Account ID: " + acctID + "------------------------------------------------------------------");
		for (Transaction t : transactions) {
			System.out.println("Transaction ID " + t.getTransID() + "------------------------------------------------------------------");
			System.out.println("Transaction Amount: $" + t.getTransAmt() + " | Timestamp: " + t.getTimestamp());
			System.out.println("------------------------------------------------------------------");
		}
		
		return transactions;
	}

	public boolean deleteTransaction(int id) {		
		return tdao.deleteTransaction(id);
	}

}
