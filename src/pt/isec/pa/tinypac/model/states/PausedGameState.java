package pt.isec.pa.tinypac.model.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.fsm.ITinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanStateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

/**
 * The PausedGameState class represents the state of the game when it is paused.
 * It extends the TinyPacmanStateAdapter class.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class PausedGameState extends TinyPacmanStateAdapter {
    private TinyPacmanState lastState;

    /**
     * Constructs a `PausedGameState` object with the specified context, data, and last state.
     *
     * @param context    the `TinyPacmanContext` object
     * @param data       the `TinyPacData` object
     * @param lastState  the last state before the game was paused
     */
    public PausedGameState(TinyPacmanContext context, TinyPacData data, TinyPacmanState lastState) {
        super(context, data);
        this.lastState = lastState;
        
        System.out.printf("\n\nULTIMO ESTADO ANTES DO PAUSE: %s", lastState);
    }

    /**
     * Retrieves the maze object associated with the game state.
     *
     * @return the `Maze` object representing the game maze
     */
    @Override
    public Maze getMaze(){
        return data.getMaze();
    }

    /**
     * Retrieves the number of game seconds elapsed.
     *
     * @return the number of game seconds
     */
    @Override
    public int getGameSeconds(){
        return data.getCurrentGameSeconds();
    }

    /**
     * Checks if Blinky (red ghost) is vulnerable.
     *
     * @return true if Blinky is vulnerable, false otherwise
     */
    @Override
    public boolean isBlinkyVulnerable(){return data.isBlinkyVulnerable();}
    /**
     * Checks if Clyde (orange ghost) is vulnerable.
     *
     * @return true if Clyde is vulnerable, false otherwise
     */
    @Override
    public boolean isClydeVulnerable(){return data.isClydeVulnerable();}

    /**
     * Sets the direction of Pacman.
     *
     * @param direction the direction to set for Pacman
     */
    @Override
    public void setPacmanDirection(Direction direction){
        data.setPacmanDirection(direction);
    }
    /**
     * Retrieves the direction of Pacman.
     *
     * @return the direction of Pacman
     */
    @Override
    public Direction getPacmanDirection(){
        return data.getPacmanDirection();
    }

    /**
     * Retrieves the current points of the current game.
     *
     * @return the current points
     */
    @Override
    public int getCurrentPoints(){
        return data.getCurrentPoints();
    }

    /**
     * Retrieves the number of lives remaining for Pacman.
     *
     * @return the number of lives
     */
    @Override
    public int getLives(){
        return data.getLives();
    }

    /**
     * Resumes the game from the paused state and changes the state to the last state.
     */
    @Override
    public void resumeGame(){
        changeState(lastState);
    }

    /**
     * Retrieves the last state before the game was paused.
     *
     * @return the last state
     */
    @Override
    public TinyPacmanState getLastState(){
        return lastState;
    }

    /**
     * Retrieves the current state identifier.
     *
     * @return the current state identifier
     */
    @Override
    public TinyPacmanState getState() {
        return TinyPacmanState.PAUSED_GAME;
    }
}
