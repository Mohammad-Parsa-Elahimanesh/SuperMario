package backend.block.mario;

import backend.Manager;
import backend.block.Block;
import backend.block.Saleable;
import backend.block.brick.Spring;
import backend.block.enemy.Enemy;
import backend.block.item.*;
import backend.gamePlay.Section;
import frontend.menu.game.AudioPlayer;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public abstract class Mario extends Block implements Saleable {
    static final double FRICTION = 0.7;
    final Manager manager = Manager.getInstance();
    public int heart = 3;
    public Map<Direction, Boolean> task = new EnumMap<>(Direction.class);
    public MarioState state = MarioState.mini;
    public double travelledDistance = 0;
    public boolean nextASAP;
    public double shield = 0.0;
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

    public boolean isAlive() {
        return !dieASAP;
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
        return task.get(d) && !task.get(d.opposite());
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

    void upgrade() {
        switch (state) {
            case mini -> state = MarioState.mega;
            case mega -> state = MarioState.giga;
            case giga -> manager.currentGame().score += 100;
        }
    }

    public void shot() {
        if (state == MarioState.giga && shotCooldown == 0 && push(Direction.DOWN) == 0) {
            manager.currentSection().add(new Fire(this));
            shotCooldown = 3;
        }
    }

    private void saberShot() {
        if (saberShotCooldown == 0 && manager.currentUser().coins >= 3) {
            manager.currentSection().add(new Saber(this));
            manager.currentUser().coins -= 3;
            saberShotCooldown = 5;
        }
    }

    @Override
    protected boolean pushed(Direction side) {
        if (side == Direction.UP && vy > 0)
            vy = 0;
        return true;
    }


    @Override
    protected void intersect(Block block) {
        if (block instanceof Enemy enemy) {
            if (shield > 0)
                enemy.Die();
            else {
                dieASAP = true;
                if (state == MarioState.mini)
                    manager.currentGame().score = Math.max(manager.currentGame().score - 20, 0);
            }
        } else if (block instanceof Item && !(block instanceof Coin)) {
            upgrade();
            block.remove();
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

    void updateSpeed() {
        vx *= FRICTION;

        if (task.get(Direction.DOWN) && task.get(Direction.UP)) {
            upAndDownBoth += Section.delay;
            if (upAndDownBoth > 3)
                saberShot();
        } else
            upAndDownBoth = 0;

        if (isDirection(Direction.LEFT))
            vx = (vx - getSpeed()) / 2;
        else if (isDirection(Direction.RIGHT))
            vx = (vx + getSpeed()) / 2;
        if (isDirection(Direction.UP) && push(Direction.DOWN) == 0)
            vy = getJumpSpeed();
    }

    @Override
    public void update() {
        travelledDistance = (int) Math.max(travelledDistance, X);
        dieASAP = nextASAP = false;
        shield = Math.max(0, shield - Section.delay);
        shotCooldown = Math.max(0, shotCooldown - Section.delay);
        saberShotCooldown = Math.max(0, saberShotCooldown - Section.delay);
        H = state == MarioState.mini || isDirection(Direction.DOWN) ? 1 : 2;
        updateSpeed();
        super.update();
        checkIntersection();
        checkGetCoins();
        checkGameState();
    }

    public void beAlive() {
        vy = getJumpSpeed() * 0.7;
        Y += 5;
    }

    void checkIntersection() {
        for (Block block : manager.currentSection().blocks)
            if (isIntersect(block))
                intersect(block);
    }

    void checkGameState() {
        if (Y + H < 0) {
            dieASAP = true;
            manager.currentGame().score = Math.max(manager.currentGame().score - 30, 0);
        } else if (manager.currentSection().getWholeTime() <= manager.currentSection().getSpentTime())
            dieASAP = true;
    }

    void checkGetCoins() {
        for (Block coin : manager.currentSection().blocks)
            if (coin instanceof Coin && distance(coin) <= getCoinRange()) {
                coin.remove();
                manager.currentSection().getCoin(1);
                manager.currentGame().score += 10;
            }
    }

    @Override
    public void draw(Graphics g, int cameraLeftLine) {
        super.draw(g, cameraLeftLine);
        if (shield > 0)
            new FireRing(this).draw(g, cameraLeftLine);
    }

}

