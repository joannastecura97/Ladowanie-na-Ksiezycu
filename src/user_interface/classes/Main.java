package user_interface.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import user_interface.classes.enums.ViewName;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


/**
 * Class used for creating the application window, initializing scenes and gaining access to the scenes. It also provides
 * a static <code>Map</code> object containing scenes under keys of enum <code>ViewName</code>. Inner static class
 * <code>Bounds</code>, containing two static final int fields, is used for setting the scene bounds.
 */
public class Main extends Application {

    /**
     * Inner static final class containing two fields. <code>IllegalAccessException</code> is thrown during instance creation
     * because this class is not intended to have any instances.
     */
    public final static class Bounds{

        /**
         * Constructor of class Bounds. It is implemented to prevent object creation.
         * @throws IllegalAccessException on constructor call.
         */
        public Bounds() throws IllegalAccessException {
            throw new IllegalAccessException("this class is not intended to have instances. ");
        }

        /**
         * Height of each scene.
         */
        public static final int HEIGHT = 600;

        /**
         * Width of each scene.
         */
        public static final int WIDTH = 400;
    }

    /**
     * Static container used for storing scenes. This object is used for gaining access to scenes without
     * <code>Main</code> class instance creation.
     */
    private static Map<ViewName, Scene> sceneMap = new TreeMap<>();

    /**
     * Static container used generally for setting scenes to the stage.
     */
    private static Stage primaryStage;

    /**
     * Method used for creating a window and setting parameters such as title, scene etc.
     * Called on app start.
     * @param primaryStage Stage of the application.
     * @throws Exception when an error occurs during window creation.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;

        //poszczególne odsłony (sceny) są w mapie pod kluczami z typu wyliczeniowego ViewName
        primaryStage.setTitle("How To Crash Your Rocket 1");

        Parent root = FXMLLoader.load(getClass().getResource("/start-view.fxml"));
        initSceneMap();

        primaryStage.setScene(sceneMap.get(ViewName.START));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Method used for gaining access to the static field <code>sceneMap</code> used for gaining access to scenes.
     * @return <code>Map</code> object containing scenes under <code>ViewName</code> keys.
     */
    public static Map<ViewName, Scene> getSceneMap() {
        return sceneMap;
    }

    /**
     * Method used for gaining access to the static field <code>primaryStage</code>, being a reference to the stage of the application.
     * @return main stage of the application.
     */
    public static Stage getStage(){
        return primaryStage;
    }

    /**
     * Method initializing <code>sceneMap</code> object with non-changing scenes.
     * @throws IOException if the FXML file is not found.
     */
    public static void initSceneMap() throws IOException {
        sceneMap.put(ViewName.START, new View().createScene("/start-view.fxml"));
        sceneMap.put(ViewName.LOSE, new View().createScene("/lose-view.fxml"));
    }

    /**
     * Method used for launching the application.
     * @param args Program arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
