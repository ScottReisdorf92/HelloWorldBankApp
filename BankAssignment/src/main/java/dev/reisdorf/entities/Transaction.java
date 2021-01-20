package dev.reisdorf.entities;

import java.time.LocalDateTime;

public class Transaction {
	
	// INSTANCE VARIABLES
	private int transID;
	private int acctID;
	private double transAmt;
	private LocalDateTime timestamp;
	
	// CONSTRUCTORS
	public Transaction() {}

	public Transaction(int acctID, double amt) {
		this.acctID = acctID;
		this.transAmt = amt;
		timestamp= LocalDateTime.now();
	}
	
	public Transaction(int transID, int acctID, double amt) {
		this.transID = transID;
		this.acctID = acctID;
		this.transAmt = amt;
		timestamp= LocalDateTime.now();
	}
	
	public Transaction(int transID, int acctID, double amt, LocalDateTime time) {
		this.transID = transID;
		this.acctID = acctID;
		this.transAmt = amt;
		timestamp= time;
	}

	//-----GETTERS AND SETTERS-----//
	public int getTransID() {
		return transID;
	}

	public void setTransID(int transID) {
		this.transID = transID;
	}

	public int getAcctID() {
		return acctID;
	}

	public void setAcctID(int acctID) {
		this.acctID = acctID;
	}

	public double getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(double transAmt) {
		this.transAmt = transAmt;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	//-----------------------------//
	@Override
	public String toString() {
		return "Transaction [transID=" + transID + ", acctID=" + acctID + ", transAmt=" + transAmt + ", timestamp="
				+ timestamp + "]";
	}
	
	
	
	
	
}

