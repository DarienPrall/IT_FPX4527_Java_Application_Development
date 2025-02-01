package edu.capella.bsit.registerforcourse;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Platform;

public class App extends Application {

    private final TextField learnerIDField = new TextField();
    private final Label learnerIDFieldLabel = new Label("Learner ID: ");
    private final Button startRegistrationButton = new Button("Start Registration");
    private final ComboBox<Course> availableCoursesComboBox = new ComboBox<>();
    private final Label availableCoursesLabel = new Label("Select a Course:");
    private final ListView<CourseRegistration> registeredCoursesListView = new ListView<>();
    private final Label registeredCoursesLabel = new Label("Registered Courses: ");
    private final Label totalCreditsLabel = new Label("Total Credits: ");
    private final Label totalCreditsDisplay = new Label();
    private final Button saveRegistrationButton = new Button("Save Registered Courses to File");
    private final Label messageLabel = new Label();

    private final BorderPane borderLayout = new BorderPane();
    private final GridPane gridLayout = new GridPane();

    private final int MAX_CREDITS = 9;

    // MariaDB connection information
    private String mariaURI = "jdbc:mariadb://localhost:3306/registration";
    private String mariaUser = "registrar";
    private String mariaPassword = "P@ssword1";

    @Override
    public void start(Stage stage) {
        // Property to bind total credits
        SimpleIntegerProperty totalCredit = new SimpleIntegerProperty(0);
        totalCreditsDisplay.textProperty().bind(totalCredit.asString());

        // Property to bind learner ID
        SimpleStringProperty learnerID = new SimpleStringProperty();
        learnerIDField.textProperty().bindBidirectional(learnerID);

        // GridPane with registration controls hidden until user ID entered
        // and startRegistrationButton pressed.
        gridLayout.setVisible(false);
        gridLayout.setHgap(15);
        gridLayout.setVgap(15);

        final Insets padding = new Insets(10, 10, 10, 10);

        HBox learnerIDHBox = new HBox();
        learnerIDFieldLabel.setPadding(padding);
        learnerIDHBox.setSpacing(15);
        learnerIDHBox.getChildren().addAll(learnerIDFieldLabel, learnerIDField,
                startRegistrationButton);
        borderLayout.setPadding(padding);
        borderLayout.setTop(learnerIDHBox);

        // ComboBox for available courses
        availableCoursesComboBox.setPromptText("Courses");
        // ObservableList for ComboBox
        ObservableList<Course> comboBoxItems = FXCollections
                .observableArrayList(loadCourseData());
        availableCoursesComboBox.setItems(comboBoxItems);

        // ArrayList to hold registered courses
        ArrayList<CourseRegistration> registrations = new ArrayList<>();

        gridLayout.setPadding(padding);
        borderLayout.setCenter(gridLayout);

        availableCoursesLabel.setPadding(padding);
        gridLayout.add(availableCoursesLabel, 0, 0);
        gridLayout.add(availableCoursesComboBox, 1, 0);

        registeredCoursesLabel.setPadding(padding);
        gridLayout.add(registeredCoursesLabel, 0, 1);
        // Limit height of ListView so it shows only 3 courses
        registeredCoursesListView.setMaxHeight(75);
        gridLayout.add(registeredCoursesListView, 1, 1);

        totalCreditsLabel.setPadding(padding);
        gridLayout.add(totalCreditsLabel, 0, 2);
        gridLayout.add(totalCreditsDisplay, 1, 2);

        gridLayout.add(saveRegistrationButton, 0, 3, 2, 1);

        Font messageFont = Font.font(21);
        messageLabel.setFont(messageFont);
        borderLayout.setBottom(messageLabel);

        Scene scene = new Scene(borderLayout);
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setScene(scene);
        stage.setTitle("Course Registration");
        stage.show();

        // Lambda expression to check if user ID has been entered & display
        // the GridPane with registration controls
//#2
        startRegistrationButton.setOnAction(e -> {
            if (learnerIDField.getText() != null && !learnerIDField.getText().isEmpty()) {
                gridLayout.setVisible(true);
                startRegistrationButton.setVisible(false);

                messageLabel.setTextFill(Color.BLUE);
                messageLabel.setText("Select a course forom the list above.");

                // Run the course loading on a separate thread
                new Thread(() -> {
                    try {
                        // Load course data (this can take time, so it's done in a separate thread)
                        ArrayList<Course> courses = loadCourseData();

                        // Once data is loaded, update the ComboBox on the JavaFX Application Thread
                        Platform.runLater(() -> {
                            // Update ComboBox items with the courses
                            comboBoxItems.setAll(courses);
                        });
                    } catch (Exception ex) {
                        // Handle any exceptions that occur during the data loading
                        Platform.runLater(() -> {
                            messageLabel.setTextFill(Color.RED);
                            messageLabel.setText("Error loading course data: " + ex.getMessage());
                        });
                    }
                }).start(); // Start the new thread
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Please enter learner ID.");
                learnerIDField.requestFocus();
            }
        });

        // Lambda expression to process selected item in ComboBox
//#3
        availableCoursesComboBox.setOnAction(e -> {
            // Get the selected course from the ComboBox
            Course selectedCourse = availableCoursesComboBox.getValue();

            if (selectedCourse != null) {
                // Validate the registration
                String validationMessage = validateRegistration(selectedCourse, registrations);

                if (validationMessage.isEmpty()) {
                    // Registration is valid, add the course to the registrations list
                    CourseRegistration registration = new CourseRegistration(learnerID.get(), selectedCourse.getCourseCode(), selectedCourse.getCreditHours());
                    registrations.add(registration);

                    // Update the registered courses ListView
                    registeredCoursesListView.getItems().add(registration);

                    // Update the total credits display
                    int totalCredits = registrations.stream().mapToInt(CourseRegistration::getCreditHours).sum();
                    totalCredit.set(totalCredits);

                    // Provide feedback to the user
                    messageLabel.setTextFill(Color.GREEN);
                    messageLabel.setText("Successfully registered for " + selectedCourse.getCourseCode());
                } else {
                    // If the registration is invalid, show the validation message
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText(validationMessage);
                }
            }
        });

        // Lambda expression to write registered courses to a file 
        saveRegistrationButton.setOnAction(e -> {
            // Start a new thread for saving the registrations to the database
            new Thread(() -> {
                try {
                    // Open a database connection
                    try (Connection connection = DriverManager.getConnection(mariaURI, mariaUser, mariaPassword)) {

                        // Prepare SQL insert statement
                        String insertSQL = "INSERT INTO learner_registration (learner_id, course_code, credit_hours) VALUES (?, ?, ?)";

                        // Use PreparedStatement for inserting data
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

                            // Loop through all registered courses and insert each into the database
                            for (CourseRegistration registration : registrations) {
                                preparedStatement.setString(1, registration.getLearnerID());
                                preparedStatement.setString(2, registration.getCourseCode());
                                preparedStatement.setInt(3, registration.getCreditHours());
                                preparedStatement.addBatch(); // Add to batch for efficiency
                            }

                            // Execute the batch insert
                            int[] result = preparedStatement.executeBatch();

                            // Check if the insert was successful
                            boolean success = result.length == registrations.size();

                            // Update UI on the JavaFX Application Thread after saving
                            Platform.runLater(() -> {
                                if (success) {
                                    messageLabel.setTextFill(Color.BLUE);
                                    messageLabel.setText("Registrations saved to Database");
                                } else {
                                    messageLabel.setTextFill(Color.RED);
                                    messageLabel.setText("Error saving registrations.");
                                }
                            });
                        }
                    } catch (SQLException ex) {
                        // Handle database-related exceptions
                        Platform.runLater(() -> {
                            messageLabel.setTextFill(Color.RED);
                            messageLabel.setText("Error saving registrations: " + ex.getMessage());
                        });
                    }
                } catch (Exception ex) {
                    // General exception handling
                    Platform.runLater(() -> {
                        messageLabel.setTextFill(Color.RED);
                        messageLabel.setText("Unexpected error: " + ex.getMessage());
                    });
                }
            }).start(); // Start the thread to save registrations
        });

    }

    // Method to load courses for ComboBox from database. Returns an 
    // ArrayList of Course objects
