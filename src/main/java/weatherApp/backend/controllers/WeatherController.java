package weatherApp.backend.controllers;

import weatherApp.backend.services.WeatherService;
import weatherApp.backend.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/city/{cityName}")
    public List<City> getCities(@PathVariable String cityName) {
        return weatherService.getCities(cityName);
    }

    @GetMapping("/current/{cityName}")
    public String getCurrentWeather(@PathVariable String cityName) {
        List<City> cities = weatherService.getCities(cityName);
        if (cities.isEmpty()) {
            return "City not found";
        }
        return weatherService.getCurrentWeather(cities.get(0));
    }

    @GetMapping("/forecast/{cityName}")
    public String getForecastWeather(@PathVariable String cityName) {
        List<City> cities = weatherService.getCities(cityName);
        if (cities.isEmpty()) {
            return "City not found";
        }
        return weatherService.getForecastWeather(cities.get(0));
    }

    @GetMapping("/historical/{cityName}")
    public String getHistoricalWeather(@PathVariable String cityName) {
        List<City> cities = weatherService.getCities(cityName);
        if (cities.isEmpty()) {
            return "City not found";
        }
        return weatherService.getHistoricalWeather(cities.get(0));
    }
}