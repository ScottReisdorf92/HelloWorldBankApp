package dev.reisdorf.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCConnection {
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		
		try {
			if (conn == null) {
				// Make a new one
				
				/*
				 * oracle needed a hotfix to ensure that ojdbc drivers would correctly load
				 * at the beginning of your app starting
				 */
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				
				
				// To establish a Connection we need 3 credentials
				// url (endpoint), username, password
				Properties props = new Properties();
				FileInputStream input = new FileInputStream(JDBCConnection.class.getClassLoader().getResource("connection.properties").getFile());
				props.load(input);
				
				String url = props.getProperty("url");
				String username = props.getProperty("username");
				String password = props.getProperty("password");
				
				// String url = System.getenv("rds_url");
				// String username = System.getenv("rds_username");
				// String password = System.getenv("rds_password");
				
				
				
				// Establish our connection
				conn = DriverManager.getConnection(url, username, password);
				
				return conn;
				
			} else {
				// return connection that already exists
				return conn;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	public static void main(String[] args) {
		Connection conn1 = getConnection();
		System.out.println(conn1);
	}

}
