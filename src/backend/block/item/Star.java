package backend.block.item;

import backend.Manager;
import backend.block.Block;
import backend.block.mario.SimpleMario;
import backend.gamePlay.Game;

public class Star extends Item {
    double old = 0, onGround = 0;

    Star(double x, double y) {
        super(x, y);
    }

    @Override
    public void Update() {
        if (Push(Block.Direction.Down) == 0 && old > 3) onGround += Game.delay;
        else onGround = 0;
        if (onGround > 1)
            vy += new SimpleMario().getJumpSpeed() / 1.5;
        if (old < 3) {
            old += Game.delay;
            if (old >= 3)
                vx = new SimpleMario().getSpeed() / 2.0;
        }
        if ((vx > 0 && Push(Block.Direction.Right) == 0) || (vx < 0 && Push(Block.Direction.Left) == 0))
            vx = -vx;
        super.Update();
    }

    @Override
    protected boolean Pushed(Block.Direction D) {
        return Neighbor(Manager.getInstance().CurrentGame().mario);
    }

    @Override
    protected String getImageName() {
        return "Star.png";
    }
}
