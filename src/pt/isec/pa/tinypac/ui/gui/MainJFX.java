package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.Main;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

/**
 * Main class for launching the JavaFX application.
 * Initializes the GUI and starts the game.
 * Depends on the TinyPacmanManager for game state and control.
 * Extends the JavaFX Application class.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class MainJFX extends Application {
    TinyPacmanManager tinyPacmanManager;

    /**
     * Initializes the application before JavaFX starts.
     * Retrieves the instance of TinyPacmanManager from the Main class.
     *
     * @throws Exception if an error occurs during initialization
     */
    @Override
    public void init() throws Exception{
        super.init();
        tinyPacmanManager = Main.tinyPacmanManager;
    }

    /**
     * Starts the JavaFX application by initializing the GUI and showing the main stage.
     *
     * @param stage the primary stage for the JavaFX application
     */
    @Override
    public void start(Stage stage){
        initGUI(new Stage(), "TinyPAcman Game");
    }

    /**
     * Initializes the graphical user interface (GUI) for the game.
     *
     * @param stage the main stage for the JavaFX application
     * @param title the title of the game window
     */
    private void initGUI(Stage stage, String title){
        RootPane rootPane = new RootPane(tinyPacmanManager);
        Scene scene = new Scene(rootPane,900,725);
        stage.getIcons().add(ImageManager.getImage("pacman-icon.png"));
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        stage.show();
    }

    /**
     * Called when the JavaFX application is stopped.
     *
     * @throws Exception if an error occurs during the stop operation
     */
    @Override
    public void stop() throws Exception {
        super.stop();
    }
}