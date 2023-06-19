package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElements;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Blinky implements IMazeElements {
    TinyPacData data;

    private int x;
    private int y;

    private boolean isVulnerable;
    public boolean getIsVulnerable(){
        return isVulnerable;
    }
    public void setIsVulnerable(boolean b){
        isVulnerable = b;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Direction currentDirection = Direction.NONE;
    private boolean blinkyFirstMove = false;

    private List<Direction> directionsHistory = new ArrayList<>();

    public Blinky(TinyPacData tinyPacData) {
        data = tinyPacData;
    }

    private Direction getPreviousDirection() {
        if (directionsHistory.isEmpty())
            return Direction.NONE;

        return directionsHistory.get(directionsHistory.size() - 1);
    }
    public void resetDirectionHistory(){
        directionsHistory.clear();
    }

    public void reset() {
        blinkyFirstMove = false;
        secondsBlinkyMoved = 9999;
    }

    private IMazeElements lastPosition;
    private IMazeElements nextPosition;

    private int secondsBlinkyMoved;

    public int getSecondsBlinkyMoved() {
        return secondsBlinkyMoved;
    }

    public void move() {
        if (!blinkyFirstMove) {
            secondsBlinkyMoved = data.getCurrentGameSeconds();

            data.getMaze().set(y, x, new freeSpace());

            lastPosition = data.getMaze().get(y - 1, x);
            data.getMaze().set(y - 1, x, this);

            --y;

            isVulnerable = false;
            blinkyFirstMove = true;
            currentDirection = Direction.UP;

            directionsHistory.add(currentDirection);
        } else {
            alreadyMoved();
            directionsHistory.add(currentDirection);
        }
    }

    private void alreadyMoved() {

        if (!(lastPosition instanceof Clyde) && !(lastPosition instanceof Inky) && !(lastPosition instanceof Pinky) && !(lastPosition instanceof PACMAN))
            data.getMaze().set(y, x, lastPosition);

        switch (currentDirection) {
            case UP -> {
                nextPosition = data.getMaze().get(y - 1, x);

                if (!isWall(nextPosition)) {
                    lastPosition = nextPosition;

                    --y;
                } else {
                    nextPosition = data.getMaze().get(y, x + 1);
                    boolean direita = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y, x - 1);
                    boolean esquerda = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y + 1, x);
                    boolean baixo = isWall(nextPosition);

                    int randomNumber = 0;
                    double random = Math.random();

                    if (random < 0.5) {
                        // ESQUERDA
                        randomNumber = 1;
                    } else if (random > 0.5) {
                        // DIREITA
                        randomNumber = 2;
                    }

                    if (randomNumber == 2) {  // DIREITA
                        if (!direita) {    // SEM PAREDE À DIREITA
                            lastPosition = data.getMaze().get(y, x + 1);
                            ++x;

                            currentDirection = Direction.RIGHT;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);
                            --x;

                            currentDirection = Direction.LEFT;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);

                            ++y;

                            currentDirection = Direction.DOWN;
                        }
                    } else if (randomNumber == 1) {     // ESQUERDA
                        if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);
                            --x;

                            currentDirection = Direction.LEFT;
                        } else if (!direita) {
                            lastPosition = data.getMaze().get(y, x + 1);
                            ++x;

                            currentDirection = Direction.RIGHT;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);
                            ++y;

                            currentDirection = Direction.DOWN;
                        }
                    }
                }
            }
            case RIGHT -> {
                nextPosition = data.getMaze().get(y, x + 1);

                if (!isWall(nextPosition)) {

                    lastPosition = nextPosition;
                    ++x;

                    currentDirection = Direction.RIGHT;
                } else {
                    nextPosition = data.getMaze().get(y - 1, x);
                    boolean cima = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y, x - 1);
                    boolean esquerda = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y + 1, x);
                    boolean baixo = isWall(nextPosition);

                    int randomNumber = 0;
                    double random = Math.random();

                    if (random < 0.50) {
                        // CIMA
                        randomNumber = 1;
                    } else if (random > 0.50) {
                        // BAIXO
                        randomNumber = 2;
                    }

                    if (randomNumber == 2) {
                        if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);
                            ++y;

                            currentDirection = Direction.DOWN;
                        } else if (!cima) {    // SEM PAREDE À CIMA
                            lastPosition = data.getMaze().get(y - 1, x);
                            --y;

                            currentDirection = Direction.UP;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);
                            --x;

                            currentDirection = Direction.LEFT;
                        }
                    } else if (randomNumber == 1) {  // CIMA
                        if (!cima) {    // SEM PAREDE À CIMA
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;

                            currentDirection = Direction.UP;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);
                            ++y;

                            currentDirection = Direction.DOWN;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);
                            --x;

                            currentDirection = Direction.LEFT;
                        }
                    }
                }
            }
            case LEFT -> {
                nextPosition = data.getMaze().get(y, x - 1);

                if (!isWall(nextPosition)) {
                    lastPosition = nextPosition;

                    --x;

                    currentDirection = Direction.LEFT;
                } else {
                    nextPosition = data.getMaze().get(y, x + 1);
                    boolean direita = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y - 1, x);
                    boolean cima = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y + 1, x);
                    boolean baixo = isWall(nextPosition);

                    int randomNumber = 0;
                    double random = Math.random();

                    if (random < 0.50) {
                        // CIMA
                        randomNumber = 1;
                    } else if (random > 0.50) {
                        // BAIXO
                        randomNumber = 2;
                    }

                    if (randomNumber == 2) {
                        if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);
                            ++y;

                            currentDirection = Direction.DOWN;
                        } else if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);
                            --y;

                            currentDirection = Direction.UP;
                        } else if (!direita) {    // SEM PAREDE À DIREITA
                            lastPosition = data.getMaze().get(y, x + 1);
                            ++x;

                            currentDirection = Direction.RIGHT;
                        }
                    } else if (randomNumber == 1) {     // ESQUERDA
                        if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);
                            --y;
                            currentDirection = Direction.UP;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);
                            ++y;

                            currentDirection = Direction.DOWN;
                        }
                        // ULTIMO CASO VOLTA PARA TRAS
                        else if (!direita) {
                            lastPosition = data.getMaze().get(y, x + 1);
                            ++x;

                            currentDirection = Direction.RIGHT;
                        }
                    }
                }
            }
            case DOWN -> {
                nextPosition = data.getMaze().get(y + 1, x);

                if (!isWall(nextPosition)) {
                    lastPosition = nextPosition;

                    ++y;

                    currentDirection = Direction.DOWN;
                } else {
                    nextPosition = data.getMaze().get(y, x + 1);
                    boolean direita = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y, x - 1);
                    boolean esquerda = isWall(nextPosition);
                    nextPosition = data.getMaze().get(y - 1, x);
                    boolean cima = isWall(nextPosition);

                    int randomNumber = 0;
                    double random = Math.random();

                    if (random < 0.50) {
                        // ESQUERDA
                        randomNumber = 1;
                    } else if (random > 0.50) {
                        // DIREITA
                        randomNumber = 2;
                    }
                    if (randomNumber == 2) {  // DIREITA
                        if (!direita) {    // SEM PAREDE À DIREITA
                            lastPosition = data.getMaze().get(y, x + 1);

                            ++x;

                            currentDirection = Direction.RIGHT;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;

                            currentDirection = Direction.LEFT;
                        } else if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;

                            currentDirection = Direction.UP;
                        }
                    } else if (randomNumber == 1) {     // ESQUERDA
                        if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;

                            currentDirection = Direction.LEFT;
                        } else if (!direita) {
                            lastPosition = data.getMaze().get(y, x + 1);

                            ++x;

                            currentDirection = Direction.RIGHT;
                        } else if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;

                            currentDirection = Direction.UP;
                        }
                    }
                }
            }
        }

        if(!(nextPosition instanceof PACMAN)){
            data.getMaze().set(y, x, this);
        }

    }

    public void moveBackwards() {
        Direction previousDirection = getPreviousDirection();

        switch (previousDirection) {
            case UP:
                data.getMaze().set(y, x, nextPosition);
                nextPosition = data.getMaze().get(y + 1, x);
                ++y;
                break;
            case DOWN:
                data.getMaze().set(y, x, nextPosition);
                nextPosition = data.getMaze().get(y - 1, x);
                --y;
                break;
            case RIGHT:
                data.getMaze().set(y, x, nextPosition);
                nextPosition = data.getMaze().get(y, x - 1);
                --x;
                break;
            case LEFT:
                data.getMaze().set(y, x, nextPosition);
                nextPosition = data.getMaze().get(y, x + 1);
                ++x;
                break;
        }
        data.getMaze().set(y, x, this);
        directionsHistory.remove(directionsHistory.size() - 1);

    }

    private boolean isWall(IMazeElements element) {
        return (element instanceof Wall);     // true se for da class Wall
    }

    public void setBlinkyFirstMove(boolean b) {
        blinkyFirstMove = b;
    }

    @Override
    public char getSymbol() {
        return 'B';
    }
}
