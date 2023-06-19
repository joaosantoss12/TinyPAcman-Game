package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.ui.gui.MainJFX;

/**
 * Main class
 * <p>
 * The first class of this project
 *
 * @ author João Santos
 * @ version 1.0.0
 *
 */

public class Main {
    /**
     * Reference to data TinyPacmanManager object
     */
    public static TinyPacmanManager tinyPacmanManager;
    static{
        tinyPacmanManager = new TinyPacmanManager();
    }

    /**
     * The entry point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(MainJFX.class, args);
    }
}