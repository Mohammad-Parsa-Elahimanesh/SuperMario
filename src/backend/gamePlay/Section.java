package backend.gamePlay;

import backend.Manager;
import backend.block.Block;
import backend.block.Checkpoint;
import backend.block.Pipe;
import backend.block.brick.*;
import backend.block.brick.Spring;
import backend.block.enemy.Goomba;
import backend.block.enemy.Koopa;
import backend.block.enemy.Spiny;
import backend.block.flag.Flag;
import backend.block.item.Coin;
import backend.block.mario.Mario;
import backend.block.mario.MarioState;
import frontend.menu.game.AudioPlayer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Section {
    final String name;
    public final Mario mario;
    public final List<Checkpoint> savedCheckpoints = new ArrayList<>();
    private final List<Block> mustBeAdded = new ArrayList<>();
    private final List<Block> mustBeRemoved = new ArrayList<>();
    private final Manager manager = Manager.getInstance();
    public final List<Block> blocks = new ArrayList<>();
    private int length;
    private int wholeTime;
    private Section nextSection;

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

    static Section makeSections(Mario mario) throws Exception {
        Section section0 = new Section(0, mario, "learn");
        Section section1 = new Section(1, mario, "explore");
        Section section2 = new Section(2, mario, "fight");
        section0.nextSection = section1;
        section1.nextSection = section2;
        return section0;
    }

    Section(int section, Mario mario, String name) throws Exception {
        this.name = name;
        add(new Brick(1, 30, -1, 0));
        this.mario = mario.getClass().getDeclaredConstructor().newInstance();
        switch (section) {
            case 0 -> section0();
            case 1 -> section1();
            case 2 -> section2();
        }
        add(this.mario);
        add(new Brick(3, 2, length - 3.0, 0));
        add(new Brick(1, 30, length, 0));
        new Flag(length - 3.0, 2, this);
    }

    Section(Mario mario, Section original, int hideNumber) throws Exception {
        name = "Secret";
        add(new Brick(1, 30, -1, 0));
        this.mario = mario.getClass().getDeclaredConstructor().newInstance();
        new Pipe(0, 2, false, this);
        switch (hideNumber) {
            case 0 -> hiddenSection0();
            case 1 -> hiddenSection1();
            case 2 -> hiddenSection2();
        }
        Pipe endPipe = new Pipe(length-2, 2, false, this);
        add(this.mario);
        endPipe.destination = original;
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

    void section0() {
        length = 95;
        wholeTime = 80;

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

        new Pipe(83,3,true, this);
        Pipe secretPipe = new Pipe(87, 3, false, this);
        try {
            secretPipe.destination = new Section(mario, this, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    void hiddenSection0() {
        length = 30;
        wholeTime = 15;
        add(new Brick(length-4.0,2,2,0));
    }
    void hiddenSection1() {
        length = 30;
        wholeTime = 15;
        add(new Brick(length-4.0,2,2,0));
    }
    void hiddenSection2() {

    }


    void section2() {
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

    void section1() throws Exception {
        length = 70;
        wholeTime = 100;
        add(new Brick(40, 1, 0, 0));
        add(new Brick(30, 1, 5, 1));
        add(new Goomba(10,2));
        add(new Goomba(20,2));
        add(new Goomba(30,2));
        for(int i = 10; i <= 30; i++)
            add(new Soft(SoftType.Simple, i, 7));
        add(new Goomba(15, 8));
        add(new Spiny(18, 8));
        add(new Goomba(20, 8));
        add(new Spiny(18, 8));
        add(new Goomba(25, 8));
        for(int i = 15; i <= 25; i++)
            add(new Solid(SolidType.Prize, i, 11));
        add(new Koopa(17,12));
        add(new Koopa(20,12));
        add(new Koopa(23,12));

        add(new Brick(1,4,45, 0));
        add(new Brick(1,7,48, 0));
        add(new Brick(1,10,51, 0));
        new Pipe(55, 10, true, this);
        new Pipe(59, 10, true, this);
        Pipe secretpipe = new Pipe(63, 10, true, this);
        secretpipe.destination = new Section(mario, this, 1);
    }

    void sectionReward() {
        manager.currentGame().score += (wholeTime - spentTime) * mario.getPowerLevel();
        manager.currentGame().score += mario.heart * 20 * mario.getPowerLevel();
        manager.currentUser().coins += coins;
    }

    void marioDie() {
        if (mario.Y < 0)
            mario.state = MarioState.mini;
        switch (mario.state) {
            case mini -> {
                AudioPlayer.getInstance().playSound("marioDeath");
                spentTime = 0;
                coins -= ((savedCheckpoints.size() + 1) * coins + progressRisk()) / (savedCheckpoints.size() + 4);
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
                if (mario.heart <= 0) {
                    timer.stop();
                    manager.currentGame().endGame();
                }
                return;
            }
            case mega -> mario.state = MarioState.mini;
            case giga -> mario.state = MarioState.mega;
        }
        mario.BeAlive();
    }

    double progressRate() {
        return mario.travelledDistance / length;
    }

    public int progressRisk() {
        return (int) (progressRate() * coins);
    }

    void update() {
        updateBlocks();
        for (Block block : blocks)
            block.Update();
        if (mario.Died())
            marioDie();
        else if (mario.goNext())
            nextSection();
        spentTime += Game.delay;
    }

    void nextSection() {
        timer.stop();
        manager.currentSection().sectionReward();
        MarioState lastStateOfMario = manager.currentMario().state;
        manager.currentGame().currentSection = nextSection;
        if(manager.currentSection() == null) {
            timer.stop();
            manager.currentGame().endGame();
        }
        else {
            manager.currentMario().state = lastStateOfMario;
            manager.currentSection().timer.start();
        }
    }
    public Timer timer = new Timer((int) (Game.delay * 1000), e -> {
        //if(manager.currentGame() == null || manager.currentSection() == null) return;
        manager.currentGame().gameFrame.repaint();
        manager.currentSection().update();
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

    @Override
    public String toString() {
        return name;
    }
}
