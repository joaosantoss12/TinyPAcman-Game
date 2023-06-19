package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.model.data.Ranking;
import pt.isec.pa.tinypac.ui.gui.resources.FontManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.rgb;

/**
 * User interface class for displaying the top five scores.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class TopFiveUI extends BorderPane {
    TinyPacmanManager tinyPacmanManager;

    Font titleFont, textFont;

    Button btnGoBack;
    Label lblTitle;
    VBox list;

    /**
     * Constructs a new instance of TopFiveUI.
     *
     * @param tinyPacmanManager the TinyPacmanManager instance
     */
    public TopFiveUI(TinyPacmanManager tinyPacmanManager) {
        this.tinyPacmanManager = tinyPacmanManager;

        titleFont = FontManager.loadFont("PAC-FONT.TTF", 45);
        textFont = FontManager.loadFont("PressStart2P-Regular.ttf", 20);

        createViews();
        registerHandlers();
        update();
    }

    /**
     * Creates the views and initializes the UI elements.
     */
    private void createViews() {
        Background background = new Background(
                new BackgroundImage(
                        ImageManager.getImage("top5-gif.gif"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(100, 100, true, true, false, true)
                )
        );
        this.setBackground(background);


        btnGoBack = new Button();
        btnGoBack.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("backArrow.png"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1, 1, true, true, true, false)
                        )
                )
        );
        btnGoBack.setId("fullscreenButton"); // css igual

        HBox hBox = new HBox(btnGoBack);
        hBox.setPadding(new Insets(20, 0, 0, 35));

        this.setTop(hBox);


        lblTitle = new Label("top scores");
        lblTitle.setFont(titleFont);
        lblTitle.setTextFill(Color.YELLOW);

        TilePane titlePane = getLine(
                800,
                Color.YELLOW,
                "Rank",
                "Nome",
                "Pontuação",
                "Tempo"
        );
        titlePane.setBackground(new Background(new BackgroundFill(rgb(0,0,0,0.8), null, null)));
        titlePane.setPadding(new Insets(10,0,10,0));

        list = new VBox();

        VBox listWithTitle = new VBox(titlePane, list);
        VBox vBox = new VBox(lblTitle, listWithTitle);
        vBox.setFillWidth(false);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);

        this.setCenter(vBox);
    }

    /**
     * Registers event handlers for UI elements.
     */
    private void registerHandlers() {
        tinyPacmanManager.addPropertyChangeListener(TinyPacmanManager.PROP_TOP_FIVE, evt -> Platform.runLater(()->update()));

        btnGoBack.setOnAction(event -> {
            tinyPacmanManager.setShowTopFive(false);
        });
    }

    /**
     * Updates the UI based on the current state of the TinyPacmanManager.
     */
    private void update() {
        if (tinyPacmanManager.showTopFive()) {
            this.setVisible(true);
            return;
        }
        this.setVisible(false);

        List<Ranking> top5Rankings = loadTop5();

        list.getChildren().clear();
            for (int i = 0; i < top5Rankings.size(); i++) {
                Ranking ranking = top5Rankings.get(i);
                TilePane tilePane = getLine(
                        800,
                        Color.WHITE,
                        "#" + (i + 1),
                        ranking.getName(),
                        String.valueOf(ranking.getPoints()),
                        String.valueOf(ranking.getSeconds())
                );
                tilePane.setBackground(new Background(new BackgroundFill(rgb(0, 0, 0, 0.8), null, null)));
                list.getChildren().add(tilePane);
            }
    }

    /**
     * Creates a TilePane representing a line of information.
     *
     * @param width   the width of the TilePane
     * @param color   the color of the text
     * @param strings the information to display in the line
     * @return the TilePane representing the line
     */
    private TilePane getLine(double width, Color color, String... strings) {
        TilePane tilePane = new TilePane();
        for (int i = 0; i < strings.length; i++) {
            Label label = new Label(strings[i]);
            label.setFont(textFont);

            label.setTextFill(color);
            label.setPadding(new Insets(10,0,15,0));

            label.setAlignment(Pos.CENTER);
            label.setPrefHeight(38);
            label.setPrefWidth(width / 3);

            tilePane.getChildren().add(label);
        }
        tilePane.setPrefWidth(width);
        tilePane.setPrefTileWidth(195);
        return tilePane;
    }

    /**
     * Loads the top 5 rankings from a file.
     *
     * @return the list of top 5 rankings
     */
    private List<Ranking> loadTop5() {
        List<Ranking> rankings = new ArrayList<>();
        File arquivo = new File("top5.ser");

        if (!arquivo.exists()) {
            return rankings;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            rankings = (List<Ranking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o arquivo top5.ser: " + e.getMessage());
        }

        return rankings;
    }
}
