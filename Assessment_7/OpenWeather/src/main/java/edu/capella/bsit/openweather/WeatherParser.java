package edu.capella.bsit.openweather;

import java.io.StringReader;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class WeatherParser {
    public static Weather parseJsonWeatherData(Scanner weatherDataInput) {
       /**
        *  Scanner will read JSON data file contents into a string. 
        *  Then use StringReader to pass JSON to JsonReader.
        *   JsonReader will get complete JsonObject with weather data.
        */
       Weather currentConditions = null;
       if(weatherDataInput != null && weatherDataInput.hasNextLine()) {
           String jsonString = weatherDataInput.nextLine();
           System.out.println("JSON: " + jsonString);
           StringReader stringData = new StringReader(jsonString);
           JsonObject jsonWeatherData;
           // try with resources guarantees reader will be closed
           try (JsonReader jsonReader = Json.createReader(stringData)) {
               // Get top-level JSON object with complete weather data
               jsonWeatherData = jsonReader.readObject();
               System.err.println(jsonWeatherData);
           }

           // Top-level item called name in JSON object is location
           String location = jsonWeatherData.getString("name");
           // Country abbreviation is in sys
           JsonObject sys = jsonWeatherData.getJsonObject("sys");
           if(sys.containsKey("country")) {
               location += " (" + sys.getString("country") + ")";
           }
           // Get conditions summary from weather subdoc
           JsonArray jsonSummary = jsonWeatherData.getJsonArray("weather");
           String conditions = jsonSummary.getJsonObject(0).getString("main") + " (";
           conditions += jsonSummary.getJsonObject(0).getString("description") + ")";
           // Get main JSON object with main body of weather data
           JsonObject jsonMainData = jsonWeatherData.getJsonObject("main");
           // Read temperature data and convert JsonNumber to double
           double currentTemp = jsonMainData.getJsonNumber("temp").doubleValue();
           double pressure = jsonMainData.getJsonNumber("pressure").doubleValue();
           int humidity = jsonMainData.getJsonNumber("humidity").intValue();
           // The wind data is in a JSON subdocument called wind
           JsonObject windData = jsonWeatherData.getJsonObject("wind");
           // Value from getJsonNumber() needs to be converted to a Java int
           int windDirection = windData.getJsonNumber("deg").intValue();
           // Convert wind speed from JsonNumber to Java double
           double windSpeed = windData.getJsonNumber("speed").doubleValue();

           // Date & time stamp for the data.
           // The # of seconds since 00:00:00 01/01/1970 UTC is in dt field
           long seconds = jsonWeatherData.getJsonNumber("dt").longValue();

           //Instantiate a Weather object to hold weather information.
           //Weather class is designed by learner. Will need to adjust code below to
           //work with Weather class
           currentConditions = new Weather(location, conditions, currentTemp,
                   pressure, humidity, windSpeed, windDirection, seconds);
       }
       return currentConditions;
    }
}    
