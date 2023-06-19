package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class represents the data model for the Tiny Pacman game.
 *
 * @version 1.0.0
 * @author João Santos
 */
public class TinyPacData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int currentGameSeconds;

    private int currentLevel;
    private int currentPoints;
    private int lives;
    private final int MAX_LEVELS = 20;

    private int mazePointsLeft;

    private int vulnerabilitySeconds;

    private int mazeWidth;
    private int mazeHeight;

    private Maze maze;

    boolean alreadySavedOneWrap = false;

    private int ghostSpawnX;
    private int ghostSpawnY;
    private int pacmanSpawnX;
    private int pacmanSpawnY;

    private int foodSpawnX;
    private int foodSpawnY;

    private int ONE_wrapSpawnX;
    private int ONE_wrapSpawnY;
    private int TWO_wrapSpawnX;
    private int TWO_wrapSpawnY;

    public int getGhostSpawnX() {
        return ghostSpawnX;
    }
    public void setGhostSpawnX(int x){ ghostSpawnX = x; }

    public int getGhostSpawnY() {
        return ghostSpawnY;
    }
    public void setGhostSpawnY(int y){ ghostSpawnY = y; }

    public int getPacmanSpawnX() {
        return pacmanSpawnX;
    }
    public void setPacmanSpawnX(int x){ pacmanSpawnX = x; }

    public int getPacmanSpawnY() {
        return pacmanSpawnY;
    }
    public void setPacmanSpawnY(int y){ pacmanSpawnY = y; }

    public int getFoodSpawnX() {
        return foodSpawnX;
    }
    public void setFoodSpawnX(int foodSpawnX) {
        this.foodSpawnX = foodSpawnX;
    }

    public int getFoodSpawnY() {
        return foodSpawnY;
    }
    public void setFoodSpawnY(int foodSpawnY) {
        this.foodSpawnY = foodSpawnY;
    }


    public int getONE_wrapSpawnX() {
        return ONE_wrapSpawnX;
    }
    public void setONE_wrapSpawnX(int ONE_wrapSpawnX) {
        this.ONE_wrapSpawnX = ONE_wrapSpawnX;
    }

    public int getONE_wrapSpawnY() {
        return ONE_wrapSpawnY;
    }
    public void setONE_wrapSpawnY(int ONE_wrapSpawnY) {
        this.ONE_wrapSpawnY = ONE_wrapSpawnY;
    }

    public int getTWO_wrapSpawnX() {
        return TWO_wrapSpawnX;
    }
    public void setTWO_wrapSpawnX(int TWO_wrapSpawnX) {
        this.TWO_wrapSpawnX = TWO_wrapSpawnX;
    }

    public int getTWO_wrapSpawnY() {
        return TWO_wrapSpawnY;
    }
    public void setTWO_wrapSpawnY(int TWO_wrapSpawnY) {
        this.TWO_wrapSpawnY = TWO_wrapSpawnY;
    }

    private List<Ranking> rankings;


    PACMAN pacman;
    Blinky blinky;
    Clyde clyde;

    /**
     * Initializes a new instance of the TinyPacData class.
     */
    public TinyPacData(){
        currentLevel = 1;
        currentPoints = 0;
        mazePointsLeft = 0;
        currentGameSeconds = 0;
        vulnerabilitySeconds = 0;
        lives = 3;

        pacman = new PACMAN(this);
        blinky = new Blinky(this);
        clyde = new Clyde(this);

        rankings = new ArrayList<>();
    }

    //TODO          PACMAN
    /**
     * Sets the direction of the PACMAN.
     *
     * @param newDirection The new direction of the PACMAN.
     */
    public void setPacmanDirection(Direction newDirection){
        pacman.setCurrentDirection(newDirection);
    }
    /**
     * Gets the direction of the PACMAN.
     *
     * @return The direction of the PACMAN.
     */
    public Direction getPacmanDirection(){
        return pacman.getCurrentDirection();
    }
    /**
     * Moves the PACMAN in the given direction.
     *
     * @param currentDirection The current direction to move the PACMAN.
     */
    public void movePacman(Direction currentDirection){
        pacman.move(currentDirection);
    }
    /**
     * Checks if the PACMAN is on an OP point.
     *
     * @return True if the PACMAN is on an OP point, false otherwise.
     */
    public boolean pacmanCheckOpPoint(){
        return pacman.checkOpPoint();
    }
    /**
     * Gets the X coordinate of the PACMAN.
     *
     * @return The X coordinate of the PACMAN.
     */
    public int getPacmanX(){
        return pacman.getX();
    }
    /**
     * Gets the Y coordinate of the PACMAN.
     *
     * @return The Y coordinate of the PACMAN.
     */
    public int getPacmanY(){
        return pacman.getY();
    }
    /**
     * Resets the PACMAN to its initial state.
     */
    public void resetPacman(){
        pacman.reset();
    }
    /**
     * Increments the number of ghosts eaten by the PACMAN.
     */
    public void pacmanIncGhostsEaten(){
        pacman.addGhostsEaten();
    }
    /**
     * Gets the number of ghosts eaten by the PACMAN.
     *
     * @return The number of ghosts eaten by the PACMAN.
     */
    public int pacmanGetGhostsEaten(){
        return pacman.getGhostsEaten();
    }
    /**
     * Gets the number of ghosts eaten by the PACMAN.
     */
    public void pacmanResetGhostsEaten(){
        pacman.resetGhostsEaten();
    }


    //TODO         BLINKY
    /**
     * Returns whether Blinky is currently vulnerable.
     *
     * @return true if Blinky is vulnerable, false otherwise.
     */
    public boolean isBlinkyVulnerable(){
        return blinky.getIsVulnerable();
    }
    /**
     * Sets the vulnerability status of Blinky.
     *
     * @param b true to make Blinky vulnerable, false otherwise.
     */
    public void setBlinkyVulnerable(boolean b){
        blinky.setIsVulnerable(b);
    }
    /**
     * Sets the first move status of Blinky.
     *
     * @param b true to set Blinky's first move, false otherwise.
     */
    public void setBlinkyFirstMove(boolean b){
        blinky.setBlinkyFirstMove(b);
    }
    /**
     * Moves Blinky.
     */
    public void moveBlinky(){
        blinky.move();
    }
    /**
     * Returns the number of seconds Blinky has moved.
     *
     * @return the number of seconds Blinky has moved.
     */
    public int getSecondsBlinkyMoved(){
        return blinky.getSecondsBlinkyMoved();
    }
    /**
     * Returns the x-coordinate of Blinky.
     *
     * @return the x-coordinate of Blinky.
     */
    public int getBlinkyX(){
        return blinky.getX();
    }
    /**
     * Returns the y-coordinate of Blinky.
     *
     * @return the y-coordinate of Blinky.
     */
    public int getBlinkyY(){
        return blinky.getY();
    }
    /**
     * Resets Blinky's position and state.
     */
    public void resetBlinky(){
        blinky.reset();
    }
    /**
     * Moves Blinky backwards.
     */
    public void moveBlinkyBackwards(){
        blinky.moveBackwards();
    }
    /**
     * Sets the x-coordinate of Blinky.
     *
     * @param x the new x-coordinate for Blinky.
     */
    public void setBlinkyX(int x){
        blinky.setX(x);
    }
    /**
     * Sets the y-coordinate of Blinky.
     *
     * @param y the new y-coordinate for Blinky.
     */
    public void setBlinkyY(int y){
        blinky.setY(y);
    }
    /**
     * Respawns Blinky at the designated spawn location.
     */
    public void respawnBlinky(){
        maze.set(getGhostSpawnY() + 1, getGhostSpawnX(), blinky);
        setBlinkyX(getGhostSpawnX());
        setBlinkyY(getGhostSpawnY() + 1);   // POSIÇAO ANTES DA SAIDA
    }
    /**
     * Resets the direction history of Blinky.
     */
    public void resetBlinkyDirectionHistory(){
        blinky.resetDirectionHistory();
    }


    //TODO       CLYDE
    /**
     * Returns the x-coordinate of Clyde.
     *
     * @return the x-coordinate of Clyde.
     */
    public int getClydeX(){
        return clyde.getX();
    }
    /**
     * Returns the y-coordinate of Clyde.
     *
     * @return the y-coordinate of Clyde.
     */
    public int getClydeY(){
        return clyde.getY();
    }
    /**
     * Sets the x-coordinate of Clyde.
     *
     * @param x the new x-coordinate for Clyde.
     */
    public void setClydeX(int x){
        clyde.setX(x);
    }
    /**
     * Sets the y-coordinate of Clyde.
     *
     * @param y the new y-coordinate for Clyde.
     */
    public void setClydeY(int y){
        clyde.setY(y);
    }
    /**
     * Sets the first move status of Clyde.
     *
     * @param b true to set Clyde's first move, false otherwise.
     */
    public void setClydeFirstMove(boolean b){
        clyde.setClydeFirstMove(b);
    }
    /**
     * Returns whether Clyde is currently vulnerable.
     *
     * @return true if Clyde is vulnerable, false otherwise.
     */
    public boolean isClydeVulnerable(){
        return clyde.getIsVulnerable();
    }
    /**
     * Sets the vulnerability status of Clyde.
     *
     * @param b true to make Clyde vulnerable, false otherwise.
     */
    public void setClydeVulnerable(boolean b){
        clyde.setIsVulnerable(b);
    }
    /**
     * Moves Clyde.
     */
    public void moveClyde(){
        clyde.move();
    }
    /**
     * Returns whether Clyde is making the first move.
     *
     * @return true if Clyde is making the first move, false otherwise.
     */
    public boolean getClydeFirstMove(){
        return clyde.getFirstMove();
    }
    /**
     * Moves Clyde backwards.
     */
    public void moveClydeBackwards(){
        clyde.moveBackwards();
    }
    /**
     * Returns the number of seconds Clyde has moved.
     *
     * @return the number of seconds Clyde has moved.
     */
    public int getSecondsClydeMoved(){
        return clyde.getSecondsClydeMoved();
    }
    /**
     * Respawns Clyde at the designated spawn location.
     */
    public void respawnClyde(){
        maze.set(getGhostSpawnY() + 1, getGhostSpawnX(), new Clyde(this));
        setClydeX(getGhostSpawnX());
        setClydeY(getGhostSpawnY() + 1);   // POSIÇAO ANTES DA SAIDA
    }
    /**
     * Resets the direction history of Clyde.
     */
    public void resetClydeDirectionHistory(){
        clyde.resetDirectionHistory();
    }
    /**
     * Resets Clyde's position and state.
     */
    public void resetClyde(){
        clyde.reset();
    }


    /**
     * Returns the maze object.
     *
     * @return the maze object.
     */
    public Maze getMaze(){
        return maze;
    }

    /**
     * Initializes the game by loading the maze and setting up initial positions.
     */
    public void initGame(){
        maze = null;

        int lastLevel = getCurrentLevel() - 1;

        String filename = "levels/Level" + getCurrentLevel() + ".txt";
        maze = CreateMaze(filename);

        while(maze == null){
            System.out.printf("\nNão existe um labirinto para o nivel %d! O labirinto do último nivel definido será carregado...\n", getCurrentLevel());
            filename = "levels/Level" + lastLevel + ".txt"; //-teste para usar o outro file + simples
            maze = CreateMaze(filename);
            lastLevel--;
        };

        fillMaze(filename, maze);

        maze.set(getPacmanSpawnY(), getPacmanSpawnX(), pacman);    // POSIÇÃO INICIAL DO PACMAN É A MESMA DO SPAWN
        pacman.setX(getPacmanSpawnX());
        pacman.setY(getPacmanSpawnY());

        maze.set(getGhostSpawnY()+1, getGhostSpawnX(), blinky);
        blinky.setX(getGhostSpawnX());
        blinky.setY(getGhostSpawnY()+1);   // POSIÇAO ANTES DA SAIDA
    }

    /**
     * Creates a new maze based on the provided file (levelx.txt).
     *
     * @param filename the path to the maze file.
     * @return the created maze, or null if there was an error reading the file.
     */
    private Maze CreateMaze(String filename) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (mazeWidth == 0) {
                    mazeWidth = line.length();
                }
                mazeHeight++;
            }
        } catch (IOException e) {
            return null;
        }

        maze = new Maze(mazeHeight, mazeWidth);

        return maze;
    }
    /**
     * Fills the maze with elements based on the provided file.
     *
     * @param filename the path to the maze file.
     * @param maze     the maze object to fill.
     */
    private void fillMaze(String filename, Maze maze) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linha;
            int y = 0;
            while ((linha = br.readLine()) != null) {
                for (int x = 0; x < linha.length(); x++) {
                    char symbol = linha.charAt(x);

                    IMazeElements element = null;

                    switch (symbol) {
                        case ' ' -> element = new freeSpace();
                        case 'x' -> element = new Wall();
                        case 'W' -> {
                            element = new Warp();
                            if(!alreadySavedOneWrap){
                                setONE_wrapSpawnX(x);  // SIM TROCADO PORQUE AQUI O X É A LINHA E NO JOGO É COLUNA
                                setONE_wrapSpawnY(y);
                                alreadySavedOneWrap = true;
                            }
                            else{
                                setTWO_wrapSpawnX(x);
                                setTWO_wrapSpawnY(y);
                            }
                        }
                        case 'o' -> {
                            element = new Point();
                            ++mazePointsLeft;
                        }
                        case 'O' -> {
                            element = new opPoint();
                            ++mazePointsLeft;
                        }
                        case 'M' -> {
                            element = new pacmanSpawn();
                            setPacmanSpawnX(x);
                            setPacmanSpawnY(y);
                        }
                        case 'F' -> {
                            element = new freeSpace();
                            setFoodSpawnX(x);
                            setFoodSpawnY(y);
                        }
                        case 'Y' -> {
                            element = new ghostSpawn();
                            setGhostSpawnX(x);
                            setGhostSpawnY(y);
                        }
                        case 'y' -> element = new ghostHome();
                        default -> System.err.println("Invalid symbol: " + symbol);
                    }
                    maze.set(y, x, element);
                }
                y++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Returns the current game time in seconds.
     *
     * @return the current game time in seconds.
     */
    public int getCurrentGameSeconds(){
        return currentGameSeconds;
    }
    /**
     * Increases the current game time by one second.
     */
    public void addCurrentGameSeconds(){
        ++currentGameSeconds;
    }

    /**
     * Returns the current level.
     *
     * @return the current level.
     */
    public int getCurrentLevel(){
        return currentLevel;
    }
    /**
     * Sets the current level.
     *
     * @param level the new level.
     */
    public void setCurrentLevel(int level){
        currentLevel = level;
    }
    /**
     * Returns the current point count.
     *
     * @return the current point count.
     */
    public int getCurrentPoints() {
        return currentPoints;
    }
    /**
     * Adds points to the current point count.
     *
     * @param points the points to add.
     */
    public void addPoints(int points) {
        currentPoints += points;
    }

    /**
     * Decrements the count of remaining maze points.
     */
    public void decMazePoints(){mazePointsLeft--;}
    /**
     * Returns the count of remaining maze points.
     *
     * @return the count of remaining maze points.
     */
    public int getMazePointsLeft() {
        return mazePointsLeft;
    }

    /**
     * Returns the vulnerability time in seconds.
     *
     * @return the vulnerability time in seconds.
     */
    public int getVulnerabilitySeconds() {
        return vulnerabilitySeconds;
    }
    /**
     * Decrements the vulnerability time by one second, if it is greater than zero.
     */
    public void decrementVulnerabilitySeconds(){
        if(vulnerabilitySeconds > 0)
            --vulnerabilitySeconds;
    }
    /**
     * Adds vulnerability time. Adds 15 seconds to the vulnerability time.
     * This is used when consuming OP point to extend the vulnerability time.
     */
    public void addVulnerabilityTime(){
        vulnerabilitySeconds += 15;      // 5 segundos de vulnerabilidade iniciais e se
                                        // comer ponto OP while ghosts vulneraveis acrescenta +5 aos que ja estavam
    }
    /**
     * Returns the number of lives remaining.
     *
     * @return the number of lives remaining.
     */
    public int getLives(){
        return lives;
    }
    /**
     * Decreases the number of lives by one.
     */
    public void loseLive(){
        lives--;
    }

    /**
     * Resets the maze by repositioning the Pacman and ghosts to their initial positions.
     * Clears the spaces occupied by Pacman and ghosts and sets them as free spaces.
     */
    public void resetMaze() {

        if(!(maze.get(blinky.getY(), blinky.getX()) instanceof Warp || maze.get(clyde.getY(), clyde.getX()) instanceof pacmanSpawn)){
            maze.set(blinky.getY(), blinky.getX(), new freeSpace());
        }
        if(!(maze.get(clyde.getY(), clyde.getX()) instanceof Warp || maze.get(clyde.getY(), clyde.getX()) instanceof pacmanSpawn)){
            maze.set(clyde.getY(), clyde.getX(), new freeSpace());
        }

        //maze.set(PACMAN.getY(), PACMAN.getX(), new freeSpace());

        maze.set(getPacmanSpawnY(), getPacmanSpawnX(), pacman);    // POSIÇÃO INICIAL DO PACMAN É A MESMA DO SPAWN
        pacman.setX(getPacmanSpawnX());
        pacman.setY(getPacmanSpawnY());

        maze.set(getGhostSpawnY()+1, getGhostSpawnX(), blinky);
        blinky.setX(getGhostSpawnX());
        blinky.setY(getGhostSpawnY()+1);   // POSIÇAO ANTES DA SAIDA
    }

    /**
     * Checks if the current player's score qualifies for the top 5 rankings.
     *
     * @return true if the current score is among the top 5, false otherwise.
     */
    public boolean checkTop5update() {
        List<Ranking> top5Pontuacoes = readTop5();

        return top5Pontuacoes.size() < 5 || currentPoints > Collections.min(top5Pontuacoes, Comparator.comparing(Ranking::getPoints)).getPoints();
    }

    /**
     * Updates the top 5 rankings with the current player's score.
     *
     * @param nomeJogador the name of the player.
     */
    public void updateTop5(String nomeJogador) {
        List<Ranking> top5Pontuacoes = readTop5();

        // Cria o objeto Pontuacao com o nome e pontuação do jogador
        Ranking newRanking = new Ranking(nomeJogador, currentPoints, currentGameSeconds);

        // Adiciona a nova pontuação ao top5
        top5Pontuacoes.add(newRanking);
        top5Pontuacoes.sort(Comparator.comparing(Ranking::getPoints).reversed());

        // Remove o registro de pontuação mais baixa (se houver mais de 5 registros)
        if (top5Pontuacoes.size() > 5) {
            top5Pontuacoes.remove(5);
        }


        // Salva as pontuações atualizadas no arquivo
        saveTop5(top5Pontuacoes);

    }

    /**
     * Reads the top 5 rankings from the file.
     *
     * @return a list of the top 5 rankings.
     */
    public List<Ranking> readTop5() {
        List<Ranking> pontuacoes = new ArrayList<>();

        File arquivo = new File("top5.ser");
        if(!arquivo.exists()){
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            pontuacoes = (List<Ranking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o arquivo top5.ser: " + e.getMessage());
        }

        return pontuacoes;
    }

    /**
     * Saves the top 5 rankings to the file.
     *
     * @param pontuacoes the list of top 5 rankings to save.
     */
    public void saveTop5(List<Ranking> pontuacoes) {
        File arquivo = new File("top5.ser");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(pontuacoes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar as pontuações no arquivo top5.ser: " + e.getMessage());
        }
    }
}