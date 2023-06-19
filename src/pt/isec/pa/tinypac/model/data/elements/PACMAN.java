package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElements;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.utils.Direction;

public class PACMAN implements IMazeElements{
    TinyPacData data;
    
    private int x;
    private int y;
    private Direction currentDirectionPacman = Direction.NONE;

    public int getX() {
        return x;
    }
    public void setX(int newX) {
        x = newX;
    }

    public int getY() {
        return y;
    }
    public void setY(int newY) {
        y = newY;
    }

    private int nGhostsEaten = 0;
    private int nBallsEaten = 0;
    private int nFoodComida = 0;

    public PACMAN(TinyPacData data) {
        this.data = data;
    }

    private IMazeElements lastPosition;
    private IMazeElements nextPosition;

    private boolean found_powerBall;


    public void reset(){
        currentDirectionPacman = Direction.NONE;
        lastPosition = null;
        nextPosition = null;
        found_powerBall = false;
        nGhostsEaten = 0;
        nBallsEaten = 0;
        nFoodComida = 0;
    }

    public void setCurrentDirection(Direction nextDirection) {
        currentDirectionPacman = nextDirection;
    }
    public Direction getCurrentDirection() {
        return currentDirectionPacman;
    }

    public void move(Direction nextDirection) {

        if (!(lastPosition instanceof Blinky || lastPosition instanceof Clyde || lastPosition instanceof Point || lastPosition instanceof opPoint || lastPosition instanceof Food))
            data.getMaze().set(y, x, lastPosition);
        else
            data.getMaze().set(y, x, new freeSpace());


        found_powerBall = false;

        switch (nextDirection) {
            case UP -> {

                nextPosition = data.getMaze().get(y - 1, x);

                if (!isWall(nextPosition)) {

                    --y;

                    currentDirectionPacman = Direction.UP;
                    lastPosition = nextPosition;
                }
            }

            case DOWN -> {
                nextPosition = data.getMaze().get(y + 1, x);

                if (!isWall(nextPosition)) {

                    ++y;

                    currentDirectionPacman = Direction.DOWN;
                    lastPosition = nextPosition;
                }
            }

            case RIGHT -> {
                nextPosition = data.getMaze().get(y, x + 1);

                if (!isWall(nextPosition)) {

                    ++x;

                    currentDirectionPacman = Direction.RIGHT;
                    lastPosition = nextPosition;
                }
            }

            case LEFT -> {
                nextPosition = data.getMaze().get(y, x - 1);

                if (!isWall(nextPosition)) {

                    --x;

                    currentDirectionPacman = Direction.LEFT;
                    lastPosition = nextPosition;
                }
            }
        }
        data.getMaze().set(y, x, this);

        check_ball_add_points_food();
        check_food();
        check_portal(y, x, currentDirectionPacman);
    }

    private void check_food() {
        if (lastPosition instanceof Food) {
            ++nFoodComida;
            data.addPoints(25 * nFoodComida);
            nBallsEaten = 0;
        }
    }

    private void check_ball_add_points_food() {
        if (isPoint(lastPosition)) {
            data.decMazePoints();
            if (isOpPoint(lastPosition)) {
                found_powerBall = true;
                data.addPoints(10);
            }
            else
                data.addPoints(1);

            nBallsEaten++;

            if (nBallsEaten % 20 == 0) {
                data.getMaze().set(data.getFoodSpawnY(), data.getFoodSpawnX(), new Food());
            }
        }
    }

    private void check_portal(int currentY, int currentX, Direction current) {
        if (nextPosition instanceof Warp && currentX == data.getONE_wrapSpawnX() && currentY == data.getONE_wrapSpawnY()) {

            data.getMaze().set(data.getTWO_wrapSpawnY(), data.getTWO_wrapSpawnX(), this);

            x = data.getTWO_wrapSpawnX();
            y = data.getTWO_wrapSpawnY();

            data.getMaze().set(data.getONE_wrapSpawnY(), data.getONE_wrapSpawnX(), new Warp());

        }
        else if (nextPosition instanceof Warp && currentX == data.getTWO_wrapSpawnX() && currentY == data.getTWO_wrapSpawnY()) {

            data.getMaze().set(data.getONE_wrapSpawnY(), data.getONE_wrapSpawnX(), this);

            x = data.getONE_wrapSpawnX();
            y = data.getONE_wrapSpawnY();

            data.getMaze().set(data.getTWO_wrapSpawnY(), data.getTWO_wrapSpawnX(), new Warp());
        }

        currentDirectionPacman = current;
    }

    public boolean checkOpPoint() {
        return found_powerBall;
    }

    private boolean isWall(IMazeElements element) {
        return (element instanceof Wall || element instanceof ghostSpawn);
    }

    private boolean isPoint(IMazeElements element) {
        return (element instanceof Point || element instanceof opPoint);
    }

    private boolean isOpPoint(IMazeElements element) {
        return (element instanceof opPoint);
    }


    public void addGhostsEaten() {
        ++nGhostsEaten;
    }

    public int getGhostsEaten() {
        return nGhostsEaten;
    }

    public void resetGhostsEaten() {
        nGhostsEaten = 1;
    }

    @Override
    public char getSymbol() {
        return 'p';
    }
}
