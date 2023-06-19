package pt.isec.pa.tinypac.model.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanStateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

/**
 * The `MovingPacmanState` class represents the state of the game when Pacman is moving alone.
 * It extends the `TinyPacmanStateAdapter` class.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class MovingPacmanState extends TinyPacmanStateAdapter {

    /**
     * Constructs a `MovingPacmanState` object with the specified context and data.
     *
     * @param context  the `TinyPacmanContext` object
     * @param data     the `TinyPacData` object
     */
    public MovingPacmanState(TinyPacmanContext context, TinyPacData data) {
        super(context, data);
    }

    /**
     * Retrieves the maze.
     *
     * @return the maze
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
     * Checks if Blinky is vulnerable.
     *
     * @return `true` if Blinky is vulnerable, `false` otherwise
     */
    @Override
    public boolean isBlinkyVulnerable(){return data.isBlinkyVulnerable();}
    /**
     * Checks if Clyde is vulnerable.
     *
     * @return `true` if Clyde is vulnerable, `false` otherwise
     */
    @Override
    public boolean isClydeVulnerable(){return data.isClydeVulnerable();}

    /**
     * Moves the game state forward by one step.
     * Handles Pacman movement and ghost movement if current seconds are higher or equal to five
     */
    @Override
    public void move(){
        data.movePacman(data.getPacmanDirection());

        checkWin();

        if(data.getCurrentGameSeconds() >= 5){
            changeState(TinyPacmanState.MOVING_GHOSTS);
            data.moveBlinky();
        }

        data.addCurrentGameSeconds();
    }

    /**
     * Sets the direction of Pacman.
     *
     * @param direction  the direction of Pacman
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
     * Retrieves the number of lives remaining.
     *
     * @return the number of lives remaining
     */
    @Override
    public int getLives(){
        return data.getLives();
    }

    /**
     * Pauses the game.
     */
    @Override
    public void pauseGame() {
        changeState(TinyPacmanState.PAUSED_GAME);
    }

    /**
     * Retrieves the current points.
     *
     * @return the current points
     */
    @Override
    public int getCurrentPoints(){
        return data.getCurrentPoints();
    }

    /**
     * Checks if Pacman has won the game by eating all the maze balls.
     */
    @Override
    public void checkWin(){
        if(data.getMazePointsLeft() == 0)
            changeState(TinyPacmanState.PACMAN_WINS);
    }

    /**
     * Retrieves the current state identifier.
     *
     * @return the current state identifier
     */
    @Override
    public TinyPacmanState getState() {
        return TinyPacmanState.MOVING_PACMAN;
    }
}
