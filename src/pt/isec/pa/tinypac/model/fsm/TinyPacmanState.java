package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.states.*;

import java.io.Serializable;

public enum TinyPacmanState implements Serializable{
    INITIAL_STATE, MOVING_PACMAN, MOVING_GHOSTS, VULNERABLE_GHOSTS, PACMAN_LOSES, PACMAN_WINS, PAUSED_GAME;

    static ITinyPacmanState createState(TinyPacmanState type, TinyPacmanContext context, TinyPacData data, TinyPacmanState lastState){
        return switch(type){
            case INITIAL_STATE -> new InitialState(context, data);
            case MOVING_PACMAN -> new MovingPacmanState(context, data);
            case MOVING_GHOSTS -> new MovingGhostsState(context, data);
            case VULNERABLE_GHOSTS -> new VulnerableGhostsState(context, data);
            case PACMAN_LOSES -> new PacmanLosesState(context, data);
            case PACMAN_WINS -> new PacmanWinsState(context, data);

            case PAUSED_GAME -> new PausedGameState(context, data, lastState);
        };
    }
}
