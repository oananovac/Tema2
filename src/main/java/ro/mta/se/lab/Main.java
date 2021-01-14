package ro.mta.se.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.ControllerApp;

import java.io.IOException;

/**
 * <h1>Main Class</h1>
 * This class contains main() method that is the key to making this program
 * executable.
 *
 * <p>Main class inherits the Application class and implements start method in
 * order to create a javaFX application. Main class creates a scene which will
 * contain all the items with @FXML tag from ControllerApp class. This class is
 * instantiated by FXMLLoader object. Also in this method, it is set the
 * application icon, the title and the location where the file WeatherView.fxml
 * is found.</p>
 *
 * @author Novac Oana
 * @version 1.0
 */

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();

        try {
            primaryStage.getIcons().add(new Image("cloud1.png"));
            primaryStage.setTitle("Your Weather App");
            loader.setLocation(this.getClass().getResource("/view/WeatherView" +
                    ".fxml"));
            loader.setController(new ControllerApp());
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
