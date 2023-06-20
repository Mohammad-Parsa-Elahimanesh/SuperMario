package backend;

abstract public class Item extends Block {
    Item(double x, double y) {
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
}
