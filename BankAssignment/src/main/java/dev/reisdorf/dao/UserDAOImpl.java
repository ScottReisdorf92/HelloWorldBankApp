package dev.reisdorf.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import dev.reisdorf.entities.User;
import dev.reisdorf.util.JDBCConnection;


public class UserDAOImpl implements UserDAO {
	
	public static Connection conn = JDBCConnection.getConnection();

	public User createUser(User user) {
		
		try {
			String sql = "CALL add_user(?, ?, ?, ?, ?)";
			
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setString(1, user.getUsername());
			cs.setString(2, user.getPassword());
			cs.setString(3, user.getFname());
			cs.setString(4, user.getLname());
			if (user.isSuper()) {
				cs.setString(5, "1");
			} else {
				cs.setString(5, "0");
			}
			
			cs.executeQuery();
			
			sql = "SELECT * FROM user_table WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				User createdUser = new User();
				createdUser.setUserID(rs.getInt("USER_ID"));
				createdUser.setUsername(rs.getString("USERNAME"));
				createdUser.setPassword(rs.getString("PASS"));
				createdUser.setFname(rs.getString("FIRST_NAME"));
				createdUser.setLname(rs.getString("LAST_NAME"));
				if (rs.getInt("IS_SUPER") == 1) {
					createdUser.setSuper(true);
				} else {
					createdUser.setSuper(false);
				}
				return createdUser;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByID(int id) {
		try {
			String sql = "SELECT * FROM user_table WHERE user_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(id));
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("USER_ID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASS"));
				user.setFname(rs.getString("FIRST_NAME"));
				user.setLname(rs.getString("LAST_NAME"));
				if (rs.getInt("IS_SUPER") == 1) {
					user.setSuper(true);
				} else {
					user.setSuper(false);
				}
				
				return user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserByUsername(String username) {
		try {
			String sql = "SELECT * FROM user_table WHERE username = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("USER_ID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASS"));
				user.setFname(rs.getString("FIRST_NAME"));
				user.setLname(rs.getString("LAST_NAME"));
				if (rs.getInt("IS_SUPER") == 1) {
					user.setSuper(true);
				} else {
					user.setSuper(false);
				}
				
				return user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> getAllUsers() {
		
		List<User> users = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM user_table";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("USER_ID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASS"));
				user.setFname(rs.getString("FIRST_NAME"));
				user.setLname(rs.getString("LAST_NAME"));
				if (rs.getInt("IS_SUPER") == 1) {
					user.setSuper(true);
				} else {
					user.setSuper(false);
				}
				
				users.add(user);
			}
			return users;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User updateUser(User user) {
		try {
			String sql = "UPDATE user_table SET username = ?, pass = ?, first_name = ?, last_name = ?, is_super = ? WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFname());
			ps.setString(4, user.getLname());
			if (user.isSuper()) {
				ps.setString(5, "1");
			} else {
				ps.setString(5, "0");
			}
			ps.setString(6, Integer.toString(user.getUserID()));
			
			ps.executeQuery();
			
			sql = "SELECT * FROM user_table WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(user.getUserID()));
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				User updatedUser = new User();
				updatedUser.setUserID(rs.getInt("USER_ID"));
				updatedUser.setUsername(rs.getString("USERNAME"));
				updatedUser.setPassword(rs.getString("PASS"));
				updatedUser.setFname(rs.getString("FIRST_NAME"));
				updatedUser.setLname(rs.getString("LAST_NAME"));
				if (rs.getInt("IS_SUPER") == 1) {
					updatedUser.setSuper(true);
				} else {
					updatedUser.setSuper(false);
				}
				
				return updatedUser;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteUserByID(int id) {
		try {
			String sql = "DELETE user_table WHERE user_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			
			ps.executeQuery();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteUserByUsername(String username) {
		try {
			String sql = "DELETE user_table WHERE username = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ps.executeQuery();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
