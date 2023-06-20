package backend;

public class Coin extends Block {
    public Coin(double x, double y) {
        super(1, 1, x, y);
    }

    @Override
    boolean Pushed(Direction D) {
        return true;
    }

    @Override
    boolean doesGravityAffects() {
        return true;
    }

    @Override
    String getImageName() {
        return "Coin.png";
    }
}
