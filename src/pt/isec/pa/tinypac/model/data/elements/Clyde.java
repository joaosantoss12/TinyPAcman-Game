package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElements;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Clyde implements IMazeElements {
    TinyPacData data;

    private List<Direction> directionsHistory = new ArrayList<>();

    public Clyde(TinyPacData tinyPacData) {
        data = tinyPacData;
    }

    private Direction getPreviousDirection() {
        if (directionsHistory.isEmpty())
            return Direction.NONE;

        return directionsHistory.get(directionsHistory.size() - 1);
    }

    public void resetDirectionHistory() {
        directionsHistory.clear();
    }

    public void reset() {
        clydeFirstMove = false;
        secondsClydeMoved = 9999;

        x = data.getGhostSpawnX();
        y = data.getGhostSpawnY() + 1;   // POSIÇAO ANTES DA SAIDA

        data.getMaze().set(y, x, new Clyde(data));
    }

    private boolean clydeFirstMove = false;

    private IMazeElements lastPosition;
    private IMazeElements nextPosition;

    private Direction currentDirection = Direction.NONE;

    private boolean catchPacman = false;


    private int x;
    private int y;

    private boolean isVulnerable;
    public boolean getIsVulnerable(){
        return isVulnerable;
    }
    public void setIsVulnerable(boolean b){
        isVulnerable = b;
    }

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


    private int secondsClydeMoved;

    public int getSecondsClydeMoved() {
        return secondsClydeMoved;
    }

    public boolean getFirstMove(){
        return clydeFirstMove;
    }

    public void move() {
        if (!clydeFirstMove) {
            secondsClydeMoved = data.getCurrentGameSeconds();

            data.getMaze().set(y, x, new freeSpace());

            lastPosition = data.getMaze().get(y - 1, x);
            data.getMaze().set(y - 1, x, new Clyde(data));

            --y;

            isVulnerable = false;
            clydeFirstMove = true;
            currentDirection = Direction.UP;

            directionsHistory.add(currentDirection);
        } else {   // JA ANDOU
            alreadyMoved();
            directionsHistory.add(currentDirection);
        }
    }

    private void alreadyMoved() {
        checkPacman_onVision();

        if (!(lastPosition instanceof Blinky) && !(lastPosition instanceof Inky) && !(lastPosition instanceof Pinky) && !(lastPosition instanceof PACMAN))
            data.getMaze().set(y, x, lastPosition);

        switch (currentDirection) {
            case UP -> {
                nextPosition = data.getMaze().get(y - 1, x);

                if (!isWall(nextPosition)) {
                    lastPosition = nextPosition;

                    --y;     // X É O MESMO
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

                            ++x;     // Y É O MESMO

                            currentDirection = Direction.RIGHT;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;     // Y É O MESMO

                            currentDirection = Direction.LEFT;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);

                            ++y;     // Y É O MESMO

                            currentDirection = Direction.DOWN;
                        }
                    } else if (randomNumber == 1) {     // ESQUERDA
                        if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;     // Y É O MESMO

                            currentDirection = Direction.LEFT;
                        } else if (!direita) {
                            lastPosition = data.getMaze().get(y, x + 1);

                            ++x;     // Y É O MESMO

                            currentDirection = Direction.RIGHT;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);

                            ++y;     // X É O MESMO

                            currentDirection = Direction.DOWN;
                        }
                    }
                }
            }
            case RIGHT -> {
                nextPosition = data.getMaze().get(y, x + 1);

                if (!isWall(nextPosition)) {

                    lastPosition = nextPosition;

                    ++x;     // Y É O MESMO

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

                            ++y;     // Y É O MESMO

                            currentDirection = Direction.DOWN;
                        } else if (!cima) {    // SEM PAREDE À CIMA
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;     // Y É O MESMO

                            currentDirection = Direction.UP;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;     // Y É O MESMO

                            currentDirection = Direction.LEFT;
                        }
                    } else if (randomNumber == 1) {  // CIMA
                        if (!cima) {    // SEM PAREDE À CIMA
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;     // Y É O MESMO

                            currentDirection = Direction.UP;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);

                            ++y;     // Y É O MESMO

                            currentDirection = Direction.DOWN;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;     // Y É O MESMO

                            currentDirection = Direction.LEFT;
                        }
                    }
                }
            }
            case LEFT -> {
                nextPosition = data.getMaze().get(y, x - 1);

                if (!isWall(nextPosition)) {
                    lastPosition = nextPosition;

                    --x;     // X É O MESMO

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

                            ++y;     // Y É O MESMO

                            currentDirection = Direction.DOWN;
                        } else if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;     // Y É O MESMO

                            currentDirection = Direction.UP;
                        } else if (!direita) {    // SEM PAREDE À DIREITA
                            lastPosition = data.getMaze().get(y, x + 1);

                            ++x;     // Y É O MESMO

                            currentDirection = Direction.RIGHT;
                        }
                    } else if (randomNumber == 1) {     // ESQUERDA
                        if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;     // Y É O MESMO

                            currentDirection = Direction.UP;
                        } else if (!baixo) {
                            lastPosition = data.getMaze().get(y + 1, x);

                            ++y;     // Y É O MESMO

                            currentDirection = Direction.DOWN;
                        }
                        // ULTIMO CASO VOLTA PARA TRAS
                        else if (!direita) {
                            lastPosition = data.getMaze().get(y, x + 1);

                            ++x;     // Y É O MESMO

                            currentDirection = Direction.RIGHT;
                        }
                    }
                }
            }
            case DOWN -> {
                nextPosition = data.getMaze().get(y + 1, x);

                if (!isWall(nextPosition)) {
                    lastPosition = nextPosition;

                    ++y;     // X É O MESMO

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

                            ++x;     // Y É O MESMO

                            currentDirection = Direction.RIGHT;
                        } else if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;     // Y É O MESMO

                            currentDirection = Direction.LEFT;
                        } else if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;     // Y É O MESMO

                            currentDirection = Direction.UP;
                        }
                    } else if (randomNumber == 1) {     // ESQUERDA
                        if (!esquerda) {
                            lastPosition = data.getMaze().get(y, x - 1);

                            --x;     // Y É O MESMO

                            currentDirection = Direction.LEFT;
                        } else if (!direita) {
                            lastPosition = data.getMaze().get(y, x + 1);

                            ++x;     // Y É O MESMO

                            currentDirection = Direction.RIGHT;
                        } else if (!cima) {
                            lastPosition = data.getMaze().get(y - 1, x);

                            --y;     // X É O MESMO

                            currentDirection = Direction.UP;
                        }
                    }
                }
            }
        }

        if (!(nextPosition instanceof PACMAN)) {
            data.getMaze().set(y, x, new Clyde(data));
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
        data.getMaze().set(y, x, new Clyde(data));
        directionsHistory.remove(directionsHistory.size() - 1);

    }


    private void checkPacman_onVision() {
        boolean wallBetween = false;

        if (x == data.getPacmanX()) {  // MESMA COLUNA
            if (y > data.getPacmanY()) {   // CLYDE EM BAIXO DO PACMAN
                for (int i = data.getPacmanY(); i <= y; i++) {       // PERCORRER A DISTANCIA ENTRE DOIS E VER SE TEM PAREDE
                    if (data.getMaze().get(i, x) instanceof Wall) {
                        wallBetween = true;
                        break;
                    }
                }
                if (!wallBetween)
                    currentDirection = Direction.UP;
            } else {  // CLYDE EM CIMA DO PACMAN
                for (int i = y; i <= data.getPacmanY(); i++) {       // PERCORRER A DISTANCIA ENTRE DOIS E VER SE TEM PAREDE
                    if (data.getMaze().get(i, x) instanceof Wall) {
                        wallBetween = true;
                        break;
                    }
                }
                if (!wallBetween)
                    currentDirection = Direction.DOWN;
            }
        } else if (y == data.getPacmanY()) {    // MESMA LINHA
            if (x > data.getPacmanX()) {   // CLYDE À DIREITA DO PACMAN
                for (int j = data.getPacmanX(); j <= x; j++) {       // PERCORRER A DISTANCIA ENTRE DOIS E VER SE TEM PAREDE
                    if (data.getMaze().get(y, j) instanceof Wall) {
                        wallBetween = true;
                        break;
                    }
                }
                if (!wallBetween)
                    currentDirection = Direction.LEFT;
            } else {   // CLYDE À ESQUERDA DO PACMAN
                for (int j = x; j <= data.getPacmanX(); j++) {       // PERCORRER A DISTANCIA ENTRE DOIS E VER SE TEM PAREDE
                    if (data.getMaze().get(y, j) instanceof Wall) {
                        wallBetween = true;
                        break;
                    }
                }
                if (!wallBetween)
                    currentDirection = Direction.RIGHT;
            }
        }
    }

    private boolean isWall(IMazeElements element) {
        return (element instanceof Wall);     // true se for da class Wall
    }

    public void setClydeFirstMove(boolean b) {
        clydeFirstMove = b;
    }

    @Override
    public char getSymbol() {
        return 'C';
    }
}
