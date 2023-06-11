public class Manager {
    static private final Manager singleton = new Manager();
    final SuperMario superMario;
    private Manager() { superMario = new SuperMario(); }
    static Manager getInstance() { return singleton;}

    final int W = 1536, column = 24, w = W / column;

    final int H = 864, row = 16, h = H / row;

    User CurrentUser() {
        return superMario.currentUser;
    }
    Game CurrentGame() {
        return CurrentUser().game[CurrentUser().currentGameIndex];
    }
    Game.Level.Section CurrentSection() {
        return CurrentGame().levels[CurrentGame().levelNumber].sections[CurrentGame().sectionNumber];
    }
}
