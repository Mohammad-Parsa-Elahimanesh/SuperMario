package backend.gamePlay;

import backend.Manager;
import backend.block.Block;
import backend.block.enemy.Goomba;
import backend.block.enemy.KillerPlant;
import backend.block.Pipe;
import backend.block.brick.*;
import backend.block.item.Coin;

import java.util.ArrayList;
import java.util.List;

public class Section {
    final transient private List<Block> mustBeAdded = new ArrayList<Block>();
    final transient private List<Block> mustBeRemoved = new ArrayList<Block>();
    public List<Block> blocks = new ArrayList<>();
    public int W;
    public int wholeTime;
    public double spentTime = 0;

    Section(int level, int section) {
        Add(new Brick(1, 30, -1, 0));
        if (level == 0) {
            switch (section) {
                case 0 -> Level0Section0();
                case 1 -> Level0Section1();
                case 2 -> Level0Section2();
                case 3 -> Level0Section3();
            }
        }
        Add(Manager.getInstance().CurrentGame().mario);
    }

    void AddPipeWithKillerPlant(int X, int H) {
        Pipe pipe = new Pipe(X, H);
        Add(pipe);
        Add(new KillerPlant(pipe));
    }

    public void Add(Block B) {
        mustBeAdded.add(B);
    }

    public void Del(Block B) {
        mustBeRemoved.add(B);
    }

    void UpdateBlocks() {
        blocks.addAll(mustBeAdded);
        mustBeAdded.clear();
        blocks.removeAll(mustBeRemoved);
        mustBeRemoved.clear();
    }
    void Level0Section0() {
        W = 60;
        wholeTime = 100;
        Add(new Brick(60, 1, 0, 0));
        Add(new Brick(40, 1, 10, 1));
        Add(new Goomba(20,2));
        Add(new Goomba(30,2));
        Add(new Goomba(40,2));

    }
    void Level0Section1() {
        W = 45;
        wholeTime = 100;
        Add(new Spring(1, 3));
        Add(new Solid(SolidType.Prize, 2, 5));
        Add(new Solid(SolidType.Coins, 3, 5));
        Add(new Solid(SolidType.Simple, 4, 5));
        Add(new Soft(SoftType.Coin, 3, 9));
        Add(new Soft(SoftType.Simple, 4, 9));

        Add(new Brick(9, 2, 0, 0));
        Add(new Brick(28, 2, 17, 0));

        Add(new Brick(2, 1, 6, 6));
        Add(new Brick(1, 8, 10, 0));
        Add(new Brick(1, 10, 13, 0));
        Add(new Brick(1, 12, 17, 0));

        Coin[] coinsWaterfall = new Coin[6];
        for (int i = 0; i < coinsWaterfall.length; i++) coinsWaterfall[i] = new Coin(20 + i, 15 - 2 * i);


        Coin[] coinInLine = new Coin[7];
        for (int i = 0; i < coinInLine.length; i++)
            coinInLine[i] = new Coin(28 + i, 8);
        Add(new Brick(7, 1, 28, 6));

        for (Coin coin : coinsWaterfall) Add(coin);
        for (Coin coin : coinInLine) Add(coin);
    }

    void Level0Section2() {
        W = 60;
        wholeTime = 100;
        Add(new Brick(10, 2, 0, 0));
        Add(new Brick(9, 2, 11, 0));
        Add(new Brick(8, 2, 22, 0));
        Add(new Brick(7, 2, 33, 0));
        Add(new Brick(6, 2, 44, 0));
        Add(new Brick(5, 2, 55, 0));

        for (int i = 0; i < 16; i++) {
            Add(new Solid(SolidType.Prize, i * 3 + 2, 4 + (int) (Math.random() * 3)));
        }
    }

    void Level0Section3() {
        W = 75;
        wholeTime = 100;
        Add(new Pipe(0, 1));
        Add(new Pipe(4, 2));
        AddPipeWithKillerPlant(8, 3);
        Add(new Pipe(12, 4));
        AddPipeWithKillerPlant(16, 5);
        Add(new Pipe(20, 6));
        for (int i = 25; i < 45; i += 3)
            Add(new Brick(1, 1, i, (int) (Math.random() * 7)));
        for (int i = 0; i < 6; i++)
            AddPipeWithKillerPlant(46 + 4 * i, i + 1);

        Add(new Brick(2, 2, 73, 0));
    }

}
