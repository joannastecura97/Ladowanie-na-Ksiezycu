package engine.classes;


import engine.interfaces.JavaFxUpdater;
import integrator.classes.Integrator;
import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;
import user_interface.classes.Main;
import user_interface.classes.View;
import user_interface.classes.enums.ViewName;

import java.io.IOException;


/**
 * Class used mainly for application data management. It also provides updating JavaFx objects.
 * Separate <code>Thread</code> object is used as a clock with frequency 10Hz in order to calculate rocket movement
 * parameters and it picks every 10 iterations the amount of fuel burned per second.
 */
public class Engine implements JavaFxUpdater, Runnable, StepHandler {

    /**
     * Instance of class <code>Integrator</code> used for calculating movement parameters.
     */
    private Integrator integrator = new Integrator();

    /**
     * Instance of class <code>Thread</code> used as a timer.
     */
    private Thread thread;

    /**
     * Field used for controlling the calculation process.
     */
    private volatile boolean isRunning = false;

    /**
     * Field used for calculating current moon translation.
     */
    private final double MAX_TRANSLATION_MOON = 1230;

    /**
     * Field used for calculating current sky translation.
     */
    private final double MAX_TRANSLATION_SKY = 400;

    /**
     * Field used for storing the least highscore stored in the 'scores.txt' file. Current score is intended to be
     * compared with this field in order to check if current score should be saved.
     */
    private static double leastHighScore;

    /**
     * File used for storing the information about how much fuel was left.
     */
    private static double howMuchFuelLeft;


    /**
     * Field ued for storing the current mass of the rocket.
     */
    private double currentM = 2730.14;

    /**
     * Field ued for storing the current velocity of the rocket.
     */
    private double currentV = -150;

    /**
     * Field ued for storing the current height of the rocket.
     */
    private double currentH = 50000;

    /**
     * Field ued for storing the current amount of fuel burned by the rocket per second.
     */
    private double currentU = 0;

    /**
     * TextField used for showing the height of the rocket.
     */
    private TextField heightTextField;

    /**
     * TextField used for showing the velocity of the rocket.
     */
    private TextField velocityTextField;

    /**
     * TextField used for showing the amount of fuel left in the rocket.
     */
    private TextField fuelTextField;

    /**
     * Series for the chart representing phase space of the rocket movement equation.
     */
    private XYChart.Series<Double, Double> series1 = new XYChart.Series<>();

    /**
     * Scatter chart representing phase space of the rocket movement equation.
     */
    private ScatterChart<Double, Double> chart;

    /**
     * TextField showing current amount of fuel burned per second.
     */
    private TextField uTextField;

    /**
     * <code>Slider</code> instance used for changing the amount of fuel burned per second.
     */
    private Slider slider;

    /**
     * Background image.
     */
    private ImageView skyImage;

    /**
     * Image of the rocket.
     */
    private ImageView rocketImage;

    /**
     * Image of the moon, which is translated upwards during the game.
     */
    private ImageView moonImage;

    /**
     * Method used for setting the fields of the class.
     * @param heightTextField TextField used for showing the height of the rocket.
     * @param speedTextField TextField used for showing the velocity of the rocket.
     * @param fuelTextField TextField used for showing the amount of fuel left in the rocket.
     * @param uTextField TextField showing current amount of fuel burned per second.
     * @param chart Scatter chart representing phase space of the rocket movement equation.
     * @param slider <code>Slider</code> instance used for changing the amount of fuel burned per second.
     * @param skyImage Background image.
     * @param rocketImage Image of the rocket.
     * @param moonImage Image of the moon, which is translated upwards during the game.
     * @param leastHighScore Value used for storing the least highscore stored in the 'scores.txt' file.
     */
    public void setFields(TextField heightTextField, TextField speedTextField, TextField fuelTextField, TextField uTextField,
                          ScatterChart<Double, Double> chart, Slider slider, ImageView skyImage, ImageView rocketImage, ImageView moonImage, Double leastHighScore) {
        this.heightTextField = heightTextField;
        this.velocityTextField =speedTextField;
        this.fuelTextField=fuelTextField;
        this.uTextField = uTextField;
        this.chart=chart;
        this.slider = slider;
        this.skyImage = skyImage;
        this.rocketImage = rocketImage;
        this.moonImage = moonImage;
        Engine.leastHighScore = leastHighScore;


        chart.getData().addAll(series1);
    }

    /**
     * Method used for gaining access to static field <code>howMuchFuelLeft</code>
     * @return value describing how much fuel was left after landing.
     */
    public static double getHowMuchFuelLeft() {
        return howMuchFuelLeft;
    }

