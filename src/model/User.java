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

    public void setUserId(int userId) {
        this.userId = userId;
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
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUserById(int userId) {
    	User users;
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String email = resultSet.getString("user_email");
                String pass = resultSet.getString("user_password");
                String role = resultSet.getString("user_role");

                users = new User(id, role, name, email, pass);
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createUser(String username, String userrole, String useremail, String userpassword) {
        String query = "INSERT INTO users (user_id, user_name, user_role, user_email, user_password) VALUES (?, ?, ?, ?,?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, 0);
            ps.setString(2, username);
            ps.setString(3, userrole);
            ps.setString(4, useremail);
            ps.setString(5, userpassword);
            ps.executeUpdate();
            return "Create User Sucess";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Create User Failed";
        }

    }

    public static void updateUser(int userid, String username, String userrole, String useremail, String userpassword) {
        String query = "UPDATE users SET user_name = ?, user_role = ?, user_email = ?, user_password = ? WHERE  user_id= ?";
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
                int id = resultSet.getInt("user_id");
                String username = resultSet.getString("user_name");
                String useremail = resultSet.getString("user_email");
                String userpassword = resultSet.getString("user_password");
                String userrole = resultSet.getString("user_role");

                User user = new User(id, userrole, username, useremail, userpassword);
                userlist.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userlist;
    }

    public static String AuthenticateUser(String useremail, String userpassword) {
        try {
            Connection connection = Connect.getInstance().getConnection();
            String query = "SELECT * FROM users WHERE user_email = ? AND user_password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, useremail);
            preparedStatement.setString(2, userpassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            return "Authenticate User Sucess";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Authenticate User Failed";
        }
    }

}
