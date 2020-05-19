package user_interface.classes.controller;

import engine.classes.Engine;
import engine.classes.ScoreIOManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import user_interface.classes.Main;
import user_interface.classes.enums.ViewName;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.text.TextAlignment.CENTER;

/**
 * Controller of scene 'Score'. Due to this class, interaction between the user and the program is available.
 */
public class WinViewController implements Initializable {

    /**
     * Instance of class <code>ScoreIOManager</code> used for score save and read.
     */
    ScoreIOManager scoreIOManager = new ScoreIOManager();

    /**
     * Label used for presenting information about the score.
     */
    @FXML
    private Label infoLabel;

    /**
     * <code>TextField</code> used for getting user's nickname.
     */
    @FXML
    private TextField nameTextField;

    /**
     * Method called after pressing button 'Back". It checks if user's score should be saved and returns to the main menu.
     * @param event <code>ActionEvent</code> instance.
     */
    @FXML
    void onClickBackBtn(ActionEvent event) {

        if(Engine.isScoreNeededToBeSaved()){
            String name = (nameTextField.getText().equals("")) ? "YouForgotYourName" : nameTextField.getText();
            String score = String.valueOf(Engine.getHowMuchFuelLeft());
            scoreIOManager.updateScore(new String[]{name, score});
        }

        Main.getStage().setScene(Main.getSceneMap().get(ViewName.START));
    }

    /**
     * Method called after FXML file read. It sets properties to the fields of the class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String message = "You landed with " + String.valueOf(Engine.getHowMuchFuelLeft()) + " kilograms of fuel " +
                "left!\n Please type in your name. ";
        infoLabel.setText(message);
        infoLabel.setTextAlignment(CENTER);
        infoLabel.setTextFill(Paint.valueOf("#ffd889"));

    }
}
