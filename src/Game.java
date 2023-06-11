import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    final static int delayMS = 80;
    static GameFrame gameFrame = new GameFrame();
    Level[] levels;    static Timer timer = new Timer(delayMS, e -> {
        Main.CurrentGame().dieASAP = Main.CurrentGame().nextASAP = false;
        for (Block block : Main.CurrentSection().blocks)
            block.Update();
        Main.CurrentGame().mario.CheckGetCoins();
        Main.CurrentGame().mario.CheckGameState();
        Main.CurrentSection().spentTimeMS += delayMS;
        gameFrame.repaint();
        if (Main.CurrentGame().dieASAP)
            Main.CurrentGame().Die();
        else if (Main.CurrentGame().nextASAP)
            Main.CurrentGame().NextSection();
    });
    Mario mario;
    int levelNumber, sectionNumber;
    Difficulty difficulty;
    int score = 0, coins = 0;
    boolean dieASAP, nextASAP;
    Game() {
        new SetGameSettings();
    }

    Game(boolean Shhhhh) {
        if (Shhhhh)
            levels = new Level[]{new Level(0)};
        else
            System.err.println("Error: Shhhhh");
    }

    static String State(Game game) {
        if (game == null)
            return "New Game";
        else
            return game.State();
    }

    void constructor() {
        levelNumber = sectionNumber = 0;
        levels = new Level[]{new Level(0)};
        gameFrame.setVisible(true);
        mario.Set(0, 0, 3);
        Start();
    }

    void setMario(Mario mario) {
        this.mario = mario;
    }

    void setDifficulty(Difficulty difficulty) {
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
    }

    void EndGame() {
        if (Main.CurrentUser().maxRating < score)
            Main.CurrentUser().maxRating = score;
        Main.CurrentUser().game[Main.CurrentUser().currentGameIndex] = null;
        gameFrame.setVisible(false);
        new MainMenu();
    }

    void EndSection() {
        timer.stop();
        score += (Main.CurrentSection().wholeTime - Main.CurrentSection().spentTimeMS / 1000) * (mario.power + 1);
        score += mario.heart * 20 * (mario.power + 1);
        mario.reset();
        Main.CurrentUser().coin += coins;
        coins = 0;
        Main.CurrentSection().spentTimeMS = 0;
    }

    void Stop() {
        timer.stop();
        gameFrame.setVisible(false);
    }

    void Resume() {
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

    void Level0Section0(Level.Section S) {
        S.W = 45;
        S.wholeTime = 20;
        MakeSection.AddBrick(S, 9, 2, 0, 0);
        MakeSection.AddBrick(S, 28, 2, 17, 0);

        MakeSection.AddBrick(S, 2, 1, 2, 2);
        MakeSection.AddBrick(S, 2, 1, 4, 4);
        MakeSection.AddBrick(S, 2, 1, 6, 6);
        MakeSection.AddBrick(S, 1, 8, 10, 0);
        MakeSection.AddBrick(S, 1, 10, 13, 0);
        MakeSection.AddBrick(S, 1, 12, 17, 0);

        Coin[] coinsWaterfall = new Coin[6];
        for (int i = 0; i < coinsWaterfall.length; i++) coinsWaterfall[i] = new Coin();
        coinsWaterfall[0].setShape(1, 1, 20, 15);
        coinsWaterfall[1].setShape(1, 1, 21, 13);
        coinsWaterfall[2].setShape(1, 1, 22, 11);
        coinsWaterfall[3].setShape(1, 1, 23, 9);
        coinsWaterfall[4].setShape(1, 1, 24, 7);
        coinsWaterfall[5].setShape(1, 1, 25, 5);

        Coin[] coinInLine = new Coin[7];
        for (int i = 0; i < coinInLine.length; i++) {
            coinInLine[i] = new Coin();
            coinInLine[i].setShape(1, 1, 28 + i, 8);
        }
        MakeSection.AddBrick(S, 7, 1, 28, 6);

        for (Coin coin : coinsWaterfall) S.Add(coin);
        for (Coin coin : coinInLine) S.Add(coin);
        S.Add(mario);
    }

    void Level0Section1(Level.Section S) {
        S.W = 60;
        S.wholeTime = 20;
        MakeSection.AddBrick(S, 10, 2, 0, 0);
        MakeSection.AddBrick(S, 9, 2, 11, 0);
        MakeSection.AddBrick(S, 8, 2, 22, 0);
        MakeSection.AddBrick(S, 7, 2, 33, 0);
        MakeSection.AddBrick(S, 6, 2, 44, 0);
        MakeSection.AddBrick(S, 5, 2, 55, 0);
        Coin[] coins = new Coin[25];
        for (int i = 0; i < coins.length; i++) {
            coins[i] = new Coin();
            coins[i].setShape(1, 1, i * 2 + 2, 4 + (int) (Math.random() * 4));
            S.Add(coins[i]);
        }

        S.Add(mario);
    }

    void Level0Section2(Level.Section S) {
        S.W = 75;
        S.wholeTime = 35;
        MakeSection.AddPipe(S, 0, 1);
        MakeSection.AddPipe(S, 4, 2);
        MakeSection.AddPipeWithKillerPlant(S, 8, 3);
        MakeSection.AddPipe(S, 12, 4);
        MakeSection.AddPipeWithKillerPlant(S, 16, 5);
        MakeSection.AddPipe(S, 20, 6);

        for (int i = 25; i < 45; i += 3)
            MakeSection.AddBrick(S, 1, 1, i, (int) (Math.random() * 7));

        for (int i = 0; i < 6; i++)
            MakeSection.AddPipeWithKillerPlant(S, 46 + 4 * i, i + 1, 10 - i);

        MakeSection.AddBrick(S, 2, 2, 73, 0);

        S.Add(mario);
    }

    enum Difficulty {
        Easy,
        Medium,
        Hard
    }

    class Level {
        Section[] sections;

        Level(int level) {
            if (level == 0)
                sections = new Section[]{new Section(0, 0), new Section(0, 1), new Section(0, 2)};
        }

        class Section {
            List<Block> blocks = new ArrayList<>();
            int W, wholeTime;
            int spentTimeMS = 0;

            Section(int level, int section) {
                if (level == 0) {
                    if (section == 0) Level0Section0(this);
                    else if (section == 1) Level0Section1(this);
                    else Level0Section2(this);
                }
            }

            void Add(Block B) {
                blocks.add(B);
            }

            void Del(Block B) {
                blocks.remove(B);
            }
        }
    }




}
