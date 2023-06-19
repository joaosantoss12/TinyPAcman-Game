package pt.isec.pa.tinypac.model.states;

import pt.isec.pa.tinypac.model.data.IMazeElements;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.data.elements.Blinky;
import pt.isec.pa.tinypac.model.data.elements.Clyde;
import pt.isec.pa.tinypac.model.data.elements.PACMAN;
import pt.isec.pa.tinypac.model.data.elements.Pinky;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanStateAdapter;

/**
 * The `PacmanWinsState` class represents the state of the game when Pacman wins.
 * It extends the `TinyPacmanStateAdapter` class.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class PacmanWinsState extends TinyPacmanStateAdapter {

    /**
     * Constructs a `PacmanWinsState` object with the specified context and data.
     *
     * @param context  the `TinyPacmanContext` object
     * @param data     the `TinyPacData` object
     */
    public PacmanWinsState(TinyPacmanContext context, TinyPacData data) {
        super(context, data);
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
     * Proceeds to the next level of the game.
     * It resets Pacman, Blinky, Clyde, and the maze,
     * increments the current level, and initializes the game.
     * The state is then changed to the initial state.
     */
    @Override
    public void nextLevel(){
        data.resetPacman();
        data.resetBlinky();       //JA ESTA A SER FEITO NO DATA, SO FAZEMOS O RESET DO FIRST MOVE
        data.resetClyde();
        //Pinky.reset();


        data.resetMaze();
        data.setCurrentLevel(data.getCurrentLevel()+1);
        data.initGame();

        changeState(TinyPacmanState.INITIAL_STATE);
    }

    /**
     * Retrieves the state identifier.
     *
     * @return the state identifier
     */
    @Override
    public TinyPacmanState getState() {
        return TinyPacmanState.PACMAN_WINS;
    }
}
