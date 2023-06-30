package backend.block.enemy;

import backend.Manager;
import backend.block.Block;

public abstract class Enemy extends Block {
    Enemy(double w, double h, double x, double y) {
        super(w, h, x, y);
    }

    @Override
    protected boolean doesGravityAffects() {
        return true;
    }

    abstract int scoreWhenBeKilled();

    public void Die() {
        Manager.getInstance().currentSection().coins += 3;
        Manager.getInstance().currentGame().score += scoreWhenBeKilled();
        Delete();
    }
}
