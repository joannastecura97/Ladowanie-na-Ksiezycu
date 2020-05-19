package user_interface.classes.interfaces;

import javafx.scene.Scene;

import java.io.IOException;

/**
 * Functional interface describing viewable objects containing a field of class <code>Scene</code>.
 */
@FunctionalInterface
public interface Viewable {

    /**
     * Method used for creating the scene.
     * @param path Path to the FXML file.
     * @return instance of class <code>Scene</code>
     * @throws IOException when FXML file could not be found or an error occurred during file read.
     */
    Scene createScene(String path) throws IOException;
}
