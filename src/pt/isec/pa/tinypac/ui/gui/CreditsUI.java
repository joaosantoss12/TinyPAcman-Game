package pt.isec.pa.tinypac.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.tinypac.model.TinyPacmanManager;
import pt.isec.pa.tinypac.ui.gui.resources.FontManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

/**
 * User interface class for displaying the credits.
 * Shows the credits information with a title, text, and an image.
 * Provides a button to go back to the previous screen.
 * Depends on the TinyPacmanManager, FontManager, and ImageManager classes.
 * Depends on the Button, Label, ImageView, Font, VBox, HBox, Background, BackgroundFill, BackgroundImage,
 * BackgroundRepeat, BackgroundPosition, BackgroundSize, Color, Insets, Pos, and BorderPane classes from JavaFX.
 * Uses custom fonts for the title and text.
 * Uses custom images for the background and button.
 * Has a constructor that takes a TinyPacmanManager as a parameter.
 * Provides methods for creating views, registering handlers, and updating the UI.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class CreditsUI extends BorderPane {
    TinyPacmanManager tinyPacmanManager;

    Font titleFont, textFont;

    Button btnGoBack;
    Label lblTitle, lblText;

    /**
     * Constructs a CreditsUI object with the specified TinyPacmanManager.
     * Initializes fonts, creates views, registers handlers, and updates the UI.
     *
     * @param tinyPacmanManager the TinyPacmanManager object
     */
    public CreditsUI(TinyPacmanManager tinyPacmanManager){
        this.tinyPacmanManager = tinyPacmanManager;

        titleFont = FontManager.loadFont("PAC-FONT.TTF", 45);
        textFont = FontManager.loadFont("PressStart2P-Regular.ttf",15);

        createViews();
        registerHandlers();
        update();
    }

    /**
     * Creates the UI views for the credits screen.
     * Includes the title, text, image, and button.
     */
    private void createViews() {
        this.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));

        lblTitle = new Label("creditos");
        lblTitle.setFont(titleFont);
        lblTitle.setId("credits");

        lblText = new Label(
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "~                                            ~\n" +
                        "~                                            ~\n" +
                        "~               DEIS-ISEC-IPC                ~\n" +
                        "~                                            ~\n" +
                        "~         LEI - Programação Avançada         ~\n" +
                        "~                                            ~\n" +
                        "~                                            ~\n" +
                        "~                                            ~\n" +
                        "~    2022/2023 - João Santos - 2020136093    ~\n" +
                        "~                                            ~\n" +
                        "~             Trabalho Académico             ~\n" +
                        "~                                            ~\n" +
                        "~                                            ~\n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        lblText.setFont(textFont);
        lblText.setId("credits");

        Image isec = ImageManager.getImage("isec.png");
        ImageView imageView = new ImageView(isec);
        imageView.setFitWidth(115);
        imageView.setFitHeight(115);


        VBox vBox = new VBox(lblTitle, lblText, imageView);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(25);
        VBox.setMargin(lblText,new Insets(50,0,0,0));

        btnGoBack = new Button();
        btnGoBack.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("backArrow.png"),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,true,false)
                        )
                )
        );
        btnGoBack.setId("fullscreenButton");// css igual

        HBox hBox = new HBox(btnGoBack);
        hBox.setPadding(new Insets(20,0,0,35));

        this.setCenter(vBox);
        this.setTop(hBox);
    }

    /**
     * Registers event handlers for the credits screen.
     * Handles the action event when the go back button is clicked.
     */
    private void registerHandlers() {
        tinyPacmanManager.addPropertyChangeListener(TinyPacmanManager.PROP_CREDITS, evt -> { update(); });

        btnGoBack.setOnAction(event -> {
            tinyPacmanManager.setShowCredits(false);
        });
    }

    /**
     * Updates the visibility of the credits screen based on the showCredits property of the TinyPacmanManager.
     */
    private void update(){
        if(tinyPacmanManager.showCredits()) {
            this.setVisible(true);
            return;
        }
        this.setVisible(false);
    }

}
