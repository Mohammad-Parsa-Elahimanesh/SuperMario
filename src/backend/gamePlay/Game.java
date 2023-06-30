package backend.gamePlay;

import backend.Manager;
import backend.block.mario.Mario;
import backend.block.mario.MarioState;
import frontend.menu.MainMenu;
import frontend.menu.game.AudioPlayer;
import frontend.menu.game.GameFrame;
import frontend.menu.game.SetGameSettings;

import javax.swing.*;

public class Game {
    public static final double delay = 0.03;
    public Level[] levels;
    public int levelNumber;
    public int sectionNumber;
    public int score = 0;
    transient public GameFrame gameFrame = new GameFrame();
    transient Manager manager = Manager.getInstance();
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

    public void constructor(Mario mario, Difficulty difficulty) {
        this.difficulty = difficulty;
        levelNumber = sectionNumber = 0;
        levels = new Level[]{new Level(0, mario)};
        gameFrame.setVisible(true);
        Start();
    }

    void Start() {
        timer.start();
    }

    void NextSection() {
        timer.stop();
        manager.currentSection().SectionReward();
        MarioState lastStateOfMario = manager.currentMario().state;
        sectionNumber++;
        if (levels[levelNumber].sections.length == sectionNumber) {
            levelNumber++;
            sectionNumber = 0;
        }
        if (levels.length == levelNumber)
            EndGame();
        else {
            manager.currentMario().state = lastStateOfMario;
            timer.start();
        }
    }

    void EndGame() {
        timer.stop();
        if (manager.currentUser().maxRating < score)
            manager.currentUser().maxRating = score;
        manager.currentUser().game[manager.currentUser().currentGameIndex] = null;
        gameFrame.dispose();
        new MainMenu();
    }

    public void Stop() {
        timer.stop();
        gameFrame.setVisible(false);
    }

    public void Resume() {
        gameFrame.setVisible(true);
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

    public transient Timer timer = new Timer((int) (delay * 1000), e -> {
        manager.currentSection().Update();
        gameFrame.repaint();
    }) {
        @Override
        public void start() {
            super.start();
            AudioPlayer.getInstance().setSilence(false);
        }

        @Override
        public void stop() {
            super.stop();
            AudioPlayer.getInstance().setSilence(true);
        }
    };


}
