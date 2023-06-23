package backend;

abstract public class Item extends Block {
    Item(double x, double y) {
        super(1, 1, x, y);
    }

    static Item RandomItem(double x, double y) {
        if (Math.random() < 0.4) return new Coin(x, y);
        else if (Math.random() < 0.5) return new Mushroom(x, y);
        else if (Math.random() < 0.6)return new Flower(x, y);
        else return new Star(x, y);
    }

    @Override
    boolean Pushed(Direction D) {
        return true;
    }

    @Override
    boolean doesGravityAffects() {
        return true;
    }
}
