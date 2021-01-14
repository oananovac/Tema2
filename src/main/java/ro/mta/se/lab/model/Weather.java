package ro.mta.se.lab.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <h1>Weather Class</h1>
 * This class represents a part of the model component of the
 * model-view-controller architecture.
 *
 * <p>It shapes the behavior and instantiates the Weather object. The role of
 * it is to store the items of interest from the file returned by server.
 * Besides Constructors, the class only shows set and get methods.</p>
 *
 * @author Novac Oana
 * @version 1.0
 */
public class Weather {
    /**
     * Members description
     */
    StringProperty main;
    StringProperty description;
    StringProperty icon;
    FloatProperty humidity;
    FloatProperty wind;
    FloatProperty temperature;

    /**
     * Default constructor of Weather class
     */
    public Weather() {
        this.main = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.icon = new SimpleStringProperty();
        this.humidity = new SimpleFloatProperty();
        this.wind = new SimpleFloatProperty();
        this.temperature = new SimpleFloatProperty();
    }

    /**
     * Weather class constructor with arguments
     *
     * @param main        Used for weather description
     * @param description Used for weather description
     * @param icon        Code of icon
     * @param humidity    Describe percent of humidity
     * @param wind        Describe speed of wind
     * @param temperature Describe value of temperature
     */
    public Weather(StringProperty main, StringProperty description,
                   StringProperty icon, FloatProperty humidity,
                   FloatProperty wind, FloatProperty temperature) {
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.humidity = humidity;
        this.wind = wind;
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity.get();
    }

    public void setHumidity(float humidity) {
        this.humidity.set(humidity);
    }

    public FloatProperty humidityProperty() {
        return humidity;
    }

    public float getWind() {
        return wind.get();
    }

    public void setWind(float wind) {
        this.wind.set(wind);
    }

    public FloatProperty windProperty() {
        return wind;
    }

    public float getTemperature() {
        return temperature.get();
    }

    public void setTemperature(float temperature) {
        this.temperature.set(temperature);
    }

    public FloatProperty temperatureProperty() {
        return temperature;
    }

    public String getMain() {
        return main.get();
    }

    public void setMain(String main) {
        this.main.set(main);
    }

    public StringProperty mainProperty() {
        return main;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getIcon() {
        return icon.get();
    }

    public void setIcon(String icon) {
        this.icon.set(icon);
    }

    public StringProperty iconProperty() {
        return icon;
    }
}
