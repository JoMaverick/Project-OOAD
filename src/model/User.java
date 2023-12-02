package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int userId;
    private String userRole;
    private String userName;
    private String userEmail;
    private String userPassword;
    
    

    public User(int userId, String userRole, String userName, String userEmail, String userPassword) {
		this.userId = userId;
		this.userRole = userRole;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	public int getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    
    public static void deleteUser(int userId) {
    	String query = "DELETE FROM users WHERE userid = ?";
        try (Connection connection = Connect.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<User> getUserById(int userId) {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE userid = ?";
        try (Connection connection = Connect.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)){
            	 ps.setInt(1, userId);
            	 ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	int id = resultSet.getInt("userid");
                String name = resultSet.getString("username");
                String email = resultSet.getString("useremail");
                String pass = resultSet.getString("userpassword");
                String role = resultSet.getString("userrole");

                User users = new User(id, email, name, pass, role);
                userList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public static String createUser(String username, String userrole, String useremail, String userpassword) {
    	String query = "INSERT INTO users (userid, username, userrole, useremail, userpassword) VALUES (?, ?, ?, ?,?)";
    	try (Connection connection = Connect.getInstance().getConnection();
    	  PreparedStatement ps = connection.prepareStatement(query)){
    	  ps.setInt(1,0);
    	  ps.setString(2,username);
    	  ps.setString(3,userrole);
    	  ps.setString(4, useremail);
    	  ps.setString(5,userpassword);
    	  ps.executeUpdate();
    	  return "Create User Sucess";
    	} catch (SQLException e) {
    	  e.printStackTrace();
    	  return "Create User Failed";
    	}
	
    }
    
    public static void updateUser(int userid, String username, String userrole, String useremail, String userpassword) {
    	String query = "UPDATE users SET username = ?, userrole = ?, useremail = ?, userpassword = ? WHERE  userid= ?";
        try (Connection connection = Connect.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userrole);
            preparedStatement.setString(3, useremail);
            preparedStatement.setString(4, userpassword);
            preparedStatement.setInt(5, userid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
   	
   }
    
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> userlist = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = Connect.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
            	int id = resultSet.getInt("userid");
                String username = resultSet.getString("username");
                String useremail = resultSet.getString("useremail");
                String userpassword = resultSet.getString("userpassword");
                String userrole = resultSet.getString("userrole");

                User user = new User(id, username, useremail, userpassword, userrole);
                userlist.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userlist;
    }
    
    
    
}
