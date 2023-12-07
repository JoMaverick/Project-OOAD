package controller;

import java.util.ArrayList;

import model.MenuItem;
import model.User;

public class UserController {
	
	public static ArrayList<User> userlist = User.getAllUsers();

	
	public static String createUserController(String username, String useremail, String userpassword, String confirmpassword) {
		
		if(username.isBlank() || useremail.isBlank() || userpassword.isBlank()) {
			return "Error: Field cannot be empty";
		}
		else if(isUserEmailUnique(useremail) == 1){
			return "Error: Email is already registered";
		}
		else if(!userpassword.equals(confirmpassword)) {
			return "Error: Confirm Password entered is not the same as Password";
		}
		else {
			User.createUser(username, "Customer", useremail, userpassword);
			return "User Sucessfully created";
		}
	}
	
	public static String updateUserController(int userid, String username, String useremail, String userpassword, String userrole) {
		if(username.isBlank() || useremail.isBlank() || userpassword.isBlank()) {
			return "Error: Field cannot be empty";
		}
		else if(isUserEmailUnique(useremail) == 1){
			return "Error: Email is already registered";
		}
		else if(isUserRoleValid(userrole) == 1) {
			return "Error: Role must be Admin || Chef || Cashier || Customer";
		}
		else {
			User.updateUser(userid, username, userrole, useremail, userpassword);
			return "User Sucessfully updated";
		}
	}
	
	public static void deleteUserController(int userid) {
		User.deleteUser(userid);
	}
	
	public static String AuthenticateUserController(String useremail, String userpass) {
		if(useremail.isBlank() || userpass.isBlank()) {
			return "Error: Field cannot be empty";
		}
		else if(isEmailValid(useremail) == 1){
			return "Error: Email is not registered";
		}
		else {
			return User.AuthenticateUser(useremail, userpass);
		}
	}
	
	public static ArrayList<User> getAllUsersController() {
		return User.getAllUsers();
	}
	
	public static User getUserByIdController(int userid) {
		return User.getUserById(userid);
	}
	
	
	public static int isEmailValid(String email) {
		// TODO Auto-generated method stub
		for ( User users : userlist) {
			if(email.equals(users.getUserEmail())) {
				return 0;
			}
		}
		return 1;
	}

	
	public static int isUserEmailUnique(String useremail) {
		for (User users : userlist) {
			if(useremail.equalsIgnoreCase(users.getUserEmail())) {
				return 1;
			}
		}
		return 0;
		
	}
	
	public static int isUserRoleValid(String role) {
		if(!role.equals("Admin") && !role.equals("Chef") && !role.equals("Cashier") && !role.equals("Customer") ) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	
	
}
