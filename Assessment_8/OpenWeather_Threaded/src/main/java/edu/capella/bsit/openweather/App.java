package edu.capella.bsit.openweather;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
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
import javafx.application.Platform;

public class App extends Application {

    private final Label weatherInfo = new Label();
    // Add your OpenWeatherMap API key below
    private final String apiKey = "4583420194fb7df52aabecc1ddcb9be2";

    @Override
    public void start(Stage stage) {
        // ===========================================
        // Redo the code below to your own design for 
        // displaying the weather data.
        // ===========================================
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
        stage.setScene(scene);
        stage.show();

        loadDataButton.setOnAction(e -> {
            // Get latitude and longitude values from the input fields
            String latStr = latInputField.getText();
            String lonStr = lonInputField.getText();

            // Validate input
            if (latStr.isEmpty() || lonStr.isEmpty()) {
                weatherInfo.setText("Please enter both latitude and longitude.");
                return;
            }

            try {
                // Parse the latitude and longitude into doubles
                double lat = Double.parseDouble(latStr);
                double lon = Double.parseDouble(lonStr);

                // Create a new thread to fetch data from OpenWeatherMap server
                new Thread(() -> {
                    try {
                        // Call connectToOpenWeatherServer() method asynchronously
                        Scanner scanner = connectToOpenWeatherServer(lat, lon, apiKey);

                        // Parse the weather data into a Weather object
                        Weather weather = WeatherParser.parseJsonWeatherData(scanner);

                        // Update the UI using Platform.runLater()
                        Platform.runLater(() -> {
                            if (weather != null) {
                                weatherInfo.setText(weather.toString()); // Display the weather data
                            } else {
                                weatherInfo.setText("Failed to retrieve weather data.");
                            }
                        });

                    } catch (Exception ex) {
                        // Handle any exceptions during data fetching or parsing
                        Platform.runLater(() -> {
                            weatherInfo.setText("Error fetching weather data: " + ex.getMessage());
                        });
                    }
                }).start(); // Start the thread
            } catch (NumberFormatException ex) {
                weatherInfo.setText("Invalid input. Please enter valid latitude and longitude.");
            }
        });

    }

    public Scanner connectToOpenWeatherServer(double lat, double lon, String apiKey) {
        Scanner scanner = null;
        try {
            // OpenWeatherMap API endpoint for weather data
            String host = "api.openweathermap.org";
            int port = 80; // Standard HTTP port
            String path = "/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;

            // Create a socket connection to the OpenWeatherMap server
            Socket socket = new Socket(host, port);

            // Set up input and output streams
            OutputStream outputStream = socket.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());

            // Send an HTTP GET request
            String request = "GET " + path + " HTTP/1.1\r\n"
                    + "Host: " + host + "\r\n"
                    + "Connection: close\r\n"
                    + "\r\n";
            outputStream.write(request.getBytes());
            outputStream.flush();

            // Create a scanner to read the response from the server
            scanner = new Scanner(inputStreamReader);

            // Wait for the response and close the socket after receiving the data
            socket.close();
        } catch (IOException e) {
            // Handle any IOException (e.g., network issues)
            e.printStackTrace();
            weatherInfo.setText("Error connecting to server. Please try again later.");
        }

        return scanner;
    }

    public static void main(String[] args) {
        launch();
    }
}
