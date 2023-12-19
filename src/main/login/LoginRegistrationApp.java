package main.login;
import java.util.HashMap;
import java.util.Map;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.User;

public class LoginRegistrationApp extends Application {

    private Map<String, User> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mystic Grills");

        // Create login scene
        Scene loginScene = createLoginScene(primaryStage);

        // Set the login scene as the initial scene
        primaryStage.setScene(loginScene);

        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(20, 20, 20, 20));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);

        // Email label and text field
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);

        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 0);

        // Password label and password field
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        // Login button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> User.AuthenticateUser(emailInput.getText(), passwordInput.getText()));

        // Switch to registration scene button
        Button switchToRegisterButton = new Button("Switch to Registration");
        GridPane.setConstraints(switchToRegisterButton, 1, 3);
        switchToRegisterButton.setOnAction(e -> primaryStage.setScene(createRegistrationScene(primaryStage)));

        loginGrid.getChildren().addAll(emailLabel, emailInput, passwordLabel, passwordInput, loginButton, switchToRegisterButton);

        // Create a StackPane to allow centering in a resizable window
        StackPane stackPane = new StackPane(loginGrid);
        stackPane.setMinSize(300, 200);

        return new Scene(stackPane, 400, 200);
    }

    private Scene createRegistrationScene(Stage primaryStage) {
        GridPane registerGrid = new GridPane();
        registerGrid.setPadding(new Insets(20, 20, 20, 20));
        registerGrid.setVgap(10);
        registerGrid.setHgap(10);

        // Name label and text field
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        // Email label and text field
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);

        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 1);

        // Password label and password field
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);

        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 2);

        // Confirm Password label and password field
        Label confirmPasswordLabel = new Label("Confirm Password:");
        GridPane.setConstraints(confirmPasswordLabel, 0, 3);

        PasswordField confirmPasswordInput = new PasswordField();
        GridPane.setConstraints(confirmPasswordInput, 1, 3);

        // Register button
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 4);
        registerButton.setOnAction(e -> UserController.createUserController(nameInput.getText(), emailInput.getText(), passwordInput.getText(), confirmPasswordInput.getText()));
        // Switch to login scene button
        Button switchToLoginButton = new Button("Switch to Login");
        GridPane.setConstraints(switchToLoginButton, 1, 5);
        switchToLoginButton.setOnAction(e -> primaryStage.setScene(createLoginScene(primaryStage)));

        registerGrid.getChildren().addAll(nameLabel, nameInput, emailLabel, emailInput, passwordLabel, passwordInput, confirmPasswordLabel, confirmPasswordInput, registerButton, switchToLoginButton);

        // Create a StackPane to allow centering in a resizable window
        StackPane stackPane = new StackPane(registerGrid);
        stackPane.setMinSize(300, 200);

        return new Scene(stackPane, 400, 300);
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

