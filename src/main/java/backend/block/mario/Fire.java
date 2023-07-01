package backend.block.mario;

import backend.Manager;
import backend.block.Block;
import backend.block.Checkpoint;
import backend.block.enemy.Enemy;
import backend.block.item.Item;
import backend.gamePlay.Section;

public class Fire extends Block {
    double alive = 1.1;

    protected Fire(Mario mario) {
        super(0.3, 0.3, mario.X, mario.Y + mario.H - 0.75);
        vx = mario.getShotSpeed() * mario.getDirection();
    }

    @Override
    public void update() {
        alive -= Section.delay;
        if (alive < 0)
            remove();
        for (Block block : Manager.getInstance().currentSection().blocks)
            if (isIntersect(block)) {
                if (block instanceof Mario || block instanceof Item || block instanceof Checkpoint)
                    continue;
                if (block instanceof Enemy)
                    ((Enemy) block).Die();
                remove();
                break;
            }
        X += vx * Section.delay;
    }

    @Override
    protected String getImageName() {
        return "fire.png";
    }

    @Override
    protected boolean pushed(Direction side) {
        return true;
    }
}
