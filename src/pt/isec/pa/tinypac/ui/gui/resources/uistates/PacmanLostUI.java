package pt.isec.pa.tinypac.ui.gui.resources.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.ui.gui.ExitAlertUI;
import pt.isec.pa.tinypac.ui.gui.resources.FontManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

/**
 * Represents the UI displayed when Pacman loses the game.
 * Extends the BorderPane class from JavaFX.
 * Provides options to start a new game, view the top 5 scores, view credits, and exit the game.
 * Depends on the TinyPacmanManager, FontManager, and ImageManager classes.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class PacmanLostUI extends BorderPane {
    TinyPacmanManager tinyPacmanManager;

    Label lblTitle;
    Font titleFont, buttonsFont;
    Button btnStart, btnTop5, btnCredits, btnExit, btnFullScreen;

    boolean alreadyAddedTop5;

    /**
     * Creates a new instance of PacmanLostUI with the specified TinyPacmanManager.
     * Initializes the UI elements, registers event handlers, and updates the UI.
     *
     * @param tinyPacmanManager the TinyPacmanManager instance
     */
    public PacmanLostUI(TinyPacmanManager tinyPacmanManager) {
        this.tinyPacmanManager = tinyPacmanManager;

        titleFont = FontManager.loadFont("PAC-FONT.ttf", 69);
        buttonsFont = FontManager.loadFont("PressStart2P-Regular.ttf", 12);

        createViews();
        registerHandlers();
        update();
    }

    /**
     * Creates and configures the UI elements for the PacmanLostUI.
     */
    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        lblTitle = new Label("game over");
        lblTitle.setFont(titleFont);
        lblTitle.setId("mainMenuTitle");

        btnStart = new Button("Iniciar Jogo");
        btnStart.setFont(buttonsFont);
        btnStart.setId("mainMenuButton");
        btnStart.setStyle("-fx-background-color: #fe6a00");

        btnTop5 = new Button("Consultar TOP 5");
        btnTop5.setFont(buttonsFont);
        btnTop5.setId("mainMenuButton");
        btnTop5.setStyle("-fx-background-color: #00fff0");

        btnCredits = new Button("Créditos");
        btnCredits.setFont(buttonsFont);
        btnCredits.setId("mainMenuButton");
        btnCredits.setStyle("-fx-background-color: #ff56c7");

        btnExit = new Button("Sair");
        btnExit.setFont(buttonsFont);
        btnExit.setId("mainMenuButton");
        btnExit.setStyle("-fx-background-color: #ff0006");

        VBox vBox = new VBox(lblTitle, btnTop5, btnCredits, btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        VBox.setMargin(btnStart, new Insets(75, 0, 0, 0)); // Set top margin for the button

        btnFullScreen = new Button();
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
        btnFullScreen.setId("fullscreenButton");

        HBox hBox = new HBox(btnFullScreen);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(btnFullScreen, new Insets(20, 35, 0, 0)); // Set top margin for the button

        this.setCenter(vBox);
        this.setTop(hBox);
    }

    /**
     * Registers the event handlers for the UI elements in the PacmanLostUI.
     */
    private void registerHandlers() {
        tinyPacmanManager.addPropertyChangeListener(TinyPacmanManager.PROP_LOSE_GAME, evt -> Platform.runLater(() -> update()));

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

        btnStart.setOnAction(event -> {
            tinyPacmanManager.start();
        });

        btnTop5.setOnAction(event -> {
            tinyPacmanManager.setShowTopFive(true);
        });

        btnCredits.setOnAction(event -> {
            tinyPacmanManager.setShowCredits(true);
        });


        ExitAlertUI.exitAlert(btnExit);
    }

    /**
     * Updates the UI based on the current state of the TinyPacmanManager.
     */
    private void update() {
        if (tinyPacmanManager.getCurrentState() == TinyPacmanState.PACMAN_LOSES) {
            this.setVisible(true);

            if (tinyPacmanManager.checkNewRanking() && !alreadyAddedTop5) {
                //fazer pop up
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Digite seu nome");
                dialog.setHeaderText(null);
                dialog.setContentText("Nome:");

                // Mostrar o pop-up e aguardar a resposta do utilizador
                java.util.Optional<String> result = dialog.showAndWait();

                // Verificar se o utilizador forneceu um nome
                if (result.isPresent()) {
                    String nomeJogador = result.get();
                    tinyPacmanManager.updateTop5(nomeJogador);
                }
                alreadyAddedTop5 = true;
            }
        } else {
            alreadyAddedTop5 = false;
            this.setVisible(false);
        }
    }


}
