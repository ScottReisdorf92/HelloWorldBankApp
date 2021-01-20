package dev.reisdorf.services;

import java.util.List;

import dev.reisdorf.dao.UserDAO;
import dev.reisdorf.dao.UserDAOImpl;
import dev.reisdorf.entities.User;

public class UserServiceImpl implements UserService {

	
	private static UserDAO udao = new UserDAOImpl();
	
	
	public User registerUser(String username, String password, String fname, String lname) {
		User user = new User(username, password, fname, lname);
		user = udao.createUser(user);
		System.out.println("New User successfully registered!");
		return user;
	}
	
	public User registerUser(String username, String password, String fname, String lname, boolean isSuper) {
		User user = new User(username, password, fname, lname, isSuper);
		user = udao.createUser(user);
		System.out.println("New user successfully registered!");
		return user;
	}

	public User login(String username, String password) {
		User user = udao.getUserByUsername(username);
		
		if (user.getPassword().equals(password)) {
			return user;
		}
		
		// TODO: look at giving username or password fail message
		System.out.println("login failed");
		return null; // User not found
	}

	public List<User> viewAllUsers() {
		List<User> allUsers = udao.getAllUsers();
		for (User u : allUsers) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("User: " + u.getUsername() + " | User ID: " + u.getUserID());
			System.out.println("\t-First Name: " + u.getFname() + " | Last Name: " + u.getLname());
			System.out.println("------------------------------------------------------------------");
		}
		return allUsers;
	}

	public User viewUser(int id) {
		User user = udao.getUserByID(id);
		if (user == null) {
			System.out.println("User does not exist");
			return null;
		}
		System.out.println("------------------------------------------------------------------");
		System.out.println("User: " + user.getUsername() + " | User ID: " + user.getUserID());
		System.out.println("\t-First Name: " + user.getFname() + " |Last Name: " + user.getLname());
		System.out.println("------------------------------------------------------------------");
		return user;
	}
	
	public User viewUser(String username) {
		User user = udao.getUserByUsername(username);
		if (user == null) {
			System.out.println("User does not exist");
			return null;
		}
		System.out.println("------------------------------------------------------------------");
		System.out.println("User: " + user.getUsername() + " | User ID: " + user.getUserID());
		System.out.println("\t-First Name: " + user.getFname() + " | Last Name: " + user.getLname());
		System.out.println("------------------------------------------------------------------");
		return user;
	}
	
	public User updateUser(int id, String username, String password, String fname, String lname) {
		User user = new User(username, password, fname, lname);
		user.setUserID(id);
		if (user.equals(user)) {
			System.out.println("Update Succesfull");
		} else {
			System.out.println("Update Failed");
		}
		
		return user;
	}

	public User updateUser(int id, String username, String password, String fname, String lname, boolean isSuper) {
		User user = new User(username, password, fname, lname, isSuper);
		user.setUserID(id);
		User retUser = udao.updateUser(user);
		if (retUser.equals(user)) {
			System.out.println("Update Succesfull");
		} else {
			System.out.println("Update Failed");
		}
		
		return retUser;
	}
	
	public User updateUser(User user) {
		User retUser = udao.updateUser(user);
		if (retUser.equals(user)) {
			System.out.println("Update Succesfull");
		} else {
			System.out.println("Update Failed");
		}
		
		return retUser;
	}

	public User updateToSuper(User user) {
		user.setSuper(true);
		udao.updateUser(user);
		System.out.println("User upgraded to SuperUser");
		return user;
	}
	
	public boolean deleteUser(int id, User superUser) {
		if (udao.deleteUserByID(id)) {
			System.out.println("User successfully deleted");
			return true;
		} else {
			System.out.println("Deletion failed");
			return false;
		}
	}
	
	public boolean deleteUserByUsername(String username, User superUser) {
		if (udao.deleteUserByUsername(username)) {
			System.out.println("User successfully deleted");
			return true;
		} else {
			System.out.println("Deletion failed");
			return false;
		}
	}

	public boolean usernameAvailability(String username) {
		if (username.indexOf(" ") != -1) {
			System.out.println("Spaces are not allowed in usernames, please try another name");
			return false;
		}
		List<User> users = udao.getAllUsers();
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				System.out.println("Username is already chosen, please choose another");
				return false;
			}
		}
		return true;
	}
	
}
