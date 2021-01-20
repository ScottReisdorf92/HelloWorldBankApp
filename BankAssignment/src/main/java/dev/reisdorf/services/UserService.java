package dev.reisdorf.services;

import java.util.List;

import dev.reisdorf.entities.User;

public interface UserService {

	User registerUser(String username, String password, String fname, String lname);
	
	User registerUser(String username, String password, String fname, String lname, boolean isSuper);
	
	User login(String username, String password);
	
	List<User> viewAllUsers();
	
	User viewUser(int id);
	
	User viewUser(String username);
	
	User updateUser(int id, String username, String password, String fname, String lname);
	
	User updateUser(User user);
	
	User updateToSuper(User user);
	
	boolean deleteUser(int id, User superUser);
	
	boolean usernameAvailability(String username);
}
