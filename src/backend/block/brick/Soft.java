package backend.block.brick;

import backend.Manager;
import backend.block.Block;
import backend.block.item.Coin;
import backend.block.mario.MarioState;

public class Soft extends Brick {
    SoftType softType;

    public Soft(SoftType softType, int x, int y) {
        super(1, 1, x, y);
        this.softType = softType;
    }

    @Override
    protected String getImageName() {
        return "soft.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (Neighbor(Manager.getInstance().CurrentGame().mario, Direction.Down)) {
            Manager.getInstance().CurrentGame().score++;
            if (softType == SoftType.Coin) {
                Manager.getInstance().CurrentSection().Add(new Coin(X, Y + 1));
                softType = SoftType.Simple;
            } else if (Manager.getInstance().CurrentGame().mario.state != MarioState.mini)
                Manager.getInstance().CurrentSection().Del(this);
        }
        return false;
    }
}
