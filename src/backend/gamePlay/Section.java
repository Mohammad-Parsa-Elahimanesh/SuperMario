package backend.gamePlay;

import backend.Manager;
import backend.block.Block;
import backend.block.Checkpoint;
import backend.block.Pipe;
import backend.block.brick.*;
import backend.block.enemy.Goomba;
import backend.block.enemy.Koopa;
import backend.block.enemy.Spiny;
import backend.block.flag.Flag;
import backend.block.item.Coin;
import backend.block.mario.Mario;
import backend.block.mario.MarioState;
import frontend.menu.game.AudioPlayer;

import java.util.ArrayList;
import java.util.List;

public class Section {
    public final Mario mario;
    public final ArrayList<Checkpoint> savedCheckpoints = new ArrayList<>();
    final transient private List<Block> mustBeAdded = new ArrayList<Block>();
    final transient private List<Block> mustBeRemoved = new ArrayList<Block>();
    final private transient Manager manager = Manager.getInstance();
    public List<Block> blocks = new ArrayList<>();
    public int W;
    public int wholeTime;
    public double spentTime = 0;
    public int coins = 0;

    Section(int level, int section, Mario mario) throws Exception {
        Add(new Brick(1, 30, -1, 0));
        this.mario = mario.getClass().getDeclaredConstructor().newInstance();
        if (level == 0) {
            switch (section) {
                case 0 -> Level0Section0();
                case 1 -> Level0Section1();
                case 2 -> Level0Section2();
            }
        }
        Add(this.mario);
        Add(new Brick(3, 2, W - 3, 0));
        Add(new Brick(1, 30, W, 0));
        new Flag(W - 3, 2, this);
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
        Add(new Goomba(20, 2));
        Add(new Spiny(30, 2));
        Add(new Goomba(40, 2));
        Add(new Koopa(25, 2));
        Add(new Koopa(35, 2));
        Add(new Checkpoint(30, 6));
        for (int i = 20; i < 40; i++) {
            if (Math.random() < 0.5) Add(new Soft(SoftType.Coin, i, 5));
            else Add(new Solid(SolidType.Prize, i, 5));
        }
        for (int i = 20; i < 40; i += 5)
            Add(new Goomba(i, 6));
        Pipe pipe1 = new Pipe(3, 3, false, this);
        Pipe pipe2 = new Pipe(55, 3, false, this);
        pipe1.destination = pipe2;
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

    void SectionReward() {
        manager.CurrentGame().score += (wholeTime - spentTime) * mario.getPowerLevel();
        manager.CurrentGame().score += mario.heart * 20 * mario.getPowerLevel();
        manager.CurrentUser().coins += coins;
    }

    void MarioDie() {
        if (mario.Y < 0)
            mario.state = MarioState.mini;
        switch (mario.state) {
            case mini -> {
                AudioPlayer.getInstance().playSound("marioDeath");
                spentTime = 0;
                coins -= ((savedCheckpoints.size() + 1) * coins + ProgressRisk()) / (savedCheckpoints.size() + 4);
                mario.reset();
                if (savedCheckpoints.isEmpty()) {
                    mario.X = 0;
                    mario.Y = 2;
                    spentTime = 0;
                } else {
                    mario.X = savedCheckpoints.get(savedCheckpoints.size() - 1).X;
                    mario.Y = savedCheckpoints.get(savedCheckpoints.size() - 1).Y;
                    spentTime = savedCheckpoints.get(savedCheckpoints.size() - 1).spendTime;
                }
                mario.heart--;
                if (mario.heart <= 0)
                    manager.CurrentGame().EndGame();
                return;
            }
            case mega -> mario.state = MarioState.mini;
            case giga -> mario.state = MarioState.mega;
        }
        mario.BeAlive();
    }

    double ProgressRate() {
        return mario.travelledDistance / W;
    }

    public int ProgressRisk() {
        return (int) (ProgressRate() * coins);
    }

    void Update() {
        UpdateBlocks();
        for (Block block : blocks)
            block.Update();
        if (mario.Died())
            MarioDie();
        else if (mario.goNext())
            manager.CurrentGame().NextSection();

        spentTime += Game.delay;
    }
}
