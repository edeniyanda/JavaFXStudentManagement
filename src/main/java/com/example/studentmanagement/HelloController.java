package com.example.studentmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private TableColumn<Student, String> DepartmentColumn;

    @FXML
    private TableColumn<Student, String> FNColumn;

    @FXML
    private TableColumn<Student, Integer> IDColumn;

    @FXML
    private TableColumn<Student, String> LNColumn;

    @FXML
    private TableColumn<Student, String> MobileColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Student> infoTable;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtMobileNumber;

//    For the Course Scene

    @FXML
    private TableColumn<Course, String> CourseCodeCol;

    @FXML
    private TableColumn<Course, Integer> CourseSizeCol;

    @FXML
    private TableColumn<Course, String> CourseTitleCol;

    @FXML
    private Button btnAddCourse;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TextField txtCourseCode;

    @FXML
    private TextField txtCourseTitle;

    @FXML
    private TextField txtMaxCap;

    //    for the Enrollment Scene
    @FXML
    private Button btnEnrollStudent;

    @FXML
    private ComboBox<Course> cmbSelectCourse;

    @FXML
    private ComboBox<Student> cmbSelectStudent;

    @FXML
    private ListView<Enrollment> enrollmentListView;

    //    FOr the Grading View

    @FXML
    private ComboBox<Student> cmbSelectStudentForGrades;

    @FXML
    private ComboBox<Course> cmbSelectCourseForGrades;

    @FXML
    private TextField txtGrade;

    @FXML
    private Button btnAssignGrade;

    @FXML
    private ListView<Enrollment> gradeListView;


    // ObservableList to store course data
    // Make the course list static to persist between scene changes
    private static ObservableList<Course> courseList = FXCollections.observableArrayList();


    // Observable list to hold student data
    private static ObservableList<Student> studentList = FXCollections.observableArrayList();

    // Observable list for enrollments
    private static ObservableList<Enrollment> enrollmentList = FXCollections.observableArrayList();

    // Counter to generate unique IDs
    private int studentIdCounter = 1;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("welcome-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToStudentScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        // Get the controller
        HelloController controller = loader.getController();


        // Manually call the method to initialize the table
        controller.initializeStudentTable(); // This is critical!

        Scene scene = new Scene(root, 720, 590);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToCourseScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("course-view.fxml"));
        Parent root = loader.load();

        // Get the controller
        HelloController controller = loader.getController();


        // Manually call the method to initialize the table
        controller.initializeCourseTable(); // This is critical!

        Scene scene = new Scene(root, 742, 590);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToEnrollmentScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("enrollment-view.fxml"));
        Parent root = loader.load();

        // Get the controller
        HelloController controller = loader.getController();


        // Manually call the method to initialize the table
        controller.initializeEnrollmentComponents(); // This is critical!

        Scene scene = new Scene(root, 742, 590);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToGradingScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("grade-view.fxml"));
        Parent root = loader.load();

        // Get the controller
        HelloController controller = loader.getController();


        // Manually call the method to initialize the table
        controller.initializeGradeComponents(); // This is critical!

        Scene scene = new Scene(root, 742, 590);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public void initializeStudentTable() {
        System.out.println("Hello Bug");
        // Bind table columns to Student class properties
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        FNColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LNColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        MobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        DepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        // Set the data to the table
        infoTable.setItems(studentList);

        // Add listener to TableView for row selection
        infoTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {  // Optional: Use double-click if desired
                populateTextFields();
            }
        });
    }

    @FXML
    public void initializeEnrollmentComponents() {
        // Populate the ComboBox with students and courses
        cmbSelectStudent.setItems(studentList);
        cmbSelectCourse.setItems(courseList);

        // Bind the enrollment list to the ListView to display enrolled students
        enrollmentListView.setItems(enrollmentList);
    }

    @FXML
    public void initializeGradeComponents() {
        // Populate the ComboBox with students
        cmbSelectStudentForGrades.setItems(studentList);

        // Add a listener to the student ComboBox to update courses based on selected student
        cmbSelectStudentForGrades.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedStudent) -> {
            if (selectedStudent != null) {
                // Filter courses that the selected student is enrolled in
                ObservableList<Course> enrolledCourses = FXCollections.observableArrayList();
                for (Enrollment enrollment : enrollmentList) {
                    if (enrollment.getStudent().equals(selectedStudent)) {
                        enrolledCourses.add(enrollment.getCourse());
                    }
                }
                cmbSelectCourseForGrades.setItems(enrolledCourses);
            }
        });

        // Bind the enrollment list to the ListView to display enrollments with grades
        gradeListView.setItems(enrollmentList);
    }


    @FXML
    void handleEnrollStudent(ActionEvent event) {
        Student selectedStudent = cmbSelectStudent.getSelectionModel().getSelectedItem();
        Course selectedCourse = cmbSelectCourse.getSelectionModel().getSelectedItem();

        if (selectedStudent == null || selectedCourse == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select both a student and a course to enroll.");
            return;
        }

        // Create a new Enrollment and add it to the enrollmentList
        Enrollment newEnrollment = new Enrollment(selectedStudent, selectedCourse);
        enrollmentList.add(newEnrollment);

        showAlert(Alert.AlertType.INFORMATION, "Enrollment Successful", "Student successfully enrolled in the course.");

        // Clear selections
        cmbSelectStudent.getSelectionModel().clearSelection();
        cmbSelectCourse.getSelectionModel().clearSelection();
    }


    @FXML
    void handleAssignGrade(ActionEvent event) {
        Student selectedStudent = cmbSelectStudentForGrades.getSelectionModel().getSelectedItem();
        Course selectedCourse = cmbSelectCourseForGrades.getSelectionModel().getSelectedItem();
        String grade = txtGrade.getText();

        if (selectedStudent == null || selectedCourse == null || grade.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a student, a course, and enter a valid grade.");
            return;
        }

        // Find the enrollment to assign the grade to
        for (Enrollment enrollment : enrollmentList) {
            if (enrollment.getStudent().equals(selectedStudent) && enrollment.getCourse().equals(selectedCourse)) {
                enrollment.setGrade(grade);
                showAlert(Alert.AlertType.INFORMATION, "Grade Assigned", "Grade successfully assigned to student.");
                break;
            }
        }

        // Clear the input fields after assigning grade
        cmbSelectStudentForGrades.getSelectionModel().clearSelection();
        cmbSelectCourseForGrades.getSelectionModel().clearSelection();
        txtGrade.clear();

        // Refresh the ListView to reflect the updated grades
        gradeListView.refresh();
    }


    // Populate text fields when a row is selected
    private void populateTextFields() {
        // Get the selected student from the table
        Student selectedStudent = infoTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Set the values in the text fields from the selected student
            txtFirstName.setText(selectedStudent.getFirstName());
            txtLastName.setText(selectedStudent.getLastName());
            txtMobileNumber.setText(selectedStudent.getMobileNumber());
            txtDepartment.setText(selectedStudent.getDepartment());
        }
    }


    @FXML
    void Add(ActionEvent event) {
        // Get data from text fields
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String mobileNumber = txtMobileNumber.getText();
        String department = txtDepartment.getText();

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || mobileNumber.isEmpty() || department.isEmpty()) {
            // Show alert for missing input
            showAlert(Alert.AlertType.WARNING, "Input Error", "All fields are required!");
            return;
        }

        // Create a new student and add to the list
        Student newStudent = new Student(studentIdCounter++, firstName, lastName, mobileNumber, department);
        studentList.add(newStudent);

        // Clear input fields
        clearFields();
    }

    @FXML
    void Clear(ActionEvent event) {
        // Clear the input fields
        clearFields();
    }

    @FXML
    void Delete(ActionEvent event) {
        // Get the selected student from the table
        Student selectedStudent = infoTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            studentList.remove(selectedStudent);
        } else {
            // Show alert for no selection
            showAlert(Alert.AlertType.WARNING, "Selection Error", "No student selected for deletion.");
        }
    }

    @FXML
    void Update(ActionEvent event) {
        // Get the selected student from the table
        Student selectedStudent = infoTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {

            // Get data from text fields
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String mobileNumber = txtMobileNumber.getText();
            String department = txtDepartment.getText();

            // Validate input
            if (firstName.isEmpty() || lastName.isEmpty() || mobileNumber.isEmpty() || department.isEmpty()) {
                // Show alert for missing input
                showAlert(Alert.AlertType.WARNING, "Input Error", "All fields are required!");
                return;
            }
            // Update the selected student's details
            selectedStudent.setFirstName(txtFirstName.getText());
            selectedStudent.setLastName(txtLastName.getText());
            selectedStudent.setMobileNumber(txtMobileNumber.getText());
            selectedStudent.setDepartment(txtDepartment.getText());

            // Refresh the table view to show updated data
            infoTable.refresh();
            clearFields();
        } else {
            // Show alert for no selection
            showAlert(Alert.AlertType.WARNING, "Selection Error", "No student selected for update.");
        }
    }

    private void clearFields() {
        // Clear the text fields
        txtFirstName.clear();
        txtLastName.clear();
        txtMobileNumber.clear();
        txtDepartment.clear();
    }

    @FXML
    private void initializeCourseTable() {
        // Set up the columns with the corresponding properties of the Course class
        CourseTitleCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        CourseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        CourseSizeCol.setCellValueFactory(new PropertyValueFactory<>("maximumCapacity"));

        // Bind the course list to the TableView
        courseTable.setItems(courseList);
    }
    

    @FXML
    void addCourse(ActionEvent event) {
        // Retrieve input values from the text fields
        String courseName = txtCourseTitle.getText();
        String courseCode = txtCourseCode.getText();
        int maxCapacity;

        // Validate max capacity input
        try {
            maxCapacity = Integer.parseInt(txtMaxCap.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid max capacity. Please enter a number.");
            return;
        }

        // Create a new Course object and add it to the list
        Course newCourse = new Course(courseCode, courseName, maxCapacity);
        courseList.add(newCourse);

        // Clear the input fields after adding a course
        txtCourseTitle.clear();
        txtCourseCode.clear();
        txtMaxCap.clear();
    }


    // Utility method to show alert dialogs
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header for this dialog
        alert.setContentText(message);
        alert.showAndWait();
    }
}
