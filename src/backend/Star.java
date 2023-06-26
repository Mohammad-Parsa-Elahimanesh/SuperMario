package backend;

public class Star extends Item{
    double old = 0, onGround = 0;
    Star(double x, double y) {
        super(x, y);
    }

    @Override
    void Update() {
        if(Push(Direction.Down) == 0 && old > 3) onGround += Game.delay;
        else onGround = 0;
        if(onGround > 1)
            vy += new SimpleMario().getJumpSpeed() / 1.5;
        if (old < 3) {
            old += Game.delay;
            if (old >= 3)
                vx = new SimpleMario().getSpeed() / 2.0;
        }
        if ((vx > 0 && Push(Direction.Right) == 0) || (vx < 0 && Push(Direction.Left) == 0))
            vx = -vx;
        super.Update();
    }

    @Override
    boolean Pushed(Direction D) {
        return Neighbor(Manager.getInstance().CurrentGame().mario);
    }

    @Override
    String getImageName() {
        return "Star.png";
    }
}
