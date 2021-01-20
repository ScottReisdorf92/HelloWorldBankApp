package dev.reisdorf.entities;

public class Account {
	
	//-----INSTANCE VARIABLES-----//
	private int acctID;
	private int userID;
	private String name;
	private double balance;
	
	
	//-----CONSTRUCTORS-----//
	public Account() {
		this.balance = 0;
	}
	
	public Account(int userID, String name) {
		this.userID = userID;
		this.name = name;
		this.balance = 0;
	}
	
	public Account(int userID, String name, double balance) {
		this.userID = userID;
		this.name = name;
		this.balance = balance;
	}
	
	public Account(int acctID, int userID, String name, double balance) {
		this.acctID = acctID;
		this.userID = userID;
		this.name = name;
		this.balance = balance;
	}

	
	//-----GETTERS AND SETTERS-----//
	public int getAcctID() {
		return acctID;
	}

	public void setAcctID(int acctID) {
		this.acctID = acctID;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [acctID=" + acctID + ", name=" + name + ", balance=" + balance + "]";
	}
	
	
	
	
	
}
