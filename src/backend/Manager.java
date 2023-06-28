package backend;

import backend.block.mario.Mario;
import backend.gamePlay.Game;
import backend.gamePlay.Section;

public class Manager {
    static private final Manager singleton = new Manager();
    public SuperMario superMario;

    private Manager() {
    }

    public static void Start() {
        singleton.superMario = new SuperMario();
    }

    public static Manager getInstance() {
        return singleton;
    }

    public User CurrentUser() {
        return superMario.currentUser;
    }

    public Game CurrentGame() {
        return CurrentUser().game[CurrentUser().currentGameIndex];
    }

    public Section CurrentSection() {
        return CurrentGame().levels[CurrentGame().levelNumber].sections[CurrentGame().sectionNumber];
    }

    public Mario CurrentMario() {
        return CurrentSection().getMario();
    }

    public final int W = 1536, column = 24, w = W / column;


    public final int H = 864, row = 16, h = H / row;


}