//#1
    public ArrayList<Course> loadCourseData() {
        // Add code to query available courses from database, put them in an 
        // ArrayList of Course objects and return the ArrayList
        ArrayList<Course> courses = new ArrayList<Course>();
        String query = "SELECT course_code, credit_hours FROM course_offerings ORDER BY course_code";
        try (Connection connection = DriverManager.getConnection(mariaURI, mariaUser, mariaPassword); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String courseCode = resultSet.getString("course_code");
                int creditHours = resultSet.getInt("credit_hours");
                courses.add(new Course(courseCode, creditHours));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /* Checks if registration request is allowed (not duplicate, not over 9 cr)
    c: Course in registration request
    registeredCourses: List of user's already registered courses
    If method returns an empty string, request is valid.
    Non-empty string indicates type of problem. */
    public String validateRegistration(Course c, List<CourseRegistration> registeredCourses) {
        // String will remain empty if no problems found
        String message = "";
        int registeredCredits = 0;

        for (CourseRegistration crsReg : registeredCourses) {
            if (crsReg.getCourseCode().equals(c.getCourseCode())) {
                message = "Already registered for " + c.getCourseCode();
                // If duplicate registration, no need to continue
                break;
            }
            // Total up registered credits
            registeredCredits += crsReg.getCreditHours();
        }
        // If message not already set, check if over max credit hours
        if (message.length() == 0 && registeredCredits + c.getCreditHours() > MAX_CREDITS) {
            message = c.getCourseCode() + " over " + MAX_CREDITS + " credit limit.";
        }
        return message;
    }

    /* Method to write registered courses to database's learner_registration
       table. The registrations parameter is a List of CourseRegisration objects */
    public void saveRegistrationsToDB(List<CourseRegistration> registrations) {
        // Add code to save registrations to the learner_registration table in the
        // database. 

    }

    public static void main(String[] args) {
        launch();
    }
}
