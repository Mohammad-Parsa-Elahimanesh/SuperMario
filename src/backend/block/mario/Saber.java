package backend.block.mario;

import backend.Manager;
import backend.block.Block;
import backend.block.enemy.Enemy;
import backend.block.item.Item;
import backend.gamePlay.Game;

public class Saber extends Block {
    double alive = 0.5;

    protected Saber(Mario mario) {
        super(2, 0.3, (mario.getVxDirectionNumber() == 1? mario.X+mario.W+0.2 : mario.X-2.3), mario.Y + 0.8);
        vx = 8.0 * mario.getVxDirectionNumber();
    }
    

    @Override
    public void Update() {
        alive -= Game.delay;
        if (alive < 0) {
            vx *= -1;
            alive = 8;
        }
        for (Block block : Manager.getInstance().CurrentSection().blocks)
            if (isIntersect(block)) {
                if (block instanceof Item)
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
        return "saber.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }
}
