package backend;

public class Flower extends Item {
    Flower(double x, double y) {
        super(x, y);
    }

    @Override
    String getImageName() {
        return "flower.png";
    }
}
