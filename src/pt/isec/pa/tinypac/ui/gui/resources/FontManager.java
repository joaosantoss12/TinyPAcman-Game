package pt.isec.pa.tinypac.ui.gui.resources;

import javafx.scene.text.Font;

import java.io.InputStream;

/**
 * Manages the loading of custom fonts.
 * Provides a method for loading fonts from resources.
 * The class contains only static methods and cannot be instantiated.
 * Depends on the Font class from JavaFX.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class FontManager {
    /**
     * Private constructor to prevent instantiation of the class.
     */
    private FontManager(){}

    /**
     * Loads a custom font from the resources folder.
     *
     * @param filename the filename of the font in the resources folder
     * @param size the size of the font to be loaded
     * @return the Font object, or null if the font cannot be loaded
     */
    public static Font loadFont(String filename, int size) {
        try(InputStream inputStreamFont =
                    FontManager.class.getResourceAsStream("fonts/" + filename)) {
            return Font.loadFont(inputStreamFont, size);
        } catch (Exception e) {
            return null;
        }
    }
}
