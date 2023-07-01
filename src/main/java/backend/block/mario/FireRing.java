package backend.block.mario;

import backend.block.Block;

public class FireRing extends Block {
    FireRing(Block block) {
        super(block.W, block.H, block.X, block.Y);
    }

    @Override
    protected String getImageName() {
        return "FireRing.png";
    }

    @Override
    protected boolean pushed(Direction side) {
        return false;
    }
}
