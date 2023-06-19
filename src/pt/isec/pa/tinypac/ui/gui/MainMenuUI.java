package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.ui.gui.resources.FontManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

/**
 * User interface class for the main menu screen.
 * Displays the main menu options and handles user interactions.
 * Manages the creation, registration of event handlers, and updates of UI elements.
 * Depends on the TinyPacmanManager for game state information and control.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class MainMenuUI extends BorderPane {
    TinyPacmanManager tinyPacmanManager;

    Font titleFont, buttonsFont;

    Label lblTitle;
    Button btnStart,btnTop5,btnCredits,btnExit,btnFullScreen;

    /**
     * Constructs a new instance of MainMenuUI.
     *
     * @param tinyPacmanManager the TinyPacmanManager instance
     */
    public MainMenuUI(TinyPacmanManager tinyPacmanManager) {
        this.tinyPacmanManager = tinyPacmanManager;

        titleFont = FontManager.loadFont("PAC-FONT.TTF",69);
        buttonsFont = FontManager.loadFont("PressStart2P-Regular.ttf",12);

        createViews();
        registerHandlers();
        update();
    }

    /**
     * Creates the views and initializes the UI elements for the main menu screen.
     */
    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        lblTitle = new Label("TinyPAcman");
        lblTitle.setFont(titleFont);
        lblTitle.setId("mainMenuTitle");

        btnStart = new Button("INICIAR JOGO");
        btnStart.setFont(buttonsFont);
        btnStart.setId("mainMenuButton");
        btnStart.setStyle("-fx-background-color: #fe6a00");

        btnTop5 = new Button("CONSULTAR TOP 5");
        btnTop5.setFont(buttonsFont);
        btnTop5.setId("mainMenuButton");
        btnTop5.setStyle("-fx-background-color: #00fff0");

        btnCredits = new Button("CREDITOS");
        btnCredits.setFont(buttonsFont);
        btnCredits.setId("mainMenuButton");
        btnCredits.setStyle("-fx-background-color: #ff56c7");

        btnExit  = new Button("SAIR");
        btnExit.setFont(buttonsFont);
        btnExit.setId("mainMenuButton");
        btnExit.setStyle("-fx-background-color: #ff0006");

        VBox vBox = new VBox(lblTitle,btnStart,btnTop5,btnCredits,btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        VBox.setMargin(btnStart, new Insets(75, 0, 0, 0)); // Set top margin for the button

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
        hBox.setMargin(btnFullScreen, new Insets(20, 35, 0, 0)); // Set top margin for the button

        this.setCenter(vBox);
        this.setTop(hBox);
    }

    /**
     * Registers event handlers for UI elements.
     */
    private void registerHandlers() {
        tinyPacmanManager.addPropertyChangeListener(TinyPacmanManager.PROP_TOP_FIVE, evt -> { update(); });
        tinyPacmanManager.addPropertyChangeListener(TinyPacmanManager.PROP_CREDITS, evt -> { update(); });

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
            }
            else {
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

        btnStart.setOnAction( event -> {
            if(tinyPacmanManager.checkSavedGame()){
                Alert alert = new Alert(
                        Alert.AlertType.WARNING,
                        null,
                        ButtonType.YES, ButtonType.NO
                );

                alert.setTitle("Jogo guardado");
                alert.setHeaderText("Foi detetado um jogo previamente guardado.\nPertence continuar [YES] ou começar um novo [NO]?");

                ImageView exitIcon = new ImageView(ImageManager.getImage("pacman-gif.gif"));
                exitIcon.setFitHeight(100);
                exitIcon.setFitWidth(100);
                alert.getDialogPane().setGraphic(exitIcon);

                alert.showAndWait().ifPresent(response -> {
                    switch (response.getButtonData()){
                        case YES -> {
                            tinyPacmanManager.load();
                        }
                        case NO -> {
                            tinyPacmanManager.delete();
                            tinyPacmanManager.start();
                        }
                    }
                });
            }
            else{
                tinyPacmanManager.start();
            }


        });

        btnTop5.setOnAction( event -> {
            tinyPacmanManager.setShowTopFive(true);
        });

        btnCredits.setOnAction( event -> {
            tinyPacmanManager.setShowCredits(true);
        });


        ExitAlertUI.exitAlert(btnExit);
    }

    /**
     * Updates the visibility of the main menu UI based on the current state of the TinyPacmanManager.
     */
    private void update(){
        if(tinyPacmanManager.showTopFive() || tinyPacmanManager.showCredits() || tinyPacmanManager.getCurrentState() != null) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }


}
