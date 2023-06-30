package backend.gamePlay;

import backend.Manager;
import backend.block.mario.Mario;
import frontend.menu.MainMenu;
import frontend.menu.game.GameFrame;
import frontend.menu.game.SetGameSettings;


public class Game {
    public static final double delay = 0.03;
    public int score = 0;
    public GameFrame gameFrame = new GameFrame();
    Manager manager = Manager.getInstance();
    Difficulty difficulty;
    public Section currentSection;

    public Game() {
        new SetGameSettings();
    }

    public static String state(Game game) {
        if (game == null)
            return "New Game";
        else
            return game.state();
    }

    public void constructor(Mario mario, Difficulty difficulty) throws Exception {
        this.difficulty = difficulty;
        currentSection = Section.makeSections(mario);
        gameFrame.setVisible(true);
        currentSection.Start();
    }

    void endGame() {
        if (manager.currentUser().maxRating < score)
            manager.currentUser().maxRating = score;
        manager.currentUser().game[manager.currentUser().currentGameIndex] = null;
        gameFrame.dispose();
        new MainMenu();
    }

    String state() {
        return  "Section: " + currentSection;
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

}
