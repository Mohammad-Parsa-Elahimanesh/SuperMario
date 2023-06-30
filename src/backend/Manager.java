package backend;

import backend.block.mario.Mario;
import backend.gamePlay.Game;
import backend.gamePlay.Section;

public class Manager {
    private static final Manager singleton = new Manager();
    public SuperMario superMario;

    private Manager() {
    }

    public static void start() {
        singleton.superMario = new SuperMario();
    }

    public static Manager getInstance() {
        return singleton;
    }

    public User currentUser() {
        return superMario.currentUser;
    }

    public Game currentGame() {
        return currentUser().game[currentUser().currentGameIndex];
    }

    public Section currentSection() {
        return currentGame().levels[currentGame().levelNumber].sections[currentGame().sectionNumber];
    }

    public Mario currentMario() {
        return currentSection().mario;
    }

    public static final int SCREEN_WIDTH = 1536;
    public static final int COLUMN = 24;
    public static final int SINGLE_BLOCK_WIDTH = SCREEN_WIDTH / COLUMN;


    public static final int SCREEN_HEIGHT = 864;
    public static final int ROW = 16;
    public static final int SINGLE_BLOCK_HEIGHT = SCREEN_HEIGHT / ROW;


}
