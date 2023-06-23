package backend;

public class KillerPlant extends Enemy {
    static int[] Height = {0, 1, 2, 3, 2, 1, 0};
    Pipe home;

    KillerPlant(Pipe home) {
        super(home.W, 0, home.X, home.Y + home.H);
        this.home = home;
    }

    @Override
    String getImageName() {
        return "KillerPlant.png";
    }

    @Override
    boolean Pushed(Direction D) {
        return true;
    }

    @Override
    void Update() {
        int H = (Manager.getInstance().CurrentSection().spentTimeMS / 1000) % Height.length;
        setShape(home.W, Height[H], home.X, home.Y + home.H);
        super.Update();
    }
}
