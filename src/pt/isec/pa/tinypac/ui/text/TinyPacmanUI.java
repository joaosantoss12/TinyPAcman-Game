/*package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacmanState;
import pt.isec.pa.tinypac.model.data.TinyPacData;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.IOException;

public class TinyPacmanUI {
    TinyPacData data;
    TinyPacmanContext fsm;

    Terminal terminal;
    Screen screen;

    private int x;
    private int y;

    private boolean gameOver;
    private boolean quit;

    public TinyPacmanUI() {
        quit = false;
    }

    public void start() throws IOException {
        terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100,40)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();

        char c;

        do{
            screen.clear();
            screen.refresh();

            showMenu();

            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Character) {
                c = key.getCharacter();
                switch(c){
                    case '1' -> {
                        data = new TinyPacData();
                        //fsm = new TinyPacmanContext(data);

                        fsm.initGame();
                        startGame();
                    }
                    case '2' -> {
                        top5();
                    }
                    case '3' -> {
                        fsm = new TinyPacmanContext();

                        quitGame();
                        do {
                            key = screen.readInput();

                            if (key.getKeyType() == KeyType.Character) {
                                if (key.getCharacter() == 'y' || key.getCharacter() == 'Y') {
                                    quit = true;
                                    break;
                                }
                            }
                        } while (key.getCharacter() != 'n' && key.getCharacter() != 'N');
                    }
                }
            }
        }while(!quit);

        close_and_exit();
    }

    public void startGame() throws IOException {
        GameEngine gameEngine = new GameEngine();

        showMaze();

        gameEngine.registerClient((g, t) -> {
            switch (fsm.getCurrentState()){
                case INITIAL_STATE -> {
                    fsm.movePacman(fsm.getCurrentDirectionPacman());
                }
                case MOVING_PACMAN -> {
                    fsm.movePacman(fsm.getCurrentDirectionPacman());
                    if(fsm.getCurrentGameSeconds() >= 5)
                        fsm.moveGhosts();

                    fsm.addCurrentGameSeconds();
                }
                case MOVING_GHOSTS -> {
                    fsm.movePacman(fsm.getCurrentDirectionPacman());
                    fsm.checkWin();

                    fsm.checkPacmanOpPoint();

                    fsm.moveGhosts();
                    fsm.checkGhostCatchPacman();

                    fsm.addCurrentGameSeconds();
                }
                case VULNERABLE_GHOSTS -> {
                    fsm.movePacman(fsm.getCurrentDirectionPacman());
                    fsm.checkWin();

                    fsm.checkPacmanOpPoint();

                    fsm.checkVulnerabilityEnd();
                    fsm.moveGhosts();

                    fsm.addCurrentGameSeconds();
                    fsm.decrementVulnerabilitySeconds();
                }
                case PACMAN_WINS -> {
                    //fsm.nextLevel();
                    gameEngine.stop();
                    try {
                        victory();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case PACMAN_LOSES -> {
                    //gameEngine.stop();
                    try {
                        defeat();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                showMaze();
            } catch (IOException e) {
                e.printStackTrace();
            }



        });

        gameEngine.start(1000);

        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    KeyStroke key = null;
                    try {
                        key = screen.readInput();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(key.getKeyType() == KeyType.Character) {
                        if (key.getCharacter() == 'q') {
                            gameEngine.pause();
                            fsm.quitGame();

                            try {
                                quitGame();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            KeyStroke key2 = null;
                            do {
                                try {
                                    key2 = screen.readInput();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (key2.getKeyType() == KeyType.Character) {
                                    if (key2.getCharacter() == 'y' || key2.getCharacter() == 'Y') {
                                        try {
                                            close_and_exit();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } while (key2.getCharacter() != 'n' && key2.getCharacter() != 'N');
                            fsm.resumeGame();
                            gameEngine.resume();
                        }
                        else if (key.getCharacter() == 'p') {
                            gameEngine.pause();
                            fsm.pauseGame();
                            try {
                                pauseGame();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            KeyStroke key2 = null;
                            do {

                                try {
                                    key2 = screen.readInput();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } while (key2.getCharacter() != 'p' && key2.getCharacter() != 'P');

                            fsm.resumeGame();
                            gameEngine.resume();
                        }
                    }
                    else {
                        if (key.getKeyType() == KeyType.ArrowUp) {
                            fsm.changePacmanDirection(Direction.UP);
                        }
                        else if (key.getKeyType() == KeyType.ArrowDown) {
                            fsm.changePacmanDirection(Direction.DOWN);
                        }
                        else if (key.getKeyType() == KeyType.ArrowRight) {
                            fsm.changePacmanDirection(Direction.RIGHT);
                        }
                        else if (key.getKeyType() == KeyType.ArrowLeft) {
                            fsm.changePacmanDirection(Direction.LEFT);
                        }
                    }
                }while(true);
            }
        });
        inputThread.start();

        while(true){

        }

        // END GAME
        //endGame();
    }

    private void victory() throws IOException{
        screen.clear();
        screen.refresh();

        int col = terminal.getTerminalSize().getColumns() / 2 - 9;
        int row = terminal.getTerminalSize().getRows() / 2 - 2;

        terminal.setCursorPosition(col, row - 1);
        terminal.putString("╔════════════════╗");
        terminal.setCursorPosition(col, row);
        terminal.putString("║     VITÓRIA    ║");
        terminal.setCursorPosition(col, row + 1);
        terminal.putString("╚════════════════╝");

        terminal.setCursorPosition(col, row + 3);
        terminal.putString("      NÍVEL " + fsm.getCurrentLevel() + "      ");
        terminal.setCursorPosition(col, row + 4);
        terminal.putString("══════════════════");
        terminal.setCursorPosition(col, row + 6);
        terminal.putString("    PONTUAÇÃO " + fsm.getCurrentPoints() + "    ");
        terminal.setCursorPosition(col, row + 7);
        terminal.putString("══════════════════");
        terminal.setCursorPosition(0, 0);

        terminal.flush();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void defeat() throws IOException{
        screen.clear();
        screen.refresh();

        int col = terminal.getTerminalSize().getColumns() / 2 - 9;
        int row = terminal.getTerminalSize().getRows() / 2 - 2;

        terminal.setCursorPosition(col, row - 1);
        terminal.putString("╔════════════════╗");
        terminal.setCursorPosition(col, row);
        terminal.putString("║     DERROTA    ║");
        terminal.setCursorPosition(col, row + 1);
        terminal.putString("╚════════════════╝");
        terminal.setCursorPosition(col, row + 3);
        terminal.putString("    PONTUAÇÃO " + fsm.getCurrentPoints() + "    ");
        terminal.setCursorPosition(col, row + 4);
        terminal.putString("══════════════════");
        terminal.setCursorPosition(0, 0);

        terminal.flush();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void showMenu() throws IOException {
        String[] creditos = {
                "################################################",
                "#                                              #",
                "#               DEIS-ISEC-IPC                  #",
                "#          LEI-Programação Avançada            #",
                "#      2022/2023-João Santos-2020136093        #",
                "#             Trabalho Académico               #",
                "#                                              #",
                "################################################"
        };

        x = (terminal.getTerminalSize().getColumns() / 2) - creditos[0].length()/2;
        y = (terminal.getTerminalSize().getRows() / 2) - 5;

        for (int i = 0; i < creditos.length; i++) {
            terminal.setCursorPosition(x,y-10+i);
            terminal.putString(creditos[i]);
        }

        String title = "| Bem-vindo/a ao TinyPACMAN |";

        terminal.setCursorPosition(x,y);
        terminal.putString(title);

        String[] options = {"1 - Começar Jogo", "2 - TOP 5", "3 - Sair"};
        for(int i=0;i< options.length;i++){
            terminal.setCursorPosition(x, y+3+i);
            terminal.putString(options[i]);
        }

        terminal.flush();
    }
    private void showMaze() throws IOException {
        terminal.clearScreen();

        char[][] char_maze = fsm.getMaze().getMaze();

        for (int i = 0; i < char_maze.length; i++) {
            for (int j = 0; j < char_maze[i].length; j++) {
                terminal.setCursorPosition(j*2,i);
                terminal.putCharacter(char_maze[i][j]);
            }
        }
        terminal.setCursorPosition(0, 32);
        terminal.putString("Nivel atual: " + fsm.getCurrentLevel());

        terminal.setCursorPosition(0, 33);
        terminal.putString("Pontuação: " + fsm.getCurrentPoints());

        terminal.setCursorPosition(0, 35);
        terminal.putString("Pressiona as setas para mover o PACMAN!");

        terminal.setCursorPosition(0, 36);
        terminal.putString("Pressiona 'p' para pausar ou 'q' para sair.");

        terminal.flush();
    }
    private void top5() throws IOException {
        terminal.setCursorPosition(x,y+10);
        terminal.putString("EM MANUTENÇÃO");

        terminal.flush();

        try {
            Thread.sleep(4000);     // AGUARDAR 4 SEGUNDOS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pauseGame() throws IOException {
        screen.clear();
        screen.refresh();

        String str = "PAUSA";
        terminal.setCursorPosition(
                (terminal.getTerminalSize().getColumns() / 2) - str.length()/2, (terminal.getTerminalSize().getRows() / 2) - 5
        );
        terminal.putString(str);

        str = "Pressiona 'p' para voltar ao jogo";
        terminal.setCursorPosition((terminal.getTerminalSize().getColumns() / 2) - str.length()/2,(terminal.getTerminalSize().getRows() / 2) - 2);
        terminal.putString(str);

        terminal.flush();
    }

    private void quitGame() throws IOException {
        screen.clear();
        screen.refresh();

        String str = "Tem a certeza que pretende sair? (y/n)";
        terminal.setCursorPosition(
                (terminal.getTerminalSize().getColumns() / 2) - str.length()/2, (terminal.getTerminalSize().getRows() / 2) - 5
        );
        terminal.putString(str);

        terminal.flush();
    }

    public void close_and_exit() throws IOException {
        terminal.close();
        screen.close();
        System.exit(-1);
    }

}*/