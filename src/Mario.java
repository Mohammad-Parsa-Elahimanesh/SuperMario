import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Mario extends Block implements Saleable {
    int jump, power, heart;
    int right = 0, left = 0;

    // TODO power
    void Set(int jump, int power, int heart) {
        this.jump = jump;
        this.power = power;
        this.heart = heart;
    }

    int getSpeed() {
        return 1;
    }

    int getCoinRange() {
        return 0;
    }

    int getShotSpeed() {
        return 3;
    }

    int getJumpHeight() {
        return 6;
    }

    @Override
    public String toString() {
        return getName();
    }

    void reset() {
        jump = power = left = right = 0;
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

    boolean Push(Direction direction) {
        boolean push = true;
        for (Block block : Main.CurrentSection().blocks)
            if (Neighbor(this, block, direction))
                push &= block.Pushed(Direction.Opposite(direction));
        return push;
    }

    @Override
    void Intersect(Block block) {
        if (block instanceof KillerPlant)
            Main.CurrentGame().dieASAP = true;
    }

    void Jump() {
        if (!Push(Direction.Down))
            jump = getJumpHeight();
    }

    void GoLeft() {
        if (X > 0 && Push(Direction.Left))
            X--;
    }

    void GoRight() {
        if (Push(Direction.Right))
            X++;
    }

    void PushDown() {
        // TODO
    }


    void Update() {
        if (left > 0) {
            if (Push(Direction.Left))
                GoLeft();
            left--;
        }
        if (right > 0) {
            if (Push(Direction.Right))
                GoRight();
            right--;
        }
        if (jump > 0) {
            if (Push(Direction.Up))
                Y++;
            else
                jump = 1;
            jump--;
        } else if (Push(Direction.Down))
            Y--;
        CheckIntersection();
    }

    void CheckIntersection() {
        for (Block block : Main.CurrentSection().blocks)
            if (Intersect(this, block))
                Intersect(block);
    }

    void CheckGameState() {
        if (Main.CurrentSection().W < X + W)
            Main.CurrentGame().nextASAP = true;
        else if (Y + H < 0)
            Main.CurrentGame().dieASAP = true;
        else if (Main.CurrentSection().wholeTime <= Main.CurrentSection().spentTimeMS / 1000)
            Main.CurrentGame().dieASAP = true;
    }

    void CheckGetCoins() {
        List<Block> mustBeEaten = new ArrayList<>();
        for (Block coin : Main.CurrentSection().blocks)
            if (coin instanceof Coin)
                if (Distance(this, coin) <= getCoinRange()) {
                    mustBeEaten.add(coin);
                }
        for (Block coin : mustBeEaten)
            Main.CurrentSection().Del(coin);
        Main.CurrentGame().coins += mustBeEaten.size();
        Main.CurrentGame().score += mustBeEaten.size() * 10 * (power + 1);
    }

    @Override
    void Draw(Graphics g, int cameraLeftLine) {
        DrawBackground(g, cameraLeftLine);
        super.Draw(g, cameraLeftLine);
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
