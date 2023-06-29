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
        Manager.getInstance().CurrentSection().coins += 3;
        Manager.getInstance().CurrentGame().score += scoreWhenBeKilled();
        Delete();
    }
}
