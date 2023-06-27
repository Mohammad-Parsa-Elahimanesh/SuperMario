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
    void Die() {
        Manager.getInstance().CurrentGame().score += scoreWhenBeKilled();
        Manager.getInstance().CurrentSection().Del(this);
    }
}
