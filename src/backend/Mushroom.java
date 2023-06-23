package backend;

public class Mushroom extends Item {
    double old = 0;

    Mushroom(double x, double y) {
        super(x, y);
    }

    @Override
    void Update() {
        if (old < 3) {
            old += Game.delay;
            if (old > 3)
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
        return "mushroom.png";
    }
}
