package backend;

import java.util.Random;

abstract public class Item extends Block {
    Item(double x, double y) {
        super(1, 1, x, y);
    }

    @Override
    boolean Pushed(Direction D) {
        return true;
    }
    static Item RandomItem(double x, double y) {
        if(Math.random() < 0.5) return new Coin(x, y);
        else if(Math.random() < 0.7)return new Mushroom(x, y);
        else return new Flower(x, y);
    }
    @Override
    boolean doesGravityAffects() {
        return true;
    }
}
