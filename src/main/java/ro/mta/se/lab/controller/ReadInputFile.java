package ro.mta.se.lab.controller;

import javafx.beans.property.*;
import ro.mta.se.lab.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>ReadInputFile Class</h1>
 * This class represents a part of the controller component of the
 * model-view-controller architecture.
 *
 * <p>Through this class, the input file, that contains several name of cities
 * and their characteristics, is parsed and a list of City objects is created.
 * The input file name "in.txt" is hard coded.</p>
 *
 * @author Novac Oana
 * @version 1.0
 */
public class ReadInputFile {
    /**
     * Members description
     */
    private List<City> listCities;

    /**
     * Default Constructor of ReadInputFile class
     */
    public ReadInputFile() {
        this.listCities = new LinkedList<City>();
    }

    /**
     * This method reads from the input file and it calls file parsing method
     * for each line extracted.
     */
    private void readFromFile() {
        try {
            File myFile = new File("src/main/resources/in.txt");
            Scanner myScanner = new Scanner(myFile);
            while (myScanner.hasNextLine()) {
                String line = myScanner.nextLine();
                if (line.startsWith("ID")) {
                    continue;
                } else {
                    parseLine(line);
                }
            }
            myScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method parses the a line extracted from the input file and creates
     * a City object it adds to the listCities list.
     *
     * @param line The string line extracted from file.
     */
    private void parseLine(String line) {
        String[] split = line.split("\\s+");
        IntegerProperty id = new SimpleIntegerProperty(Integer.parseInt(split[0]));
        StringProperty name = new SimpleStringProperty(split[1]);
        FloatProperty latitude = new SimpleFloatProperty(Float.parseFloat(split[2]));
        FloatProperty longitude = new SimpleFloatProperty(Float.parseFloat(split[3]));
        StringProperty countryCode = new SimpleStringProperty(split[4]);

        City city = new City(id, name, countryCode, latitude, longitude);
        this.listCities.add(city);
    }

    /**
     * This function calls the method readFromFile() and provides the list of
     * cities extracted from the file.
     *
     * @return the listCities member
     */
    public List<City> getListCities() {
        this.readFromFile();
        return this.listCities;
    }
}
