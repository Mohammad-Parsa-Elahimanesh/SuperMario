package backend;

import java.util.ArrayList;
import java.util.List;

public class Section {
    public List<Block> blocks = new ArrayList<>();
    public int W;
    public int wholeTime;
    public int spentTimeMS = 0;

    Section(int level, int section) {
        MakeSection.AddBrick(this, 1, 30, -1, 0);
        if (level == 0) {
            if (section == 0) Level0Section0(this);
            else if (section == 1) Level0Section1(this);
            else Level0Section2(this);
        }
        Add(Manager.getInstance().CurrentGame().mario);
    }

    void Add(Block B) {
        blocks.add(B);
    }

    void Del(Block B) {
        blocks.remove(B);
    }

    void Level0Section0(Section S) {
        S.W = 45;
        S.wholeTime = 100;
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
    }

    void Level0Section1(Section S) {
        S.W = 60;
        S.wholeTime = 100;
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
    }

    void Level0Section2(Section S) {
        S.W = 75;
        S.wholeTime = 100;
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
    }
}
