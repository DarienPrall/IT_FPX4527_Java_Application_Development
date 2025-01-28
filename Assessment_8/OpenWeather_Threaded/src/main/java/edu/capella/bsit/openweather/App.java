package edu.capella.bsit.openweather;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Scanner;

public class App extends Application {
    private final Label weatherInfo = new Label();
    // OPENWEAHTERMAP API
    private final String apiKey = "4583420194fb7df52aabecc1ddcb9be2";

    @Override
    public void start(Stage stage) {        
        // START CONTAINER
        GridPane appGrid = new GridPane();
        appGrid.setPadding(new Insets(20, 20, 20, 20));
        appGrid.setHgap(20);
        appGrid.setVgap(20);
        appGrid.setStyle(
                "-fx-background-color: #f0f0f0;"
        );
        
        // LATITUDE SECTION
        Label latitudeLabel = new Label("Latitude: ");
            latitudeLabel.setFont(Font.font(12));
            latitudeLabel.setAlignment(Pos.CENTER_RIGHT);
            latitudeLabel.setStyle(
                    "-fx-font-weight: bold;"
            );
        Label latitudeErrorLabel = new Label();
            latitudeErrorLabel.setFont(Font.font(8));
            latitudeErrorLabel.setStyle(
                    "-fx-text-fill: red;" +
                    "-fx-font-weight: bold;"
            );
        TextField latitudeInput = new TextField();
            latitudeInput.setFont(Font.font("Courier New", 12));
            latitudeInput.setAlignment(Pos.CENTER);
            latitudeInput.setPrefWidth(250);
            latitudeInput.setStyle(
                    "-fx-background-color: #ffffff;" +
                    "-fx-border-color: #ccc;" +
                    "-fx-border-radius: 5px;" +
                    "-fx-text-fill: #555555;"
            );
        HBox latitudeHBox = new HBox(10, latitudeLabel, latitudeInput);
            latitudeHBox.setAlignment(Pos.CENTER_RIGHT);
        
        // LONGITUDE SECTION
        Label longitudeLabel = new Label("Longitude: ");
            longitudeLabel.setFont(Font.font(12));
            longitudeLabel.setAlignment(Pos.CENTER_RIGHT);
            longitudeLabel.setStyle(
                    "-fx-font-weight: bold;"
            );
        Label longitudeErrorLabel = new Label();
        longitudeErrorLabel.setFont(Font.font(8));
            longitudeErrorLabel.setStyle(
                    "-fx-text-fill: red;" +
                    "-fx-font-weight: bold;"
            );
        TextField longitudeInput = new TextField();
            longitudeInput.setFont(Font.font("Courier New", 12));
            longitudeInput.setAlignment(Pos.CENTER);
            longitudeInput.setPrefWidth(250);
            longitudeInput.setStyle(
                    "-fx-background-color: #ffffff;" +
                    "-fx-border-color: #ccc;" +
                    "-fx-border-radius: 5px;" +
                    "-fx-text-fill: #555555;"
            );
        HBox longitudeHBox = new HBox(10, longitudeLabel, longitudeInput);
            longitudeHBox.setAlignment(Pos.CENTER_RIGHT);
        
        // BUTTON SECTION
        Button loadDataButton = new Button("Get Data");
            loadDataButton.setFont(Font.font(12));
            loadDataButton.setPrefWidth(150);
            loadDataButton.setAlignment(Pos.CENTER);
            loadDataButton.setPadding(new Insets(10, 20, 10, 20));
            loadDataButton.setStyle(
                    "-fx-background-color: rgb(51, 121, 246); " +
                    "-fx-text-fill: white;" +
                    "-fx-border-radius: 10px;" +
                    "-fx-border-width: 0px;" +
                    "-fx-padding: 10px 20px;" +
                    "-fx-cursor: hand;" +
                    "-fx-effect: dropshadow(gaussian, #888, 10, 0, 0, 2);"
            );
            
        // GRID DISPLAY
        appGrid.add(latitudeHBox, 0, 0);
        appGrid.add(latitudeErrorLabel, 1, 0);
        appGrid.add(longitudeHBox, 0, 1);
        appGrid.add(longitudeErrorLabel, 1, 1);
        appGrid.add(loadDataButton, 0, 2);
        
        // SCENE
        weatherInfo.setPadding(new Insets(20, 20, 20, 20));
        weatherInfo.setFont(Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 18));
        weatherInfo.setLineSpacing(1.25);
        appGrid.add(weatherInfo, 0, 1, 2, 1);
        var scene = new Scene(appGrid, 650, 400);
        stage.setScene(scene);
        stage.setTitle("Darien's Weather App");
        stage.show();
        
        loadDataButton.setOnAction( e -> {
            // Complete the lambda expression with code that uses the Thread class
            // to call the connectToOpenWeatherServer() method on a new thread. 
            // Then use Platform.runLater() to update the JavaFX user interface.
            // Be sure to include appropriate exception handling. 
            
            latitudeErrorLabel.setText("");
            longitudeErrorLabel.setText("");
            
            String latitudeText = latitudeInput.getText();
            String longitudeText = longitudeInput.getText();
            
            if (latitudeText.isEmpty()) {
                latitudeErrorLabel.setText("Latitude cannot be empty");
            }
            
            if (longitudeText.isEmpty()) {
                longitudeErrorLabel.setText("Longitude cannot be empty");
            }
            
            
        });
    }
    
    public Scanner connectToOpenWeatherServer(double lat, double lon, String apiKey) {
        Scanner scanner = null;

        // Complete code to connect to server using Socket or HttpURLConnection.
        // Be sure to include appropriate exception handling.

        return scanner;
    }

    public static void main(String[] args) {
        launch();
    }
}