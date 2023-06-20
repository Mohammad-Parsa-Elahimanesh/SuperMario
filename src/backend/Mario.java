package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Mario extends Block implements Saleable {
    final static double FRICTION = 0.8;
    public int heart;
    public Map<Direction, Boolean> task = new HashMap<>();
    int upAndDownBoth = 0;
    int jump;
    int power;

    Mario() {
        super(1, 2, 0, 2);
        reset();
    }

    // TODO power
    void Set(int jump, int power, int heart) {
        this.jump = jump;
        this.power = power;
        this.heart = heart;
    }

    public int getSpeed() {
        return 75;
    }

    int getCoinRange() {
        return 0;
    }

    int getShotSpeed() {
        return 3;
    }

    int getJumpSpeed() {
        return 105;
    }

    @Override
    boolean doesGravityAffects() {
        return true;
    }

    void reset() {
        for (Direction direction : Direction.values())
            task.put(direction, false);
        jump = power = 0;
        vx = vy = 0;
        upAndDownBoth = 0;
        W = 1;
        H = 2;
        X = 0;
        Y = 2;
    }

    @Override
    boolean Pushed(Direction D) {
        System.err.println("ERROR");
        return false;
    }


    @Override
    void Intersect(Block block) {
        if (block instanceof KillerPlant)
            Manager.getInstance().CurrentGame().dieASAP = true;
    }

    void UpdateSpeed() {
        vx *= FRICTION;

        if (task.get(Direction.Down) && task.get(Direction.Up))
            upAndDownBoth++;
        else
            upAndDownBoth = 0;

        if (task.get(Direction.Left) && !task.get(Direction.Right))
            vx = (vx - getSpeed()) / 2;
        else if (task.get(Direction.Right) && !task.get(Direction.Left))
            vx = (vx + getSpeed()) / 2;

        if (task.get(Direction.Up) && !task.get(Direction.Down) && Push(Direction.Down) == 0)
            vy = getJumpSpeed();
    }

    void Update() {
        UpdateSpeed();
        super.Update();
        CheckIntersection();
    }

    void CheckIntersection() {
        for (Block block : Manager.getInstance().CurrentSection().blocks)
            if (isIntersect(block))
                Intersect(block);
    }

    void CheckGameState() {
        if (Manager.getInstance().CurrentSection().W < X + W)
            Manager.getInstance().CurrentGame().nextASAP = true;
        else if (Y + H < 0)
            Manager.getInstance().CurrentGame().dieASAP = true;
        else if (Manager.getInstance().CurrentSection().wholeTime <= Manager.getInstance().CurrentSection().spentTimeMS / 1000)
            Manager.getInstance().CurrentGame().dieASAP = true;
    }

    void CheckGetCoins() {
        List<Block> mustBeEaten = new ArrayList<>();
        for (Block coin : Manager.getInstance().CurrentSection().blocks)
            if (coin instanceof Coin)
                if (Distance(coin) <= getCoinRange()) {
                    mustBeEaten.add(coin);
                }
        for (Block coin : mustBeEaten)
            Manager.getInstance().CurrentSection().Del(coin);
        Manager.getInstance().CurrentGame().coins += mustBeEaten.size();
        Manager.getInstance().CurrentGame().score += mustBeEaten.size() * 10 * (power + 1);
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
