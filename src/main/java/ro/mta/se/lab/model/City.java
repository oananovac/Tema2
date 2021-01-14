package ro.mta.se.lab.model;

import javafx.beans.property.*;

/**
 * <h1>City Class</h1>
 * This class represents a part of the model component of the
 * model-view-controller architecture.
 *
 * <p>It shapes the behavior and instantiates the City object. The role of it
 * is to store the items of interest from the input file that describes the
 * characteristics of cities. Besides Constructors, the class only shows set
 * and get methods.</p>
 *
 * @author Novac Oana
 * @version 1.0
 */
public class City {
    /**
     * Members description
     */
    IntegerProperty id;
    StringProperty name;
    StringProperty country;
    FloatProperty latitude;
    FloatProperty longitude;

    /**
     * Default constructor of City class
     */
    public City() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
        this.latitude = new SimpleFloatProperty();
        this.longitude = new SimpleFloatProperty();
    }

    /**
     * City class constructor with arguments
     *
     * @param id        City ID in input file.
     * @param name      City name.
     * @param country   The country code where the city is located.
     * @param latitude  Latitude coordinate of the city.
     * @param longitude Longitude coordinate of the city.
     */
    public City(IntegerProperty id, StringProperty name,
                StringProperty country, FloatProperty latitude,
                FloatProperty longitude) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty countryProperty() {
        return country;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public float getLatitude() {
        return latitude.get();
    }

    public void setLatitude(float latitude) {
        this.latitude.set(latitude);
    }

    public FloatProperty latitudeProperty() {
        return latitude;
    }

    public float getLongitude() {
        return longitude.get();
    }

    public void setLongitude(float longitude) {
        this.longitude.set(longitude);
    }

    public FloatProperty longitudeProperty() {
        return longitude;
    }
}
