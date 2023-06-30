package backend.block.mario;

import backend.Manager;
import backend.block.Block;
import backend.block.Saleable;
import backend.block.brick.Spring;
import backend.block.enemy.Enemy;
import backend.block.item.*;
import backend.gamePlay.Game;
import frontend.menu.game.AudioPlayer;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public abstract class Mario extends Block implements Saleable {
    static final double FRICTION = 0.7;
    final transient Manager manager = Manager.getInstance();
    public int heart = 3;
    public Map<Direction, Boolean> task = new EnumMap<>(Direction.class);
    public MarioState state = MarioState.mini;
    public double travelledDistance = 0;
    public boolean nextASAP;
    double shield = 0.0;
    double shotCooldown = 0.0, saberShotCooldown = 0.0, upAndDownBoth = 0.0;
    private boolean dieASAP;

    Mario() {
        super(1, 1, 0, 2);
        W = H = 1;
        X = 0;
        Y = 2;
        reset();
    }

    public boolean goNext() {
        return nextASAP;
    }

    public boolean Died() {
        return dieASAP;
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

    @Override
    protected boolean doesGravityAffects() {
        return true;
    }

    public boolean isDirection(Direction d) {
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
        vx = vy = upAndDownBoth = 0;
    }

    void Upgrade() {
        switch (state) {
            case mini -> state = MarioState.mega;
            case mega -> state = MarioState.giga;
            case giga -> manager.currentGame().score += 100;
        }
    }

    public void Shot() {
        if (state == MarioState.giga && shotCooldown == 0 && Push(Direction.Down) == 0) {
            manager.currentSection().Add(new Fire(this));
            shotCooldown = 3;
        }
    }

    private void saberShot() {
        if (saberShotCooldown == 0 && manager.currentUser().coins >= 3) {
            manager.currentSection().Add(new Saber(this));
            manager.currentUser().coins -= 3;
            saberShotCooldown = 5;
        }
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (D == Direction.Up && vy > 0)
            vy = 0;
        return true;
    }


    @Override
    protected void Intersect(Block block) {
        if (block instanceof Enemy) {
            if (shield > 0)
                ((Enemy) block).Die();
            else {
                dieASAP = true;
                if (state == MarioState.mini)
                    manager.currentGame().score = Math.max(manager.currentGame().score - 20, 0);
            }
        } else if (block instanceof Item && !(block instanceof Coin)) {
            Upgrade();
            block.Delete();
            if (block instanceof Flower)
                manager.currentGame().score += 20;
            else if (block instanceof Mushroom)
                manager.currentGame().score += 30;
            else if (block instanceof Star) {
                manager.currentGame().score += 40;
                shield = 15;
                AudioPlayer.getInstance().playSound("shield");
            }
        } else if (block instanceof Spring) {
            vy = getJumpSpeed() * 1.3;
        }

    }

    void UpdateSpeed() {
        vx *= FRICTION;

        if (task.get(Direction.Down) && task.get(Direction.Up)) {
            upAndDownBoth += Game.delay;
            if (upAndDownBoth > 3)
                saberShot();
        } else
            upAndDownBoth = 0;

        if (isDirection(Direction.Left))
            vx = (vx - getSpeed()) / 2;
        else if (isDirection(Direction.Right))
            vx = (vx + getSpeed()) / 2;
        if (isDirection(Direction.Up) && Push(Direction.Down) == 0)
            vy = getJumpSpeed();
    }

    public void Update() {
        travelledDistance = (int) Math.max(travelledDistance, X);
        dieASAP = nextASAP = false;
        shield = Math.max(0, shield - Game.delay);
        shotCooldown = Math.max(0, shotCooldown - Game.delay);
        saberShotCooldown = Math.max(0, saberShotCooldown - Game.delay);
        H = state == MarioState.mini || isDirection(Direction.Down) ? 1 : 2;
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
        for (Block block : manager.currentSection().blocks)
            if (isIntersect(block))
                Intersect(block);
    }

    void CheckGameState() {
        if (Y + H < 0) {
            dieASAP = true;
            manager.currentGame().score = Math.max(manager.currentGame().score - 30, 0);
        } else if (manager.currentSection().wholeTime <= manager.currentSection().spentTime)
            dieASAP = true;
    }

    void CheckGetCoins() {
        for (Block coin : manager.currentSection().blocks)
            if (coin instanceof Coin && Distance(coin) <= getCoinRange()) {
                coin.Delete();
                manager.currentSection().coins += 1;
                manager.currentGame().score += 10;
            }
    }

    @Override
    public void Draw(Graphics g, int cameraLeftLine) {
        super.Draw(g, cameraLeftLine);
        if (shield > 0)
            new FireRing(this).Draw(g, cameraLeftLine);
    }

}

