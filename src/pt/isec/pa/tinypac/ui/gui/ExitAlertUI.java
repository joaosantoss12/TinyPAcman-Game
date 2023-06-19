package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

/**
 * Utility class for displaying an exit confirmation alert.
 * Provides a static method to show the exit alert dialog.
 * Depends on the ImageManager class for loading images.
 * Depends on the Button, Alert, ButtonType, ImageView, and Platform classes from JavaFX.
 * Uses a warning alert type with yes/no buttons for confirmation.
 * Shows a sad Pacman icon in the alert dialog.
 * Exits the application when confirmed.
 * Does not perform any action when canceled.
 * Does not have a constructor.
 * All methods are static.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class ExitAlertUI {

    /**
     * Displays an exit confirmation alert dialog when the specified exit button is clicked.
     * Exits the application when confirmed and performs no action when canceled.
     *
     * @param btnExit the button that triggers the exit alert
     */
    public static void exitAlert(Button btnExit) {

        btnExit.setOnAction(event -> {
            Alert alert = new Alert(
                    Alert.AlertType.WARNING,
                    null,
                    ButtonType.YES, ButtonType.NO
            );

            alert.setTitle("Sair");
            alert.setHeaderText("Quer realmente sair do jogo?");

            ImageView exitIcon = new ImageView(ImageManager.getImage("pacman-sad.png"));
            exitIcon.setFitHeight(100);
            exitIcon.setFitWidth(100);
            alert.getDialogPane().setGraphic(exitIcon);

            alert.showAndWait().ifPresent(response -> {
                switch (response.getButtonData()){
                    case YES -> {
                        Platform.exit();
                    }
                    case NO -> {}
                }
            });
        });
    }
}
