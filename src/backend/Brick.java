package backend;

public class Brick extends Block {
    @Override
    boolean Pushed(Direction D) {
        return false;
    }

    @Override
    void Intersect(Block block) {
    }

    @Override
    String getImageName() {
        return "Brick.png";
    }
}
