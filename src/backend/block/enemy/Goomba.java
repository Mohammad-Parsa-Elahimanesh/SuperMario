package backend.block.enemy;

import backend.Manager;
import backend.block.mario.SimpleMario;

public class Goomba extends Enemy {
    public Goomba(double x, double y) {
        super(0.8, 0.8, x, y);
        vx = new SimpleMario().getSpeed() * 0.6;
    }

    @Override
    public void Update() {
        double lastX = X;
        super.Update();
        if (Push(Direction.Down) > 0) {
            vx *= -1;
            X = lastX;
        }
    }

    int scoreWhenBeKilled() {
        return 1;
    }

    @Override
    protected String getImageName() {
        return "goomba.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (Neighbor(Manager.getInstance().CurrentMario(), Direction.Up)) {
            Die();
        } else if ((vx < 0 && D == Direction.Left) || (vx > 0 && D == Direction.Right))
            vx *= -1;
        return Neighbor(Manager.getInstance().CurrentMario(), D) && D != Direction.Up;
    }
}
