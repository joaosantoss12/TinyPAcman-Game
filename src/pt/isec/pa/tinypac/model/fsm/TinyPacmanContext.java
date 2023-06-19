package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.*;

/**
 * The context class for TinyPacman game, responsible for managing the game state and data.
 * It provides methods to initialize the game, retrieve game information, control game flow, and handle game saves and loads.
 * Implements the Serializable interface to support game serialization.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class TinyPacmanContext implements Serializable {
    private static final long serialVersionUID = 1L;

    TinyPacData data;
    private ITinyPacmanState currentState;

    /**
     * Constructs an instance of the TinyPacmanContext class.
     * Initializes the game data.
     */
    public TinyPacmanContext(){
        this.data = new TinyPacData();
    }


    /**
     * Initializes the game by setting the initial state and calling the initGame method of the current state.
     */
    public void initGame() {
        currentState = TinyPacmanState.createState(TinyPacmanState.INITIAL_STATE, this, data, TinyPacmanState.INITIAL_STATE);
        currentState.initGame();
    }

    /**
     * Retrieves the current maze from the current state.
     *
     * @return The maze object.
     */
    public Maze getMaze(){
        return currentState.getMaze();
    }

    /**
     * Retrieves the number of lives remaining from the current state.
     *
     * @return The number of lives remaining.
     */
    public int getLives() {
        return currentState.getLives();
    }

    /**
     * Moves the game state forward by one step by calling the move method of the current state.
     */
    public void move(){
        currentState.move();
    }

    /**
     * Pauses the game by calling the pauseGame method of the current state.
     */
    public void pauseGame() {
        currentState.pauseGame();
    }

    /**
     * Resumes the game from a paused state by calling the resumeGame method of the current state.
     */
    public void resumeGame() {
        currentState.resumeGame();
    }

    /**
     * Advances to the next game level by calling the nextLevel method of the current state.
     */
    public void nextLevel(){
        currentState.nextLevel();
    }


    /**
     * Changes the direction of Pacman by calling the setPacmanDirection method of the current state.
     *
     * @param direction The direction to set for Pacman.
     */
    public void changePacmanDirection(Direction direction) {
        currentState.setPacmanDirection(direction);
    }

    /**
     * Retrieves the current direction of Pacman from the current state.
     *
     * @return The current direction of Pacman.
     */
    public Direction getPacmanCurrentDirection() {
        return currentState.getPacmanDirection();
    }

    /**
     * Retrieves the current state of the game.
     *
     * @return The current state of the game.
     */
    public TinyPacmanState getCurrentState(){
        if(currentState == null)
            return null;
        return currentState.getState();
    }

    /**
     * Changes the current state of the game.
     *
     * @param newState The new state to set.
     */
    void changeState(ITinyPacmanState newState){
        currentState = newState;
    }



    /**
     * Retrieves the current game time in seconds from the current state.
     *
     * @return The current game time in seconds.
     */
    public int getCurrentGameSeconds(){
        return currentState.getGameSeconds();
    }

    /**
     * Retrieves the current score from the current state.
     *
     * @return The current score.
     */
    public int getCurrentPoints() { return currentState.getCurrentPoints(); }

    /**
     * Retrieves the vulnerability state of Blinky from the current state.
     *
     * @return True if Blinky is vulnerable, false otherwise.
     */
    public boolean getBlinkyVul(){
        return currentState.isBlinkyVulnerable();
    }
    /**
     * Retrieves the vulnerability state of Clyde from the current state.
     *
     * @return True if Clyde is vulnerable, false otherwise.
     */
    public boolean getClydeVul(){
        return currentState.isClydeVulnerable();
    }
    /**
     * Retrieves the vulnerability state of Pinky from the current state.
     *
     * @return True if Pinky is vulnerable, false otherwise.
     */
    public boolean getPinkyVul(){
        return true;
    }
    /**
     * Retrieves the vulnerability state of Inky from the current state.
     *
     * @return True if Inky is vulnerable, false otherwise.
     */
    public boolean getInkyVul(){
        return true;
    }

    /**
     * Saves the current game data and state to a .dat file.
     */
    public void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("saves/saved_game.dat")
        )){
            oos.writeObject(data);
            oos.writeObject(TinyPacmanState.createState(TinyPacmanState.MOVING_GHOSTS, this, this.data, TinyPacmanState.MOVING_GHOSTS));
        }
        catch(Exception e){
            System.err.println("Error saving the file");
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Loads a previously saved game state from a file.
     */
    public void load(){
        try(ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("saves/saved_game.dat")
        )){
            data = (TinyPacData) ois.readObject();
            currentState = TinyPacmanState.createState(TinyPacmanState.INITIAL_STATE, this, data, TinyPacmanState.INITIAL_STATE);//(ITinyPacmanState) ois.readObject();
        }
        catch(Exception e){
            System.err.println("Error loading the file");
            System.out.println("" + e.getMessage());
        }
    }

    /**
     * Updates the top 5 ranking with a new player entry.
     *
     * @param nomeJogador The name of the player.
     */
    public void updateTop5(String nomeJogador) {
        currentState.updateTop5(nomeJogador);
    }

    /**
     * Checks if a new ranking entry can be added to top5.
     *
     * @return True if a new ranking entry can be added to top5, false otherwise.
     */
    public boolean checkNewRanking() {
        return currentState.checkNewRanking();
    }
}
