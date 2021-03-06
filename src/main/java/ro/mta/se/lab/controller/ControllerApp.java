package ro.mta.se.lab.controller;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Weather;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * <h1>ControllerApp Class</h1>
 * Class implementing the controller part of a model-view-controller design
 * pattern.
 *
 * <p>Is instantiated in Main.java class by FXMLLoader which has the default
 * behavior to create a new instance of the controller class and use that
 * instance as the controller.</p>
 *
 * <p>This class responds to the input and performs on the data model objects.
 * The input is generated by the class ReadInputFile.java and is composed of
 * city names and other characteristics. The model objects, created by classes
 * City.java and Weather.java, are controlled so as to be presented in a
 * particular format by view component of the architectural model, composed of
 * WeatherView.fxml file.</p>
 *
 * @author Novac Oana
 * @version 1.0
 */

public class ControllerApp {
    /**
     * Members description
     */
    public ReadInputFile myReadInputFile;
    public List<City> cityList;
    public Logger logger;
    public City currentCity;
    public Weather currentWeather;

    /**
     * Description of members that links to the elements of the fxml file
     */
    @FXML
    private ChoiceBox choiceCountry;
    @FXML
    private ChoiceBox choiceCity;
    @FXML
    private Label labelCity;
    @FXML
    private Label labelTime;
    @FXML
    private Label labelHum;
    @FXML
    private Label labelHumidity;
    @FXML
    private Label labelWin;
    @FXML
    private Label labelWind;
    @FXML
    private Label labelWeather;
    @FXML
    private Label labelTemp;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Button buttonCelsius;
    @FXML
    private Button buttonFahn;

    /**
     * Default constructor of ControllerApp class
     */
    public ControllerApp() {
        this.myReadInputFile = new ReadInputFile();
        this.cityList = new LinkedList<City>();
        this.currentCity = new City();
        this.currentWeather = new Weather();
        this.logger = Logger.getLogger("Weather App Logs");
    }

