package pt.isec.pa.tinypac.model.states;

import pt.isec.pa.tinypac.model.data.Ranking;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanStateAdapter;

import java.util.List;

/**
 * The `PacmanLosesState` class represents the state of the game when Pacman loses.
 * It extends the `TinyPacmanStateAdapter` class.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class PacmanLosesState extends TinyPacmanStateAdapter {

    /**
     * Constructs a `PacmanLosesState` object with the specified context and data.
     *
     * @param context  the `TinyPacmanContext` object
     * @param data     the `TinyPacData` object
     */
    public PacmanLosesState(TinyPacmanContext context, TinyPacData data) {
        super(context, data);
        data.resetPacman();
        data.resetBlinky();
        data.resetClyde();
        //Pinky.reset();
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
     * Loads the top 5 rankings from the data.
     *
     * @return the list of top 5 rankings
     */
    @Override
    public List<Ranking> loadTop5(){
        return data.readTop5();
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
     * Checks if a new ranking can be added.
     *
     * @return `true` if a new ranking can be added, `false` otherwise
     */
    @Override
    public boolean checkNewRanking(){
        return data.checkTop5update();
    }
    /**
     * Updates the top 5 rankings with the specified player name.
     *
     * @param nomeJogador  the player name
     */
    @Override
    public void updateTop5(String nomeJogador){
        data.updateTop5(nomeJogador);
    }

    /**
     * Retrieves the current state identifier.
     *
     * @return the current state identifier
     */
    @Override
    public TinyPacmanState getState() {
        return TinyPacmanState.PACMAN_LOSES;
    }
}
