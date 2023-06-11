public class KillerPlant extends Block {
    static int[] Height = {0, 1, 2, 3, 2, 1, 0};
    Pipe home;
    int base;

    KillerPlant(Pipe home, int base) {
        this.base = base;
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
    void Intersect(Block block) {
    }

    @Override
    void Update() {
        int H = (Main.CurrentSection().spentTimeMS / 1000 + base) % Height.length;
        setShape(home.W, Height[H], home.X, home.Y + home.H);
    }
}
