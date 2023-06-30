package backend.gamePlay;

import backend.Manager;
import backend.block.Block;
import backend.block.Checkpoint;
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
    public final List<Checkpoint> savedCheckpoints = new ArrayList<>();
    private final List<Block> mustBeAdded = new ArrayList<>();
    private final List<Block> mustBeRemoved = new ArrayList<>();
    private final Manager manager = Manager.getInstance();
    public final List<Block> blocks = new ArrayList<>();
    private int length;
    private int wholeTime;

    public int getLength() {
        return length;
    }

    public double getWholeTime() {
        return wholeTime;
    }

    private double spentTime = 0;

    public double getSpentTime() {
        return spentTime;
    }

    private int coins = 0;

    Section(int level, int section, Mario mario) throws Exception {
        add(new Brick(1, 30, -1, 0));
        this.mario = mario.getClass().getDeclaredConstructor().newInstance();
        if (level == 0) {
            switch (section) {
                case 0 -> level0Section0();
                case 1 -> Level0Section1();
                case 2 -> Level0Section2();
            }
        }
        add(this.mario);
        add(new Brick(3, 2, length - 3.0, 0));
        add(new Brick(1, 30, length, 0));
        new Flag(length - 3.0, 2, this);
    }

    public void add(Block block) {
        mustBeAdded.add(block);
    }

    public void del(Block block) {
        mustBeRemoved.add(block);
    }

    private void updateBlocks() {
        blocks.addAll(mustBeAdded);
        mustBeAdded.clear();
        blocks.removeAll(mustBeRemoved);
        mustBeRemoved.clear();
    }

    public int getCoins() {
        return coins;
    }
    public void getCoin(int number) {
        coins+=number;
    }

    void level0Section0() {
        length = 85;
        wholeTime = 100;

        add(new Brick(33,2,0,0));
        add(new Soft(SoftType.Coin, 3, 5));
        add(new Soft(SoftType.Simple, 6, 5));
        add(new Solid(SolidType.Coins, 9, 5));
        add(new Solid(SolidType.Simple, 12, 5));
        for(int i = 1; i <= 9; i++)
            add(new Solid(SolidType.Prize, 15+i, 5));
        for(int i = 1; i <= 7; i++) {
            add(new Soft(SoftType.Coin, 26+i, 5));
            add(new Solid(SolidType.Prize, 26 + i, 9));
        }

        add(new Brick(10,2,36,0));
        add(new Goomba(38, 2));
        add(new Goomba(42, 2));

        add(new Brick(5,2,48,0));
        add(new Checkpoint(50,2));
        add(new Spring(52, 4));

        add(new Brick(20,2,60,0));
        add(new Koopa(65, 2));
        add(new Spiny(70, 2));
        add(new Koopa(75, 2));
    }

    void Level0Section1() {
        length = 45;
        wholeTime = 100;
        add(new Spring(1, 3));
        add(new Solid(SolidType.Prize, 2, 5));
        add(new Solid(SolidType.Coins, 3, 5));
        add(new Solid(SolidType.Simple, 4, 5));
        add(new Soft(SoftType.Coin, 3, 9));
        add(new Soft(SoftType.Simple, 4, 9));

        add(new Brick(9, 2, 0, 0));
        add(new Brick(28, 2, 17, 0));

        add(new Brick(2, 1, 6, 6));
        add(new Brick(1, 8, 10, 0));
        add(new Brick(1, 10, 13, 0));
        add(new Brick(1, 12, 17, 0));

        Coin[] coinsWaterfall = new Coin[6];
        for (int i = 0; i < coinsWaterfall.length; i++) coinsWaterfall[i] = new Coin(20 + i, 15 - 2 * i);


        Coin[] coinInLine = new Coin[7];
        for (int i = 0; i < coinInLine.length; i++)
            coinInLine[i] = new Coin(28 + i, 8);
        add(new Brick(7, 1, 28, 6));

        for (Coin coin : coinsWaterfall) add(coin);
        for (Coin coin : coinInLine) add(coin);
    }

    void Level0Section2() {
        length = 60;
        wholeTime = 100;
        add(new Brick(10, 2, 0, 0));
        add(new Brick(9, 2, 11, 0));
        add(new Brick(8, 2, 22, 0));
        add(new Brick(7, 2, 33, 0));
        add(new Brick(6, 2, 44, 0));
        add(new Brick(5, 2, 55, 0));

        for (int i = 0; i < 16; i++) {
            add(new Solid(SolidType.Prize, i * 3 + 2, 4 + (int) (Math.random() * 3)));
        }
    }

    void SectionReward() {
        manager.currentGame().score += (wholeTime - spentTime) * mario.getPowerLevel();
        manager.currentGame().score += mario.heart * 20 * mario.getPowerLevel();
        manager.currentUser().coins += coins;
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
                    manager.currentGame().EndGame();
                return;
            }
            case mega -> mario.state = MarioState.mini;
            case giga -> mario.state = MarioState.mega;
        }
        mario.BeAlive();
    }

    double ProgressRate() {
        return mario.travelledDistance / length;
    }

    public int ProgressRisk() {
        return (int) (ProgressRate() * coins);
    }

    void Update() {
        updateBlocks();
        for (Block block : blocks)
            block.Update();
        if (mario.Died())
            MarioDie();
        else if (mario.goNext())
            manager.currentGame().NextSection();
        spentTime += Game.delay;
    }
}
