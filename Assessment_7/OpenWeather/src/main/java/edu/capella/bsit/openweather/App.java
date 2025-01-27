package edu.capella.bsit.openweather;

import java.io.*;
import java.net.*;
import java.util.Scanner;
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


/**
 * JavaFX App
 */

// Sample URL: https://api.openweathermap.org/data/2.5/weather?lat=44.9778&lon=-93.2650&appid=efa9914c7b90e2908ea267c546035a3f

public class App extends Application {
    // THE URL CAN BE SPLIT INTO DIFFERENT SECTIONS AND STORED TO VARIABLES
    Label weatherInfo = new Label();
    private final String serverAddr = "api.openweathermap.org";
    private final String appPath = "/data/2.5/weather";
    private final String queryStringFormat = "lat=%f&lon=%f&appid=%s";
    // Add OpenWeatherMap API key
    private final String apiKey = "4583420194fb7df52aabecc1ddcb9be2";

    @Override
    public void start(Stage stage) {
        // CREATING APP VIEW INCLUDING LABLES AND INPUTFIELDS
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15, 15, 15, 15));
        Label latPrompt = new Label("Latititude:  ");
        latPrompt.setFont(Font.font(15));
        latPrompt.setAlignment(Pos.BOTTOM_RIGHT);
        TextField latInputField = new TextField();
        latInputField.setFont(Font.font(15));
        latInputField.setAlignment(Pos.BASELINE_LEFT);
        HBox latHBox = new HBox(latPrompt, latInputField);
        latHBox.setPadding(new Insets(10, 10, 10, 10));
        Label lonPrompt = new Label("Longitude:  ");
        lonPrompt.setFont(Font.font(15));
        lonPrompt.setAlignment(Pos.BOTTOM_RIGHT);
        TextField lonInputField = new TextField();
        lonInputField.setFont(Font.font(15));
        lonInputField.setAlignment(Pos.BASELINE_LEFT);
        HBox lonHBox = new HBox(lonPrompt, lonInputField);
        lonHBox.setPadding(new Insets(10, 10, 10, 10));
        Button loadDataButton = new Button("Get Data");
        grid.add(latHBox, 0, 0);
        grid.add(lonHBox, 1, 0);
        grid.add(loadDataButton, 2, 0);
        weatherInfo.setPadding(new Insets(20, 20, 20, 20));
        weatherInfo.setFont(Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 18));
        weatherInfo.setLineSpacing(1.25);
        grid.add(weatherInfo, 0, 1, 2, 1);
        var scene = new Scene(grid, 650, 400);
        stage.setTitle("Darien Prall's Weather App");
        stage.setScene(scene);
        stage.show();
        
        // CREATING THE ACTION WHEN BUTTON IS PRESSED
        loadDataButton.setOnAction( e -> {
            double lat = Double.parseDouble(latInputField.getText());
            double lon = Double.parseDouble(lonInputField.getText());
            
            // LAUNCHES THE CONNECTTOOPENWEATHERSERVER METHOD
            Scanner weatherDataInput = connectToOpenWeatherServer(lat, lon, apiKey);
            if (weatherDataInput != null) {
                Weather weather = WeatherParser.parseJsonWeatherData(weatherDataInput);
                weatherInfo.setText(weather.toString());
                String location = weather.getLocation();
                stage.setTitle("Darien Prall's Weather App: Weather Conditions for " + location);
            // IF WEATHERINFO IS NULL, THROW AND ERROR
            } else {
                weatherInfo.setText("Failed to retrieve weather data. ");
            }

        });
    }
    
    public Scanner connectToOpenWeatherServer(double lat, double lon, String apiKey) {
        Scanner scanner = null;
        
        // THE URL NEEDS TO BE CREATED FIRST
        try {
            String mainQuery = String.format(queryStringFormat, lat, lon, apiKey);
            String urlString = "https://" + serverAddr + appPath + "?" + mainQuery;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                scanner = new Scanner(inputStream);
            } else {
                System.out.println("There was an error, code: " + responseCode);
            }
            
        // THROW AN ERROR IF THE CONNECTION WAS UNSUCCESSFUL
        } catch (IOException e) {
            System.err.println("There was an error while connecting to OpenWeatherMap: " + e.getMessage());
        }
        
        return scanner;
    }

    public static void main(String[] args) {
        launch();
    }
}