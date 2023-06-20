package backend;

public class Coin extends Item {
    public Coin(double x, double y) {
        super(x, y);
    }

    @Override
    String getImageName() {
        return "Coin.png";
    }
}
