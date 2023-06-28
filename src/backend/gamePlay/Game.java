package backend.gamePlay;

import backend.Manager;
import backend.block.Block;
import backend.block.mario.Mario;
import backend.block.mario.MarioState;
import frontend.menu.MainMenu;
import frontend.menu.game.GameFrame;
import frontend.menu.game.SetGameSettings;

import javax.swing.*;

public class Game {
    public static final double delay = 0.03;
    public Level[] levels;
    public Mario mario;
    public int levelNumber;
    public int sectionNumber;
    public int score = 0;
    public int coins = 0;
    public boolean nextASAP;
    transient Manager manager = Manager.getInstance();
    transient GameFrame gameFrame = new GameFrame();
    Difficulty difficulty;

    public Game() {
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
        mario.heart = 3;
        Start();
    }

    public void setMario(Mario mario) {
        try {
            this.mario = mario.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    void Start() {
        mario.reset();
        timer.start();
    }

    void NextSection() {
        EndSection(true);
        sectionNumber++;
        if (levels[levelNumber].sections.length == sectionNumber) {
            levelNumber++;
            sectionNumber = 0;
        }
        if (levels.length == levelNumber)
            EndGame();
        else
            timer.start();
    }

    void EndGame() {
        if (manager.CurrentUser().maxRating < score)
            manager.CurrentUser().maxRating = score;
        manager.CurrentUser().game[manager.CurrentUser().currentGameIndex] = null;
        gameFrame.setVisible(false);
        new MainMenu();
    }

    void EndSection(boolean goNextLevel) {
        timer.stop();
        if(goNextLevel)
            score += (manager.CurrentSection().wholeTime - manager.CurrentSection().spentTime) * mario.getPowerLevel();
        if(goNextLevel)
            score += mario.heart * 20 * mario.getPowerLevel();
        mario.reset();
        manager.CurrentUser().coin += coins;
        coins = 0;
        manager.CurrentSection().spentTime = 0;
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
        if (mario.Y < 0)
            mario.state = MarioState.mini;
        switch (mario.state) {
            case mini -> {
                EndSection(false);
                mario.heart--;
                if (mario.heart <= 0)
                    EndGame();
                else
                    timer.start();
                return;
            }
            case mega -> mario.state = MarioState.mini;
            case giga -> mario.state = MarioState.mega;
        }
        mario.BeAlive();
    }

    String State() {
        return "Level: " + (levelNumber + 1) + " Section: " + (sectionNumber + 1);
    }

    public enum Difficulty {
        Easy,
        Medium,
        Hard
    }

    public transient Timer timer = new Timer((int)(delay*1000), e -> {
        manager.CurrentSection().UpdateBlocks();
        for (Block block : manager.CurrentSection().blocks)
            block.Update();
        manager.CurrentSection().spentTime += delay;
        gameFrame.repaint();
        if (mario.mustBeDied())
            Die();
        else if (nextASAP) {
            nextASAP = false;
            NextSection();
        }
    });


}
