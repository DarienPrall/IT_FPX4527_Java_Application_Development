package edu.capella.bsit.registration_hibernate;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;

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
    private final Label messageLabel = new Label();

    private final BorderPane borderLayout = new BorderPane();
    private final GridPane gridLayout = new GridPane();
    
    private EntityManagerFactory emf;
    private EntityManager em;
    private RegistrationService service;
    
    private final int MAX_CREDITS = 9;

    @Override
    public void start(Stage stage) {
        // Initialize EntityManager and RegistrationService
        emf = Persistence.createEntityManagerFactory("RegistrationService");
        em = emf.createEntityManager();
        service = new RegistrationService(em);

        // Bind the learner ID field
        SimpleIntegerProperty totalCredit = new SimpleIntegerProperty(0);
        totalCreditsDisplay.textProperty().bind(totalCredit.asString());

        SimpleStringProperty learnerID = new SimpleStringProperty();
        learnerIDField.textProperty().bindBidirectional(learnerID);

        // GridPane with registration controls hidden until user ID entered
        gridLayout.setVisible(false);
        gridLayout.setHgap(15);
        gridLayout.setVgap(15);
        final Insets padding = new Insets(10, 10, 10, 10);

        // Learner ID UI setup
        HBox learnerIDHBox = new HBox();
        learnerIDFieldLabel.setPadding(padding);
        learnerIDHBox.setSpacing(15);
        learnerIDHBox.getChildren().addAll(learnerIDFieldLabel, learnerIDField, startRegistrationButton);
        borderLayout.setPadding(padding);
        borderLayout.setTop(learnerIDHBox);

        availableCoursesComboBox.setPromptText("Courses");

        // Asynchronous task to load courses from the database
        Task<List<Course>> loadCoursesTask = new Task<>() {
            @Override
            protected List<Course> call() {
                return service.getAvailableCourses();
            }
        };
        
        loadCoursesTask.setOnSucceeded(e -> {
            List<Course> courses = loadCoursesTask.getValue();
            availableCoursesComboBox.getItems().setAll(courses);
        });

        new Thread(loadCoursesTask).start();

        // Handle course registration logic
        startRegistrationButton.setOnAction(e -> {
            if (learnerIDField.getText() != null) {
                gridLayout.setVisible(true);
                startRegistrationButton.setVisible(false);
                messageLabel.setTextFill(Color.DARKBLUE);
                messageLabel.setText("Select a course from the list above.");
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Enter ID.");
                learnerIDField.requestFocus();
            }
        });

        // Handle course selection
        availableCoursesComboBox.setOnAction(e -> {
            Course selectedCourse = availableCoursesComboBox.getValue();
            String learnerId = learnerID.get();
            // Validate and register for the selected course
            Task<String> registrationTask = new Task<>() {
                @Override
                protected String call() {
                    return service.validateRegistration(selectedCourse, learnerId);
                }
            };

            registrationTask.setOnSucceeded(event -> {
                String message = registrationTask.getValue();
                messageLabel.setText(message);
            });

            new Thread(registrationTask).start();
        });

        // Setup the UI
        gridLayout.setPadding(padding);
        borderLayout.setCenter(gridLayout);

        availableCoursesLabel.setPadding(padding);
        gridLayout.add(availableCoursesLabel, 0, 0);
        gridLayout.add(availableCoursesComboBox, 1, 0);

        registeredCoursesLabel.setPadding(padding);
        gridLayout.add(registeredCoursesLabel, 0, 1);
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
    }

    public static void main(String[] args) {
        launch();
    }
}
