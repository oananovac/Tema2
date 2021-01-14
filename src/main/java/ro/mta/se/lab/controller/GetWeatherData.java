package ro.mta.se.lab.controller;


import org.json.JSONObject;
import ro.mta.se.lab.model.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <h1>GetWeatherClass Class</h1>
 * This class represents a part of the controller component of the
 * model-view-controller architecture.
 *
 * <p>This class deals with obtaining data about the current weather of a city
 * by using the API provided by OpenWeatherMap and creates a Weather object
 * using the extracted elements. The connection with this API is made on the
 * basis of a key received after creating an account.</p>
 *
 * @author Novac Oana
 * @version 1.0
 * @see <a href="https://openweathermap.org/api">https://openweathermap.org/api
 * </a>
 * @see Class Weather.java
 */
public class GetWeatherData {
    /**
     * Members description
     */
    private final StringBuffer weatherData;
    private HttpURLConnection connection;
    private Integer cityId;

    /**
     * Constructor of GetWeatherData class
     *
     * @param cityId The weather search is based on the city ID
     */
    public GetWeatherData(Integer cityId) {
        this.weatherData = new StringBuffer();
        this.cityId = cityId;
    }

    /**
     * This method parses the string in json format returned by server and
     * creates a Weather object to which it sets the parameters
     *
     * @param myJson The json string received from the server
     * @return the Weather object created
     */
    private static Weather getWeatherData(String myJson) {
        Weather currentWeather = new Weather();

        JSONObject jsonObject = new JSONObject(myJson);
        currentWeather.setDescription(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
        currentWeather.setMain(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
        currentWeather.setIcon(jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon"));
        currentWeather.setTemperature(jsonObject.getJSONObject("main").getFloat("temp"));
        currentWeather.setHumidity(jsonObject.getJSONObject("main").getFloat("humidity"));
        currentWeather.setWind(jsonObject.getJSONObject("wind").getFloat("speed"));

        return currentWeather;
    }

    /**
     * In this method the connection with API is made, is checked and the data
     * stream is stored in weatherData member line by line.
     */
    private void obtainJSONData() {
        BufferedReader reader;
        String line;

        try {
            String apiKey = "ed5023c5edf0f36887d312934e014c0d";
            String urlString = "http://api.openweathermap.org/data/2.5/weather?id="
                    + this.cityId + "&appid=" + apiKey;
            URL url = new URL(urlString);
            this.connection = (HttpURLConnection) url.openConnection();

            // Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status > 399) {
                // The connection was not established due to a client or server
                // error and it is read error stream
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    this.weatherData.append(line);
                }
                reader.close();
            } else {
                // The connection was established and it is read input stream
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    this.weatherData.append(line);
                }
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * This method calls the two previous private methods to extract and parse
     * information. This method is a public one and it can be called outside.
     *
     * @return the Weather object extracted according to the specific city.
     */
    public Weather getCurrentWeather() {
        obtainJSONData();
        return getWeatherData(this.weatherData.toString());
    }
}
