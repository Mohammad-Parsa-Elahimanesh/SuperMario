package backend.block.mario;

import backend.Manager;
import backend.block.Block;
import backend.block.enemy.Enemy;
import backend.block.item.Item;
import backend.gamePlay.Game;

public class Fire extends Block {
    double alive = 1.1;

    protected Fire(Mario mario) {
        super(0.3, 0.3, mario.X, mario.Y + mario.H - 0.75);
        vx = mario.getShotSpeed() * mario.getDirection();
    }

    @Override
    public void Update() {
        alive -= Game.delay;
        if (alive < 0)
            Delete();
        for (Block block : Manager.getInstance().CurrentSection().blocks)
            if (isIntersect(block)) {
                if (block instanceof Mario || block instanceof Item)
                    continue;
                if (block instanceof Enemy)
                    ((Enemy) block).Die();
                Delete();
                break;
            }
        X += vx * Game.delay;
    }

    @Override
    protected String getImageName() {
        return "fire.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }
}
