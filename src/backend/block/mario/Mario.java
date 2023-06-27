package backend.block.mario;

import backend.Manager;
import backend.block.Block;
import backend.block.Saleable;
import backend.block.brick.Spring;
import backend.block.enemy.Enemy;
import backend.block.item.*;
import backend.gamePlay.Game;

import java.awt.*;
import java.util.HashMap;
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
    transient double shotCooldown = 0.0;

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
        return getSpeed();
    }

    public double getJumpSpeed() {
        return 20;
    }

    protected int getVxDirectionNumber() {
        return vx < 0 ? -1 : 1;
    }

    @Override
    protected boolean doesGravityAffects() {
        return true;
    }

    public boolean mustBeDied() {
        return dieASAP;
    }

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

    public void Shot() {
        if (state == MarioState.giga && shotCooldown == 0 && Push(Direction.Down) == 0) {
            manager.CurrentSection().Add(new Fire(this));
            shotCooldown = 3;
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
                ((Enemy) block).Die();
            else
                dieASAP = true;
        } else if (block instanceof Item && !(block instanceof Coin)) {
            Upgrade();
            block.Delete();
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
        shotCooldown = Math.max(0, shotCooldown - Game.delay);
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
        vy = getJumpSpeed() * 0.7;
        Y += 5;
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
        for (Block coin : manager.CurrentSection().blocks)
            if (coin instanceof Coin)
                if (Distance(coin) <= getCoinRange()) {
                    coin.Delete();
                    manager.CurrentGame().coins += 1;
                    manager.CurrentGame().score += 10;
                }
    }

    @Override
    public void Draw(Graphics g, int cameraLeftLine) {
        super.Draw(g, cameraLeftLine);
        if (dieBye > 0)
            new FireRing(this).Draw(g, cameraLeftLine);
    }

}

// TODO: 3 coins when kill enemy!
