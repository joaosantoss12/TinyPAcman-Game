package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.Ranking;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.utils.Direction;

import java.util.List;

/**
 * An abstract adapter class for the TinyPacman state interface, providing default implementations for the methods.
 * Custom state classes can extend this adapter class to only override the necessary methods.
 *
 * @author João Santos
 * @version 1.0.0
 */
public abstract class TinyPacmanStateAdapter implements ITinyPacmanState{
    protected TinyPacData data;
    protected TinyPacmanContext context;

    /**
     * Constructs an instance of the TinyPacmanStateAdapter class.
     *
     * @param context The TinyPacmanContext object.
     * @param data    The TinyPacData object containing game data.
     */
    protected TinyPacmanStateAdapter(TinyPacmanContext context, TinyPacData data){
        this.data = data;
        this.context = context;
    }

    /**
     * Retrieves the maze.
     *
     * @return The maze object.
     */
    @Override
    public Maze getMaze(){
        return null;
    }

    /**
     * Sets the direction of Pacman.
     *
     * @param direction The direction to set.
     */
    @Override
    public void setPacmanDirection(Direction direction) {
    }

    /**
     * Retrieves the current direction of Pacman.
     *
     * @return The direction of Pacman.
     */
    @Override
    public Direction getPacmanDirection() {
        return null;
    }

    /**
     * Initializes the game.
     */
    @Override
    public void initGame(){}

    /**
     * Retrieves the game time in seconds.
     *
     * @return The game time in seconds.
     */
    @Override
    public int getGameSeconds(){
        return 0;
    }

    /**
     * Retrieves the number of lives remaining.
     *
     * @return The number of lives remaining.
     */
    @Override
    public int getLives(){return 0;}

    /**
     * Moves the game state forward by one step.
     * Handles elements movement and game logic.
     */
    @Override
    public void move(){}

    /**
     * Checks if Pacman caught a powerball.
     */
    @Override
    public void checkPacmanOpPoint() {}

    /**
     * Checks if Pacman has lost the game.
     *
     * @return True if Pacman has lost, false otherwise.
     */
    @Override
    public boolean checkLost(){return false;}
    /**
     * Checks if Pacman has won the game.
     */
    @Override
    public void checkWin(){}

    /**
     * Checks if Pacman has eaten ghosts.
     */
    @Override
    public void checkPacmanEatGhosts() {}

    /**
     * Checks if Blinky (Red Ghost) is vulnerable.
     *
     * @return True if Blinky is vulnerable, false otherwise.
     */
    @Override
    public boolean isBlinkyVulnerable(){return false;}
    /**
     * Checks if Clyde (Orange Ghost) is vulnerable.
     *
     * @return True if Clyde is vulnerable, false otherwise.
     */
    @Override
    public boolean isClydeVulnerable(){return false;}

    /**
     * Resumes the game from a paused state.
     */
    @Override
    public void resumeGame() {}

    /**
     * Pauses the game.
     */
    @Override
    public void pauseGame() {}

    /**
     * Retrieves the current score.
     *
     * @return The current score.
     */
    @Override
    public int getCurrentPoints(){
        return 0;
    }

    /**
     * Advances to the next game level.
     */
    @Override
    public void nextLevel(){}

    /**
     * Loads the top 5 ranking.
     *
     * @return A list of ranking entries.
     */
    @Override
    public List<Ranking> loadTop5(){return null;}
    /**
     * Updates the top 5 ranking with a new entry.
     *
     * @param nomeJogador The name of the player.
     */
    @Override
    public void updateTop5(String nomeJogador){}
    /**
     * Checks if a new ranking entry can be added to top5.
     *
     * @return True if a new ranking entry can be added to top5, false otherwise.
     */
    @Override
    public boolean checkNewRanking(){return false;}

    /**
     * Retrieves the last game state.
     *
     * @return The last game state.
     */
    @Override
    public TinyPacmanState getLastState(){
        return null;
    }

    /**
     * Retrieves the current state identifier.
     *
     * @return the current state identifier
     */
    protected void changeState(TinyPacmanState newState){
        context.changeState(TinyPacmanState.createState(newState, context, data, getState()));
    }
}
