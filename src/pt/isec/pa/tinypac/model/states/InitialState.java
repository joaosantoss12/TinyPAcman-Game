package pt.isec.pa.tinypac.model.states;


import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanStateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

/**
 * Represents the initial state of the game.
 * This state is active when the game is starting or restarting.
 * It handles the initialization of the game and transitions to the moving state when the Pacman's direction is set.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class InitialState extends TinyPacmanStateAdapter {

    /**
     * Constructs an instance of the InitialState class.
     *
     * @param context The TinyPacmanContext object.
     * @param data    The TinyPacData object containing game data.
     */
    public InitialState(TinyPacmanContext context, TinyPacData data){
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
     * Initializes the game data.
     * This method resets the game and prepares it for a new round.
     */
    @Override
    public void initGame(){
        data.initGame();
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
     * Moves the game state forward by one step.
     * If the Pacman's direction is set, it transitions to the MovingPacmanState.
     * Otherwise, it remains in the initial state.
     */
    @Override
    public void move(){
        if(data.getPacmanDirection() != Direction.NONE){
            changeState(TinyPacmanState.MOVING_PACMAN);
            data.movePacman(data.getPacmanDirection());

            data.addCurrentGameSeconds();
        }
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
     * @return the number of lives
     */
    @Override
    public int getLives(){
        return data.getLives();
    }

    /**
     * Pauses the game. (Change to PausedGameState)
     */
    @Override
    public void pauseGame(){
        changeState(TinyPacmanState.PAUSED_GAME);
    }

    /**
     * Retrieves the state of the game.
     *
     * @return the state of the game
     */
    @Override
    public TinyPacmanState getState() {
        return TinyPacmanState.INITIAL_STATE;
    }
}
