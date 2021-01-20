package dev.reisdorf.dao;

import java.util.List;

import dev.reisdorf.entities.User;

public interface UserDAO {
	
	// CREATE
		User createUser(User user);
		
		// READ
		User getUserByID(int id);
		
		User getUserByUsername(String username);
		
		List<User> getAllUsers();
		
		//Update
		User updateUser(User User);
		
		//Delete
		boolean deleteUserByID(int id);
		
		boolean deleteUserByUsername(String username);
	
}
