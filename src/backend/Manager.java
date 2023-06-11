package backend;

public class Manager {
    static private final Manager singleton = new Manager();
    public final SuperMario superMario;

    private Manager() {
        superMario = new SuperMario();
    }

    public static Manager getInstance() {
        return singleton;
    }

    public User CurrentUser() {
        return superMario.currentUser;
    }    final public int W = 1536, column = 24, w = W / column;

    public Game CurrentGame() {
        return CurrentUser().game[CurrentUser().currentGameIndex];
    }    final public int H = 864, row = 16, h = H / row;

    public Game.Level.Section CurrentSection() {
        return CurrentGame().levels[CurrentGame().levelNumber].sections[CurrentGame().sectionNumber];
    }




}
