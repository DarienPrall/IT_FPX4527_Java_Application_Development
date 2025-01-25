package edu.capella.bsit.registerforcourse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
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
    // PART 1: DEFINING THE LEARNERID LABEL, TEXTFIELD, AND BUTTON
    private final TextField learnerIDField = new TextField();
    private final Label learnerIDFieldLabel = new Label("Learner ID: ");
    private final Button startRegistrationButton = new Button("Start Registration");
    
    // PART 2: DEFINING THE CONTAINER FOR AVAILABLE COURSES, ITS LABEL
    private final ComboBox<Course> availableCoursesComboBox = new ComboBox<>();
    private final Label availableCoursesLabel = new Label("Select a Course:");
    
    // PART 3: CREATING THE VIEW FOR REGISTERED COURSES, ITS LABEL, AND LABEL FOR TOTAL CREDITS
    private final ListView<CourseRegistration> registeredCoursesListView = new ListView<>();
    private final Label registeredCoursesLabel = new Label("Registered Courses: ");
    private final Label totalCreditsLabel = new Label("Total Credits: ");
    
    // PART 4: CREATING A LABEL TO DISPLAY THE TOTAL CREDITS
    private final Label totalCreditsDisplay = new Label();
    
    // PART 5: CREATING THE BUTTON TO SAVE REGISTERED COURSES TO A FILE WITH AN EMPTY LABEL
    private final Button saveRegistrationButton = new Button("Save Registered Courses to File");
    private final Label messageLabel = new Label();
    
    // PART 6: DEFINING THE BORDER AND THE GRID
    private final BorderPane borderLayout = new BorderPane();
    private final GridPane gridLayout = new GridPane();
    
    // PART 7: PLACING A LIMIT ON CREDITS OF 9
    private final int MAX_CREDITS = 9;
    
    @Override
    public void start(Stage stage) {
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
        ObservableList<Course> comboBoxItems = FXCollections
                .observableArrayList(loadCourseData("course.data.txt"));
        availableCoursesComboBox.setItems(comboBoxItems);
        
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
            // check to see if the value of the SimpleStringProperty is null (blank)
            // otherwise, set the messageLabel's to prompt the user to enter a learner ID and put the cursor in the learnerIDFiled by calling its requestFocus() method
            if (learnerID.get() != null && !learnerID.get().trim().isEmpty()) {
                gridLayout.setVisible(true);
                messageLabel.setTextFill(Color.BLUE);
                messageLabel.setText("Select a Course from the List Above");
                learnerIDField.requestFocus();
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Please enter a Learner ID");
                learnerIDField.requestFocus();
            }
            


        });
        
        // Lambda expression to process selected item in ComboBox
        availableCoursesComboBox.setOnAction( e -> {
            // ComboBox declared wtih type Course, so no type cast needed
            Course selectedCourse = availableCoursesComboBox.getValue();
            
            String validationMessage = validateRegistration(selectedCourse, registrations);
            if (validationMessage.isEmpty()) {
                CourseRegistration registration = new CourseRegistration(learnerID.get(), selectedCourse.getCourseCode(), selectedCourse.getCreditHours());
                registrations.add(registration);
                registeredCoursesListView.setItems(FXCollections.observableArrayList(registrations));
                totalCredit.set(totalCredit.get() + selectedCourse.getCreditHours());
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Registered for " + selectedCourse);
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText(validationMessage);
            }



        });
        
        // Lambda expression to write registered courses to a file 
        saveRegistrationButton.setOnAction( e -> {
            if (learnerID.get() != null && !learnerID.get().trim().isEmpty()) {
                String fileName = "Registrations_" + learnerID.get() + ".txt";
                writeRegistrationsToFile(fileName, registrations);
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Registration data has been saved to " + fileName);
                
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Please enter a LearnerID before saving");
            }



        });
    }

    // Method to load courses for ComboBox from text file
    public ArrayList<Course> loadCourseData(String fileName) {
        ArrayList<Course> courseList = new ArrayList<>();
        File dataFile = new File(fileName);
        // try with resource
        try(Scanner input = new Scanner(dataFile)) {
            while(input.hasNext()) {
                String courseCode = input.next();
                int creditHours = input.nextInt();
                courseList.add(new Course(courseCode, creditHours));
            }
        }
        catch(IOException ex) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Data file error: " + ex.getMessage());
        }
        catch(InputMismatchException ex) {
            messageLabel.setTextFill(Color.RED);
        }
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
    
    /* Method to write registered courses to output file. 
    fileName: Name for output file (will be overwritten, if exists)
    registrations: List of CourseRegisration objects */
    public void writeRegistrationsToFile(String fileName, List<CourseRegistration> registrations) {
        File outputFile = new File(fileName);
        // try with resource will automatically close file
        try(PrintWriter fileWriter = new PrintWriter(outputFile);) {
            for(CourseRegistration crsReg : registrations) {
                fileWriter.println(crsReg.getLearnerID() + ": " + crsReg);
            }
        }
        catch(IOException ex) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Output error: " + ex.getMessage());
        }
    }    
    
    public static void main(String[] args) {
        launch();
    } 
}