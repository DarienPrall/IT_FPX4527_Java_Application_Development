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
        startRegistrationButton.setOnAction( e -> {  
            if(learnerIDField.getText() != null) {
                gridLayout.setVisible(true);
                startRegistrationButton.setVisible(false);
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
            // Use validateRegistration() method to check registration. 
            // Add valid registration requests to the registrations ArrayList. 



        });
        
        // Lambda expression to write registered courses to a file 
        saveRegistrationButton.setOnAction( e -> {
            // Use a separate Thread to call the saveRegistrationsToDB() method


        });
    }

    // Method to load courses for ComboBox from database. Returns an 
    // ArrayList of Course objects
    public ArrayList<Course> loadCourseData() {
        // Add code to query available courses from database, put them in an 
        // ArrayList of Course objects and return the ArrayList


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