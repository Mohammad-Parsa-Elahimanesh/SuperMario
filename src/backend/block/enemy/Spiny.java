package backend.block.enemy;

import backend.Manager;
import backend.block.mario.SimpleMario;

public class Spiny extends Enemy {
    public Spiny(double x, double y) {
        super(1.5, 1.3, x, y);
    }

    private static double getNormalSpeed() {
        return new SimpleMario().getSpeed() * 0.5;
    }

    @Override
    int scoreWhenBeKilled() {
        return 3;
    }

    @Override
    public void update() {
        double lastX = X;
        super.update();
        if (Push(Direction.Down) > 0) {
            vx *= -1;
            X = lastX;
        }
        if (Manager.getInstance().currentMario().Y == Y && Distance(Manager.getInstance().currentMario()) < 4) {
            vx += (vx < 0 ? -1 : 1) * getNormalSpeed() * 0.1;
            if (Side(Manager.getInstance().currentMario(), Direction.Left))
                vx = -Math.abs(vx);
            else
                vx = Math.abs(vx);
        } else {
            vx = (vx * 4 + (vx < 0 ? -1 : 1) * getNormalSpeed()) / 5;
        }
    }

    @Override
    protected String getImageName() {
        return "spiny.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if ((vx < 0 && D == Direction.Left) || (vx > 0 && D == Direction.Right))
            vx *= -1;
        return Neighbor(Manager.getInstance().currentMario(), D);
    }
}
