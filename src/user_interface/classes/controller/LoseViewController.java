package user_interface.classes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import user_interface.classes.Main;
import user_interface.classes.enums.ViewName;

/**
 * Controller of scene 'Start'. Due to this class, interaction between the user and the program is available.
 */
public class LoseViewController {

    /**
     * Method called after pressing button 'Back". It checks if user's score should be saved and returns to the main menu.
     * @param event <code>ActionEvent</code> instance.
     */
    @FXML
    void onClickBackBtn(ActionEvent event) {
        Main.getStage().setScene(Main.getSceneMap().get(ViewName.START));
    }


}
