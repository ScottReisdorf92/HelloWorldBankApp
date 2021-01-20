package dev.reisdorf.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reisdorf.entities.Account;
import dev.reisdorf.util.JDBCConnection;

public class AccountDAOImpl implements AccountDAO {
	
	public static Connection conn = JDBCConnection.getConnection();
	
	public Account createAccount(Account acct) {
		
		try {
			String sql = "CALL add_acct(?, ?, ?)";
			
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setString(1, Integer.toString(acct.getUserID()));
			cs.setString(2, acct.getName());
			cs.setString(3, Double.toString(acct.getBalance()));
			
			
			cs.executeQuery();
			
			
			sql = "SELECT * FROM acct_table WHERE name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, acct.getName());
			ResultSet rs = ps.executeQuery();
			
			
			if(rs.next()) {
				Account createdAcct = new Account();
				createdAcct.setAcctID(rs.getInt("ACCT_ID"));
				createdAcct.setUserID(rs.getInt("USER_ID"));
				createdAcct.setName(rs.getString("NAME"));
				createdAcct.setBalance(rs.getDouble("BALANCE"));				
				return createdAcct;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Account getAccountByID(int id) {
		try {
			String sql = "SELECT * FROM acct_table WHERE acct_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(id));			
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Account createdAcct = new Account();
				createdAcct.setAcctID(rs.getInt("ACCT_ID"));
				createdAcct.setUserID(rs.getInt("USER_ID"));
				createdAcct.setName(rs.getString("NAME"));
				createdAcct.setBalance(rs.getDouble("BALANCE"));				
				return createdAcct;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Account getAccountByName(String name, int userID) {
		try {
			String sql = "SELECT * FROM acct_table WHERE name = ? AND user_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, name);
			ps.setString(2, Integer.toString(userID));			
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Account createdAcct = new Account();
				createdAcct.setAcctID(rs.getInt("ACCT_ID"));
				createdAcct.setUserID(rs.getInt("USER_ID"));
				createdAcct.setName(rs.getString("NAME"));
				createdAcct.setBalance(rs.getDouble("BALANCE"));				
				return createdAcct;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Account> getAllAccts() {
		try {
			List<Account> accts = new ArrayList<Account>();
			String sql = "SELECT * FROM acct_table";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Account createdAcct = new Account();
				createdAcct.setAcctID(rs.getInt("ACCT_ID"));
				createdAcct.setUserID(rs.getInt("USER_ID"));
				createdAcct.setName(rs.getString("NAME"));
				createdAcct.setBalance(rs.getDouble("BALANCE"));				
				accts.add(createdAcct);
			}
			
			return accts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Account> getUsersAccts(int userID) {
		try {
			List<Account> accts = new ArrayList<Account>();
			String sql = "SELECT * FROM acct_table WHERE user_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(userID));			
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Account createdAcct = new Account();
				createdAcct.setAcctID(rs.getInt("ACCT_ID"));
				createdAcct.setUserID(rs.getInt("USER_ID"));
				createdAcct.setName(rs.getString("NAME"));
				createdAcct.setBalance(rs.getDouble("BALANCE"));				
				accts.add(createdAcct);
			}
			return accts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Account updateAccount(Account acct) {
		try {
			String sql = "UPDATE acct_table SET name = ?, balance = ? WHERE acct_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, acct.getName());
			ps.setString(2, Double.toString(acct.getBalance()));
			ps.setString(3, Integer.toString(acct.getAcctID()));
			
			
			ps.executeQuery();
			sql = "SELECT * FROM acct_table WHERE acct_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(acct.getAcctID()));
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Account createdAcct = new Account();
				createdAcct.setAcctID(rs.getInt("ACCT_ID"));
				createdAcct.setUserID(rs.getInt("USER_ID"));
				createdAcct.setName(rs.getString("NAME"));
				createdAcct.setBalance(rs.getDouble("BALANCE"));				
				return createdAcct;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteAccount(int id) {
		try {
			String sql = "DELETE acct_table WHERE acct_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			
			ps.executeQuery();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
	
	
	

}
