package backend;

import java.util.ArrayList;
import java.util.List;

public class Section {
    final transient private List<Block> mustBeAdded = new ArrayList<Block>();
    final transient private List<Block> mustBeRemoved = new ArrayList<Block>();
    public List<Block> blocks = new ArrayList<>();
    public int W;
    public int wholeTime;
    public int spentTimeMS = 0;

    Section(int level, int section) {
        Add(new Brick(1, 30, -1, 0));
        if (level == 0) {
            if (section == 0) Level0Section0(this);
            else if (section == 1) Level0Section1(this);
            else Level0Section2(this);
        }
        Add(Manager.getInstance().CurrentGame().mario);
    }

    static void AddPipeWithKillerPlant(Section S, int X, int H) {
        Pipe pipe = new Pipe(X, H);
        S.Add(pipe);
        S.Add(new KillerPlant(pipe));
    }

    void Add(Block B) {
        mustBeAdded.add(B);
    }

    void Del(Block B) {
        mustBeRemoved.add(B);
    }

    void UpdateBlocks() {
        blocks.addAll(mustBeAdded);
        mustBeAdded.clear();
        blocks.removeAll(mustBeRemoved);
        mustBeRemoved.clear();
    }

    void Level0Section0(Section S) {
        S.W = 45;
        S.wholeTime = 100;
        S.Add(new Spring(1, 3));
        S.Add(new Solid(SolidType.Prize, 2, 5));
        S.Add(new Solid(SolidType.Coins, 3, 5));
        S.Add(new Solid(SolidType.Simple, 4, 5));
        S.Add(new Soft(SoftType.Coin, 3, 9));
        S.Add(new Soft(SoftType.Simple, 4, 9));

        S.Add(new Brick(9, 2, 0, 0));
        S.Add(new Brick(28, 2, 17, 0));

        S.Add(new Brick(2, 1, 6, 6));
        S.Add(new Brick(1, 8, 10, 0));
        S.Add(new Brick(1, 10, 13, 0));
        S.Add(new Brick(1, 12, 17, 0));

        Coin[] coinsWaterfall = new Coin[6];
        for (int i = 0; i < coinsWaterfall.length; i++) coinsWaterfall[i] = new Coin(20 + i, 15 - 2 * i);


        Coin[] coinInLine = new Coin[7];
        for (int i = 0; i < coinInLine.length; i++)
            coinInLine[i] = new Coin(28 + i, 8);
        S.Add(new Brick(7, 1, 28, 6));

        for (Coin coin : coinsWaterfall) S.Add(coin);
        for (Coin coin : coinInLine) S.Add(coin);
    }

    void Level0Section1(Section S) {
        S.W = 60;
        S.wholeTime = 100;
        S.Add(new Brick(10, 2, 0, 0));
        S.Add(new Brick(9, 2, 11, 0));
        S.Add(new Brick(8, 2, 22, 0));
        S.Add(new Brick(7, 2, 33, 0));
        S.Add(new Brick(6, 2, 44, 0));
        S.Add(new Brick(5, 2, 55, 0));

        for (int i = 0; i < 16; i++) {
            S.Add(new Solid(SolidType.Prize, i * 3 + 2, 4 + (int) (Math.random() * 3)));
        }
    }

    void Level0Section2(Section S) {
        S.W = 75;
        S.wholeTime = 100;
        S.Add(new Pipe(0, 1));
        S.Add(new Pipe(4, 2));
        AddPipeWithKillerPlant(S, 8, 3);
        S.Add(new Pipe(12, 4));
        AddPipeWithKillerPlant(S, 16, 5);
        S.Add(new Pipe(20, 6));
        for (int i = 25; i < 45; i += 3)
            S.Add(new Brick(1, 1, i, (int) (Math.random() * 7)));
        for (int i = 0; i < 6; i++)
            AddPipeWithKillerPlant(S, 46 + 4 * i, i + 1);

        S.Add(new Brick(2, 2, 73, 0));
    }

}
