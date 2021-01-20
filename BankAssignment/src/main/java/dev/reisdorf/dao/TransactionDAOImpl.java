package dev.reisdorf.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.reisdorf.entities.Transaction;
import dev.reisdorf.util.JDBCConnection;

public class TransactionDAOImpl implements TransactionDAO {

	public static Connection conn = JDBCConnection.getConnection();
	
	public Transaction addTransaction(Transaction transaction) {
		try {
			String sql = "CALL add_trans(?, ?, ?)";
			
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setString(1, Integer.toString(transaction.getAcctID()));
			cs.setString(2, Double.toString(transaction.getTransAmt()));
			cs.setString(3, transaction.getTimestamp().toString());
			
			
			cs.executeQuery();
			
			
			sql = "SELECT * FROM trans_table WHERE acct_id = ? AND trans_amt = ? AND trans_date = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(transaction.getAcctID()));
			ps.setString(2, Double.toString(transaction.getTransAmt()));
			ps.setString(3, transaction.getTimestamp().toString());
			ResultSet rs = ps.executeQuery();
			
			
			if(rs.next()) {
				Transaction createdTransaction = new Transaction();
				createdTransaction.setTransID(rs.getInt("TRANS_ID"));
				createdTransaction.setAcctID(rs.getInt("ACCT_ID"));
				createdTransaction.setTransAmt(rs.getDouble("TRANS_AMT"));
				createdTransaction.setTimestamp(LocalDateTime.parse(rs.getString("TRANS_DATE")));				
				return createdTransaction;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Transaction getTransactionByID(int transID) {
		try {
			String sql = "SELECT * FROM trans_table WHERE trans_id = ?";
			
			PreparedStatement ps = conn.prepareCall(sql);
			
			ps.setString(1, Integer.toString(transID));
			
			
			ResultSet rs = ps.executeQuery();
			
			
			if(rs.next()) {
				Transaction createdTransaction = new Transaction();
				createdTransaction.setTransID(rs.getInt("TRANS_ID"));
				createdTransaction.setAcctID(rs.getInt("ACCT_ID"));
				createdTransaction.setTransAmt(rs.getDouble("TRANS_AMT"));
				createdTransaction.setTimestamp(LocalDateTime.parse(rs.getString("TRANS_DATE")));				
				return createdTransaction;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Transaction> getAcctTransactions(int acctID) {
		try {
			String sql = "SELECT * FROM trans_table WHERE acct_id = ?";
			
			PreparedStatement ps = conn.prepareCall(sql);
			
			ps.setString(1, Integer.toString(acctID));
			
			
			ResultSet rs = ps.executeQuery();
			
			List<Transaction> transactions = new ArrayList<Transaction>();
			while (rs.next()) {
				Transaction createdTransaction = new Transaction();
				createdTransaction.setTransID(rs.getInt("TRANS_ID"));
				createdTransaction.setAcctID(rs.getInt("ACCT_ID"));
				createdTransaction.setTransAmt(rs.getDouble("TRANS_AMT"));
				createdTransaction.setTimestamp(LocalDateTime.parse(rs.getString("TRANS_DATE")));				
				transactions.add(createdTransaction);
			}
			
			return transactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Transaction> getAllTransactions() {
		try {
			String sql = "SELECT * FROM trans_table";
			
			PreparedStatement ps = conn.prepareCall(sql);			
			
			ResultSet rs = ps.executeQuery();
			
			List<Transaction> transactions = new ArrayList<Transaction>();
			if(rs.next()) {
				Transaction createdTransaction = new Transaction();
				createdTransaction.setTransID(rs.getInt("TRANS_ID"));
				createdTransaction.setAcctID(rs.getInt("ACCT_ID"));
				createdTransaction.setTransAmt(rs.getDouble("TRANS_AMT"));
				createdTransaction.setTimestamp(LocalDateTime.parse(rs.getString("TRANS_DATE")));				
				transactions.add(createdTransaction);
			}
			
			return transactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Transaction updateTransaction(Transaction transaction) {
	
		try {
			String sql = "UPDATE trans_table SET acct_id = ?, trans_amt = ?, trans_date = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(transaction.getAcctID()));
			ps.setString(2, Double.toString(transaction.getTransAmt()));
			ps.setString(3, transaction.getTimestamp().toString());
			
			ps.executeQuery();
			
			sql = "SELECT * FROM trans_table WHERE trans_id = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(transaction.getTransID()));
			ResultSet rs = ps.executeQuery();
			
			
			if(rs.next()) {
				Transaction createdTransaction = new Transaction();
				createdTransaction.setTransID(rs.getInt("TRANS_ID"));
				createdTransaction.setAcctID(rs.getInt("ACCT_ID"));
				createdTransaction.setTransAmt(rs.getDouble("TRANS_AMT"));
				createdTransaction.setTimestamp(LocalDateTime.parse(rs.getString("TRANS_DATE")));				
				return createdTransaction;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteTransaction(int transID) {
		try {
			String sql = "DELETE trans_table WHERE trans_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(transID));
			
			ps.executeQuery();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
