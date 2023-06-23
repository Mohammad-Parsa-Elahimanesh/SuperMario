package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum MarioState {
    mini,
    mega,
    giga
}

public abstract class Mario extends Block implements Saleable {
    final static double FRICTION = 0.8;
    final transient Manager manager = Manager.getInstance();
    public int heart;
    public Map<Direction, Boolean> task = new HashMap<>();
    int upAndDownBoth = 0;
    int jump;
    int power;
    MarioState state = MarioState.mini;

    Mario() {
        super(1, 1, 0, 2);
        reset();
    }

    // TODO power
    void Set(int jump, int power, int heart) {
        this.jump = jump;
        this.power = power;
        this.heart = heart;
    }

    int getSpeed() {
        return 8;
    }

    int getCoinRange() {
        return 0;
    }

    int getShotSpeed() {
        return 3;
    }

    int getJumpSpeed() {
        return 20;
    }

    @Override
    boolean doesGravityAffects() {
        return true;
    }

    private boolean isDirection(Direction d) {return task.get(d) && !task.get(d.Opposite());}

    void reset() {
        for (Direction direction : Direction.values())
            task.put(direction, false);
        jump = power = 0;
        vx = vy = 0;
        upAndDownBoth = 0;
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
    boolean Pushed(Direction D) {
        return false;
    }


    @Override
    void Intersect(Block block) {
        if (block instanceof KillerPlant)
            manager.CurrentGame().dieASAP = true;
        else if (block instanceof Flower) {
            manager.CurrentGame().score += 20;
            Upgrade();
            manager.CurrentSection().Del(block);
        }
        else if(block instanceof Mushroom) {
            manager.CurrentGame().score += 30;
            Upgrade();
            manager.CurrentSection().Del(block);
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

    void Update() {
        switch (state) {
            case mini -> H = 1;
            case mega, giga -> H = isDirection(Direction.Down)?1:2;
        }
        UpdateSpeed();
        super.Update();
        CheckIntersection();
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
            manager.CurrentGame().dieASAP = true;
        else if (manager.CurrentSection().wholeTime <= manager.CurrentSection().spentTimeMS / 1000)
            manager.CurrentGame().dieASAP = true;
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
        manager.CurrentGame().score += mustBeEaten.size() * 10 * (power + 1);
    }

    // TODO for score calculation
    // Kill enemy 15*(power+1)
    // extra power constant score

    // TODO power Up
    // first power: moqavem
    // second: shot ready
    // more : just score
    // when die power will loses to 0
}
