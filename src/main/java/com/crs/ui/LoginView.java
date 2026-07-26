package com.crs.ui;

import com.crs.models.User;
import com.crs.services.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private final Stage stage;
    private final AuthService authService = new AuthService();

    public LoginView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("Course Registration System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblUser = new Label("Username:");
        TextField txtUser = new TextField();
        
        Label lblPass = new Label("Password:");
        PasswordField txtPass = new PasswordField();

        grid.add(lblUser, 0, 0);
        grid.add(txtUser, 1, 0);
        grid.add(lblPass, 0, 1);
        grid.add(txtPass, 1, 1);

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red;");

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String username = txtUser.getText();
            String password = txtPass.getText();

            User user = authService.login(username, password);
            if (user != null) {
                switch (user.getRole()) {
                    case "ADMIN":
                        stage.setScene(new AdminDashboard(stage, user).getScene());
                        break;
                    case "STUDENT":
                        stage.setScene(new StudentDashboard(stage, user).getScene());
                        break;
                    case "FACULTY":
                        stage.setScene(new FacultyDashboard(stage, user).getScene());
                        break;
                    default:
                        lblError.setText("Unknown role.");
                }
            } else {
                lblError.setText("Invalid credentials.");
            }
        });

        root.getChildren().addAll(title, grid, btnLogin, lblError);
        return new Scene(root, 400, 300);
    }
}
