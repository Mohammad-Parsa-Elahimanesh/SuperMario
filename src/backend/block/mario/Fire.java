package backend.block.mario;

import backend.block.Block;

public class Fire extends Block {
    protected Fire(Mario mario) {
        super(0.3, 0.3, mario.X, mario.Y+ mario.H - 0.75);
        vx = new SimpleMario().getSpeed();
    }

    @Override
    protected String getImageName() {
        return "fire.png";
    }

    @Override
    protected boolean Pushed(Direction D) {
        return true;
    }
}
