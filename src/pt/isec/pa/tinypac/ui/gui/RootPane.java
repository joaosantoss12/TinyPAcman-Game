package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.uistates.GameUI;
import pt.isec.pa.tinypac.ui.gui.resources.uistates.PacmanLostUI;
import pt.isec.pa.tinypac.ui.gui.resources.uistates.PacmanWonUI;

/**
 * Root pane class that serves as the main container for different UI elements.
 * Manages the creation, registration of event handlers, and updates of UI elements.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class RootPane extends BorderPane {
    TinyPacmanManager tinyPacmanManager;

    /**
     * Constructs a new instance of RootPane.
     *
     * @param tinyPacmanManager the TinyPacmanManager instance
     */
    public RootPane(TinyPacmanManager tinyPacmanManager){
        this.tinyPacmanManager = tinyPacmanManager;

        createViews();
        registerHandlers();
        update();
    }

    /**
     * Creates the views and initializes the UI elements.
     */
    private void createViews(){
        CSSManager.applyCSS(this,"styles.css");

        StackPane stackPane = new StackPane(
                new MainMenuUI(tinyPacmanManager),
                new TopFiveUI(tinyPacmanManager),
                new CreditsUI(tinyPacmanManager),
                new GameUI(tinyPacmanManager),
                new PacmanWonUI(tinyPacmanManager),
                new PacmanLostUI(tinyPacmanManager)
        );

        this.setCenter(stackPane);
    }

    /**
     * Registers event handlers for UI elements.
     */
    private void registerHandlers(){
    }

    /**
     * Updates the UI based on the current state of the TinyPacmanManager.
     */
    private void update() {
    }


}
