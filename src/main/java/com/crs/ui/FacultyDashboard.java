package com.crs.ui;

import com.crs.dao.EnrollmentDAO;
import com.crs.dao.FacultyDAO;
import com.crs.models.Enrollment;
import com.crs.models.Faculty;
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

public class FacultyDashboard {
    private final Stage stage;
    private final User user;
    private final Faculty faculty;
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public FacultyDashboard(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        FacultyDAO facultyDAO = new FacultyDAO();
        this.faculty = facultyDAO.findByUserId(user.getId());
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        
        String facultyName = faculty != null ? faculty.getName() : "Unknown";
        Label title = new Label("Faculty Dashboard - Welcome " + facultyName);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> stage.setScene(new LoginView(stage).getScene()));
        
        HBox topBar = new HBox(20, title, btnLogout);
        topBar.setPadding(new Insets(10));
        root.setTop(topBar);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(createViewEnrollmentsTab());
        root.setCenter(tabPane);

        return new Scene(root, 800, 600);
    }

    private Tab createViewEnrollmentsTab() {
        Tab tab = new Tab("View Student Enrollments");
        tab.setClosable(false);
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TableView<Enrollment> table = new TableView<>();
        
        TableColumn<Enrollment, String> colStudent = new TableColumn<>("Student");
        colStudent.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getName()));
        
        TableColumn<Enrollment, String> colCourse = new TableColumn<>("Course");
        colCourse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourse().getTitle()));
        
        TableColumn<Enrollment, String> colSemester = new TableColumn<>("Semester");
        colSemester.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSemester()));

        TableColumn<Enrollment, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        TableColumn<Enrollment, String> colGrade = new TableColumn<>("Grade");
        colGrade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGrade() != null ? data.getValue().getGrade() : "N/A"));

        table.getColumns().addAll(colStudent, colCourse, colSemester, colStatus, colGrade);

        // For simplicity, showing all enrollments across the system. 
        // In a full implementation, you would filter by courses taught by this faculty.
        List<Enrollment> enrollments = enrollmentDAO.findAll(Enrollment.class);
        ObservableList<Enrollment> data = FXCollections.observableArrayList(enrollments);
        table.setItems(data);

        Label lblMessage = new Label("Showing all system enrollments for grading reference.");
        
        // Input grades functionality
        HBox gradeBox = new HBox(10);
        TextField txtGrade = new TextField();
        txtGrade.setPromptText("Enter Grade (e.g., A, B+)");
        Button btnSubmitGrade = new Button("Submit Grade");
        
        btnSubmitGrade.setOnAction(e -> {
            Enrollment selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setGrade(txtGrade.getText());
                if ("REGISTERED".equals(selected.getStatus())) {
                    selected.setStatus("COMPLETED"); // Marking completed when graded
                }
                enrollmentDAO.update(selected);
                table.refresh();
                lblMessage.setText("Grade updated for " + selected.getStudent().getName());
            } else {
                lblMessage.setText("Please select an enrollment first.");
            }
        });
        
        gradeBox.getChildren().addAll(txtGrade, btnSubmitGrade);

        vbox.getChildren().addAll(lblMessage, table, gradeBox);
        tab.setContent(vbox);
        return tab;
    }
}