    /**
     * Method used for deciding if the score is needed to be saved.
     * @return true if least highscore is less than current score, otherwise false.
     */
    public static boolean isScoreNeededToBeSaved() {
        return leastHighScore < howMuchFuelLeft;
    }

    /**
     * Method used for creating the timer thread and launching the timer.
     */
    public void start() {
        thread = new Thread(this, "built-in timer");
        thread.start();
    }

    /**
     * Method used for stopping the timer.
     */
    public void stop(){
        isRunning = false;
    }

    /**
     * Method used for interrupting the thread of the timer and stopping the timer.
     */
    public void interrupt(){
        isRunning = false;
        thread.interrupt();
    }

    /**
     * Method called after executing method <code>start()</code>.
     * It calculates rocket movement parameters, updates JavaFX objects.
     */
    @Override
    public void run() {
        int loopCounter = 0;
        isRunning = true;

        while(isRunning){
            try{
                if(loopCounter == 0 && currentM > 1000) {
                    currentU = slider.getValue();
                }

                //paliwo co 1 sekundę ma być, więc co 100ms daję 0.1 u, z minusem bo we wzorze ma byc ujemne
                integrator.integrateStep(this, -currentU, currentM, currentV, currentH);
                //zdefiniowałem toString wyswietlajacy czas i trzy liczone składowe

                if(currentM < 1000){
                    currentM = 1000;
                    currentU = 0;
                }

                /*
                //cheating
                if(currentH < 500){
                    currentV = 1.88;
                    currentH = -1;
                }
                */


                if(currentH < 0){
                    stop();
                    if(Math.abs(currentV) <= 2){
                        Platform.runLater( ()-> {
                            try {
                                Main.getStage().setScene(new View().createScene("/win-view.fxml"));
                            } catch (IOException e) {
                                //path exists
                            }
                        });
                    } else {
                        Platform.runLater(() ->Main.getStage().setScene(Main.getSceneMap().get(ViewName.LOSE)));
                    }

                }


                updateFxObjects();

                loopCounter = (loopCounter+1)%10; //zwiększam o 1, chce co dziesiąty

                Thread.sleep(100);//co 100ms gui, ^ pozwala na co 1000ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method used for setting parameters of JavaFx objects. The method is intended to be called via default
     * <code>updateFxObjects()</code> method from <code>JavaFxUpdater</code> interface, which will
     * launch the method in <code>Platform.runLater(Runnable runnable)</code>.
     */
    @Override
    public void initUpdate() {

        fuelTextField.setText(Math.round(currentM - 1000) + " kg");
        velocityTextField.setText(Math.round((currentV)) + " m/s");
        heightTextField.setText(Math.round(currentH) + " m");
        uTextField.setText((double) Math.round(currentU * 10) / 10 + " kg/s");
        rocketImage.setImage(new Image(
                (currentU == 0) ? "pictures\\spaceship.gif" : "pictures\\spaceship1.gif")
        );
        skyImage.setTranslateY(200 - MAX_TRANSLATION_SKY*(50000 - currentH)/50000); //200 jak pełna wysokość, -200 jak zerowa
        moonImage.setTranslateY(230 + (MAX_TRANSLATION_MOON-230)/50000*currentH);//H maleje, wiez przesuw maleje z 1230 do 230
        //series1 dodane do chart w konstruktorze, referencja nie jest zmieniana, wiec punkty dodawane są dynamicznie
        series1.getData().add(new XYChart.Data<>(currentV, currentH));
    }

    /**
     * Method used for initializing the step handler. Its implementation was not necessary for the work of th program.
     * @param v see <a href="https://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/ode/sampling/StepHandler.html">LINK</a>
     * @param doubles see <a href="https://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/ode/sampling/StepHandler.html">LINK</a>
     * @param v1 see <a href="https://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/ode/sampling/StepHandler.html">LINK</a>
     */
    @Override
    public void init(double v, double[] doubles, double v1) { }


    /**
     * Method used for handling the last integration step.
     * @param stepInterpolator see <a href="https://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/ode/sampling/StepHandler.html">LINK</a>
     * @param b see <a href="https://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/ode/sampling/StepHandler.html">LINK</a>
     * @throws MaxCountExceededException see <a href="https://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/ode/sampling/StepHandler.html">LINK</a>
     */
    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {
        double[] x = stepInterpolator.getInterpolatedState();
        currentH = x[0];
        currentV = x[1];
        currentM = x[2];
        Engine.howMuchFuelLeft = (double)Math.round((currentM-1000)*10)/10;
    }
}