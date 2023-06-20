package backend;

import frontend.GameFrame;
import frontend.MainMenu;
import frontend.SetGameSettings;

import javax.swing.*;

public class Game {
    final static int delayMS = 30;
    final static double delay = 0.0001 * delayMS;
    final transient Manager manager = Manager.getInstance();
    public Level[] levels;
    public Mario mario;
    public int levelNumber;
    public int sectionNumber;
    public int score = 0;
    public int coins = 0;
    transient GameFrame gameFrame = new GameFrame();
    Difficulty difficulty;
    boolean dieASAP, nextASAP;

    Game() {
        new SetGameSettings();
    }

    public static String State(Game game) {
        if (game == null)
            return "New Game";
        else
            return game.State();
    }

    public void constructor() {
        levelNumber = sectionNumber = 0;
        levels = new Level[]{new Level(0)};
        gameFrame.setVisible(true);
        mario.Set(0, 0, 3);
        Start();
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    void Start() {
        mario.reset();
        timer.start();
    }

    void NextSection() {
        EndSection();
        sectionNumber++;
        if (levels[levelNumber].sections.length == sectionNumber) {
            levelNumber++;
            sectionNumber = 0;
        }
        if (levels.length == levelNumber)
            EndGame();
        else
            timer.start();
    }    public transient Timer timer = new Timer(delayMS, e -> {
        manager.CurrentGame().dieASAP = manager.CurrentGame().nextASAP = false;
        manager.CurrentSection().UpdateBlocks();
        for (Block block : manager.CurrentSection().blocks)
            block.Update();
        manager.CurrentGame().mario.CheckGetCoins();
        manager.CurrentGame().mario.CheckGameState();
        manager.CurrentSection().spentTimeMS += delayMS;
        gameFrame.repaint();
        if (manager.CurrentGame().dieASAP)
            manager.CurrentGame().Die();
        else if (manager.CurrentGame().nextASAP)
            manager.CurrentGame().NextSection();
    });

    void EndGame() {
        if (manager.CurrentUser().maxRating < score)
            manager.CurrentUser().maxRating = score;
        manager.CurrentUser().game[manager.CurrentUser().currentGameIndex] = null;
        gameFrame.setVisible(false);
        new MainMenu();
    }

    void EndSection() {
        timer.stop();
        score += (manager.CurrentSection().wholeTime - manager.CurrentSection().spentTimeMS / 1000) * (mario.power + 1);
        score += mario.heart * 20 * (mario.power + 1);
        mario.reset();
        manager.CurrentUser().coin += coins;
        coins = 0;
        manager.CurrentSection().spentTimeMS = 0;
    }

    public void Stop() {
        timer.stop();
        gameFrame.setVisible(false);
    }

    public void Resume() {
        gameFrame.setVisible(true);
        timer.start();
    }

    void Die() {
        EndSection();
        mario.heart--;
        if (mario.heart == 0)
            EndGame();
        else
            timer.start();
    }

    String State() {
        return "Level: " + (levelNumber + 1) + " Section: " + (sectionNumber + 1);
    }

    public enum Difficulty {
        Easy,
        Medium,
        Hard
    }




}
