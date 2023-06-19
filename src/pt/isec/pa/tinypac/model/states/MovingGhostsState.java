package pt.isec.pa.tinypac.model.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanStateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

/**
 * The `MovingGhostsState` class represents the state of the game when the ghosts are moving.
 * It extends the `TinyPacmanStateAdapter` class.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class MovingGhostsState extends TinyPacmanStateAdapter {

    /**
     * Constructs a `MovingGhostsState` object with the specified context and data.
     *
     * @param context  the `TinyPacmanContext` object
     * @param data     the `TinyPacData` object
     */
    public MovingGhostsState(TinyPacmanContext context, TinyPacData data) {
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
     * Handles Pacman movement and ghosts movement,
     * checks for game-ending conditions (win or lose), and performs necessary actions accordingly.
     * It also manages the timing of the game.
     */
    @Override
    public void move(){
        data.movePacman(data.getPacmanDirection());
        checkPacmanOpPoint();

        checkWin();

        if(checkLost()){
            data.setBlinkyFirstMove(false);
            data.setClydeFirstMove(false);

            data.loseLive();

            if(data.getLives() == -1){
                changeState(TinyPacmanState.PACMAN_LOSES);
            }

            data.resetMaze();
        }


        data.moveBlinky();

        if ((data.getCurrentGameSeconds() - data.getSecondsBlinkyMoved()) >= 3) {
            data.moveClyde();
        }
        else{
            //data.getMaze().set(data.coordinates().getGhostSpawnY()+1, data.coordinates().getGhostSpawnX(), new Pinky(data));

            data.respawnClyde();
        }

        if ((data.getCurrentGameSeconds() - data.getSecondsClydeMoved()) >= 3) {
            //Pinky.move();
        }
        else{
            //data.getMaze().set(data.coordinates().getGhostSpawnY()+1, data.coordinates().getGhostSpawnX(), new Pinky(data));
            //Pinky.setX(data.coordinates().getGhostSpawnX());
            //Pinky.setY(data.coordinates().getGhostSpawnY()+1);   // POSIÇAO ANTES DA SAIDA
        }

        if(checkLost()){
            data.setBlinkyFirstMove(false);
            data.setClydeFirstMove(false);

            data.loseLive();

            if(data.getLives() == -1){
                changeState(TinyPacmanState.PACMAN_LOSES);
            }

            data.resetMaze();
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
     * Checks if Pacman reaches an overpowered point.
     * If true, sets the ghosts variable "vulnerable" to true and changes to VulnerableGhostsState.
     */
    @Override
    public void checkPacmanOpPoint(){
        if(data.pacmanCheckOpPoint()){
            data.addVulnerabilityTime();
            data.setBlinkyVulnerable(true);
            data.setClydeVulnerable(true);
            changeState(TinyPacmanState.VULNERABLE_GHOSTS);
        }
    }

    /**
     * Checks if Pacman has won the game by eating all the maze balls.
     */
    @Override
    public void checkWin(){
        if(data.getMazePointsLeft() == 0) {
            changeState(TinyPacmanState.PACMAN_WINS);
        }
    }

    /**
     * Checks if Pacman loses the game.
     *
     * @return `true` if Pacman loses, `false` otherwise
     */
    @Override
    public boolean checkLost(){
        return (data.getPacmanX() == data.getBlinkyX() && data.getPacmanY() == data.getBlinkyY()) ||
                (data.getPacmanX() == data.getClydeX() && data.getPacmanY() == data.getClydeY());
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
     * Retrieves the state of the game.
     *
     * @return the state of the game
     */
    @Override
    public TinyPacmanState getState() {
        return TinyPacmanState.MOVING_GHOSTS;
    }
}
