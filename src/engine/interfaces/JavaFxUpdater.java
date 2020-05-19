package engine.interfaces;

import javafx.application.Platform;

/**
 * Interface used for updating JavaFx objects.
 */
public interface JavaFxUpdater {

    /**
     * Method used for setting parameters of JavaFx objects. The method is intended to be called via default
     * <code>updateFxObjects()</code> method, which will launch the method in <code>Platform.runLater(Runnable runnable)</code>.
     */
    void initUpdate();

    /**
     * Method used to call <code>initUpdate()</code>. Implementation of this method delegates executing <code>initUpdate()</code>
     * method to JavaFx main thread.
     */
    default void updateFxObjects() {
        Platform.runLater(this::initUpdate);
    }
}

