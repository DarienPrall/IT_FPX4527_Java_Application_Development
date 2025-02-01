package edu.capella.bsit.registration_hibernate;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class App extends Application {
    // Hibernate objects
    EntityManagerFactory emf;
    EntityManager em;
    RegistrationService service;
    
    private final TextField learnerIDField = new TextField();
    private final Label learnerIDFieldLabel = new Label("Learner ID: ");
    private final Button startRegistrationButton = new Button("Start Registration");
    private final ComboBox<Course> availableCoursesComboBox = new ComboBox<>();
    private final Label availableCoursesLabel = new Label("Select a Course:");
    private final ListView<CourseRegistration> registeredCoursesListView = new ListView<>();
    private final Label registeredCoursesLabel = new Label("Registered Courses: ");
    private final Label totalCreditsLabel = new Label("Total Credits: ");        
    private final Label totalCreditsDisplay = new Label();
    //private final Button saveRegistrationButton = new Button("Save Registered Courses to File");
    private final Label messageLabel = new Label();
    
    private final BorderPane borderLayout = new BorderPane();
    private final GridPane gridLayout = new GridPane();
    
    private final int MAX_CREDITS = 9;
    
    private String mariaURI = "jdbc:mariadb://localhost:3306/registration";
    private String mariaUser = "root";
    private String mariaPassword = "P@ssword1";
    
    @Override
    public void start(Stage stage) {
        // Instantiate EntityManagerFactory, EntityManager, and RegistrationService
        
        SimpleIntegerProperty totalCredit = new SimpleIntegerProperty(0);
        totalCreditsDisplay.textProperty().bind(totalCredit.asString());
        
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
        
        availableCoursesComboBox.setPromptText("Courses");
        // Get list of available courses
        
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
        startRegistrationButton.setOnAction( e -> {  
            if(learnerIDField.getText() != null) {
                gridLayout.setVisible(true);
                startRegistrationButton.setVisible(false);
                messageLabel.setTextFill(Color.DARKBLUE);
                messageLabel.setText("Select a course from the list above.");
            }
            else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Enter ID.");
                learnerIDField.requestFocus();
            }
        });
        
        // Lambda expression to process selected item in ComboBox
        availableCoursesComboBox.setOnAction( e -> {
            // ComboBox declared wtih type Course, so no type cast needed
            Course c = availableCoursesComboBox.getValue();

            // Validate course selection using validateRegistration() below
            // If request is valid, save registration to the database.
            // Update the JavaFX to reflect success or failure.
            
            
        });
        
    }

    // Method to load courses for ComboBox from database
    public synchronized ArrayList<Course> loadCourseData() {
        ArrayList<Course> courseList = new ArrayList<>();



        return courseList;        
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
       
        for(CourseRegistration crsReg : registeredCourses) {
            if(crsReg.getCourseCode().equals(c.getCourseCode())) {
                message = "Already registered for " + c.getCourseCode();
                // If duplicate registration, no need to continue
                break;
            }
            // Total up registered credits
            registeredCredits += crsReg.getCreditHours();
        }
        // If message not already set, check if over max credit hours
        if(message.length() == 0 && registeredCredits + c.getCreditHours() > MAX_CREDITS) {
            message = c.getCourseCode() + " over " + MAX_CREDITS + " credit limit.";
        }
        return message;
    }
    
  
    
    public static void main(String[] args) {
        launch();
    } 
}