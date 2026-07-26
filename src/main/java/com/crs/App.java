package com.crs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.crs.ui.LoginView;
import com.crs.utils.HibernateUtil;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Course Registration System");
        
        LoginView loginView = new LoginView(primaryStage);
        primaryStage.setScene(loginView.getScene());
        
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtil.shutdown(); // Close database connection when app closes
    }

    public static void main(String[] args) {
        launch(args);
    }
}
