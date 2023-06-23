package backend;

public class FireRing extends Block{
    FireRing(Block block) {
        super(block.W, block.H, block.X, block.Y);
    }

    @Override
    String getImageName() {
        return "FireRing.png";
    }

    @Override
    boolean Pushed(Direction D) {
        return false;
    }
}
