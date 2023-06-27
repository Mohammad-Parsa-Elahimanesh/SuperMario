package backend.block.enemy;

import backend.Manager;
import backend.block.mario.SimpleMario;
import backend.gamePlay.Game;

import static java.lang.Math.max;

public class Koopa extends Enemy{

    transient double freeze = 0.0;

    public Koopa(double x, double y) {
        super(1.8, 1, x, y);
        vx = getNormalSpeed();
    }

    private static double getNormalSpeed(){return new SimpleMario().getSpeed() * 0.4;}

    @Override
    public void Update() {
        super.Update();
        if(vx < 0 && Push(Direction.Left) == 0)
            vx *= -1;
        if(vx > 0 && Push(Direction.Right) == 0)
            vx *= -1;
        if(Push(Direction.Down) > 0)
            vx *= -1;
        freeze = max(freeze- Game.delay,0);
        if(freeze == 0)
            vx = (vx < 0?-1:1)*getNormalSpeed();
        else
            vx = vx*0.9;

    }

    @Override
    protected String getImageName() {
        return "koopa.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (Neighbor(Manager.getInstance().CurrentGame().mario, Direction.Up)) {
            if (0 < freeze && freeze < 2.6) {
                Manager.getInstance().CurrentGame().score += 2;
                Manager.getInstance().CurrentSection().Del(this);
            } else {
                vx = 3 * getNormalSpeed();
                freeze = 3;
            }
        }
        else if((vx < 0 && D == Direction.Left) || (vx > 0 && D == Direction.Right))
            vx *= -1;
        return Neighbor(Manager.getInstance().CurrentGame().mario, D) && D != Direction.Up;
    }
}
