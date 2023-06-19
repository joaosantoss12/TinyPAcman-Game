package pt.isec.pa.tinypac.ui.gui.resources.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.ui.gui.resources.FontManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

/**
 * Represents the UI displayed when Pacman wins the game.
 * Extends the BorderPane class from JavaFX.
 * Provides a countdown timer and a full screen button.
 * Depends on the TinyPacmanManager, FontManager, and ImageManager classes.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class PacmanWonUI extends BorderPane {
    TinyPacmanManager tinyPacmanManager;

    Label lblTitle, lblText;
    Font titleFont, textFont;
    Button btnFullScreen;

    private int seconds = 10;

    /**
     * Creates a new instance of PacmanWonUI with the specified TinyPacmanManager.
     * Initializes the UI elements, registers event handlers, and updates the UI.
     *
     * @param tinyPacmanManager the TinyPacmanManager instance
     */
    public PacmanWonUI(TinyPacmanManager tinyPacmanManager) {
        this.tinyPacmanManager = tinyPacmanManager;

        titleFont = FontManager.loadFont("PAC-FONT.ttf",55);
        textFont = FontManager.loadFont("PressStart2P-Regular.ttf",30);

        createViews();
        registerHandlers();
        update();
    }

    /**
     * Creates and configures the UI elements for the PacmanWonUI.
     */
    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        lblTitle = new Label("nivel dois");
        lblTitle.setFont(titleFont);
        lblTitle.setId("mainMenuTitle");

        lblText = new Label();
        lblText.setFont(textFont);
        lblText.setId("gameWonText");

        VBox vBox = new VBox(lblTitle,lblText);
        vBox.setAlignment(Pos.CENTER);
        //vBox.setSpacing(150);
        VBox.setMargin(lblText, new Insets(100,0,0,0));



        btnFullScreen = new Button();
        btnFullScreen.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("fullscreen.png"),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,true,false)
                        )
                )
        );
        btnFullScreen.setId("fullscreenButton");

        HBox hBox = new HBox(btnFullScreen);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(btnFullScreen, new Insets(20, 35, 0, 0)); // Set top margin for the button

        this.setCenter(vBox);
        this.setTop(hBox);
    }

    /**
     * Registers the event handlers for the UI elements.
     */
    private void registerHandlers() {
        tinyPacmanManager.addPropertyChangeListener(TinyPacmanManager.PROP_WIN_GAME, evt -> Platform.runLater(() -> update()));

        btnFullScreen.setOnAction(event -> {
            Stage stage = (Stage) btnFullScreen.getScene().getWindow();
            if (stage.isFullScreen()) {
                btnFullScreen.setBackground(
                        new Background(
                                new BackgroundImage(
                                        ImageManager.getImage("fullscreen.png"),
                                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                                        new BackgroundSize(1, 1, true, true, true, false)
                                )
                        )
                );

                stage.setFullScreen(false);
            } else {
                btnFullScreen.setBackground(
                        new Background(
                                new BackgroundImage(
                                        ImageManager.getImage("minimize.png"),
                                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                                        new BackgroundSize(1, 1, true, true, true, false)
                                )
                        )
                );

                stage.setFullScreen(true);
            }
        });
    }

    /**
     * Updates the UI based on the current state of the TinyPacmanManager.
     */
    private void update() {
        if(seconds == 0){
            tinyPacmanManager.nextLevel();
            seconds = 10;
        }

        if(tinyPacmanManager.getCurrentState() == TinyPacmanState.PACMAN_WINS) {
            this.setVisible(true);
            lblText.setText("Começa em " + seconds/2 + " segundos...");     // game Engine corre em 0,5 segundos
            seconds--;
        }
        else
            this.setVisible(false);
    }
}
