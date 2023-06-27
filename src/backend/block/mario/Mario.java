package backend.block.mario;

import backend.Manager;
import backend.block.Block;
import backend.block.enemy.Enemy;
import backend.block.Saleable;
import backend.block.brick.Spring;
import backend.block.item.*;
import backend.gamePlay.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Mario extends Block implements Saleable {
    final static double FRICTION = 0.7;
    final transient Manager manager = Manager.getInstance();
    public int heart;
    public Map<Direction, Boolean> task = new HashMap<>();
    public MarioState state = MarioState.mini;
    int upAndDownBoth = 0;
    transient double dieBye = 0.0;
    transient boolean dieASAP = false;
    Mario() {
        super(1, 1, 0, 2);
        reset();
    }

    public int getSpeed() {
        return 8;
    }

    double getCoinRange() {
        return 0;
    }

    double getShotSpeed() {
        return 3;
    }

    public double getJumpSpeed() {
        return 20;
    }

    @Override
    protected boolean doesGravityAffects() {
        return true;
    }
    public boolean mustBeDied(){return dieASAP;}
    private boolean isDirection(Direction d) {
        return task.get(d) && !task.get(d.Opposite());
    }

    public int getPowerLevel() {
        switch (state) {
            case mini -> {
                return 1;
            }
            case mega -> {
                return 2;
            }
            case giga -> {
                return 3;
            }
        }
        return 0;
    }

    public void reset() {
        for (Direction direction : Direction.values())
            task.put(direction, false);
        vx = vy = 0;
        upAndDownBoth = 0;
        dieBye = 0;
        W = 1;
        H = 1;
        X = 0;
        Y = 2;
    }

    void Upgrade() {
        switch (state) {
            case mini -> state = MarioState.mega;
            case mega -> state = MarioState.giga;
            case giga -> manager.CurrentGame().score += 100;
        }
    }


    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }


    @Override
    protected void Intersect(Block block) {
        if (block instanceof Enemy) {
            if (dieBye > 0)
                manager.CurrentSection().Del(block);
            else
                dieASAP = true;
        } else if (block instanceof Item && !(block instanceof Coin)) {
            Upgrade();
            manager.CurrentSection().Del(block);
            if (block instanceof Flower)
                manager.CurrentGame().score += 20;
            else if (block instanceof Mushroom)
                manager.CurrentGame().score += 30;
            else if (block instanceof Star) {
                manager.CurrentGame().score += 40;
                dieBye = 15;
            }
        } else if (block instanceof Spring) {
            vy = getJumpSpeed() * 1.3;
        }

    }

    void UpdateSpeed() {
        vx *= FRICTION;

        if (task.get(Direction.Down) && task.get(Direction.Up))
            upAndDownBoth++;
        else
            upAndDownBoth = 0;

        if (isDirection(Direction.Left))
            vx = (vx - getSpeed()) / 2;
        else if (isDirection(Direction.Right))
            vx = (vx + getSpeed()) / 2;
        if (isDirection(Direction.Up) && Push(Direction.Down) == 0)
            vy = getJumpSpeed();
    }

    public void Update() {
        dieASAP = false;
        dieBye = Math.max(0, dieBye - Game.delay);
        switch (state) {
            case mini -> H = 1;
            case mega, giga -> H = isDirection(Direction.Down) ? 1 : 2;
        }
        UpdateSpeed();
        super.Update();
        CheckIntersection();
        CheckGetCoins();
        CheckGameState();
    }

    public void BeAlive() {
        vy = getJumpSpeed() * 1.2;
        Y += 2;
    }

    void CheckIntersection() {
        for (Block block : manager.CurrentSection().blocks)
            if (isIntersect(block))
                Intersect(block);
    }

    void CheckGameState() {
        if (manager.CurrentSection().W < X + W)
            manager.CurrentGame().nextASAP = true;
        else if (Y + H < 0)
            dieASAP = true;
        else if (manager.CurrentSection().wholeTime <= manager.CurrentSection().spentTime)
            dieASAP = true;
    }

    void CheckGetCoins() {
        List<Block> mustBeEaten = new ArrayList<>();
        for (Block coin : manager.CurrentSection().blocks)
            if (coin instanceof Coin)
                if (Distance(coin) <= getCoinRange()) {
                    mustBeEaten.add(coin);
                }
        for (Block coin : mustBeEaten)
            manager.CurrentSection().Del(coin);
        manager.CurrentGame().coins += mustBeEaten.size();
        manager.CurrentGame().score += mustBeEaten.size() * 10;
    }

    @Override
    public void Draw(Graphics g, int cameraLeftLine) {
        super.Draw(g, cameraLeftLine);
        if (dieBye > 0)
            new FireRing(this).Draw(g, cameraLeftLine);
    }
}

// TODO: 3 coins when kill enemy!
