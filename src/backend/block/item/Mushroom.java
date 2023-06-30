package backend.block.item;

import backend.Manager;
import backend.block.Block;
import backend.block.mario.SimpleMario;
import backend.gamePlay.Game;

public class Mushroom extends Item {
    double old = 0;

    Mushroom(double x, double y) {
        super(x, y);
    }

    @Override
    public void update() {
        if (old < 3) {
            old += Game.delay;
            if (old > 3)
                vx = new SimpleMario().getSpeed() / 2.0;
        }
        super.update();
    }

    @Override
    protected boolean Pushed(Block.Direction D) {
        if ((vx < 0 && D == Direction.Left) || (vx > 0 && D == Direction.Right))
            vx *= -1;
        return Neighbor(Manager.getInstance().currentMario());
    }

    @Override
    protected String getImageName() {
        return "mushroom.png";
    }
}
