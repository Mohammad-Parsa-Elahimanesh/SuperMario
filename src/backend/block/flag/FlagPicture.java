package backend.block.flag;

import backend.block.Block;

import java.awt.event.WindowListener;

public class FlagPicture extends Block {
    FlagPicture(double x, double y) {
        super(1.5, 1, x, y);
    }
    void goUp() {
        Y += 0.2;
    }
    @Override
    protected String getImageName() {
        return "flag.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }
}