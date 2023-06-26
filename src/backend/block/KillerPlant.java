package backend.block;

import backend.Manager;

public class KillerPlant extends Enemy {
    static int[] Height = {0, 1, 2, 3, 2, 1, 0};
    Pipe home;

    public KillerPlant(Pipe home) {
        super(home.W, 0, home.X, home.Y + home.H);
        this.home = home;
    }

    @Override
    protected String getImageName() {
        return "KillerPlant.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }

    @Override
    public void Update() {
        int H = (Manager.getInstance().CurrentSection().spentTimeMS / 1000) % Height.length;
        setShape(home.W, Height[H], home.X, home.Y + home.H);
        super.Update();
    }
}
