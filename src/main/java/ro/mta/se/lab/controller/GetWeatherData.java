package ro.mta.se.lab.controller;

import org.json.JSONObject;

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
    private Integer cityId;
    private String apiKey;
    private String urlString;

    /**
     * Constructor of GetWeatherData class
     */
    public GetWeatherData() {
        this.weatherData = new StringBuffer();
        this.cityId = 0;
        this.apiKey = new String();
        this.urlString = new String();
    }

    /**
     * This method parses the string in json format returned by server and
     * creates an array of strings which contains all the parameters of
     * Weather class.
     *
     * @param myJson The json string received from the server
     * @return the array with Weather characteristics
     */
    public String[] parseData(String myJson) {
        JSONObject jsonObject = new JSONObject(myJson);

        String parsed[] = new String[6];
        Float myFloat;

        parsed[0] = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        parsed[1] = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
        parsed[2] = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
        myFloat = jsonObject.getJSONObject("main").getFloat("temp");
        parsed[3] = myFloat.toString();
        myFloat = jsonObject.getJSONObject("main").getFloat("humidity");
        parsed[4] = myFloat.toString();
        myFloat = jsonObject.getJSONObject("wind").getFloat("speed");
        parsed[5] = myFloat.toString();

        return parsed;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * This method provides the url for connection to API server.
     *
     * @param cityId The id of city for which weather data are obtained.
     * @param apiKey The key used to connect to the server.
     * @return url in string format
     */
    public String getUrlStringById(String cityId, String apiKey) {
        return "http://api.openweathermap.org/data/2.5/weather?id=" + cityId
                + "&appid=" + apiKey;
    }

    /**
     * In this method the connection with API is made, is checked and the data
     * stream is stored in a string buffer line by line.
     *
     * @param apiKey    The key used to connect to the server.
     * @param urlString The url for connection to API server.
     * @return string buffer which contains the json data of weather.
     */
    public StringBuffer obtainJSONData(String apiKey, String urlString) {
        BufferedReader reader;
        String line;
        StringBuffer data = new StringBuffer();
        HttpURLConnection connection;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

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
                    data.append(line);
                }
                reader.close();
            } else {
                // The connection was established and it is read input stream
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
                reader.close();
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return data;
    }

    /**
     * This method calls the two previous methods to extract and parse
     * information.
     *
     * @return parsed data that is stored into a string buffer.
     */
    public String[] getCurrentWeather() {
        setApiKey("ed5023c5edf0f36887d312934e014c0d");
        StringBuffer myJson = obtainJSONData(this.apiKey,
                getUrlStringById(this.cityId.toString(), this.apiKey));

        return parseData(myJson.toString());
    }
}
