package user_interface.classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import user_interface.classes.interfaces.Viewable;

import java.io.IOException;

/**
 * Class used for scene creation from FXML files.
 */
public class View implements Viewable {

    /**
     * Method used for creating a scene from FXML files. This method is intended to create scenes for the main stage of the application.
     * @param path path to FXML file.
     * @return <code>Scene</code> object containing all properties present in the FXML file.
     * @throws IOException When file is not found or an error occurs during file read.
     */
    @Override
    public Scene createScene(String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        return new Scene(root, Main.Bounds.HEIGHT, Main.Bounds.WIDTH);
    }

}
