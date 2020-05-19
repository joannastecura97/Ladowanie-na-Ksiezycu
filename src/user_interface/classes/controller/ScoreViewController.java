package user_interface.classes.controller;

import engine.classes.ScoreIOManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import user_interface.classes.Main;
import user_interface.classes.enums.ViewName;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller of scene 'Score'. Due to this class, interaction between the user and the program is available.
 */
public class ScoreViewController implements Initializable {

    /**
     * Instance of class <code>ScoreIOManager</code> used for score save and read.
     */
    private ScoreIOManager scoreIOManager = new ScoreIOManager();

    /**
     * Parent element to labels containing information about scores, nicknames and places.
     */
    @FXML
    private GridPane gridPane;

    /**
     * OnClick method for button 'Back' used for returning to the main scene.
     * @param actionEvent <code>ActionEvent</code> instance.
     */
    public void onClickReturnBtn(ActionEvent actionEvent) {
        Main.getStage().setScene(Main.getSceneMap().get(ViewName.START));
    }


    /**
     * Method called after FXML file read. It sets properties to the fields of the class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String[]> scores = scoreIOManager.readScore();

        for (int i = 1; i < 11; i++) {
            String[] row = scores.get(i - 1);
            for (int j = 1; j < 3; j++) {
                gridPane.add(new Label(row[j - 1]), j, i);
            }
        }
    }
}
