package pt.isec.pa.tinypac.ui.gui.resources.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.ui.gui.ExitAlertUI;
import pt.isec.pa.tinypac.ui.gui.resources.FontManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.utils.Direction;

public class GameUI extends BorderPane {
    TinyPacmanManager tinyPacmanManager;
    GameEngine gameEngine;

    HBox hBox_lives;
    GridPane gridPane;
    Button btnPauseGame, btnFullScreen, btnSaveGame, btnExit;
    Label gamePoints, gameSeconds;

    public GameUI(TinyPacmanManager tinyPacmanManager) {
        this.tinyPacmanManager = tinyPacmanManager;

        gameEngine = new GameEngine();
        gameEngine.registerClient(this.tinyPacmanManager);
        gameEngine.start(500);

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.BLACK, null, null
                        )
                )
        );

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

        btnPauseGame = new Button();
        btnPauseGame.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("pause.png"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1, 1, true, true, true, false)
                        )
                )
        );
        btnPauseGame.setId("fullscreenButton");


        // TOP DO MAIN BORDER PANE
        gamePoints = new Label();
        gameSeconds = new Label();
        VBox vBox = new VBox(gamePoints, gameSeconds);
        vBox.setAlignment(Pos.CENTER);
        VBox.setMargin(gameSeconds, new Insets(20, 0, 20, 0));

        BorderPane borderPane = new BorderPane();
        borderPane.setRight(btnFullScreen);
        borderPane.setLeft(btnPauseGame);
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(20, 35, 0, 35));

        // CENTER DO MAIN BORDER PANE
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        // RIGHT DO MAIN BORDER PANE
        btnSaveGame = new Button("Guardar Jogo");
        btnSaveGame.setFont(FontManager.loadFont("PressStart2P-Regular.ttf", 10));
        btnSaveGame.setId("gameButton");

        // LEFT DO MAIN BORDER PANE
        btnExit = new Button("Sair");
        btnExit.setFont(FontManager.loadFont("PressStart2P-Regular.ttf", 10));
        btnExit.setId("gameButton");

        // HBOX APENAS PARA ALINHAR OS BOTOES A MEIO DA GRIDPANE
        HBox hBox = new HBox(btnExit, gridPane, btnSaveGame);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(30);


        // BOTTOM DO MAIN BORDER PANE
        hBox_lives = new HBox();      //VIDAS DO PACMANNNNNNNN
        hBox_lives.setAlignment(Pos.CENTER_RIGHT);
        hBox_lives.setPadding(new Insets(0, 35, 20, 0));

        this.setTop(borderPane);
        this.setCenter(hBox);
        this.setBottom(hBox_lives);
    }

    private void registerHandlers() {
        tinyPacmanManager.addPropertyChangeListener(TinyPacmanManager.PROP_UPDATE_MAP, evt -> Platform.runLater(() -> update()));

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

        btnPauseGame.setOnAction(event -> {
            if(tinyPacmanManager.getCurrentState() == TinyPacmanState.PAUSED_GAME)
                tinyPacmanManager.resumeGame();
            else
                tinyPacmanManager.pauseGame();
        });


        this.setFocusTraversable(true); // NAO DETETA ARROW KEYS
        btnPauseGame.setOnMouseClicked(mouseEvent -> {
            this.requestFocus();            // MANTER FOCO PARA DETETAR AS ARROW KEYS DEPOIS DE CLICAR NUM BOTAO
        });
        btnFullScreen.setOnMouseClicked(mouseEvent -> {
            this.requestFocus();            // MANTER FOCO PARA DETETAR AS ARROW KEYS DEPOIS DE CLICAR NUM BOTAO
        });
        btnSaveGame.setOnMouseClicked(mouseEvent -> {
                    tinyPacmanManager.save();
                }
        );

        ExitAlertUI.exitAlert(btnExit);

        // KEY PRESSED HANDLER
        this.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                tinyPacmanManager.changePacmanDirection(Direction.UP);
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                tinyPacmanManager.changePacmanDirection(Direction.DOWN);
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                tinyPacmanManager.changePacmanDirection(Direction.RIGHT);
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                tinyPacmanManager.changePacmanDirection(Direction.LEFT);
            }

            else if(keyEvent.getCode() == KeyCode.P){
                btnPauseGame.fire();
            }
            else if(keyEvent.getCode() == KeyCode.F){
                btnFullScreen.fire();
            }
        });

    }

    private void update() {
        if (tinyPacmanManager.getCurrentState() == null || tinyPacmanManager.getCurrentState() == TinyPacmanState.PACMAN_WINS || tinyPacmanManager.getCurrentState() == TinyPacmanState.PACMAN_LOSES) {
            this.setVisible(false);
            if(tinyPacmanManager.getCurrentState() == TinyPacmanState.PACMAN_LOSES){
                gameEngine.unregisterClient(this.tinyPacmanManager);
            }
            return;
        } else
            this.setVisible(true);

        if (tinyPacmanManager.getCurrentState() != TinyPacmanState.PAUSED_GAME) {
            btnPauseGame.setBackground(
                    new Background(
                            new BackgroundImage(
                                    ImageManager.getImage("pause.png"),
                                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,
                                    new BackgroundSize(1, 1, true, true, true, false)
                            )
                    )
            );
        } else {
            btnPauseGame.setBackground(
                    new Background(
                            new BackgroundImage(
                                    ImageManager.getImage("resume.png"),
                                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,
                                    new BackgroundSize(1, 1, true, true, true, false)
                            )
                    )
            );
        }

        if (tinyPacmanManager.getCurrentState() != null) {
            gamePoints.setText(String.format("PONTUAÇÃO: %d", tinyPacmanManager.getPoints()));
            gamePoints.setFont(FontManager.loadFont("PressStart2P-Regular.ttf", 16));
            gamePoints.setId("mainMenuTitle");

            gameSeconds.setText(String.format("TEMPO: %d SEGUNDOS", tinyPacmanManager.getSeconds()));
            gameSeconds.setFont(FontManager.loadFont("PressStart2P-Regular.ttf", 16));
            gameSeconds.setId("mainMenuTitle");

            if (tinyPacmanManager.getCurrentState() != TinyPacmanState.PAUSED_GAME) {
                btnExit.setVisible(false);
                btnSaveGame.setVisible(false);
            } else {
                btnExit.setVisible(true);
                btnSaveGame.setVisible(true);
            }
        }

        gridPane.getChildren().clear();
        // MAPA DE JOGO
        if (tinyPacmanManager.getMaze() != null) {
            char[][] char_maze = tinyPacmanManager.getMaze().getMaze();

            for (int i = 0; i < char_maze.length; i++) {
                for (int j = 0; j < char_maze[i].length; j++) {
                    Label label = new Label();
                    label.setPrefSize(19, 19);
                    ImageView imageView = null;

                    switch (char_maze[i][j]) {
                        case 'x':
                            imageView = new ImageView(ImageManager.getImage("wall.png"));
                            break;

                        case 'p':
                            imageView = new ImageView(ImageManager.getImage("pacman-gif.gif"));

                            switch (tinyPacmanManager.getCurrentDirectionPacman()) {
                                case UP -> {
                                    imageView.setRotate(-90);
                                }
                                case DOWN -> {
                                    imageView.setRotate(90);
                                }
                                case RIGHT -> {
                                    imageView.setRotate(0);
                                }
                                case LEFT -> {
                                    imageView.setRotate(180);
                                }
                                case NONE -> {
                                    imageView = new ImageView(ImageManager.getImage("pacman-gif-none.gif"));
                                }
                            }

                            break;

                        case 'o':
                            imageView = new ImageView(ImageManager.getImage("pontoPequeno.png"));
                            break;

                        case 'O':
                            imageView = new ImageView(ImageManager.getImage("pontoOP.png"));
                            break;

                        case 'B':
                            if (tinyPacmanManager.getCurrentState() == TinyPacmanState.VULNERABLE_GHOSTS && tinyPacmanManager.getBlinkyVul())
                                imageView = new ImageView(ImageManager.getImage("Vulnerable-Ghosts.png"));
                            else
                                imageView = new ImageView(ImageManager.getImage("Blinky.png"));
                            break;

                        case 'C':
                            if (tinyPacmanManager.getCurrentState() == TinyPacmanState.VULNERABLE_GHOSTS && tinyPacmanManager.getClydeVul())
                                imageView = new ImageView(ImageManager.getImage("Vulnerable-Ghosts.png"));
                            else
                                imageView = new ImageView(ImageManager.getImage("Clyde.png"));
                            break;

                        case 'P':
                            if (tinyPacmanManager.getCurrentState() == TinyPacmanState.VULNERABLE_GHOSTS && tinyPacmanManager.getPinkyVul())
                                imageView = new ImageView(ImageManager.getImage("Vulnerable-Ghosts.png"));
                            else
                                imageView = new ImageView(ImageManager.getImage("Pinky.png"));
                            break;

                        case 'I':
                            if (tinyPacmanManager.getCurrentState() == TinyPacmanState.VULNERABLE_GHOSTS && tinyPacmanManager.getInkyVul())
                                imageView = new ImageView(ImageManager.getImage("Vulnerable-Ghosts.png"));
                            else
                                imageView = new ImageView(ImageManager.getImage("Inky.png"));
                            break;

                        case 'W':
                            imageView = new ImageView(ImageManager.getImage("Warp.png"));
                            break;

                        case ' ':
                            label.setText(" ");
                            break;

                        case 'Y':
                            imageView = new ImageView(ImageManager.getImage("GhostsSpawn.png"));
                            break;

                        case 'M':
                            imageView = new ImageView(ImageManager.getImage("PacmanSpawn.png"));
                            break;

                        case 'F':
                            imageView = new ImageView(ImageManager.getImage("Food.png"));
                            break;
                    }
                    if(imageView != null) {
                        imageView.setPreserveRatio(true);
                        imageView.setFitWidth(label.getPrefWidth());
                        imageView.setFitHeight(label.getPrefHeight());
                        label.setGraphic(imageView);

                        gridPane.add(label, j, i);
                    }
                }
            }
        }

        hBox_lives.getChildren().clear();
        if(tinyPacmanManager.getLives() == 0) {
            Label lives_label = new Label("");      // PARA QUANDO FICAR SEM VIDAS NAO DESFORMATE A JANELA
            hBox_lives.getChildren().add(lives_label);
        }
        else {
            for (int i = 0; i < tinyPacmanManager.getLives(); i++) {
                ImageView lives = new ImageView(ImageManager.getImage("lives.png"));
                lives.setFitWidth(25);
                lives.setFitHeight(25);
                hBox_lives.getChildren().add(lives);
            }
        }
    }


}