    /**
     * This method allows access to @FXML fields referring to components defined
     * in the WeatherView.fxml file.
     * <p>
     * Initially, the function for configuring the Logger is called. The
     * background is set and only the fields related to the selection of the
     * country and the city are visible and the country items are set. Next, two
     * Listeners are added that call the methods inside them when it is detected
     * that a country or a city has been selected from the respective fields.
     * When selecting the country, the method for setting the city items is
     * called and the country member of currentCity object is set. When
     * selecting the city, the currentCity member is set and all currentWeather
     * member fields are displayed after it is instantiating a
     * GetWeatherData.java class object. Also in this Listener, there are
     * created events by pressing two buttons that display the temperature in
     * celsius degree or fahrenheit degree. In addition, the elements that are
     * displayed in the user interface are written in the log file.
     */
    @FXML
    private void initialize() {
        this.setCityList();
        this.configureLogger();

        // Hide buttons
        buttonCelsius.setVisible(false);
        buttonFahn.setVisible(false);

        // Set background color
        BackgroundFill background_fill = new BackgroundFill(Color.LIGHTSKYBLUE,
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        anchorPane.setBackground(background);

        choiceCountry.setItems(getCountryList());

        // Listen for country selection
        choiceCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String s, String t1) {
                choiceCity.setItems(getCitiesList(t1));
                currentCity.setCountry((String) choiceCountry.getValue());
            }
        });

        // Listen for city selection
        choiceCity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String s, String t1) {

                String c = choiceCountry.getValue().toString();

                // Check if country of current city is the same as the one
                // selected by the user to listen after updating items
                if (c.equals(currentCity.getCountry())) {
                    setCityByName(t1);

                    // Make buttons visible
                    buttonCelsius.setVisible(true);
                    buttonFahn.setVisible(true);

                    // Obtain weather data for current city
                    setCurrentWeather();

                    // Write data to user interface
                    labelCity.setText(currentCity.getName()
                            + ", " + currentCity.getCountry());
                    labelTime.setText(getCurrentTime());
                    labelWin.setText(currentWeather.getWind() + " m/s");
                    labelHum.setText(currentWeather.getHumidity() + "%");
                    labelTemp.setText(convertKelvinToCelsius(currentWeather.getTemperature())
                            + " " + (char) 176 + "C");
                    labelWeather.setText(currentWeather.getMain() + ", "
                            + currentWeather.getDescription());
                    labelHumidity.setText("Humidity : ");
                    labelWind.setText("Wind : ");

                    // Display icon weather
                    String imageUrl = "http://openweathermap" + ".org"
                            + "/img/wn/" + currentWeather.getIcon() + "@2x.png";
                    Image image = new Image(imageUrl);
                    imageView.setImage(image);

                    // Event Celsius button
                    buttonCelsius.setOnAction(e -> {
                        labelTemp.setText(convertKelvinToCelsius(currentWeather.getTemperature())
                                + " " + (char) 176 + "C");
                    });

                    // Event Fahrenheit button
                    buttonFahn.setOnAction(e -> {
                        labelTemp.setText(convertKelvinToFahrenheit(currentWeather.getTemperature())
                                + " " + (char) 176 + "F");
                    });

                    // Write data to logFile
                    logger.info(labelCity.getText() + " : "
                            + labelTime.getText() + " : "
                            + labelWeather.getText() + ", "
                            + labelTemp.getText() + " , wind = "
                            + labelWin.getText() + ", humidity = "
                            + labelHum.getText() + ", image = "
                            + imageUrl);
                }
            }
        });
    }

    /**
     * This method creates a list of countries that are available in the input
     * file.
     *
     * @return sorted list of countries.
     */
    public ObservableList<String> getCountryList() {
        ObservableList<String> countries = new SimpleListProperty<String>(FXCollections.observableArrayList());
        for (City c : this.cityList) {
            if (!countries.contains(c.getCountry())) {
                countries.add(c.getCountry());
            }
        }
        Collections.sort(countries);
        return countries;
    }

    /**
     * This method creates a list of cities that are available in the input file
     * for a specific country.
     *
     * @param country The country for which the list of cities is created.
     * @return sorted list of cities.
     */
    public ObservableList<String> getCitiesList(String country) {
        ObservableList<String> cities = new SimpleListProperty<String>(FXCollections.observableArrayList());
        for (City c : this.cityList) {
            if (c.getCountry().equals(country)) {
                if (!cities.contains(c.getName())) {
                    cities.add(c.getName());
                }
            }
        }
        Collections.sort(cities);
        return cities;
    }

    /**
     * This method set currentCity member after the city is selected by the
     * user.
     *
     * @param name The name of the city that is selected by the user.
     */
    public void setCityByName(String name) {
        for (City c : this.cityList) {
            if (c.getName().equals(name)) {
                this.currentCity = c;
            }
        }
    }

    /**
     * This method is used to obtain the current time that appears in the user
     * interface.
     *
     * @return the current time in string format.
     */
    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' "
                + "HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    /**
     * This method converts from Kelvin degrees, as they are present in the json
     * file returned by the server, to Celsius degrees.
     *
     * @param temp The temperature value in Kelvin degrees.
     * @return the temperature value in Celsius degrees.
     */
    public String convertKelvinToCelsius(Float temp) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Double celsiusTemp = temp - 273.15;
        return df.format(celsiusTemp);
    }

    /**
     * This method converts from Kelvin degrees, as they are present in the json
     * file returned by the server, to Fahrenheit degrees.
     *
     * @param temp The temperature value in Kelvin degrees.
     * @return the temperature value in Fahrenheit degrees.
     */
    public String convertKelvinToFahrenheit(Float temp) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Double celsiusTemp = (temp * 9.0) / 5.0 - 459.67;
        return df.format(celsiusTemp);
    }

    /**
     * This method is used to configure the Logger member through which the
     * display history will be written in the log file "logFile.txt".
     * Logger needs a handler and a formatter to open the file in append mode
     * and to write data to it.
     */
    private void configureLogger() {
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("src/main/resources/logFile.txt",
                    true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to obtain the data of the currentWeather. It is
     * instantiated a GetWeatherData object, it is set the id of current city
     * and the data is returned as an array of strings.
     */
    private void setCurrentWeather() {
        GetWeatherData obj = new GetWeatherData();
        obj.setCityId(currentCity.getId());
        String data[] = obj.getCurrentWeather();

        currentWeather.setDescription(data[0]);
        currentWeather.setMain(data[1]);
        currentWeather.setIcon(data[2]);
        currentWeather.setTemperature(Float.parseFloat(data[3]));
        currentWeather.setHumidity(Float.parseFloat(data[4]));
        currentWeather.setWind(Float.parseFloat(data[5]));
    }

    /**
     * This method calls the getListCities() method of ReadInputFile class in
     * order to set the the member list which contains all cities.
     */
    public void setCityList() {
        this.cityList = myReadInputFile.getListCities();
    }
}