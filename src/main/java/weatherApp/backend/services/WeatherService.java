package weatherApp.backend.services;

import weatherApp.backend.model.City;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private final String GEOCODE_API = "https://geocoding-api.open-meteo.com/v1/search?name=";
    private final String WEATHER_API = "https://api.open-meteo.com/v1/forecast";

    private final String WEATHER_API_HISTORICAL = "https://archive-api.open-meteo.com/v1/era5";

    public List<City> getCities(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String geocodeUrl = GEOCODE_API + city;
        String geocodeResponse = restTemplate.getForObject(geocodeUrl, String.class);

        List<City> cities = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(geocodeResponse);
        JSONArray results = jsonResponse.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String name = result.getString("name");
            double latitude = result.optDouble("latitude");
            double longitude = result.optDouble("longitude");
            String country = result.optString("country");
            List<String> admins = new ArrayList<>();
            admins.add(result.optString("admin1"));
            admins.add(result.optString("admin2"));
            admins.add(result.optString("admin3"));

            City cityObj = new City(name, latitude, longitude, country, admins);
            cities.add(cityObj);
        }
        return cities;
    }
    public String getCurrentWeather(City city) {
        RestTemplate restTemplate = new RestTemplate();
        String weatherUrl = WEATHER_API + "?latitude=" + city.getLatitude() + "&longitude=" + city.getLongitude() + "&current_weather=true";
        return restTemplate.getForObject(weatherUrl, String.class);
    }

    public String getForecastWeather(City city) {
        RestTemplate restTemplate = new RestTemplate();
        String forecastUrl = WEATHER_API + "?latitude=" + city.getLatitude() + "&longitude=" + city.getLongitude() + "&hourly=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m";
        return restTemplate.getForObject(forecastUrl, String.class);
    }

    public String getHistoricalWeather(City city) {
        RestTemplate restTemplate = new RestTemplate();

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        String historicalUrl = WEATHER_API_HISTORICAL + "?latitude=" + city.getLatitude() + "&longitude=" + city.getLongitude() +
                "&start_date=" + formattedStartDate + "&end_date=" + formattedEndDate + "&hourly=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m";
        return restTemplate.getForObject(historicalUrl, String.class);
    }
}