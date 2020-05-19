package user_interface.classes.controller;

import engine.classes.Engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import user_interface.classes.Main;
import user_interface.classes.enums.ViewName;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of scene 'Game'. Due to this class, interaction between the user and the program is available.
 */
public class GameViewController implements Initializable {

    /**
     * Instance of class <code>Engine</code>. This object is the main calculation core of the application.
     */
    private Engine engine;

    /**
     * <code>Slider</code> instance used for changing the amount of fuel burned per second.
     */
    @FXML
    private Slider slider;

    /**
     * Background image.
     */
    @FXML
    private ImageView skyImage;

    /**
     * Scatter chart representing phase space of the rocket movement equation.
     */
    @FXML
    private ScatterChart<Double, Double> chart;

    /**
     * TextField showing current amount of fuel burned per second.
     */
    @FXML
    private TextField uTextField;

    /**
     * TextField showing current rocket speed.
     */
    @FXML
    private TextField velocityTextField;

    /**
     * TextField showing current rocket height.
     */
    @FXML
    private TextField heightTextField;

    /**
     * TextField showing the amount of fuel left.
     */
    @FXML
    private TextField fuelTextField;

    /**
     * Button used for starting the game.
     */
    @FXML
    private Button playButton;

    /**
     * Image of the rocket.
     */
    @FXML
    private ImageView rocketImage;

    /**
     * Image of the moon, which is translated upwards during the game.
     */
    @FXML
    private ImageView moonImage;

    /**
     * Method called after pressing the button 'play'. Calling this method will cause the game to start.
     * @param event <code>ActionEvent</code> instance.
     */
    @FXML
    void playButtonClicked(ActionEvent event) {

        //dzięki temu działają przyciski
        skyImage.requestFocus();

        /* przypisanie akcji klawiszom up down */
        Main.getSceneMap().get(ViewName.GAME).setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP:    slider.increment(); break;
                case DOWN:  slider.decrement(); break;
            }
        });


        chart.setAnimated(false);

        playButton.setOpacity(0);
        chart.setOpacity(1);

        engine.start();


    }

    /**
     * Method called after FXML file read. It sets properties to the fields of the class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String lastScoreString = "", input;
        try(BufferedReader br = new BufferedReader(new FileReader(new File("scores2.txt")))){
            while((input = br.readLine()) != null){
                lastScoreString = input.split(",")[1];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double lastHighScore = Double.parseDouble(lastScoreString);
        engine = new Engine();
        engine.setFields(heightTextField, velocityTextField,fuelTextField, uTextField, chart, slider, skyImage, rocketImage, moonImage, lastHighScore);
    }
}
