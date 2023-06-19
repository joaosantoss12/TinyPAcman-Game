package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.Ranking;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.Serializable;
import java.util.List;

public interface ITinyPacmanState extends Serializable {
    static final long serialVersionUID = 1L;

    Maze getMaze();

    void move();

    void checkPacmanOpPoint();

    int getLives();

    void initGame();

    int getGameSeconds();

    void setPacmanDirection(Direction direction);
    Direction getPacmanDirection();

    void resumeGame();
    void pauseGame();

    void checkPacmanEatGhosts();

    boolean checkLost();
    void checkWin();

    int getCurrentPoints();

    boolean isBlinkyVulnerable();
    boolean isClydeVulnerable();

    List<Ranking> loadTop5();
    void updateTop5(String nomeJogador);
    boolean checkNewRanking();

    void nextLevel();

    TinyPacmanState getState();
    TinyPacmanState getLastState();
}
