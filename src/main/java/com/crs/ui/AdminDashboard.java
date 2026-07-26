package com.crs.ui;

import com.crs.dao.CourseDAO;
import com.crs.models.Course;
import com.crs.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AdminDashboard {
    private final Stage stage;
    private final User adminUser;
    private final CourseDAO courseDAO = new CourseDAO();

    public AdminDashboard(Stage stage, User adminUser) {
        this.stage = stage;
        this.adminUser = adminUser;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        
        Label title = new Label("Admin Dashboard - Welcome " + adminUser.getUsername());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> stage.setScene(new LoginView(stage).getScene()));
        
        HBox topBar = new HBox(20, title, btnLogout);
        topBar.setPadding(new Insets(10));
        root.setTop(topBar);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createManageCoursesTab(), createManageStudentsTab(), createReportsTab());
        root.setCenter(tabPane);

        return new Scene(root, 800, 600);
    }

    private Tab createManageCoursesTab() {
        Tab tab = new Tab("Manage Courses");
        tab.setClosable(false);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TableView<Course> table = new TableView<>();
        
        TableColumn<Course, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        
        TableColumn<Course, String> colDept = new TableColumn<>("Department");
        colDept.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment()));

        TableColumn<Course, String> colCredits = new TableColumn<>("Credits");
        colCredits.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCredits())));

        TableColumn<Course, String> colCapacity = new TableColumn<>("Max Capacity");
        colCapacity.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMaxCapacity())));

        table.getColumns().addAll(List.of(colTitle, colDept, colCredits, colCapacity));

        Runnable refreshTable = () -> {
            List<Course> courses = courseDAO.findAll(Course.class);
            ObservableList<Course> data = FXCollections.observableArrayList(courses);
            table.setItems(data);
        };
        refreshTable.run();

        HBox form = new HBox(10);
        TextField txtTitle = new TextField();
        txtTitle.setPromptText("Title");
        TextField txtDept = new TextField();
        txtDept.setPromptText("Department");
        TextField txtCredits = new TextField();
        txtCredits.setPromptText("Credits");
        TextField txtCapacity = new TextField();
        txtCapacity.setPromptText("Max Capacity");
        
        Button btnAdd = new Button("Add Course");
        btnAdd.setOnAction(e -> {
            try {
                Course c = new Course(txtTitle.getText(), Integer.parseInt(txtCredits.getText()), txtDept.getText(), Integer.parseInt(txtCapacity.getText()));
                courseDAO.save(c);
                refreshTable.run();
            } catch (Exception ex) {
                // handle input error
            }
        });

        Button btnDelete = new Button("Delete Selected");
        btnDelete.setOnAction(e -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                courseDAO.delete(selected);
                refreshTable.run();
            }
        });

        form.getChildren().addAll(txtTitle, txtDept, txtCredits, txtCapacity, btnAdd, btnDelete);

        vbox.getChildren().addAll(table, form);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createManageStudentsTab() {
        Tab tab = new Tab("Manage Students");
        tab.setClosable(false);
        tab.setContent(new Label("Student Management functionality goes here..."));
        return tab;
    }

    private Tab createReportsTab() {
        Tab tab = new Tab("Reports");
        tab.setClosable(false);
        tab.setContent(new Label("Reporting functionality goes here..."));
        return tab;
    }
}
