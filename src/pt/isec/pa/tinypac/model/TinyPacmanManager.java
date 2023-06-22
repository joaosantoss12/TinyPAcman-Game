package pt.isec.pa.tinypac.model;

import javafx.application.Platform;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.Ranking;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.data.elements.PACMAN;
import pt.isec.pa.tinypac.model.fsm.ITinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.utils.Direction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for managing the TinyPacman game.
 * Implements the IGameEngineEvolve interface to enable game evolution.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class TinyPacmanManager implements IGameEngineEvolve {
    TinyPacmanContext fsm;
    PropertyChangeSupport pcs;

    public static final String PROP_TOP_FIVE = "_top_five_";
    public static final String PROP_CREDITS = "_credits_";
    public static final String PROP_UPDATE_MAP = "_update_map_";
    public static final String PROP_WIN_GAME = "_win_game_";
    public static final String PROP_LOSE_GAME = "_lose_game_";

    // ALTERNAR ENTRE MAIN MENU E TOP 5
    private boolean showTopFive;
    // ALTERNAR ENTRE MAIN MENU E CREDITOS POIS NAO EXISTE FSM
    private boolean showCredits;

    /**
     * Constructs a TinyPacmanManager object.
     * Initializes the PropertyChangeSupport instance and sets showCredits to false.
     */
    public TinyPacmanManager(){
        pcs = new PropertyChangeSupport(this);

        showCredits = false;
    }

    /**
     * Adds a property change listener for the specified property.
     *
     * @param property The property to which the listener will be added.
     * @param listener The listener to be added.
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    /**
     * Adds a property change listener to all properties.
     *
     * @param listener The listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Starts the TinyPacman game.
     * Initializes the TinyPacman context and fires a PROP_UPDATE_MAP property change event.
     */
    public void start(){
        fsm = new TinyPacmanContext();      // COLOCAR FSM NO START GAME
        fsm.initGame();
        pcs.firePropertyChange(PROP_UPDATE_MAP, null, null);
    }

    /**
     * Gets the current state of TinyPacman.
     *
     * @return The current state of TinyPacman. Null if the context is null
     */
    public TinyPacmanState getCurrentState() {
        if(fsm!=null)
            return fsm.getCurrentState();
        return null;
    }

    /**
     * Gets the Maze object representing the TinyPacman maze.
     *
     * @return The Maze object representing the TinyPacman maze.
     */
    public Maze getMaze(){
        return fsm.getMaze();
    }

    /**
     * Gets the number of lives remaining for the player.
     *
     * @return The number of lives remaining for the player.
     */
    public int getLives() {
        return fsm.getLives();
    }


    /**
     * Evolves the game to the next state.
     *
     * @param gameEngine The IGameEngine object controlling the game.
     * @param currentTime The current time of the game.
     */
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        if(fsm != null) {
            fsm.move();
            pcs.firePropertyChange(PROP_UPDATE_MAP, null, null);
        }
    }

    /**
     * Pauses the TinyPacman game. (Change to PauseGamedState)
     */
    public void pauseGame() {
        fsm.pauseGame();
    }

    /**
     * Resumes the TinyPacman game. (Change to the last state before pause)
     */
    public void resumeGame() {
        fsm.resumeGame();
    }

    /**
     * Gets the current points of the player.
     *
     * @return The current points of the player.
     */
    public int getPoints(){
        return fsm.getCurrentPoints();
    }

    /**
     * Gets the current game time in seconds.
     *
     * @return The current game time in seconds.
     */
    public int getSeconds(){
        return fsm.getCurrentGameSeconds();
    }

    /**
     * Changes the direction of the Pacman character.
     *
     * @param direction The direction to change to.
     */
    public void changePacmanDirection(Direction direction){
        fsm.changePacmanDirection(direction);
    }

    /**
     * Gets the current direction of the Pacman character.
     *
     * @return The current direction of the Pacman character.
     */
    public Direction getCurrentDirectionPacman(){
        return fsm.getPacmanCurrentDirection();
    }


    /**
     * Toggles the UI between the main menu and the top 5 (no FSM state).
     *
     * @return True if the UI should show the top 5, false otherwise.
     */
    public boolean showTopFive(){ return showTopFive; }

    /**
     * Sets the flag indicating whether the UI should show the top 5 or not.
     *
     * @param newBoolean The new value for the showTopFive flag.
     */
    public void setShowTopFive(boolean newBoolean){
        showTopFive = newBoolean;
        pcs.firePropertyChange(PROP_TOP_FIVE, null, null);
    }

    /**
     * Toggles the UI between the main menu and the credits (no FSM state).
     *
     * @return True if the UI should show the credits, false otherwise.
     */
    public boolean showCredits(){
        return showCredits;
    }

    /**
     * Sets the flag indicating whether the UI should show the credits or not.
     *
     * @param newBoolean The new value for the showCredits flag.
     */
    public void setShowCredits(boolean newBoolean){
        showCredits = newBoolean;
        pcs.firePropertyChange(PROP_CREDITS, null, null);
    }

    /**
     * Gets the vulnerability state of Blinky.
     *
     * @return True if Blinky is vulnerable, false otherwise.
     */
    public boolean getBlinkyVul(){
        return fsm.getBlinkyVul();
    }
    /**
     * Gets the vulnerability state of Clyde.
     *
     * @return True if Clyde is vulnerable, false otherwise.
     */
    public boolean getClydeVul(){
        return fsm.getClydeVul();
    }
    /**
     * Gets the vulnerability state of Pinky.
     *
     * @return True if Pinky is vulnerable, false otherwise.
     */
    public boolean getPinkyVul(){
        return fsm.getPinkyVul();
    }
    /**
     * Gets the vulnerability state of Inky.
     *
     * @return True if Inky is vulnerable, false otherwise.
     */
    public boolean getInkyVul(){
        return fsm.getInkyVul();
    }

    /**
     * Advances to the next level of the game.
     */
    public void nextLevel(){
        fsm.nextLevel();
    }

    /**
     * Loads the game state and data.
     */
    public void load(){
        fsm = new TinyPacmanContext();
        fsm.load();
        pcs.firePropertyChange(PROP_UPDATE_MAP,null,null);
    }

    /**
     * Saves the game state and data.
     */
    public void save() {
        fsm.save();
    }

    /**
     * Checks if a saved game exists.
     *
     * @return True if a saved game exists, false otherwise.
     */
    public boolean checkSavedGame(){
        try(ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("saves/saved_game.dat")
        )){
            return true;
        }
        catch(Exception e){
            System.err.println("No saved game found!");
            System.out.println("" + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes the saved game.
     */
    public void delete(){
        File file = new File("saves/saved_game.dat");
        if(file.delete()){
            System.out.println("Jogo apagado com sucesso!");
        }
        else{
            System.err.println("Erro ao apagar o jogo!");
            Platform.exit();
        }
    }

    /**
     * Updates the top 5 ranking with the given player name.
     *
     * @param nomeJogador The player name to update the ranking.
     */
    public void updateTop5(String nomeJogador) {
        fsm.updateTop5(nomeJogador);
    }

    /**
     * Checks if a new ranking can be added to top5.
     *
     * @return True if a new ranking can be added to top5, false otherwise.
     */
    public boolean checkNewRanking() {
        return fsm.checkNewRanking();
    }

    /**
     * Loads the top 5 rankings from a file.
     *
     * @return the list of top 5 rankings
     */
    public List<Ranking> loadTop5() {
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
