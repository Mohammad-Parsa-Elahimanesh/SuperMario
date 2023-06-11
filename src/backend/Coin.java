package backend;

import java.awt.*;

public class Coin extends Block {
    @Override
    boolean Pushed(Direction D) {
        return true;
    }

    @Override
    void Intersect(Block block) {
    }

    @Override
    String getImageName() {
        return "Coin.png";
    }
}
