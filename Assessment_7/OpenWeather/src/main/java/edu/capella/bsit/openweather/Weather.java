package edu.capella.bsit.openweather;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Weather {
    private final String location;
    private final String conditions;
    private final double currentTemperature;
    private final double pressure;
    private final int humidity;
    private final double windSpeed;
    private final int windDirection;
    private final long dtSeconds;

    public Weather(String location, String conditions, double currentTemperature, 
            double pressure, int humidity, double windSpeed, int windDirection, 
            long dtSeconds) {
        this.location = location;
        this.conditions = conditions;
        this.currentTemperature = currentTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.dtSeconds = dtSeconds;
    }
        
    public String getLocation() {
        return location;
    }
    
    public String getConditions() {
        return conditions;
    }

    public double getCurrentTemperatureF() {
        // Convert Kelvin to Fahrenheit
        return (currentTemperature - 273.15) * 9 / 5 + 32;
    }

    public double getPressureInHg() {
        // Convert from hectopascals to inches of mercury
        return pressure * 0.02953;
    }

    public int getHumidity() {
        // relative humidity already a percentage - no conversion
        return humidity;
    }

    public double getWindSpeed() {
        // Convert meters per second to MPH
        return windSpeed * 2.23694;
    }

    public int getWindDirection() {
        // Direction in degrees (compass heading)
        return windDirection;
    }
    
    public ZonedDateTime getReadingDateTime() {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(dtSeconds) , ZoneId.systemDefault());
        return zdt;
    }
    
    @Override
    public String toString() {
        // Format for zoned date time data to use with toString() output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
        return String.format(
            // Multiline string with formatting conversion chars
            """
            Location:       %s
            Date/Time:      %s
            Conditions:     %s
            Temperature:    %.1f\u00b0 F
            Humidity:       %d%%
            Pressure:       %.2f\" Hg
            Wind Speed:     %.1f MPH
            Wind Direction: %s (%d\u00b0)
            """,
            this.getLocation(), this.getReadingDateTime().format(formatter),
            this.getConditions(), this.getCurrentTemperatureF(), this.getHumidity(),
            this.getPressureInHg(), this.getWindSpeed(), this.getWindCompassDirection(),
            this.getWindDirection()
        );
    }

    public String getWindCompassDirection() {
        final String[] directions = {"N", "NNE", "NE", "ENE",
                                     "E", "ESE", "SE", "SSE",
                                     "S", "SSW", "SW", "WSW",
                                     "W", "WNW", "NW", "NNW"};
        // Math.round() returns a long, so cast to int.
        // Each compass direction covers 22.5 deg.
        // N includes 348.75 - 360, so modulus needed to limit to 0 - 15.
        return directions[(int) Math.round(windDirection / 22.5) % 16];
    }
}
