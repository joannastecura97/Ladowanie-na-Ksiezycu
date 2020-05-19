package user_interface.classes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import user_interface.classes.Main;
import user_interface.classes.View;
import user_interface.classes.enums.ViewName;
import java.io.IOException;

/**
 * Controller of scene 'Start'. Due to this class, interaction between the user and the program is available.
 */
public class StartViewController {

    /**
     * Method called after pressing the button 'Game'. It creates new <code>Scene</code> object with FXML file for
     * game scene.
     * @param event <code>ActionEvent</code> instance.
     */
    @FXML
    void onClickGameBtn(ActionEvent event) {
        try {
            Main.getSceneMap().put(ViewName.GAME, new View().createScene("/game-view.fxml"));
        } catch (IOException e) {
            //the path exists, line above is needed to create a new, blank game view
        }
        Main.getStage().setScene(Main.getSceneMap().get(ViewName.GAME));
    }

    /**
     * Method called after pressing the button 'Score'. It creates new <code>Scene</code> object with FXML file for
     * score scene.
     * @param event <code>ActionEvent</code> instance.
     */
    @FXML
    void onClickScoreBtn(ActionEvent event) {
        try {
            Main.getSceneMap().put(ViewName.SCORE, new View().createScene("/score-view.fxml"));
            Main.getSceneMap().get(ViewName.SCORE).getStylesheets().add("/score-viewCSS.css");
        } catch (IOException e) {
            //the path exists, line above is needed to create a new, blank game view
        }
        Main.getStage().setScene(Main.getSceneMap().get(ViewName.SCORE));
    }

}
