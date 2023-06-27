package backend.block.enemy;

import backend.Manager;
import backend.block.mario.SimpleMario;
import backend.gamePlay.Game;

public class Goomba extends Enemy{
    public Goomba(double x, double y) {
        super(1, 1, x, y);
        vx = new SimpleMario().getSpeed() * 0.5;
    }

    @Override
    public void Update() {
        super.Update();
        if(vx < 0 && Push(Direction.Left) == 0)
            vx *= -1;
        if(vx > 0 && Push(Direction.Right) == 0)
            vx *= -1;
        if(Push(Direction.Down) > 0)
            vx *= -1;
    }

    @Override
    protected String getImageName() {
        return "goomba.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        if (Neighbor(Manager.getInstance().CurrentGame().mario, Direction.Up)) {
            Manager.getInstance().CurrentGame().score ++;
            Manager.getInstance().CurrentSection().Del(this);
        }
        else if((vx < 0 && D == Direction.Left) || (vx > 0 && D == Direction.Right))
            vx *= -1;
        return Neighbor(Manager.getInstance().CurrentGame().mario, D) && D != Direction.Up;
    }
}
