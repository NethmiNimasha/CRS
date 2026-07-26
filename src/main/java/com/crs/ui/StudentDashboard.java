package com.crs.ui;

import com.crs.dao.CourseDAO;
import com.crs.dao.EnrollmentDAO;
import com.crs.dao.StudentDAO;
import com.crs.models.Course;
import com.crs.models.Enrollment;
import com.crs.models.Student;
import com.crs.models.User;
import com.crs.services.RegistrationService;
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

public class StudentDashboard {
    private final Stage stage;
    private final Student student;
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final RegistrationService registrationService = new RegistrationService();

    public StudentDashboard(Stage stage, User user) {
        this.stage = stage;
        StudentDAO studentDAO = new StudentDAO();
        this.student = studentDAO.findByUserId(user.getId());
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        
        String studentName = student != null ? student.getName() : "Unknown";
        Label title = new Label("Student Dashboard - Welcome " + studentName);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> stage.setScene(new LoginView(stage).getScene()));
        
        HBox topBar = new HBox(20, title, btnLogout);
        topBar.setPadding(new Insets(10));
        root.setTop(topBar);

        if (student == null) {
            root.setCenter(new Label("No student profile found for this user."));
            return new Scene(root, 800, 600);
        }

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createRegisterTab(), createMyRecordsTab());
        root.setCenter(tabPane);

        return new Scene(root, 800, 600);
    }

    private Tab createRegisterTab() {
        Tab tab = new Tab("Course Registration");
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

        table.getColumns().addAll(List.of(colTitle, colDept, colCredits));

        List<Course> courses = courseDAO.findAll(Course.class);
        ObservableList<Course> data = FXCollections.observableArrayList(courses);
        table.setItems(data);

        Label lblMessage = new Label();
        
        Button btnRegister = new Button("Register for Selected Course");
        btnRegister.setOnAction(e -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String result = registrationService.registerStudentForCourse(student.getId(), selected.getId(), "Fall 2026");
                lblMessage.setText(result);
            }
        });

        vbox.getChildren().addAll(new Label("Available Courses:"), table, btnRegister, lblMessage);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createMyRecordsTab() {
        Tab tab = new Tab("My Records");
        tab.setClosable(false);
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TableView<Enrollment> table = new TableView<>();
        
        TableColumn<Enrollment, String> colCourse = new TableColumn<>("Course");
        colCourse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourse().getTitle()));
        
        TableColumn<Enrollment, String> colSemester = new TableColumn<>("Semester");
        colSemester.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSemester()));

        TableColumn<Enrollment, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        TableColumn<Enrollment, String> colGrade = new TableColumn<>("Grade");
        colGrade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGrade() != null ? data.getValue().getGrade() : "N/A"));

        table.getColumns().addAll(List.of(colCourse, colSemester, colStatus, colGrade));

        Runnable refreshRecords = () -> {
            List<Enrollment> enrollments = enrollmentDAO.findByStudentId(student.getId());
            ObservableList<Enrollment> data = FXCollections.observableArrayList(enrollments);
            table.setItems(data);
        };

        // Needs to refresh when tab is selected to show newly registered courses
        tab.setOnSelectionChanged(e -> {
            if (tab.isSelected()) {
                refreshRecords.run();
            }
        });
        
        refreshRecords.run();
        
        Label lblMessage = new Label();
        Button btnDrop = new Button("Drop Selected Course");
        btnDrop.setOnAction(e -> {
            Enrollment selected = table.getSelectionModel().getSelectedItem();
            if (selected != null && "REGISTERED".equals(selected.getStatus())) {
                String result = registrationService.dropCourse(student.getId(), selected.getCourse().getId());
                lblMessage.setText(result);
                refreshRecords.run();
            } else {
                lblMessage.setText("Can only drop REGISTERED courses.");
            }
        });

        vbox.getChildren().addAll(new Label("Your Enrollments:"), table, btnDrop, lblMessage);
        tab.setContent(vbox);
        return tab;
    }
}
