package pt.isec.pa.tinypac.model.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanStateAdapter;
import pt.isec.pa.tinypac.utils.Direction;

/**
 * The VulnerableGhostsState represents the game when ghosts are vulnerable in Tiny Pacman.
 * Extends the TinyPacmanStateAdapter class.
 *
 * @author João Santos
 * @version 1.0.0
 */
public class VulnerableGhostsState extends TinyPacmanStateAdapter {

    /**
     * Constructs a new VulnerableGhostsState object with the specified context and data.
     *
     * @param context the TinyPacmanContext object
     * @param data    the TinyPacData object
     */
    public VulnerableGhostsState(TinyPacmanContext context, TinyPacData data){
        super(context, data);
    }

    /**
     * Retrieves the maze object.
     *
     * @return the Maze object
     */
    @Override
    public Maze getMaze(){
        return data.getMaze();
    }

    /**
     * Retrieves the elapsed game seconds.
     *
     * @return the game seconds
     */
    @Override
    public int getGameSeconds(){
        return data.getCurrentGameSeconds();
    }

    /**
     * Checks if Blinky (the red ghost) is vulnerable.
     *
     * @return true if Blinky is vulnerable, false otherwise
     */
    @Override
    public boolean isBlinkyVulnerable(){return data.isBlinkyVulnerable();}
    /**
     * Checks if Clyde (the orange ghost) is vulnerable.
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
     * Moves the game state forward by one step.
     * Handles Pacman movement, ghost movement, and checks for win or loss conditions.
     */
    @Override
    public void move(){
        if(data.getVulnerabilitySeconds() == 0) {
            data.setBlinkyVulnerable(false);
            data.setClydeVulnerable(false);
            changeState(TinyPacmanState.MOVING_GHOSTS);
            return;
        }

        data.movePacman(data.getPacmanDirection());

        checkPacmanEatGhosts();
        checkPacmanOpPoint();

        checkWin();

        if(data.isBlinkyVulnerable())
            data.moveBlinkyBackwards();
        else
            data.moveBlinky();

        if ((data.getCurrentGameSeconds() - data.getSecondsBlinkyMoved()) >= 3 || data.getClydeFirstMove()) {
            if(data.isClydeVulnerable())
                data.moveClydeBackwards();
            else
                data.moveClyde();
        }

        if((data.getBlinkyX() == data.getGhostSpawnX() && data.getBlinkyY() == data.getGhostSpawnY() && data.isBlinkyVulnerable()) ||
        data.getClydeX() == data.getGhostSpawnX() && data.getClydeY() == data.getGhostSpawnY() && data.isClydeVulnerable()) {
            changeState(TinyPacmanState.MOVING_GHOSTS);
            return;
        }

        checkPacmanEatGhosts();

        data.addCurrentGameSeconds();
        data.decrementVulnerabilitySeconds();
    }

    /**
     * Checks if Pacman reaches an overpowered point and adds vulnerability time if so.
     */
    @Override
    public void checkPacmanOpPoint(){
        if(data.pacmanCheckOpPoint())
            data.addVulnerabilityTime();
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
     * Checks if Pacman has eaten the ghosts and updates the game state accordingly.
     */
    @Override
    public void checkPacmanEatGhosts(){
        if(data.getPacmanX() == data.getBlinkyX() && data.getPacmanY() == data.getBlinkyY()) {   // SE O PACMAN FOR PARA CIMA DO BLINKY OU VICE VERSA
            if(data.isBlinkyVulnerable()) {
                data.pacmanIncGhostsEaten();

                if (data.pacmanGetGhostsEaten() <= 4) {
                    data.addPoints(50 * data.pacmanGetGhostsEaten());
                } else {
                    data.pacmanResetGhostsEaten();
                    data.addPoints(50);
                }

                data.setBlinkyVulnerable(false);

                data.resetBlinkyDirectionHistory();

                data.respawnBlinky();

                data.setBlinkyFirstMove(false);
            }
            else{
                data.setBlinkyFirstMove(false);
                data.setClydeFirstMove(false);

                data.loseLive();

                if(data.getLives() == -1){
                    changeState(TinyPacmanState.PACMAN_LOSES);
                }

                data.resetMaze();
            }
        }
        else if(data.getPacmanX() == data.getClydeX() && data.getPacmanY() == data.getClydeY()){
            if(data.isClydeVulnerable()) {
                data.pacmanIncGhostsEaten();

                if (data.pacmanGetGhostsEaten() <= 4) {
                    data.addPoints(50 * data.pacmanGetGhostsEaten());
                } else {
                    data.pacmanResetGhostsEaten();
                    data.addPoints(50);
                }

                data.setClydeVulnerable(false);

                data.resetClydeDirectionHistory();

                data.respawnClyde();

                data.setClydeFirstMove(false);
            }
            else{
                data.setBlinkyFirstMove(false);
                data.setClydeFirstMove(false);

                data.loseLive();

                if(data.getLives() == -1){
                    changeState(TinyPacmanState.PACMAN_LOSES);
                }

                data.resetMaze();
            }
        }
        /*else if(PACMAN.comiPinky()){
            data.getMaze().set(data.coordinates().getGhostSpawnY() + 1, data.coordinates().getGhostSpawnX(), new Pinky(data));
            Blinky.setX(data.coordinates().getGhostSpawnX());
            Blinky.setY(data.coordinates().getGhostSpawnY() + 1);   // POSIÇAO ANTES DA SAIDA
        }
        else if(PACMAN.comiInky()){
            data.getMaze().set(data.coordinates().getGhostSpawnY() + 1, data.coordinates().getGhostSpawnX(), new Inky(data));
            Blinky.setX(data.coordinates().getGhostSpawnX());
            Blinky.setY(data.coordinates().getGhostSpawnY() + 1);   // POSIÇAO ANTES DA SAIDA
        }*/
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
     * Pauses the game. (Change state to PausedGameState)
     */
    @Override
    public void pauseGame() {
        changeState(TinyPacmanState.PAUSED_GAME);
    }

    /**
     * Retrieves the current points accumulated by Pacman.
     *
     * @return the current points
     */
    @Override
    public int getCurrentPoints(){
        return data.getCurrentPoints();
    }

    /**
     * Retrieves the current state identifier.
     *
     * @return the current state identifier
     */
    @Override
    public TinyPacmanState getState() {
        return TinyPacmanState.VULNERABLE_GHOSTS;
    }
}
