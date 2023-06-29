package backend.block.enemy;

import backend.Manager;
import backend.block.mario.SimpleMario;
import backend.gamePlay.Game;

import static java.lang.Math.max;

public class Koopa extends Enemy {

    transient double freeze = 0.0;


    public Koopa(double x, double y) {
        super(1.8, 1, x, y);
        vx = getNormalSpeed();
    }

    private static double getNormalSpeed() {
        return new SimpleMario().getSpeed() * 0.4;
    }

    int scoreWhenBeKilled() {
        return 2;
    }

    @Override
    public void Update() {
        double lastX = X;
        super.Update();
        if (Push(Direction.Down) > 0) {
            vx *= -1;
            X = lastX;
        }
        freeze = max(freeze - Game.delay, 0);
        if (freeze == 0)
            vx = (vx < 0 ? -1 : 1) * getNormalSpeed();
        else
            vx = vx * 0.9;

    }

    @Override
    protected String getImageName() {
        return freeze>0?"koopaShell.png":"koopa.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (Neighbor(Manager.getInstance().CurrentMario(), Direction.Up)) {
            if (0 < freeze && freeze < 2.6) {
                Die();
            } else {
                vx = 3 * getNormalSpeed();
                freeze = 3;
            }
        } else if ((vx < 0 && D == Direction.Left) || (vx > 0 && D == Direction.Right))
            vx *= -1;
        return Neighbor(Manager.getInstance().CurrentMario(), D) && D != Direction.Up;
    }
}
